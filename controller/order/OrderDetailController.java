package controller.order;

import db.DBConnection;
import dto.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
