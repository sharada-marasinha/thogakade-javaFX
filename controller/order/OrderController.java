package controller.order;

import controller.item.ItemController;
import db.DBConnection;
import dto.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController {
    public boolean placeOrder(Order order) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement stm = connection.prepareStatement("INSERT INTO Orders value(?,?,?)");
            stm.setObject(1,order.getId());
            stm.setObject(2,order.getDate());
            stm.setObject(3,order.getCustomerId());
            boolean orderIsAdded = stm.executeUpdate()>0;
            if(orderIsAdded){
                boolean orderDetailAdded = OrderDetailController.addOrderDetail(order.getOrderDetails());
                if (orderDetailAdded) {
                    boolean itemIsUpdate = ItemController.updateStock(order.getOrderDetails());
                    if (itemIsUpdate) {
                        connection.commit();
                        return true;
                    }
                }
            }

            return false;
        }finally {
            connection.setAutoCommit(true);
        }

    }
}
