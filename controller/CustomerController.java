package controller;

import db.DBConnection;
import dto.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CustomerModel;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;

    public TableView<Customer> fxTable;
    public TableColumn<Customer, String> colId;
    public TableColumn<Customer, String> colName;
    public TableColumn<Customer, String> colAddress;
    public TableColumn<Customer, Double> colSalary;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String SQL = "Select * From Customer";
        ObservableList<Customer> list = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery(SQL);

            while (rst.next()) {
                Customer customer = new Customer(rst.getString("id"), rst.getString("name"), rst.getString("address"), rst.getDouble("salary"));
                list.add(customer);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        colId.setCellValueFactory(new PropertyValueFactory<Customer, String>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<Customer, Double>("salary"));
        fxTable.setItems(list);
        System.out.println("Hello");

    }

    public void btnCancelAction(ActionEvent actionEvent) {

    }

    public void btnAddAction(ActionEvent actionEvent) {
        System.out.println("Hello");
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        double salary = Double.parseDouble(txtSalary.getText());
        Customer customer = new Customer(id, name, address, salary);
        try {
            boolean isAdd = CustomerModel.addCustomer(customer);
            if (isAdd) {
                System.out.println("Add Success");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void btnSearchAction(ActionEvent actionEvent) {
        Customer customer = null;
        try {
            customer = CustomerModel.searchCustomer(txtId.getText());
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtSalary.setText(String.valueOf(customer.getSalary()));
            System.out.println(customer!=null?"Sucsess":"Fald");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void btnUpdateAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        double salary = Double.parseDouble(txtSalary.getText());
        Customer customer = new Customer(id, name, address, salary);

        try {
            boolean isUpdated = CustomerModel.updateCustomer(customer);
            System.out.println(isUpdated ? "Sucsess" : "Faild");

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void btnDeleteAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = txtId.getText();
        boolean isDeleted = CustomerModel.deleteCustomer(id);

    }


    public void btnViewAction(ActionEvent actionEvent) {


    }
}
