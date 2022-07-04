//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.shop.command;

import com.shop.Validation;
import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.CategoryDao;
import com.shop.models.entity.Category;
import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddCategoryCommand implements Command {
    public AddCategoryCommand() {
    }

    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "controller?command=showHomePage";
        CategoryDao categoryDao = new CategoryDao();
        Category category = new Category();
        String categoryName = req.getParameter("category");
        if (!Validation.isTitleValid(categoryName)) {
            req.getSession().setAttribute("categoryMessage", "2-10 length, only characters");
            System.out.println("surname must contain only characters (2-10)");
            return "addCategory.jsp";
        } else {
            category.setCategory(categoryName);
            Connection con = DbHelper.getInstance().getConnection();

            try {
                categoryDao.add(con, category);
            } catch (DbException var9) {
                System.out.println("Can't add category ==> " + category);
                address = "addCategory.jsp";
                req.getSession().setAttribute("categoryMessage", "Category already exist!!!");
            }

            return address;
        }
    }
}
