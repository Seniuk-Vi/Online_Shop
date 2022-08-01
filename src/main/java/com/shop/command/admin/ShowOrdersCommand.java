package com.shop.command.admin;

import com.shop.command.Command;
import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.OrderDao;
import com.shop.db.dao.OrderItemDao;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.Order;
import com.shop.models.entity.OrderItem;
import com.shop.models.entity.Product;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowOrdersCommand implements Command {
    final static Logger logger = Logger.getLogger(ShowOrdersCommand.class);
    private final String error = "No orders yet";
    private final String itemError = "Can't get order items";
    private final String productError = "Can't get product";

    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "orders.jsp";
        Connection con = DbHelper.getInstance().getConnection();
        OrderDao orderDao = new OrderDao();
        OrderItemDao orderItemDao = new OrderItemDao();
        ProductDao productDao = new ProductDao();
        List<Order> orders;
        // get all orders
        try {
            orders = orderDao.findAll(con);
        } catch (DbException ex) {
            logger.error("Can't show orders");
            req.getSession().setAttribute("errorMessage", "Orders are empty");
            return address;
        }
        if (orders.isEmpty()) {
            logger.error("Orders are empty");
            req.getSession().setAttribute("errorMessage", "Orders are empty");
            return address;
        }
        Map<Order, Map<OrderItem, Product>> orderItems = new TreeMap<>();
        orders.forEach((o) -> {
            List<OrderItem> orderItemList = new ArrayList<>();
            // get order items
            try {
                orderItemList = orderItemDao.findByOrderId(con, o.getId());
            } catch (DbException ex) {
              logger.error(itemError,ex);
            }
            // get products for order items
            Map<OrderItem, Product> map = new HashMap<>();
            orderItemList.forEach((l) -> {
                Product product = null;
                try {
                    product = productDao.findById(con, l.getProductId());
                } catch (DbException e) {
                    logger.error(productError,e);
                }
                if(product!=null){
                    map.put(l, product);
                }
            });
            // check if not empty
            if(!map.isEmpty()){
                orderItems.put(o, map);
            }
        });
        DbHelper.getInstance().close(con);
        if(orderItems.isEmpty()){
            req.getSession().setAttribute("errorMessage",error);
        }else {
            req.getSession().setAttribute("orders", orderItems);
        }
        address = "orders.jsp";
        return address;


    }
}
