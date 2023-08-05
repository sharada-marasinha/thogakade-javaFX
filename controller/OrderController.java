package controller;

import db.DBConnection;
import dto.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController {
    public boolean placeOrder(Order order) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("INSERT INTO Orders value(?,?,?)");
        stm.setObject(1,order.getId());
        stm.setObject(2,order.getDate());
        stm.setObject(3,order.getCustomerId());
        boolean orderIsAdded = stm.executeUpdate()>0;
        if(orderIsAdded){
            return true;
        }

        return false;
    }
}
