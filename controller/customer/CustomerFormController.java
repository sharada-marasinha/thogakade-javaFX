package controller.customer;

import db.DBConnection;
import dto.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {
    public TextField txtId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;

    public TableView<Customer> fxTable;
    public TableColumn<Customer, String> colId;
    public TableColumn<Customer, String> colName;
    public TableColumn<Customer, String> colAddress;
    public TableColumn<Customer, Double> colSalary;
    private Alert wrongAlert = new Alert(Alert.AlertType.ERROR, "Something went wrong !");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactory();
        loadTable();
        fxTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setTableValuesToTxt(newValue);
            }
        });
    }
    public void btnAddAction() {
        if (new Alert(Alert.AlertType.CONFIRMATION, "Do you want to add this customer?", ButtonType.YES, ButtonType.NO).showAndWait().isPresent()) {
            if (CustomerController.getInstance().addCustomer(
                    new Customer(txtId.getText(),
                            txtName.getText(),
                            txtAddress.getText(),
                            Double.parseDouble(txtSalary.getText())
                    ))) {
                loadTable();
                clear();
                new Alert(Alert.AlertType.INFORMATION, "Customer Added !").show();
            } else {
                wrongAlert.show();
            }
        }
    }
    public void btnUpdateAction() {
        if (CustomerController.getInstance().updateCustomer(
                new Customer(
                        txtId.getText(),
                        txtName.getText(),
                        txtAddress.getText(),
                        Double.parseDouble(txtSalary.getText())))) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Updated !").show();
            loadTable();
            clear();
        } else {
            wrongAlert.show();
        }
    }
    public void txtSearch(ActionEvent actionEvent) {
        btnSearchAction(actionEvent);
    }

    public void btnSearchAction(ActionEvent actionEvent) {
        Customer customer = CustomerController.getInstance().searchCustomer(txtId.getText());
        if (customer == null) {
            new Alert(Alert.AlertType.ERROR, "Customer Not Found !").show();
        } else {
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtSalary.setText(String.valueOf(customer.getSalary()));
        }
    }
    public void btnDeleteAction() {
        if (new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Delete this customer?", ButtonType.YES, ButtonType.NO).showAndWait().isPresent()) {
            if (CustomerController.getInstance().deleteCustomer(txtId.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Deleted !").show();
                loadTable();
                clear();
            } else {
                wrongAlert.show();
            }
        }
    }
    public void btnClearOnAction() {
        clear();
    }
    private void clear() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
    }
    private void loadTable() {
        fxTable.setItems(CustomerController.getInstance().getAllCustomer());
    }
    private void setTableValuesToTxt(Customer newValue) {
        txtId.setText(newValue.getId());
        txtName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());
        txtSalary.setText(String.valueOf(newValue.getSalary()));
    }
    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    public List<String> getCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(rst.getString(1));
        }
        return ids;
    }
}


