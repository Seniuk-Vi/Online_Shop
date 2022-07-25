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

public class ShowOrdersCommand implements Command {


    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "orders.jsp";
        User user = (User) req.getSession().getAttribute("currentUser");
        if (user != null && user.getRole() == 1) {
            Connection con = DbHelper.getInstance().getConnection();
            OrderDao orderDao = new OrderDao();
            OrderItemDao orderItemDao = new OrderItemDao();
            ProductDao productDao = new ProductDao();

            List<Order> orders;
            try {
                orders = orderDao.findAll(con);
            } catch (DbException ex) {
                System.out.println("Orders are empty");
                req.getSession().setAttribute("errorMessage", "Orders are empty");
                return address;
            }

            if (orders.isEmpty()) {
                System.out.println("Orders are empty");
                req.getSession().setAttribute("errorMessage", "Orders are empty");
                return address;
            } else {
                System.out.println("Orders ==> " + orders);
                Map<Order, Map<OrderItem, Product>> orderItems = new HashMap();
                orders.forEach((o) -> {
                    List<OrderItem> orderItemList = null;
                    Map<OrderItem, Product> maapp = new HashMap();

                    try {
                        orderItemList = orderItemDao.findByOrderId(con, o.getId());
                        System.out.println("Order items ==> " + orderItemList);
                        orderItemList.forEach((l) -> {

                            Product product = null;
                            try {
                                product = productDao.findById(con, l.getProductId());
                            } catch (DbException e) {
                                //todo
                            }
                            maapp.put(l, product);

                        });
                    } catch (DbException ex) {
                        DbHelper.getInstance().close(con);
                        throw new RuntimeException(ex);
                    }

                    orderItems.put(o, maapp);
                });
                System.out.println("ORDERS ==> " + orderItems);
                req.getSession().setAttribute("orders", orderItems);
                address = "orders.jsp";
                return address;
            }
        } else {
            req.getSession().removeAttribute("currentUser");
            req.getSession().setAttribute("errorMessage", "You aren't logged in");
            return "login.jsp";
        }
    }
}
