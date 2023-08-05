package controller;

import db.DBConnection;
import dto.Customer;
import dto.Item;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.CustomerModel;
import dto.OrderDetails;
import model.ItemModel;
import dto.Order;
import view.tm.CartTm;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {
    public Label lblDate;
    public Label lblTime;
    public ComboBox<String> cmbCustomerIds;
    public ComboBox<String> cmdItemCode;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtSalary;
    public TextField txtDesc;
    public TextField txtQty;
    public TextField txtUnitPrice;
    public TableView<CartTm> tblCart;
    public TableColumn colCode;
    public TableColumn colDesc;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public TextField txtQtyOnNow;
    public Label lblTtl;
    public Label lblOrderId;

    int cartSelectedRowForRemove = -1;
    ObservableList<CartTm> oblist = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        loadDateAndTime();
        setOrderId();
        try {
            loadCustomerIds();
            loadItemCodes();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblCart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });
        /*===============================================*/
        cmbCustomerIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setCustomerData(newValue);
        });

        cmdItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setItemData(newValue);
        });
        /*===============================================*/
    }

    private void setItemData(String itemCode) {
        Item item = null;
        try {
            item = ItemModel.searchItem(itemCode);
            txtDesc.setText(item.getDescription());
            txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            txtQty.setText(String.valueOf(item.getQtyOnHand()));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (item == null) {
            new Alert(Alert.AlertType.ERROR, "Item Not Found !").show();
        }
    }

    private void setCustomerData(String customerId) {
        try {
            Customer customer = CustomerModel.searchCustomer(customerId);
            if (customer == null) {
                new Alert(Alert.AlertType.ERROR, "Customer Not Found !").show();
            }
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtSalary.setText(String.valueOf(customer.getSalary()));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> customerIds = new CustomerController().getCustomerIds();
        cmbCustomerIds.getItems().addAll(customerIds);
    }

    private void loadItemCodes() throws SQLException, ClassNotFoundException {
        List<String> itemCodes = new ItemFormController().getItemCodes();
        cmdItemCode.getItems().addAll(itemCodes);
    }
    Date date;
    private void loadDateAndTime() {
        date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime cTime = LocalTime.now();
            lblTime.setText(
                    cTime.getHour() + ":" + cTime.getMinute() + ":" + cTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {

        String description = txtDesc.getText();
        int qtyOnHand = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyForCustomer = Integer.parseInt(txtQtyOnNow.getText());
        double total = qtyForCustomer * unitPrice;
        if (qtyOnHand < qtyForCustomer) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }

        CartTm tm = new CartTm(cmdItemCode.getValue(), description, qtyForCustomer, unitPrice, total);
        int rowNumber = isExist(tm);
        if (rowNumber == -1) {
            oblist.add(tm);
        } else {
            CartTm tempTm = oblist.get(rowNumber);
            CartTm newTm = new CartTm(tempTm.getCode(),
                    tempTm.getDescription(),
                    tempTm.getQty() + qtyForCustomer,
                    unitPrice,
                    total + tempTm.getTotal()
            );
            if (qtyOnHand < tempTm.getQty()) {
                new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
                return;
            }
            oblist.remove(rowNumber);
            oblist.add(newTm);
        }

        tblCart.setItems(oblist);
        calculateCost();
    }

    private int isExist(CartTm tm) {
        for (int i = 0; i < oblist.size(); i++) {
            if (tm.getCode().equals(oblist.get(i).getCode())) {
                return i;
            }
        }
        return -1;
    }

    void calculateCost() {
        double ttl = 0;
        for (CartTm tm : oblist) {
            ttl += tm.getTotal();
        }
        lblTtl.setText(ttl + " /=");
    }

    public void clearOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a Row").show();
        } else {
            oblist.remove(cartSelectedRowForRemove);
            calculateCost();
            tblCart.refresh();
        }
    }

    public void btnPlaceOrder(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String oId=lblOrderId.getText();
        String customerId=cmbCustomerIds.getValue();
        String orderDate=lblDate.getText();

        ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<>();
        double ttl = 0;
        for (CartTm tempTm : oblist) {
            ttl+=tempTm.getTotal();
            String itemCode=tempTm.getCode();
            int orderQty=tempTm.getQty();
            double unitPrice= tempTm.getUnitPrice();
            OrderDetails orderDetails = new OrderDetails(oId,itemCode,orderQty,unitPrice);
            orderDetailsArrayList.add(orderDetails);
        }
        Order order = new Order(oId,orderDate,customerId,orderDetailsArrayList);

        System.out.println(orderDetailsArrayList);
        System.out.println(order);
    if (new OrderController().placeOrder(order)){
        new Alert(Alert.AlertType.CONFIRMATION,"Order Success !").show();
    }else{
        new Alert(Alert.AlertType.ERROR,"Try Again").show();
    }

    }
    private void setOrderId(){
        try{
            String lastOrderId= getLastOrderId();
            if(lastOrderId!=null){
                lastOrderId = lastOrderId.split("[A-Z]")[1]; // D001==> 001
                System.out.println(lastOrderId);
                lastOrderId = String.format("D%03d",(Integer.parseInt(lastOrderId)+1));
                lblOrderId.setText(lastOrderId);
            }else{
                lblOrderId.setText("D001");
            }
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public String getLastOrderId() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT id FROM Orders ORDER BY id DESC LIMIT 1");
        return rst.next() ? rst.getString("id") : null;
    }


}
