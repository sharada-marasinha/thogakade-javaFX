package controller.customer;

import dto.Customer;
import javafx.collections.ObservableList;

public interface CustomerService {
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    Customer searchCustomer(String customerId);
    boolean deleteCustomer(String customerId);
    ObservableList<Customer> getAllCustomer();
}
