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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class AddOrderCommand implements Command {


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp)   {
        String address = "error.jsp";

        req.getSession().removeAttribute("errorMessage");
        OrderDao orderDao = new OrderDao();
        OrderItemDao orderItemDao = new OrderItemDao();
        Order order = new Order();

        User currentUser = (User) req.getSession().getAttribute("currentUser");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        String status = "registered";
        int userId = currentUser.getId();


        // toDO check if param is valid

        //

        order.setUserId(userId);
        order.setStatus(status);
        order.setOrderDate(date);

        Map<Product, OrderItem> orderItems = null;
        orderItems = (Map<Product, OrderItem>) req.getSession().getAttribute("cart");
        if (orderItems == null) {
            address = "error.jsp";
            return address;
        }


        Connection con;
        con = DbHelper.getInstance().getConnection();


        try {
            con.setAutoCommit(false);
            // add order
            int orderId;
            orderId = orderDao.add(con, order);
            if (orderId == -1) {
                throw new DbException("Can't make order");
            }
            order.setId(orderId);
            // add order items
            System.out.println(orderItems);
            for (Map.Entry<Product, OrderItem> entry : orderItems.entrySet()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(orderId);
                System.out.println(entry.getKey().getId());
                orderItem.setQuantity(Integer.parseInt((String) req.getParameter(String.valueOf(entry.getKey().getId()))));
                orderItem.setProductId(entry.getKey().getId());
                System.out.println("trying add orderItem ==> " + orderItem);
                orderItemDao.add(con, orderItem);
                //  System.out.println("Key : " + entry.getKey() + ", Value : " + entry.getValue());

            }
            // clear cart
            req.getSession().removeAttribute("cart");
            con.commit();
            address = "homePage.jsp";
        } catch (Exception ex) {
            try {
                con.rollback();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Can't add order ==> " + order);
            System.out.println(ex);
            req.getSession().setAttribute("errorMessage", "Can't add order!!");
            address = "displayCart.jsp";
        }

        return address;

    }

    private String passAttributesToSession(HttpServletRequest request, HttpServletResponse response, Map<String, String> viewAttributes) throws ServletException, IOException {
        for (Map.Entry<String, String> entry : viewAttributes.entrySet())
            request.getSession().setAttribute(entry.getKey(), entry.getValue());
        return "registration.jsp";
    }

}
