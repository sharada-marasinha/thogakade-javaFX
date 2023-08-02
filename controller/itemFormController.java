package controller;

import db.DBConnection;
import dto.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ItemModel;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class itemFormController implements Initializable {


    @FXML
    private TableColumn<Item, String> colCode;

    @FXML
    private TableColumn<Item, String> colDesc;

    @FXML
    private TableColumn<Item, Integer> colQty;

    @FXML
    private TableColumn<Item, Double> colUnitPrice;

    @FXML
    private TableView<Item> itemTable;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtUnitPrice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactory();
        loadTable();
        itemTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setTableValuesToTxt(newValue);
        });
    }

    @FXML
    void btnAddAction(ActionEvent event) {
        Item item = new Item(txtCode.getText(),txtDesc.getText(),Double.parseDouble(txtUnitPrice.getText()),Integer.valueOf(txtQty.getText()));
        try {
            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to add this customer?", ButtonType.YES, ButtonType.NO).showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                    boolean isAdd = ItemModel.addItem(item);
                    if (isAdd) {
                        new Alert(Alert.AlertType.INFORMATION, "Item Added !").show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Something went wrong !").show();
                    }
                }
            } catch (SQLException | ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                e.printStackTrace();
            }

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtCode.setText("");
        txtDesc.setText("");
        txtUnitPrice.setText("");
        txtQty.setText("");
    }

    @FXML
    void btnDeleteAction(ActionEvent event) {
        try {
            boolean isDeleted = ItemModel.deleteItem(txtCode.getText());
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Item Deleted !").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong !").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSearchAction(ActionEvent event) {
        Item item = null;
        try {
            item = ItemModel.searchItem(txtCode.getText());
            txtDesc.setText(item.getDescription());
            txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            txtQty.setText(String.valueOf(item.getQtyOnHand()));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(item == null){
            new Alert(Alert.AlertType.ERROR, "Item Not Found !").show();
        }

    }

    @FXML
    void btnUpdateAction(ActionEvent event) {

    }

    @FXML
    void txtSearch(ActionEvent event) {
        btnSearchAction(event);
    }

    private void loadTable() {
        String SQL = "Select * From item";
        ObservableList<Item> list = FXCollections.observableArrayList();
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery(SQL);

            while (rst.next()) {
                Item item = new Item(rst.getString(1), rst.getString(2), rst.getDouble(3), rst.getInt(4));
                list.add(item);
            }
            itemTable.setItems(list);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void setTableValuesToTxt(Item newValue) {
        txtCode.setText(newValue.getCode());
        txtDesc.setText(newValue.getDescription());
        txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
        txtQty.setText(String.valueOf(newValue.getQtyOnHand()));
    }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
    }


}
