package com.shop.command;

import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.OrderItem;
import com.shop.models.entity.Product;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteProductFromCartCommand implements Command {

    public String execute(HttpServletRequest req, HttpServletResponse resp)  {
        String address = "displayCart.jsp";
        Map<Product, OrderItem> orderItems = (Map)req.getSession().getAttribute("cart");
        Integer product_id = Integer.valueOf(req.getParameter("product_id"));
        ProductDao productDao = new ProductDao();
        Connection con = DbHelper.getInstance().getConnection();
        Product product ;
        try {
            product = productDao.findById(con, product_id);
        } catch (DbException e) {
            req.getSession().setAttribute("errorMessage","Can't delete this product");
            return address;
        }
        System.out.println("Before ==> " + orderItems);
        orderItems.remove(product);
        System.out.println("After ==> " + orderItems);
        req.getSession().setAttribute("cart", orderItems);
        return address;
    }
}
