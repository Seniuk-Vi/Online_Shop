package com.shop.command;

import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.OrderItem;
import com.shop.models.entity.Product;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DecreaseQuantityCommand implements Command {
    final static Logger logger = Logger.getLogger(DecreaseQuantityCommand.class);
    private final String error = "Can't decrease quantity";
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "displayCart.jsp";
        Map<Product, OrderItem> orderItems = (Map<Product, OrderItem>) req.getSession().getAttribute("cart");
        int product_id = Integer.parseInt(req.getParameter("product_id"));
        ProductDao productDao = new ProductDao();
        Connection con = DbHelper.getInstance().getConnection();
        Product product;
        try {
            product = productDao.findById(con, product_id);
        } catch (DbException e) {
            logger.error(error,e);
            req.getSession().setAttribute("errorMessage",error);
            return address;
        }
        OrderItem orderItem = orderItems.get(product);
        orderItems.remove(product);
        if (orderItem.getQuantity() > 1) {
            orderItem.setQuantity(orderItem.getQuantity() - 1);
            orderItems.put(product, orderItem);
        }else {
            logger.error(error);
            req.getSession().setAttribute("errorMessage",error);
            return address;
        }
        req.getSession().setAttribute("cart", orderItems);
        return address;
    }
}
