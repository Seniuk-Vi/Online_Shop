package com.shop.command;

import com.shop.Validation;
import com.shop.command.admin.UnblockUserCommand;
import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.CategoryDao;
import com.shop.models.entity.Category;
import org.apache.log4j.Logger;

import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddCategoryCommand implements Command {
    final static Logger logger = Logger.getLogger(AddCategoryCommand.class);
    private final String error = "Can't add category";
    private final String info = "Category already exist!!!";

    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "addCategory.jsp";
        CategoryDao categoryDao = new CategoryDao();
        Category category = new Category();
        String categoryName = req.getParameter("category");
        if (!Validation.isTitleValid(categoryName)) {
            req.getSession().setAttribute("categoryMessage", "2-10 length, only characters");
            return address;
        }
        category.setCategory(categoryName);
        Connection con = DbHelper.getInstance().getConnection();
        try {
            if (categoryDao.findByName(con, categoryName) != null) throw new DbException();
        } catch (DbException e) {
            req.getSession().setAttribute("categoryMessage", info);
            return address;
        }
        try {
            categoryDao.add(con, category);
            address = "controller?command=showHomePage";
        } catch (DbException ex) {
            req.getSession().setAttribute("categoryMessage", error);
            return address;
        }
        return address;
    }
}
