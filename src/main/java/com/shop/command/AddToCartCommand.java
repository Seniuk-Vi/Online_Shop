package com.shop.command;

import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.CategoryDao;
import com.shop.db.dao.OrderItemDao;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.Category;
import com.shop.models.entity.Order;
import com.shop.models.entity.OrderItem;
import com.shop.models.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddToCartCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String address = "homePage.jsp";

        OrderItemDao orderItemDao = new OrderItemDao();
        OrderItem orderItem = new OrderItem();

        ProductDao productDao = new ProductDao();
        Product product = new Product();

        Connection con = DbHelper.getInstance().getConnection();
        // int orderId = Integer.parseInt(req.getParameter("order_id"));
        int productId = Integer.parseInt(req.getParameter("product_id"));


        // todo check if param is valid

        //

        orderItem.setProductId(productId);
        orderItem.setQuantity(1);

        Map<Product,OrderItem> orderItems = null;

        try {
            orderItems = (Map<Product, OrderItem>) req.getSession().getAttribute("cart");
            if(orderItems==null){
                orderItems= new HashMap<Product, OrderItem>();
            }
            product = productDao.findById(con,productId);
            orderItems.put(product,orderItem);
            con.close();
            req.getSession().setAttribute("cart", orderItems);
        } catch (Exception ex) {
            System.out.println("Can't add to cart ==> " + orderItem);
            address = "/";
            req.getSession().setAttribute("errorMessage", "Can't add to cart!!");
        }
        System.out.println("orderItems ==> "+orderItems.toString());
        ShowHomePageCommand s = new ShowHomePageCommand();
        address = s.execute(req, resp);
        return address;

    }
}
