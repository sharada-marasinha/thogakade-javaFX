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

/*
    Customer customer = new Customer(
            txtId.getText(),
            txtName.getText(),
            txtAddress.getText(),
            Double.parseDouble(txtSalary.getText()
            ));
        try {
                Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to add this customer?", ButtonType.YES, ButtonType.NO).showAndWait();

        if (buttonType.get() == ButtonType.YES){
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?,?)");
        pstm.setObject(1,customer.getId());
        pstm.setObject(2,customer.getName());
        pstm.setObject(3,customer.getAddress());
        pstm.setObject(4,customer.getSalary());

        if (pstm.executeUpdate()>0){
        new Alert(Alert.AlertType.INFORMATION,"Customer Added !").show();
        }else{
        new Alert(Alert.AlertType.ERROR,"Something went wrong !").show();
        }
        }

        } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
        new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }*/
