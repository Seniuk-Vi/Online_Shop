//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.command.admin;

import com.shop.command.Command;
import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteProductCommand implements Command {
    public DeleteProductCommand() {
    }

    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "controller?command=showHomePage";
        String productId = req.getParameter("product_id");
        Connection con = DbHelper.getInstance().getConnection();
        ProductDao productDao = new ProductDao();

        try {
            productDao.deleteById(con, Integer.parseInt(productId));
        } catch (SQLException var8) {
            var8.printStackTrace();
        }

        req.getSession().setAttribute("info", "product was deleted");
        return address;
    }
}
