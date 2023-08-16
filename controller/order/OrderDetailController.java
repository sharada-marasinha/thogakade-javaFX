package controller.order;

import db.DBConnection;
import dto.OrderDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import view.tm.OrderDetailTm;
import view.tm.OrderTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class OrderDetailController {
    private OrderDetailController() {
    }

    public static boolean addOrderDetail(List<OrderDetails> orderDetailList) throws ClassNotFoundException, SQLException {
        for (OrderDetails orderDetail : orderDetailList) {
            boolean isAdded = addOrderDetail(orderDetail);
            if (!isAdded) {
                return false;
            }
        }
        return true;
    }

    public static boolean addOrderDetail(OrderDetails orderDetail) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement stm = connection.prepareStatement("Insert into OrderDetail values(?,?,?,?)")) {
            stm.setObject(1, orderDetail.getOrderId());
            stm.setObject(2, orderDetail.getItemCode());
            stm.setObject(3, orderDetail.getQty());
            stm.setObject(4, orderDetail.getUnitPrice());
            return stm.executeUpdate() > 0;
        }
    }
    public static ObservableList<OrderTm> loadTableDataOrders(){
        ObservableList<OrderTm> orderDataList= FXCollections.observableArrayList();
        String SQL = "SELECT * FROM Orders";
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement(SQL).executeQuery();
            while (rst.next()){
                orderDataList.add(new OrderTm(rst.getString(1),rst.getString(2),rst.getString(3)));
            }
            return orderDataList;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return orderDataList;
    }

    public static ObservableList<OrderDetailTm> searchOrderDetail(String id) {
        String SQL = "SELECT *FROM orders INNER JOIN orderdetail ON orders.id = orderdetail.orderId inner join item on item.code=orderdetail.itemcode WHERE orderdetail.orderId='"+id+"'";
        ObservableList<OrderDetailTm> orderDetailList = FXCollections.observableArrayList();
        try {
            ResultSet rst = DBConnection.getInstance().getConnection().prepareStatement(SQL).executeQuery();
            while(rst.next()){
                orderDetailList.add(new OrderDetailTm(rst.getString(1),rst.getString(5),rst.getInt(6),rst.getDouble(10)));
            }
            return orderDetailList;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
