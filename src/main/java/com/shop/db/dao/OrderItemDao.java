//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.db.dao;

import com.shop.db.DbException;
import com.shop.models.entity.OrderItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderItemDao extends GenericDAO<OrderItem> {
    public String SQL_GET_ALL_ITEM = "SELECT * FROM order_items";
    public static final String SQL_FIND_BY_ORDER_ID = "SELECT * FROM order_items WHERE order_id= ?";
    public static final String SQL_ADD_ITEM = "INSERT INTO order_items (order_id,product_id,quantity) VALUES(?,?,?)";
    public static final String SQL_UPDATE_ITEM = "UPDATE order_items SET order_id=?,product_id=?,quantity=?WHERE order_id = ? AND product_id=?";
    public static final String SQL_DELETE_ITEM = "DELETE * FROM order_items WHERE order_id = ? AND product_id=?";

    public OrderItemDao() {
    }

    public List<OrderItem> findAll(Connection con) throws SQLException {

        return this.findAll(con, this.SQL_GET_ALL_ITEM);
    }

    public List<OrderItem> findByOrderId(Connection con, int id) throws SQLException {
        List<OrderItem> list = this.findByField(con, SQL_FIND_BY_ORDER_ID, id);
        if (list.isEmpty()) {
            throw new SQLException("Can't find order by id" + id);
        }
        return list;
    }

    public void add(Connection con, OrderItem orderItem) throws DbException {
        this.add(con, SQL_ADD_ITEM, orderItem);
    }

    public void delete(Connection con, OrderItem orderItem) throws SQLException {
        this.deleteByMultFields(con, SQL_DELETE_ITEM, orderItem.getOrderId(), orderItem.getProductId());
    }

    protected void mapFromEntity(PreparedStatement pstmt, OrderItem orderItem) throws SQLException {
        int k = 1;
        pstmt.setInt(k++, orderItem.getOrderId());
        pstmt.setInt(k++, orderItem.getProductId());
        pstmt.setInt(k++, orderItem.getQuantity());
    }

    protected OrderItem mapToEntity(ResultSet rs) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(rs.getInt("order_id"));
        orderItem.setProductId(rs.getInt("product_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        return orderItem;
    }
}
