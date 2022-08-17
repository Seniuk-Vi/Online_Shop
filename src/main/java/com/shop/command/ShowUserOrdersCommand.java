package com.shop.command;

import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.OrderDao;
import com.shop.db.dao.OrderItemDao;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.Order;
import com.shop.models.entity.OrderItem;
import com.shop.models.entity.Product;
import com.shop.models.entity.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowUserOrdersCommand implements Command {
    final static Logger logger = Logger.getLogger(ShowUserOrdersCommand.class);
    final String error = "Trying to enter orders page without signing in";
    private final String itemError = "Can't get order items";
    private final String productError = "Can't get product";
    final String message = "You don't have any orders";

    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "user.jsp";
        User user = (User) req.getSession().getAttribute("currentUser");
        if (user == null) {
            logger.error(error);
            req.getSession().setAttribute("errorMessage", "You aren't logged in");
            return "login.jsp";
        }
        Connection con = DbHelper.getInstance().getConnection();
        OrderDao orderDao = new OrderDao();
        OrderItemDao orderItemDao = new OrderItemDao();
        ProductDao productDao = new ProductDao();
        List<Order> orders;
        try {
            orders = orderDao.findByUserId(con, user.getId());
        } catch (DbException ex) {
            req.getSession().setAttribute("errorMessage", message);
            return address;
        }
        Map<Order, Map<OrderItem, Product>> orderItems = new TreeMap<>();
        orders.forEach((o) -> {
            List<OrderItem> orderItemList = new ArrayList<>();
            try {
                orderItemList = orderItemDao.findByOrderId(con, o.getId());
            } catch (DbException ex) {
                logger.error(itemError);
            }
            Map<OrderItem, Product> map = new HashMap<>();
            orderItemList.forEach((l) -> {
                try {
                    Product product = productDao.findById(con, l.getProductId());
                    map.put(l, product);
                } catch (DbException ex) {
                    logger.error(productError);
                }
            });
            if (!map.isEmpty()) {
                orderItems.put(o, map);
            }
        });
        DbHelper.getInstance().close(con);
        if (orderItems.isEmpty()) {
            req.getSession().setAttribute("errorMessage", message);
        } else {
            req.getSession().setAttribute("orders", orderItems);
        }
        req.getSession().setAttribute("orders", orderItems);
        address = "userOrders.jsp";
        return address;

    }
}
