package com.shop.command;

import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.CategoryDao;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.Category;
import com.shop.models.entity.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;

public class AddCategoryCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String address = "homePage.jsp";

        CategoryDao categoryDao = new CategoryDao();
        Category category = new Category();

        String categoryName = req.getParameter("category");

        // todo check if param is valid

        //

        category.setCategory(categoryName);

        Connection con;

        con = DbHelper.getInstance().getConnection();

        try {
            categoryDao.add(con, category);

        } catch (DbException ex) {
            System.out.println("Can't add category ==> " + category);
            address="addCategory.jsp";
            req.getSession().setAttribute("errorMessage","Category already exist!!!");
        }

        return address;

    }

}
