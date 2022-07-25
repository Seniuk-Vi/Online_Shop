//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.db.dao;

import com.shop.db.DbException;
import com.shop.models.entity.Order;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDao extends GenericDAO<Order> {
    final static Logger logger = Logger.getLogger(OrderDao.class);

    public String SQL_GET_ALL_ORDERS = "SELECT * FROM ordercol";
    public static final String SQL_ADD_ORDER = "INSERT INTO ordercol (user_id,status,order_date)VALUES(?,?,?)";
    public static final String SQL_FIND_BY_ID = "SELECT * FROM ordercol WHERE id= ?";
    public static final String SQL_FIND_BY_USER_ID = "SELECT * FROM ordercol WHERE user_id= ?";
    public static final String SQL_UPDATE_ORDER = "UPDATE ordercol SET user_id=?, status =?, order_date=? WHERE id = ?";
    public static final String SQL_DELETE_BY_ID = "DELETE * FROM ordercol WHERE id = ?";

    public OrderDao() {
    }

    public List<Order> findAll(Connection con) throws DbException {
        return this.findAll(con, this.SQL_GET_ALL_ORDERS);
    }

    public Order findById(Connection con, int id) throws DbException {
        List<Order> list = findByField(con, SQL_FIND_BY_ID, id);
        if (list.isEmpty()) {
            logger.error("No order with this id ==>"+id);
            throw new DbException("No order with this id ==>"+id);
        } else {
            return list.get(0);
        }
    }

    public List<Order> findByUserId(Connection con, int id) throws DbException {
        List<Order> list = findByField(con, SQL_FIND_BY_USER_ID, id);
        if (list.isEmpty()) {
            logger.error("No order with this userId ==>"+id);
            throw new DbException("No order with this userId ==>"+id);
        } else {
            return list;
        }
    }

    public int add(Connection con, Order order) throws DbException {
        return add(con, SQL_ADD_ORDER, order);
    }

    public void update(Connection con, Order order, Order newOrder) throws DbException {
        updateByField(con, SQL_UPDATE_ORDER, newOrder, 4, order.getId());
    }

    public void delete(Connection con, Order order) throws DbException {
        deleteByField(con, SQL_DELETE_BY_ID, order.getId());
    }

    protected void mapFromEntity(PreparedStatement pstm, Order order) throws SQLException {
        int k = 1;
        pstm.setInt(k++, order.getUserId());
        pstm.setString(k++, order.getStatus());
        pstm.setString(k++, order.getOrderDate());
        logger.info("PreparedStatement ==>"+pstm);
    }

    protected Order mapToEntity(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setUserId(rs.getInt("user_id"));
        order.setStatus(rs.getString("status"));
        order.setOrderDate(rs.getString("order_date"));
        logger.info("Order ==>"+order);
        return order;
    }
}
