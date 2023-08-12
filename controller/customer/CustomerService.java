package controller.customer;

import dto.Customer;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface CustomerService {
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    Customer searchCustomer(String customerId) throws SQLException, ClassNotFoundException;
    boolean deleteCustomer(String customerId);
    ObservableList<Customer> getAllCustomer();
}
