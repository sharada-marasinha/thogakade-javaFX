package controller.customer;

import db.DBConnection;
import dto.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class CustomerController implements CustomerService {
    private static CustomerController instance;
    private CustomerController(){}
    public static CustomerController getInstance(){
        if(null == instance){
            instance=new CustomerController();
        }
        return instance;
    }

    @Override
    public boolean addCustomer(Customer customer) {
        try {
            String SQL = "Insert into Customer Values(?,?,?,?)";
            PreparedStatement psTm =DBConnection.getInstance().getConnection().prepareStatement(SQL);
            psTm.setObject(1, customer.getId());
            psTm.setObject(2, customer.getName());
            psTm.setObject(3, customer.getAddress());
            psTm.setObject(4, customer.getSalary());
            return psTm.executeUpdate()>0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        try {
            String SQL = "Update Customer set name=?, address=?, salary=? where id=?";
            PreparedStatement psTm = DBConnection.getInstance().getConnection().prepareStatement(SQL);
            psTm.setObject(1, customer.getName());
            psTm.setObject(2, customer.getAddress());
            psTm.setObject(3, customer.getSalary());
            psTm.setObject(4, customer.getId());
            return psTm.executeUpdate()>0;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return false;
    }
    @Override
    public Customer searchCustomer(String customerId)  {
        try {
            String SQL = "Select * From Customer where id='" + customerId + "'";
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            if (resultSet.next()) {
                return new Customer(customerId, resultSet.getString("name"), resultSet.getString("address"), resultSet.getDouble("salary"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean deleteCustomer(String customerId) {
        try {
           return DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete From Customer where id='" + customerId + "'")>0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return false;
    }
    @Override
    public ObservableList<Customer> getAllCustomer() {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        try {
            String SQL = "Select * From Customer";
            ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery(SQL);
            while (rst.next()) {

                list.add(new Customer(rst.getString("id"), rst.getString("name"), rst.getString("address"), rst.getDouble("salary")));
            }
            return list;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
