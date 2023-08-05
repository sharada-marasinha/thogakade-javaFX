package controller.item;

import controller.item.ItemController;
import db.DBConnection;
import dto.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {


    ItemController itemController;
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
        itemController = new ItemController();
        setCellValueFactory();
        loadTable();
        itemTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setTableValuesToTxt(newValue);
            }
        });
    }

    @FXML
    void btnAddAction(ActionEvent event) {
        Item item = new Item(txtCode.getText(), txtDesc.getText(), Double.parseDouble(txtUnitPrice.getText()), Integer.valueOf(txtQty.getText()));

        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to add this customer?", ButtonType.YES, ButtonType.NO).showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            boolean isAdd = itemController.addItem(item);
            if (isAdd) {
                loadTable();
                clearTxt();
                new Alert(Alert.AlertType.INFORMATION, "Item Added !").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong !").show();
            }
        }


    }

    @FXML
    void btnUpdateAction(ActionEvent event) {
        Item item = new Item(txtCode.getText(), txtDesc.getText(), Double.parseDouble(txtUnitPrice.getText()), Integer.valueOf(txtQty.getText()));
        boolean isUpdated = itemController.updateItem(item);
        if (isUpdated) {
            loadTable();
            clearTxt();
            new Alert(Alert.AlertType.INFORMATION, "Item Updated !").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Something went wrong !").show();
        }
    }

    @FXML
    void btnSearchAction(ActionEvent event) {
        Item item = null;
        item = itemController.searchItem(txtCode.getText());
        if (item == null) {
            new Alert(Alert.AlertType.ERROR, "Item Not Found !").show();
            return;
        }
        txtDesc.setText(item.getDescription());
        txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        txtQty.setText(String.valueOf(item.getQtyOnHand()));


    }

    @FXML
    void txtSearch(ActionEvent event) {
        btnSearchAction(event);
    }

    @FXML
    void btnDeleteAction(ActionEvent event) {
        Optional<ButtonType> buttonType = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to Delete this customer?", ButtonType.YES, ButtonType.NO).showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            boolean isDeleted = itemController.deleteItem(txtCode.getText());
            if (isDeleted) {
                loadTable();
                clearTxt();
                new Alert(Alert.AlertType.INFORMATION, "Item Deleted !").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong !").show();
            }
        }
    }

    private void loadTable() {
        ObservableList<Item> allItem = itemController.getAllItem();
        itemTable.setItems(allItem);
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

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearTxt();
    }

    public void clearTxt() {
        txtCode.clear();
        txtDesc.clear();
        txtUnitPrice.clear();
        txtQty.clear();
    }

    public List<String> getItemCodes() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item").executeQuery();
        List<String> codes = new ArrayList<>();
        while (rst.next()) {
            codes.add(rst.getString(1));
        }
        return codes;
    }


}
