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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddOrderCommand implements Command {
    final static Logger logger = Logger.getLogger(AddOrderCommand.class);
    private final String error = "Can't add order";
    private final String info = "Cart is empty";

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
        Map<Product, OrderItem> orderItems = (Map<Product, OrderItem>) req.getSession().getAttribute("cart");
        if (orderItems.isEmpty()) {
            req.getSession().setAttribute("errorMessage", info);
            return address;
        }
            Connection con = DbHelper.getInstance().getConnection();
            try {
                con.setAutoCommit(false);
                int orderId = orderDao.add(con, order);
                if (orderId == -1) {
                    throw new DbException();
                }
                order.setId(orderId);
                Iterator<Map.Entry<Product, OrderItem>> iterator = orderItems.entrySet().iterator();

                while(iterator.hasNext()) {
                    Map.Entry<Product, OrderItem> entry =iterator.next();
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderId(orderId);
                    orderItem.setQuantity((entry.getValue()).getQuantity());
                    orderItem.setProductId((entry.getKey()).getId());
                    Product product = productDao.findById(con, (entry.getKey()).getId());
                    product.setInStock(product.getInStock() - (entry.getValue()).getQuantity());
                    if (product.getInStock() < 0) {
                        String title = product.getTitle();
                        req.getSession().setAttribute("info","No available product in stock: " + title + ", In stock = " + product.getInStock() + entry.getValue().getQuantity());
                        return address;
                    }
                    productDao.update(con, product, product);
                    orderItemDao.add(con, orderItem);
                }
                Map<Product, OrderItem> cart = new HashMap();
                con.commit();
                req.getSession().removeAttribute("cart");
                req.getSession().setAttribute("cart", cart);
                address = "homePage.jsp";
            } catch (DbException| SQLException ex) {
                try {
                    con.rollback();
                    con.close();
                } catch (SQLException exx) {
                   logger.info("Can't close and rollback connection",ex);
                }
                logger.error(error);
                req.getSession().setAttribute("errorMessage", error);
                address = "displayCart.jsp";
            }
            return address;
    }
}
