package tk.tinkerit.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import tk.tinkerit.demo.model.Customer;
import tk.tinkerit.demo.repository.CustomerRepository;

import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CustomerTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private CustomerRepository customerRepository;

	@BeforeEach
    public void setUp() {
		for (Customer customer : Arrays.asList(
			new Customer("Justin", "Zhang"),
			new Customer("Jessie", "Song"),
			new Customer("Sunny", "Zhang")
		)) {
			customerRepository.save(customer);
		}
	}

	@Test
	public void contextLoads() {
    }
}
