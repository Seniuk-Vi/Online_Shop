package com.shop.command.admin;

import com.shop.command.Command;
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
import java.util.List;

public class EditOrderStatusCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "controller?command=showOrders";

        ProductDao productDao = new ProductDao();

        req.getSession().removeAttribute("errorMessage");
        // move to homaPage if not in
        User admin;
        admin = (User) req.getSession().getAttribute("currentUser");
        if (admin == null) {
            req.getSession().setAttribute("errorMessage", "You must firstly login");
            return "login.jsp";
        }
        if (admin.getRole() != 1) {
            req.getSession().setAttribute("errorMessage", "You aren't admin");
            return address;
        }

        // get orderId and status
        int orderId;
        String status;
        if (req.getParameter("order_id") != null && req.getParameter("status") != null) {
            orderId = Integer.parseInt(req.getParameter("order_id"));
            status = req.getParameter("status");
        } else {
            req.getSession().setAttribute("errorMessage", "This order doesn't exist");
            return address;
        }

        // todo check data
        System.out.println("OrderId ==> " + orderId);
        System.out.println("New status ==> " + status);


        // get User
        OrderDao orderDao = new OrderDao();
        OrderItemDao orderItemDao = new OrderItemDao();
        List<OrderItem> orderItemList;
        Order order;
        Connection con = DbHelper.getInstance().getConnection();

        try {
            con.setAutoCommit(false);
            order = orderDao.findById(con, orderId);
            order.setStatus(status);
            if (status.equals("canceled")) {

                orderItemList = orderItemDao.findByOrderId(con, orderId);
                for (OrderItem orderItem : orderItemList) {
                    Product product;
                    product = productDao.findById(con,orderItem.getProductId());
                    product.setInStock(product.getInStock()+orderItem.getQuantity());
                    System.out.println("product updated ==> "+product);
                    productDao.update(con,product,product);
                }
            }
            orderDao.update(con, order, order);
            con.commit();
        } catch (SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Can't change status");
            System.out.println(ex.getMessage());
            req.getSession().setAttribute("errorMessage", "Can't change status");
            address = "error.jsp";
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return address;

    }


}
