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
            String address = "controller?command=showOrders";

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

            // get orderId and status
            int orderId;
            String status;
            if(req.getParameter("order_id")!=null&&req.getParameter("status")!=null){
                orderId= Integer.parseInt(req.getParameter("order_id"));
                status= req.getParameter("status");
            }else {
                req.getSession().setAttribute("errorMessage", "This order doesn't exist");
                return address;
            }

            // todo check data
            System.out.println("OrderId ==> " + orderId);
            System.out.println("New status ==> " +status);


            // get User
            OrderDao orderDao = new OrderDao();
            Order order;
            Connection con = DbHelper.getInstance().getConnection();

            try {
                order = orderDao.findById(con,orderId);
                order.setStatus(status);
                System.out.println("1");
                orderDao.update(con,order,order);
                System.out.println("2");

            } catch (SQLException ex) {
                System.out.println("Can't change status");
                System.out.println(ex.getMessage());
                req.getSession().setAttribute("errorMessage", "Can't change status");
                address =  "error.jsp";
            }finally {
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            return address;

        }


}
