package tk.tinkerit.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import tk.tinkerit.demo.CustomerProtos;
import tk.tinkerit.demo.model.Customer;
import tk.tinkerit.demo.model.CustomerNotFoundException;
import tk.tinkerit.demo.repository.CustomerRepository;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping(value="/v1/proto/customers")
public class CustomerController {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostConstruct
    public void setupSeedCustomers() {
        System.out.println("About to setup seed customers...");
        for (Customer customer : Arrays.asList(
            new Customer("Justin", "Zhang"),
            new Customer("Jessie", "Song"),
            new Customer("Sunny", "Zhang")
        )) {
            customerRepository.save(customer);
            customerRepository.flush();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerProtos.Customer> get(@PathVariable Long id) {
        return this.customerRepository.findById(id)
            .map(this::fromEntityToProtoBuf)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> {
                System.out.printf("Customer %d NotFound\n", id);
                return new CustomerNotFoundException(id);
            })
            ;
    }

    @GetMapping
    public ResponseEntity<CustomerProtos.Customers> all() {
        List<Customer> all = this.customerRepository.findAll();
        CustomerProtos.Customers customers = this.fromCollectionToProtobuf(all);
        return ResponseEntity.ok(customers);
    }

    @PostMapping
    public ResponseEntity<CustomerProtos.Customer> post(
        @RequestBody CustomerProtos.Customer c) {
        Customer customer = this.customerRepository.save(
            new Customer(c.getFirstName(), c.getLastName())
        );
        URI uri = MvcUriComponentsBuilder.fromController(getClass())
            .path("/{id}")
            .buildAndExpand(customer.getId())
            .toUri();
        return ResponseEntity.created(uri).body(this.fromEntityToProtoBuf(customer));
    }

    @PutMapping
    public ResponseEntity<CustomerProtos.Customer> put(
        @PathVariable Long id, @RequestBody CustomerProtos.Customer c) {
        return this.customerRepository
            .findById(id)
            .map(this::refresh)
            .map(d -> {
                URI selfLink = URI.create(fromCurrentRequest().toUriString());
                return ResponseEntity.created(selfLink).body(d);
            })
            .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    private CustomerProtos.Customer refresh(Customer existing) {
        Customer customer = this.customerRepository.save(
            new Customer(existing.getFirstName(), existing.getLastName())
        );
        return fromEntityToProtoBuf(customer);
    }

    private CustomerProtos.Customers fromCollectionToProtobuf(List<Customer> all) {
        return CustomerProtos.Customers
            .newBuilder()
            .addAllCustomer(
                all.stream().map(this::fromEntityToProtoBuf).collect(Collectors.toList())
            ).build();
    }

    private CustomerProtos.Customer fromEntityToProtoBuf(Customer customer) {
        return fromEntityToProtoBuf(
            customer.getId(), customer.getFirstName(), customer.getLastName());
    }

    private CustomerProtos.Customer fromEntityToProtoBuf(Long id, String firstName, String lastName) {
        CustomerProtos.Customer.Builder builder = CustomerProtos.Customer.newBuilder();
        if (id != null && id > 0) {
            builder.setId(id);
        }
        return builder
            .setFirstName(firstName)
            .setLastName(lastName)
            .build();
    }
}
