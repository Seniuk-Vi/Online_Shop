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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class AddOrderCommand implements Command {


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String address = "error.jsp";

        OrderDao orderDao = new OrderDao();
        OrderItemDao orderItemDao = new OrderItemDao();
        Order order = new Order();

        User currentUser = (User) req.getSession().getAttribute("currentUser");
        int userId = currentUser.getId();
        String status = req.getParameter("status");
        String date=req.getParameter("order_date");


        // todo check if param is valid

        //

        order.setUserId(userId);
        order.setStatus(status);
        order.setOrderDate(date);

        ArrayList<OrderItem> orderItems =null;
        orderItems = (ArrayList<OrderItem>) req.getSession().getAttribute("cart");
        if(orderItems==null){
            address ="error.jsp";
            return address;
        }


        Connection con;
        con = DbHelper.getInstance().getConnection();

        try {
            con.setAutoCommit(false);

            orderDao.add(con, order);
            orderItems.forEach((OrderItem o)->{
                try {
                    orderItemDao.add(con,o);
                } catch (DbException e) {
                   throw new RuntimeException(e);
                }
            });
            con.commit();
            address = "homePage.jsp";
        } catch (Exception ex) {
            con.rollback();
            System.out.println("Can't add order ==> " + order);
            req.getSession().setAttribute("errorMessage","Can't add order!!" );
            address="addProduct.jsp";
        }

        return address;

    }
    private String passAttributesToSession(HttpServletRequest request, HttpServletResponse response, Map<String, String> viewAttributes) throws ServletException, IOException {
        for (Map.Entry<String, String> entry : viewAttributes.entrySet())
            request.getSession().setAttribute(entry.getKey(), entry.getValue());
        return "registration.jsp";
    }

}
