package com.shop.command;

import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.CategoryDao;
import com.shop.db.dao.OrderItemDao;
import com.shop.models.entity.Category;
import com.shop.models.entity.OrderItem;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;

public class AddToCartCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String address = "displayCart.jsp";

        OrderItemDao orderItemDao = new OrderItemDao();
        OrderItem orderItem = new OrderItem();

        // int orderId = Integer.parseInt(req.getParameter("order_id"));
        int productId = Integer.parseInt(req.getParameter("product_id"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        // todo check if param is valid

        //

        orderItem.setProductId(productId);
        orderItem.setQuantity(quantity);

        ArrayList<OrderItem> orderItems = null;
        try {
            orderItems = (ArrayList<OrderItem>) req.getSession().getAttribute("cart");
            if(orderItems==null){
                orderItems= new ArrayList<>();
            }
            orderItems.add(orderItem);
            req.getSession().setAttribute("cart", orderItems);
        } catch (Exception ex) {
            System.out.println("Can't add to cart ==> " + orderItem);
            address = "/";
            req.getSession().setAttribute("errorMessage", "Can't add to cart!!");
        }
        System.out.println("orderItems ==> "+orderItems.toString());

        return address;

    }
}
