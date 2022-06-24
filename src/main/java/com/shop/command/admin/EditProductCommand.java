package com.shop.command.admin;

import com.shop.command.Command;
import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.OrderItem;
import com.shop.models.entity.Product;
import com.shop.models.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EditProductCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "homePage.jsp";

        req.getSession().removeAttribute("errorMessage");
        // move to homaPage if not in
        User admin;
        admin = (User) req.getSession().getAttribute("currentUser");
        if (admin == null) {
            req.getSession().setAttribute("errorMessage", "You must firstly login");
            return "login.jsp";
        }
        if(admin.getRole()!=1){
            req.getSession().setAttribute("errorMessage", "You aren't admin");
            return address;
        }

        // get data
        int productId;
        if(req.getParameter("product_id")!=null){
            productId= Integer.parseInt(req.getParameter("product_id"));
        }else {
            req.getSession().setAttribute("errorMessage", "This product doesn't exist");
            return address;
        }

        // todo check data
        System.out.println("ProductId ==> " + productId);


        // get User
        ProductDao productDao = new ProductDao();
        Product product;
        Connection con = DbHelper.getInstance().getConnection();

        try {
           product = productDao.findById(con,productId);
        } catch (SQLException ex) {
            System.out.println("Can't obtain product from DB");
            req.getSession().setAttribute("errorMessage", "Can't get product");
            return "error.jsp";
        }
        req.getSession().setAttribute("product",product);
        address = "editProduct.jsp";
        return address;

    }
}
