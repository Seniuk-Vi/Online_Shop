//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.command;

import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.OrderItem;
import com.shop.models.entity.Product;
import java.sql.Connection;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DecreaseQuantityCommand implements Command {
    public DecreaseQuantityCommand() {
    }

    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String address = "displayCart.jsp";
        Map<Product, OrderItem> orderItems = (Map)req.getSession().getAttribute("cart");
        int product_id = Integer.valueOf(req.getParameter("product_id"));
        ProductDao productDao = new ProductDao();
        Connection con = DbHelper.getInstance().getConnection();
        Product product = productDao.findById(con, product_id);
        OrderItem orderItem = (OrderItem)orderItems.get(product);
        System.out.println("Before ==> " + orderItems);
        orderItems.remove(product);
        System.out.println("OrderItem ==> " + orderItem);
        if (orderItem.getQuantity() > 1) {
            orderItem.setQuantity(orderItem.getQuantity() - 1);
            orderItems.put(product, orderItem);
        }

        System.out.println("After ==> " + orderItems);
        req.getSession().setAttribute("cart", orderItems);
        return address;
    }
}
