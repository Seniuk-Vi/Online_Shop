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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowUserOrdersCommand implements Command {

    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "user.jsp";
        User user = (User)req.getSession().getAttribute("currentUser");
        if (user == null) {
            req.getSession().setAttribute("errorMessage", "You aren't logged in");
            return "login.jsp";
        } else {
            Connection con = DbHelper.getInstance().getConnection();
            OrderDao orderDao = new OrderDao();
            OrderItemDao orderItemDao = new OrderItemDao();
            ProductDao productDao = new ProductDao();
            List<Order> orders;

            try {
                orders = orderDao.findByUserId(con, user.getId());
            } catch (DbException ex) {
                System.out.println("You don't have any orders");
                req.getSession().setAttribute("errorMessage", "You don't have any orders");
                return address;
            }

            System.out.println("Orders ==> " + orders);
            Map<Order, Map<OrderItem, Product>> orderItems = new HashMap();
            orders.forEach((o) -> {
                List<OrderItem> orderItemList;
                Map<OrderItem, Product> maapp = new HashMap();

                try {
                    orderItemList = orderItemDao.findByOrderId(con, o.getId());
                    System.out.println("Order items ==> " + orderItemList);
                    orderItemList.forEach((l) -> {
                        try {
                            Product product = productDao.findById(con, l.getProductId());
                            maapp.put(l, product);
                        } catch (DbException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                } catch (DbException ex) {
                    DbHelper.getInstance().close(con);
                    throw new RuntimeException(ex);
                }

                orderItems.put(o, maapp);
            });
            System.out.println("ORDERS ==> " + orderItems);
            if (orderItems.isEmpty()) {
                req.getSession().setAttribute("errorMessage", "No orders yet");
            }

            req.getSession().setAttribute("orders", orderItems);
            address = "userOrders.jsp";
            return address;
        }
    }
}
