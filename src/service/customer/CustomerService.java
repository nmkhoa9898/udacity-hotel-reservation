package service.customer;

import model.customer.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private static final CustomerService INSTANCE = new CustomerService();

    private final Map<String, Customer> customers = new HashMap<>();

    private CustomerService() {}

    public static CustomerService getCustomerServiceInstance() {
        return INSTANCE;
    }

    public void addCustomer(String email, String firstName, String lastName) {
        if (isCustomerExisted(email)) {
            System.out.println("Customer existed, please type in a different email!");
            return;
        }
        customers.put(email, new Customer(firstName, lastName, email));
        System.out.println("Customer added successfully!");
    }

    public boolean isCustomerExisted(String email) {
        return customers.containsKey(email);
    };

    public Customer getCustomer( String customerEmail) {
        return customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
