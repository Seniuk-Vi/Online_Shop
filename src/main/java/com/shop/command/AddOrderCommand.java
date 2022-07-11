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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddOrderCommand implements Command {

    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "displayCart.jsp";
        OrderDao orderDao = new OrderDao();
        OrderItemDao orderItemDao = new OrderItemDao();
        ProductDao productDao = new ProductDao();
        User currentUser = (User)req.getSession().getAttribute("currentUser");
        int userId = currentUser.getId();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String orderDate = formatter.format(date);
        String status = "registered";
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(status);
        order.setOrderDate(orderDate);
        Map<Product, OrderItem> orderItems = (Map)req.getSession().getAttribute("cart");
        if (orderItems.isEmpty()) {
            req.getSession().setAttribute("errorMessage", "Your cart is empty!");
            return address;
        } else {
            Connection con = DbHelper.getInstance().getConnection();

            try {
                con.setAutoCommit(false);
                int orderId = orderDao.add(con, order);
                if (orderId == -1) {
                    throw new DbException("Can't make order");
                }

                order.setId(orderId);
                System.out.println(orderItems);
                Iterator iterator = orderItems.entrySet().iterator();

                while(iterator.hasNext()) {
                    Map.Entry<Product, OrderItem> entry = (Map.Entry)iterator.next();
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderId(orderId);
                    orderItem.setQuantity(((OrderItem)entry.getValue()).getQuantity());
                    orderItem.setProductId(((Product)entry.getKey()).getId());
                    Product product = productDao.findById(con, ((Product)entry.getKey()).getId());
                    product.setInStock(product.getInStock() - ((OrderItem)entry.getValue()).getQuantity());
                    if (product.getInStock() < 0) {
                        String title = product.getTitle();
                        throw new IllegalArgumentException("No available product in stock: " + title + ", In stock = " + product.getInStock() + ((OrderItem)entry.getValue()).getQuantity());
                    }

                    productDao.update(con, product, product);
                    System.out.println("trying add orderItem ==> " + orderItem);
                    orderItemDao.add(con, orderItem);
                }

                Map<Product, OrderItem> cart = new HashMap();
                con.commit();
                req.getSession().removeAttribute("cart");
                req.getSession().setAttribute("cart", cart);
                address = "homePage.jsp";
            } catch (Exception ex) {
                try {
                    con.rollback();
                    con.close();
                } catch (SQLException exx) {
                    throw new RuntimeException(exx);
                }

                System.out.println("Can't add order ==> " + order);
                System.out.println(ex.getMessage());
                req.getSession().setAttribute("errorMessage", "Can't add order!! " + ex.getMessage());
                address = "displayCart.jsp";
            }

            return address;
        }
    }
}
