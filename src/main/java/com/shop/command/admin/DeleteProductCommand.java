//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.command.admin;

import com.shop.command.Command;
import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteProductCommand implements Command {
    final static Logger logger = Logger.getLogger(DeleteProductCommand.class);

    private final String error = "Can't delete product";
    private final String info = "Product was deleted";

    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "controller?command=showHomePage";
        String productId = req.getParameter("product_id");
        Connection con = DbHelper.getInstance().getConnection();
        ProductDao productDao = new ProductDao();
        try {
            productDao.deleteById(con, Integer.parseInt(productId));
        } catch (DbException ex) {
            logger.error(error,ex);
            req.getSession().setAttribute("errorMessage",error);
        }
        req.getSession().setAttribute("info", info);
        return address;
    }
}
