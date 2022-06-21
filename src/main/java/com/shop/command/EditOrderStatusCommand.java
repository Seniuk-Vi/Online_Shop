package com.shop.command;

import com.shop.db.DbHelper;
import com.shop.db.dao.OrderDao;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.Order;
import com.shop.models.entity.Product;
import com.shop.models.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;

public class EditOrderStatusCommand implements Command {

        @Override
        public String execute(HttpServletRequest req, HttpServletResponse resp) {
            String address = "orders.jsp";

            req.getSession().removeAttribute("errorMessage");
            // move to homaPage if not in
            User admin;
            admin = (User) req.getSession().getAttribute("currentUser");
            if (admin == null) {
                req.getSession().setAttribute("errorMessage", "You must firstly login");
                return "login.jsp";
            }
            if(admin.getRole()!=1){
                req.getSession().setAttribute("errorMessage", "You aren't admin");
                return address;
            }

            // get data
            int orderId;
            if(req.getParameter("order_id")!=null){
                orderId= Integer.parseInt(req.getParameter("order_id"));
            }else {
                req.getSession().setAttribute("errorMessage", "This order doesn't exist");
                return address;
            }

            // todo check data
            System.out.println("OrderId ==> " + orderId);


            // get User
            OrderDao orderDao = new OrderDao();
            Order order;
            Connection con = DbHelper.getInstance().getConnection();

            try {
                order = orderDao.findById(con,orderId);
            } catch (SQLException ex) {
                System.out.println("Can't obtain order from DB");
                req.getSession().setAttribute("errorMessage", "Can't get order");
                return "error.jsp";
            }
            req.getSession().setAttribute("order",order);
            address = "editOrder.jsp";
            return address;

        }


}
