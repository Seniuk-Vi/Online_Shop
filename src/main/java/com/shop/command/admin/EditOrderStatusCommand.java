//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditOrderStatusCommand implements Command {


    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "controller?command=showOrders";
        ProductDao productDao = new ProductDao();
        req.getSession().removeAttribute("errorMessage");
        User admin = (User)req.getSession().getAttribute("currentUser");
        if (admin == null) {
            req.getSession().setAttribute("errorMessage", "You must firstly login");
            return "login.jsp";
        } else if (admin.getRole() != 1) {
            req.getSession().setAttribute("errorMessage", "You aren't admin");
            return address;
        } else if (req.getParameter("order_id") != null && req.getParameter("status") != null) {
            int orderId = Integer.parseInt(req.getParameter("order_id"));
            String status = req.getParameter("status");
            System.out.println("OrderId ==> " + orderId);
            System.out.println("New status ==> " + status);
            OrderDao orderDao = new OrderDao();
            OrderItemDao orderItemDao = new OrderItemDao();
            Connection con = DbHelper.getInstance().getConnection();

            try {
                con.setAutoCommit(false);
                Order order = orderDao.findById(con, orderId);
                order.setStatus(status);
                if (status.equals("canceled")) {
                    List<OrderItem> orderItemList = orderItemDao.findByOrderId(con, orderId);
                    Iterator it = orderItemList.iterator();

                    while(it.hasNext()) {
                        OrderItem orderItem = (OrderItem)it.next();
                        Product product = productDao.findById(con, orderItem.getProductId());
                        product.setInStock(product.getInStock() + orderItem.getQuantity());
                        System.out.println("product updated ==> " + product);
                        productDao.update(con, product, product);
                    }
                }

                orderDao.update(con, order, order);
                con.commit();
            } catch (SQLException ex) {
                try {
                    con.rollback();
                } catch (SQLException exx) {
                    exx.printStackTrace();
                }

                System.out.println("Can't change status");
                System.out.println(ex.getMessage());
                req.getSession().setAttribute("errorMessage", "Can't change status");
                address = "error.jsp";
            } finally {
                try {
                    con.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }

            return address;
        } else {
            req.getSession().setAttribute("errorMessage", "This order doesn't exist");
            return address;
        }
    }
}
