package controller.order;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import view.tm.OrderDetailTm;
import view.tm.OrderTm;

import java.net.URL;
import java.util.*;

public class OrderDetailFormController implements Initializable {

    public TableColumn button;
    @FXML
    private TableColumn<OrderTm,String> colCustomerId;

    @FXML
    private TableColumn<OrderTm,String> colDate;

    @FXML
    private TableColumn<OrderTm,String> colId;

    @FXML
    private TableView<OrderTm> tblOrders;

    @FXML
    private TableColumn<OrderDetailTm,String> colItemCode;

    @FXML
    private TableColumn<OrderDetailTm,String> colOrderId;

    @FXML
    private TableColumn<OrderDetailTm,Integer> colQty;

    @FXML
    private TableColumn<OrderDetailTm,Double> colUnitPrice;

    @FXML
    private TableView<OrderDetailTm> tblOrderDetails;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        button.setCellValueFactory(new PropertyValueFactory<>("button"));

        loadTable();
        /*-----------------------------------------------------------------------------------*/
        tblOrders.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            searchOrderDetail(newValue);
        });
        /*-----------------------------------------------------------------------------------*/
    }

    private void searchOrderDetail(OrderTm newValue) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("oId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        ObservableList<OrderDetailTm> orderDetailTms = OrderDetailController.searchOrderDetail(newValue.getId());
        tblOrderDetails.setItems(orderDetailTms);

    }

    public void loadTable(){
        ObservableList<OrderTm> list = OrderDetailController.loadTableDataOrders();
        tblOrders.setItems(list);
    }


}
