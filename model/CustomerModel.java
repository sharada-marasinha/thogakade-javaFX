package model;

import db.DBConnection;
import dto.Customer;

import java.sql.*;

public class CustomerModel {
    public static boolean addCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        String SQL = "Insert into Customer Values(?,?,?,?)";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement(SQL);
        psTm.setObject(1, customer.getId());
        psTm.setObject(2, customer.getName());
        psTm.setObject(3, customer.getAddress());
        psTm.setObject(4, customer.getSalary());
        int i = psTm.executeUpdate();
        return i > 0 ? true : false;


    }
    public static Customer searchCustomer(String id) throws SQLException, ClassNotFoundException {
        String SQL = "Select * From Customer where id='" + id + "'";

            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet resultSet = stm.executeQuery(SQL);
            if (resultSet.next()) {
                Customer customer = new Customer(id, resultSet.getString("name"), resultSet.getString("address"), resultSet.getDouble("salary"));
                return customer;
            } else {
                System.out.println("Faild");
            }
        return null;
    }

    public static boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        int i = DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete From Customer where id='" + id + "'");
        return i > 0 ? true : false;
    }
    public static boolean updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        String SQL = "Update Customer set name=?, address=?, salary=? where id=?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement(SQL);
        psTm.setObject(1,customer.getName());
        psTm.setObject(2,customer.getAddress());
        psTm.setObject(3,customer.getSalary());
        psTm.setObject(4,customer.getId());
        int i = psTm.executeUpdate();
        return i>0?true:false;
    }
}
