package com.shop.command;

import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.OrderItem;
import com.shop.models.entity.Product;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddToCartCommand implements Command {
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().removeAttribute("errorMessageCart");
        String address = "homePage.jsp";
        OrderItem orderItem = new OrderItem();
        ProductDao productDao = new ProductDao();
        Connection con = DbHelper.getInstance().getConnection();
        int productId = Integer.parseInt(req.getParameter("product_id"));
        orderItem.setProductId(productId);
        orderItem.setQuantity(1);
        System.out.println("OrderItem ==> " + orderItem);
        Map<Product, OrderItem> orderItems = null;

        try {
            orderItems = (Map)req.getSession().getAttribute("cart");
            if (orderItems == null) {
                orderItems = new HashMap();
            }

            Product product = productDao.findById(con, productId);
            if (((Map)orderItems).get(product) == null) {
                ((Map)orderItems).put(product, orderItem);
            } else {
                req.getSession().setAttribute("errorMessageCart", "This product is already in your cart");
            }

            con.close();
            req.getSession().removeAttribute("cart");
            req.getSession().setAttribute("cart", orderItems);
        } catch (DbException | SQLException ex) {
            System.out.println("Can't add to cart ==> " + orderItem);
            address = "/";
            req.getSession().setAttribute("errorMessageCart", "Can't add to cart!!");
        }

        System.out.println("orderItems ==> " + orderItems.toString());
        return address;
    }
}
