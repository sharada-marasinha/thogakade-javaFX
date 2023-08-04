package controller;

import db.DBConnection;
import model.Order;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController {
    public boolean placeOrder(Order order){
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Orders VALUES (?,?,?,?,?)");
            stm.setObject(1,order.getOrderId());
            stm.setObject(2,order.getOrderDate());
            stm.setObject(3,order.getCustomerId());
            //stm.setObject(4,order.getOrderTime());
           // stm.setObject(5,order.getCost());

            return stm.executeUpdate()>0;


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
