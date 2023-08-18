package controller.dash;

import db.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
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
    public BarChart barChart;
    @FXML
    private Label lblCustomers;

    @FXML
    private Label lblItems;

    @FXML
    private Label lblOrders;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadValuesToLabel();
        loadChart();
    }

    private void loadChart(){
        XYChart.Series<String,Integer> se1=new XYChart.Series<>();
        se1.setName("Order");
        se1.getData().add(new XYChart.Data<>("January",2323));
        se1.getData().add(new XYChart.Data<>("February",4333));
        se1.getData().add(new XYChart.Data<>("March",10000));
        se1.getData().add(new XYChart.Data<>("April",350000));
        se1.getData().add(new XYChart.Data<>("May",435000));
        se1.getData().add(new XYChart.Data<>("June",21000));
        se1.getData().add(new XYChart.Data<>("Jule",78000));
        barChart.getData().add(se1);
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

    public void retCustomerOnAction() throws IOException {
        URL resource = this.getClass().getResource("/view/customer-Form.fxml");

        assert resource != null;

        Parent load =  FXMLLoader.load(resource);
        rootPane.getChildren().clear();
        rootPane.getChildren().add(load);
    }

    public void retOrderOnAction(MouseEvent mouseEvent) {
    }

    public void retInventoryOnAction() throws IOException {
        URL resource = this.getClass().getResource("/view/item-Form.fxml");

        assert resource != null;

        Parent load =  FXMLLoader.load(resource);
        rootPane.getChildren().clear();
        rootPane.getChildren().add(load);
    }

    public void orderOnAction() throws IOException {
        URL resource = this.getClass().getResource("/view/order-detail-form.fxml");

        assert resource != null;

        Parent load =  FXMLLoader.load(resource);
        rootPane.getChildren().clear();
        rootPane.getChildren().add(load);
    }
}

