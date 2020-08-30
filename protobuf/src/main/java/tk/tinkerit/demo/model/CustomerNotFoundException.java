package tk.tinkerit.demo.model;

public class CustomerNotFoundException extends RuntimeException {
    private Long customerId;
    public CustomerNotFoundException(Long id) {
        super();
        this.customerId = id;
    }

    @Override
    public String toString() {
        return "CustomerNotFoundException{" +
            "customerId=" + customerId +
            '}';
    }
}
