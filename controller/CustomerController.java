package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerController {
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;

    public void btnCancelAction(ActionEvent actionEvent) {
    }

    public void btnAddAction(ActionEvent actionEvent) {
        System.out.println("Hello");
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        double salary= Double.parseDouble(txtSalary.getText());
        Customer customer = new Customer(id,name,address,salary);

        String SQL = "Insert into Customer Values(?,?,?,?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,customer.getId());
            psTm.setObject(2,customer.getName());
            psTm.setObject(3,customer.getAddress());
            psTm.setObject(4,customer.getSalary());
            int i = psTm.executeUpdate();
            if(i>0){
                System.out.println("Add sucsss");
            }else{
                System.out.println("fall");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
