package controller;

import dto.Customer;
import javafx.collections.ObservableList;

public interface CustomerService {
    boolean customerAdd(Customer customer);
    boolean customerUpdate(Customer customer);
    Customer customerSearch(String customerId);
    boolean deleteCustomer(String customerId);
    ObservableList<Customer> getAllCustomer();
}
