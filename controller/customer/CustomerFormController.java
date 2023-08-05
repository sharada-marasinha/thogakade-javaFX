package controller.customer;

import db.DBConnection;
import dto.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    CustomerController customerController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerController = new CustomerController();
        setCellValueFactory();
        loadTable();
        fxTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setTableValuesToTxt(newValue);
            }
        });
    }

    public void btnAddAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        double salary = Double.parseDouble(txtSalary.getText());
        Customer customer = new Customer(id, name, address, salary);

        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to add this customer?", ButtonType.YES, ButtonType.NO).showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            boolean isAdd = customerController.addCustomer(customer);
            // setCellValueFactory();
            loadTable();
            clear();
            if (isAdd) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Added !").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong !").show();
            }
        }

    }

    public void btnUpdateAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        double salary = Double.parseDouble(txtSalary.getText());
        Customer customer = new Customer(id, name, address, salary);
        boolean isUpdated = customerController.updateCustomer(customer);
        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Updated !").show();
            loadTable();
            clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something went wrong !").show();
        }

    }

    public void btnSearchAction(ActionEvent actionEvent) {
            Customer customer = customerController.searchCustomer(txtId.getText());
            if (customer == null) {
                new Alert(Alert.AlertType.ERROR, "Customer Not Found !").show();
            }
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtSalary.setText(String.valueOf(customer.getSalary()));
    }

    public void txtSearch(ActionEvent actionEvent) {
        btnSearchAction(actionEvent);
    }

    public void btnDeleteAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        boolean isDeleted;
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Delete this customer?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                isDeleted = customerController.deleteCustomer(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer Deleted !").show();
                    loadTable();
                    clear();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong !").show();
                }
            }

    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clear();
    }

    public void clear() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtSalary.clear();
    }


    private void loadTable() {
        ObservableList<Customer> allCustomer = customerController.getAllCustomer();
        fxTable.setItems(allCustomer);
    }

    public void setTableValuesToTxt(Customer newValue) {
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
            ids.add(
                    rst.getString(1)
            );

        }
        return ids;
    }


}


