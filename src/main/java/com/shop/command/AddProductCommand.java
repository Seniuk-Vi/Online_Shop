package com.shop.command;

import com.mysql.cj.Session;
import com.shop.Validation;
import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import com.shop.db.dao.UserDao;
import com.shop.models.entity.Product;
import com.shop.models.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AddProductCommand implements Command {


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        req.getSession().removeAttribute("errorMessage");
        String address = "controller?command=showHomePage";

        ProductDao productDao = new ProductDao();
        Product product = new Product();

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        int price = Integer.parseInt(req.getParameter("price"));
//        String imageUrl = req.getParameter("image_url");
        int model_year = Integer.parseInt(req.getParameter("model_year"));
        int inStock = Integer.parseInt(req.getParameter("in_stock"));
        String categotyId = req.getParameter("category");
        String condition = req.getParameter("state");

        // image downloading
        String fileName = (String) req.getSession().getAttribute("imageName");
//        System.out.println("fileName ==> " + fileName);
//
//        InputStream fileContent = new ByteArrayInputStream(req.getSession().getAttribute("imageStream").toString()
//                .getBytes(StandardCharsets.UTF_8));
//
//        String imageAddress = (String) req.getSession().getAttribute("imageAddress");
//        imageAddress = imageAddress.concat("\\shopImageFiles");
//        System.out.println("imageAddress ==> " + imageAddress);
//
//        File directory = new File(imageAddress);
//        if (! directory.exists()){
//            directory.mkdir();
//        }
//        imageAddress = imageAddress.concat("\\"+fileName);
//        Files.copy(fileContent, Paths.get(imageAddress), StandardCopyOption.REPLACE_EXISTING);
//

        String imageUrl = fileName;
        // todo check if param is valid

        //

        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);
        product.setModelYear(model_year);
        product.setInStock(inStock);
        product.setCategory(categotyId);
        product.setCondition(condition);

        Connection con;

        con = DbHelper.getInstance().getConnection();

        try {
            productDao.add(con, product);

        } catch (DbException ex) {
            System.out.println("Can't add product ==> " + product);
            req.getSession().setAttribute("errorMessage", "Can't add product!!");
            address = "addProduct.jsp";
        }

        return address;

    }

    private String passAttributesToSession(HttpServletRequest request, HttpServletResponse response, Map<String, String> viewAttributes) throws ServletException, IOException {
        for (Map.Entry<String, String> entry : viewAttributes.entrySet())
            request.getSession().setAttribute(entry.getKey(), entry.getValue());
        return "registration.jsp";
    }

}
