package model;

import db.DBConnection;
import dto.Customer;
import dto.Item;

import java.sql.*;

public class ItemModel {

    public static boolean addItem(Item item) throws SQLException, ClassNotFoundException {
        String SQL = "Insert into Item Values(?,?,?,?)";
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement(SQL);
        psTm.setObject(1,item.getCode());
        psTm.setObject(2,item.getDescription());
        psTm.setObject(3,item.getUnitPrice());
        psTm.setObject(4,item.getQtyOnHand());
        int i = psTm.executeUpdate();
        return i>0?true:false;
    }

    public static boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        int i = DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete From Item where code='" + code + "'");
        return i > 0 ? true : false;
    }

    public static Item searchItem(String code) throws SQLException, ClassNotFoundException {
        String SQL = "Select * From Item where code='"+code+"'";
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet resultSet = stm.executeQuery(SQL);
        if (resultSet.next()) {
            Item item = new Item(code, resultSet.getString(2), resultSet.getDouble(3), resultSet.getInt(4));
            return item;
        }
        return null;

    }
}
