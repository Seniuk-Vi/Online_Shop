//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
import com.shop.models.entity.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditOrderStatusCommand implements Command {
    final static Logger logger = Logger.getLogger(EditOrderStatusCommand.class);

    private final String error = "Can't edit order status";

    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "controller?command=showOrders";
        ProductDao productDao = new ProductDao();
        if (req.getParameter("order_id") == null && req.getParameter("status") == null) {
            req.getSession().setAttribute("errorMessage", "This order doesn't exist");
        }
        int orderId = Integer.parseInt(req.getParameter("order_id"));
        String status = req.getParameter("status");
        OrderDao orderDao = new OrderDao();
        OrderItemDao orderItemDao = new OrderItemDao();
        Connection con = DbHelper.getInstance().getConnection();
        try {
            con.setAutoCommit(false);
            Order order = orderDao.findById(con, orderId);
            order.setStatus(status);
            // release products
            if (status.equals("canceled")) {
                List<OrderItem> orderItemList = orderItemDao.findByOrderId(con, orderId);
                for (OrderItem orderItem : orderItemList) {
                    Product product = productDao.findById(con, orderItem.getProductId());
                    product.setInStock(product.getInStock() + orderItem.getQuantity());
                    productDao.update(con, product, product);
                }
            }
            orderDao.update(con, order, order);
            con.commit();
        } catch (DbException | SQLException ex) {
            try {
                con.rollback();
            } catch (SQLException exx) {
                logger.error("Can't rollback", ex);
            }
            req.getSession().setAttribute("errorMessage", error);
            address = "error.jsp";
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                logger.error("Can't close connection", ex);

            }
        }
        return address;
    }
}
