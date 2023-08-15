package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashRootController implements Initializable {
    public AnchorPane rootPane;
    @FXML
    private Label lblCustomers;

    @FXML
    private Label lblItems;

    @FXML
    private Label lblOrders;

    @FXML
    private LineChart<?, ?> lineChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadValuesToLabel();
        XYChart.Series orderSeries = new XYChart.Series();
        orderSeries.setName("Average Order Value");
        orderSeries.getData().add(new XYChart.Data("1", 5));
        orderSeries.getData().add(new XYChart.Data("2", 6));
        orderSeries.getData().add(new XYChart.Data("3", 7));

        XYChart.Series customerSeries = new XYChart.Series();
        customerSeries.setName("Average Customers");
        customerSeries.getData().add(new XYChart.Data("1", 2));
        customerSeries.getData().add(new XYChart.Data("3", 6));
        customerSeries.getData().add(new XYChart.Data("5", 10));

        lineChart.getData().addAll(orderSeries,customerSeries);

    }

    public void loadValuesToLabel(){
        String SQL1="Select Count(*) AS customerCount From Customer";
        String SQL2="Select Count(*) AS itemCount From Item";
        String SQL3="Select Count(*) AS orderCount From Orders";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm1 = connection.prepareStatement(SQL1);
            ResultSet rst1 = psTm1.executeQuery();
            int customerCount=0;
            while(rst1.next()){
                customerCount = rst1.getInt("customerCount");
            }
            lblCustomers.setText(String.valueOf(customerCount));
            rst1.beforeFirst();
            PreparedStatement pstm2 = connection.prepareStatement(SQL2);
            ResultSet rst2 = pstm2.executeQuery();
            int itemCount = 0;
            if (rst2.next()) {
                itemCount = rst2.getInt("itemCount");
            }
            lblItems.setText(String.valueOf(itemCount));

            PreparedStatement pstm3 = connection.prepareStatement(SQL3);
            ResultSet rst3 = pstm3.executeQuery();
            int orderCount = 0;
            if (rst3.next()) {
                orderCount = rst3.getInt("orderCount");
            }
            lblOrders.setText(String.valueOf(orderCount));

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
    DashBordFormController dashBordFormController=new DashBordFormController();

    public void retCustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/customer-Form.fxml");

        assert resource != null;

        Parent load =  FXMLLoader.load(resource);
        rootPane.getChildren().clear();
        rootPane.getChildren().add(load);
    }

    public void retOrderOnAction(MouseEvent mouseEvent) {
    }

    public void retInventoryOnAction(ActionEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/item-Form.fxml");

        assert resource != null;

        Parent load =  FXMLLoader.load(resource);
        rootPane.getChildren().clear();
        rootPane.getChildren().add(load);
    }

    public void orderOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/order-detail-form.fxml");

        assert resource != null;

        Parent load =  FXMLLoader.load(resource);
        rootPane.getChildren().clear();
        rootPane.getChildren().add(load);
    }
}

