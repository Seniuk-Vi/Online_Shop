package com.shop.command;

import com.shop.db.DbHelper;
import com.shop.db.dao.OrderDao;
import com.shop.db.dao.OrderItemDao;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.Order;
import com.shop.models.entity.OrderItem;
import com.shop.models.entity.Product;
import com.shop.models.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowUserOrdersCommand implements Command {


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "user.jsp";

        User user = (User) req.getSession().getAttribute("currentUser");
        if (user == null) {
            req.getSession().removeAttribute("currentUser");
            req.getSession().setAttribute("errorMessage", "You aren't logged in");
            return "login.jsp";

        }

        Connection con = DbHelper.getInstance().getConnection();


        OrderDao orderDao = new OrderDao();
        OrderItemDao orderItemDao = new OrderItemDao();
        ProductDao productDao = new ProductDao();
        // get orders
        List<Order> orders = null;
        try {
            orders = orderDao.findByUserId(con, user.getId());
        } catch (SQLException e) {
            System.out.println("You don't have any orders");
            req.getSession().setAttribute("errorMessage", "You don't have any orders");
            return address;
        }
        System.out.println("Orders ==> " + orders);
        // get order items

        Map<Order, Map<OrderItem, Product>> orderItems = new HashMap<>();    // order to item to product
        orders.forEach((o) -> {
            List<OrderItem> orderItemList = null;   // items
            Map<OrderItem, Product> maapp = new HashMap<>(); // item to product
            try {
                orderItemList = orderItemDao.findByOrderId(con, o.getId());
                System.out.println("Order items ==> " + orderItemList);
                orderItemList.forEach((l) -> {
                    try {
                        Product product = productDao.findById(con, l.getProductId());
                        maapp.put(l, product);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (SQLException e) {
                DbHelper.getInstance().close(con);
                throw new RuntimeException(e);
            }
            orderItems.put(o, maapp);
        });
        System.out.println("ORDERS ==> " + orderItems);
        req.getSession().setAttribute("orders", orderItems);
        address = "userOrders.jsp";
        return address;

    }
}
