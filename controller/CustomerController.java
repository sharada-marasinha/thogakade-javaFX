package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;

    public TableView<Customer> fxTable;
    public TableColumn<Customer,String> colId;
    public TableColumn<Customer,String> colName;
    public TableColumn<Customer,String> colAddress;
    public TableColumn<Customer,Double> colSalary;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String SQL="Select * From Customer";
        ObservableList<Customer> list = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery(SQL);

            while(rst.next()){
                Customer customer=new Customer(rst.getString("id"), rst.getString("name"), rst.getString("address"),rst.getDouble("salary"));
                list.add(customer);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
       colId.setCellValueFactory(new PropertyValueFactory<Customer,String>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Customer,String>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<Customer,String>("address"));
       colSalary.setCellValueFactory(new PropertyValueFactory<Customer,Double>("salary"));
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

    public void btnSearchAction(ActionEvent actionEvent) {
        String SQL="Select * From Customer where id='"+txtId.getText()+"'";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet resultSet = stm.executeQuery(SQL);
        if(resultSet.next()) {
            Customer customer = new Customer(txtId.getText(), resultSet.getString("name"), resultSet.getString("address"), resultSet.getDouble("salary"));
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtSalary.setText(String.valueOf(customer.getSalary()));
        }else{
            System.out.println("Faild");
        }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnUpdateAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String SQL = "Update Customer set name=?, address=?, salary=? where id=?";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement(SQL);
        psTm.setObject(1,txtName.getText());
        psTm.setObject(2,txtAddress.getText());
        psTm.setObject(3,txtSalary.getText());
        psTm.setObject(4,txtId.getText());
        int i = psTm.executeUpdate();

        System.out.println(i>0?"Susess":"Fald");


    }

    public void btnDeleteAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        int i = DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete From Customer where id='" + txtId.getText() + "'");
        System.out.println(i>0?"Susess":"Fald");
    }


    public void btnViewAction(ActionEvent actionEvent) {


    }
}
