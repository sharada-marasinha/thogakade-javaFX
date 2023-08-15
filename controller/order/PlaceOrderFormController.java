package controller.order;

import controller.customer.CustomerController;
import controller.customer.CustomerFormController;
import controller.item.ItemController;
import controller.item.ItemFormController;
import db.DBConnection;
import dto.Customer;
import dto.Item;
import dto.Order;
import dto.OrderDetails;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import view.tm.CartTm;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    @FXML
    private ComboBox<String> cmbCustomerIds;

    @FXML
    private ComboBox<String> cmdItemCode;

    @FXML
    private TableColumn<CartTm, String> colCode;

    @FXML
    private TableColumn<CartTm, String> colDesc;

    @FXML
    private TableColumn<CartTm, Integer> colQty;

    @FXML
    private TableColumn<CartTm, Double> colTotal;

    @FXML
    private TableColumn<CartTm, Double> colUnitPrice;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblTtl;

    @FXML
    private TableView<CartTm> tblCart;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtQtyOnNow;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtUnitPrice;

    private int cartSelectedRowForRemove = -1;
    private ObservableList<CartTm> obList = FXCollections.observableArrayList();
    private CustomerController customerController;
    private ItemController itemController;
    Date date;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customerController = CustomerController.getInstance();
        itemController = ItemController.getInstance();
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

        tblCart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> cartSelectedRowForRemove = (int) newValue);
        /*===============================================*/
        cmbCustomerIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setCustomerData(newValue));

        cmdItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setItemData(newValue));
        /*===============================================*/
    }

    private void setItemData(String itemCode) {
        Item item = itemController.searchItem(itemCode);
        if (item != null) {
            txtDesc.setText(item.getDescription());
            txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            txtQty.setText(String.valueOf(item.getQtyOnHand()));

        } else {
            new Alert(Alert.AlertType.ERROR, "Item Not Found !").show();
        }
    }

    private void setCustomerData(String customerId) {
        Customer customer = customerController.searchCustomer(customerId);
        if (customer != null) {
            txtName.setText(customer.getName());
            txtAddress.setText(customer.getAddress());
            txtSalary.setText(String.valueOf(customer.getSalary()));
        } else {

            new Alert(Alert.AlertType.ERROR, "Customer Not Found !").show();
        }


    }

    private void loadCustomerIds() throws SQLException, ClassNotFoundException {
        List<String> customerIds = new CustomerFormController().getCustomerIds();
        cmbCustomerIds.getItems().addAll(customerIds);
    }

    private void loadItemCodes() throws SQLException, ClassNotFoundException {
        List<String> itemCodes = new ItemFormController().getItemCodes();
        cmdItemCode.getItems().addAll(itemCodes);
    }

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

    public void btnAddToCartOnAction() {

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
            obList.add(tm);
        } else {
            CartTm tempTm = obList.get(rowNumber);
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
            obList.remove(rowNumber);
            obList.add(newTm);
        }

        tblCart.setItems(obList);
        calculateCost();
    }

    private int isExist(CartTm tm) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getCode().equals(obList.get(i).getCode())) {
                return i;
            }
        }
        return -1;
    }

    void calculateCost() {
        double ttl = 0;
        for (CartTm tm : obList) {
            ttl += tm.getTotal();
        }
        lblTtl.setText(ttl + " /=");
    }

    public void clearOnAction() {
        if (cartSelectedRowForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a Row").show();
        } else {
            obList.remove(cartSelectedRowForRemove);
            calculateCost();
            tblCart.refresh();
        }
    }

    public void btnPlaceOrder() throws SQLException, ClassNotFoundException {
        String oId = lblOrderId.getText();
        String customerId = cmbCustomerIds.getValue();
        String orderDate = lblDate.getText();

        ArrayList<OrderDetails> orderDetailsArrayList = new ArrayList<>();

        for (CartTm tempTm : obList) {
            String itemCode = tempTm.getCode();
            int orderQty = tempTm.getQty();
            double unitPrice = tempTm.getUnitPrice();
            orderDetailsArrayList.add(new OrderDetails(oId, itemCode, orderQty, unitPrice));
        }
        Order order = new Order(oId, orderDate, customerId, orderDetailsArrayList);

        if (new OrderController().placeOrder(order)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Order Success !").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Try Again").show();
        }

    }

    private void setOrderId() {
        try {
            String lastOrderId = getLastOrderId();
            if (lastOrderId != null) {
                lastOrderId = lastOrderId.split("[A-Z]")[1]; // D001==> 001
                lastOrderId = String.format("D%03d", (Integer.parseInt(lastOrderId) + 1));
                lblOrderId.setText(lastOrderId);
            } else {
                lblOrderId.setText("D001");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getLastOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT id FROM Orders ORDER BY id DESC LIMIT 1");
        return rst.next() ? rst.getString("id") : null;
    }


}
