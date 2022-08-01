package com.shop.command;

import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.OrderItem;
import com.shop.models.entity.Product;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddToCartCommand implements Command {
    final static Logger logger = Logger.getLogger(AddProductCommand.class);
    private final String error = "Can't add to cart";
    private final String info = "This product is already in your cart";

    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "controller?command=showHomePage";
        OrderItem orderItem = new OrderItem();
        ProductDao productDao = new ProductDao();
        Connection con = DbHelper.getInstance().getConnection();
        int productId = Integer.parseInt(req.getParameter("product_id"));
        orderItem.setProductId(productId);
        orderItem.setQuantity(1);
        Map<Product, OrderItem> orderItems;
        try {
            orderItems = (Map<Product, OrderItem>) req.getSession().getAttribute("cart");
            if (orderItems == null) {
                orderItems = new HashMap();
            }
            Product product = productDao.findById(con, productId);
            if (orderItems.get(product) == null) {
                orderItems.put(product, orderItem);
            } else {
                logger.info(info);
                req.getSession().setAttribute("errorMessageCart", info);
            }
            req.getSession().removeAttribute("cart");
            req.getSession().setAttribute("cart", orderItems);
        } catch (DbException ex) {
            req.getSession().setAttribute("errorMessageCart", error);
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                logger.error("Can't close Connection");
            }
        }
        return address;
    }
}
