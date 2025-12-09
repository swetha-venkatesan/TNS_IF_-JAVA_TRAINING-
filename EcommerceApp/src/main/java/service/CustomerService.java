package service;

import model.Customer;
import java.util.*;

public class CustomerService {

    private Map<Integer, Customer> customers = new LinkedHashMap<>();

    public void createCustomer(Customer customer) {
        customers.put(customer.getUserId(), customer);
    }

    public Customer getCustomer(int id) {
        return customers.get(id);
    }

    public Collection<Customer> getCustomers() {
        return customers.values();
    }
}
