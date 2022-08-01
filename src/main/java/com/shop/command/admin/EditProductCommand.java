//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.command.admin;

import com.shop.Validation;
import com.shop.command.Command;
import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.Product;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditProductCommand implements Command {
    final static Logger logger = Logger.getLogger(EditProductCommand.class);
    private final String error = "Can't edit product";
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "editProduct.jsp";
        String productId = req.getParameter("product_id");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String price = req.getParameter("price");
        String modelYear = req.getParameter("model_year");
        String inStock = req.getParameter("in_stock");
        String category = req.getParameter("category");
        String state = req.getParameter("state");
        if (!Validation.isTitleValid(title)) {
            req.getSession().setAttribute("titleMessage", "2-10 length, title only letters");
            return address;
        }
        if (!Validation.isDescValid(description)) {
            req.getSession().setAttribute("descriptionMessage", "60-500 length");
            return address;
        }
        if (!Validation.isPriceValid(Integer.parseInt(price))) {
            req.getSession().setAttribute("priceMessage", "1-10000 price");
            return address;
        }
        if (!Validation.isYearValid(String.valueOf(modelYear))) {
            req.getSession().setAttribute("modelYearMessage", "only 4 digits");
            return address;
        }
        if (!Validation.isStockValid(Integer.parseInt(inStock))) {
            req.getSession().setAttribute("inStockMessage", "min 0 in stock");
            return address;
        }
        if (!Validation.isStateValid(Integer.parseInt(state))) {
            req.getSession().setAttribute("stateMessage", "1-10 condition");
            return address;
        }
        Connection con = DbHelper.getInstance().getConnection();
        ProductDao productDao = new ProductDao();
        Product product ;
        try {
            product = productDao.findById(con, Integer.parseInt(productId));
        } catch (DbException ex) {
            req.getSession().setAttribute("errorMessage",error);
            logger.error(error);
            return address;
        }
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(Integer.parseInt(price));
        product.setModelYear(Integer.parseInt(modelYear));
        product.setInStock(Integer.parseInt(inStock));
        product.setCategory(category);
        product.setCondition(state);
        try {
            productDao.update(con, product, product);
            req.getSession().setAttribute("info", "product has changed");
        } catch (DbException ex) {
            req.getSession().setAttribute("errorMessage", error);
            logger.error(error);
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                logger.error("Can't close connection",ex);
            }
        }
        req.getSession().setAttribute("product", product);
        return address;

    }
}
