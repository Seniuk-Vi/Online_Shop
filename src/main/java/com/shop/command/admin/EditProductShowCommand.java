//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.command.admin;

import com.shop.command.Command;
import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.CategoryDao;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.Category;
import com.shop.models.entity.Product;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditProductShowCommand implements Command {
    final static Logger logger = Logger.getLogger(EditProductShowCommand.class);
    private final String error = "Can't edit product";
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "editProduct.jsp";
        String productId = req.getParameter("product_id");
        Connection con = DbHelper.getInstance().getConnection();
        ProductDao productDao = new ProductDao();
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categories;
        Product product;
        try {
            categories = categoryDao.findAll(con);
            product = productDao.findById(con, Integer.parseInt(productId));
        } catch (DbException ex) {
            logger.error(error,ex);
            req.getSession().setAttribute("errorMessage",error);
            return address;
        }
        try {
            con.close();
        } catch (SQLException ex) {
          logger.error("Can't close connection",ex);
        }
        req.getSession().setAttribute("product", product);
        req.getSession().setAttribute("categories", categories);
        return address;
    }
}
