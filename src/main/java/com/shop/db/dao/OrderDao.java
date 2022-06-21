package com.shop.db.dao;

import com.shop.db.DbException;
import com.shop.models.entity.Order;
import com.shop.models.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao extends GenericDAO<Order> {


    public String SQL_GET_ALL_ORDERS = "SELECT * FROM ordercol";

    public static final String SQL_ADD_ORDER = "INSERT INTO ordercol (user_id,status,order_date)"
            + "VALUES" + "(?,?,?)";

    public static final String SQL_FIND_BY_ID = "SELECT * FROM ordercol WHERE id= ?";
    public static final String SQL_FIND_BY_USER_ID = "SELECT * FROM ordercol WHERE user_id= ?";
    public static final String SQL_UPDATE_ORDER = "UPDATE order SET" +
            "user_id=?, status =?,order_date=?" + "WHERE id = ?";
    public static final String SQL_DELETE_BY_ID = "DELETE * FROM order WHERE id = ?";


    public List<Order> findAll(Connection con) throws SQLException {
        List<Order> list;
        list = findAll(con, SQL_GET_ALL_ORDERS);
        return list;
    }


    public Order findById(Connection con, int id) throws SQLException {
        List<Order> list = findByField(con, SQL_FIND_BY_ID, id);
        if (list.isEmpty()) {
            throw new SQLException("Can't find order by id");
        }
        return list.get(0);
    }
    public List<Order> findByUserId(Connection con, int id) throws SQLException {
        System.out.println("User id = " +id);
        List<Order> list = findByField(con, SQL_FIND_BY_USER_ID, id);
        if (list.isEmpty()) {
            throw new SQLException("Can't find order by user id");
        }
        return list;
    }

    public int add(Connection con, Order order) throws DbException {
        return add(con, SQL_ADD_ORDER, order);


    }


    public void update(Connection con, Order order, Order newOrder) throws SQLException {
        updateByField(con, SQL_UPDATE_ORDER, newOrder, 4, order.getId());
    }


    public void delete(Connection con, Order order) throws SQLException {
        deleteByField(con, SQL_DELETE_BY_ID, order.getId());
    }


    protected void mapFromEntity(PreparedStatement pstmt, Order order) throws SQLException {
        int k = 1;
        pstmt.setInt(k++, order.getUserId());
        pstmt.setString(k++, order.getStatus());
        pstmt.setString(k++, order.getOrderDate().toString());

        System.out.println(pstmt.toString());
    }

    @Override
    protected Order mapToEntity(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setUserId(rs.getInt("user_id"));
        order.setStatus(rs.getString("status"));
        order.setOrderDate(rs.getString("order_date"));
        System.out.println(order);
        return order;
    }
}
