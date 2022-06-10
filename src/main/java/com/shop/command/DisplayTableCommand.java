package com.shop.command;

import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class DisplayTableCommand implements Command {


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String address = "showTables.jsp";

        ProductDao productDao = new ProductDao();
        Connection con = DbHelper.getInstance().getConnection();
        List<Product> table = productDao.findAll(con);
        System.out.println(table);
        req.getSession().setAttribute("products",table);
        return address;
    }
}
