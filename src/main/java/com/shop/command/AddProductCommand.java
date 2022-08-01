
package com.shop.command;

import com.shop.Validation;
import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.Product;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class AddProductCommand implements Command {
    final static Logger logger = Logger.getLogger(AddProductCommand.class);
    private final String error = "Can't add product";
    private final String imageError = "Can't add image";

    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().removeAttribute("errorMessage");
        String address = "controller?command=showHomePage";
        ProductDao productDao = new ProductDao();
        Product product = new Product();
        // get params from request
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        int price = Integer.parseInt(req.getParameter("price"));
        int model_year = Integer.parseInt(req.getParameter("model_year"));
        int inStock = Integer.parseInt(req.getParameter("in_stock"));
        String categoryId = req.getParameter("productCategory");
        String condition = req.getParameter("state");
        // send params back to user
        Map<String, String> registrationAttributes = new HashMap();
        registrationAttributes.put("title", title);
        registrationAttributes.put("description", description);
        registrationAttributes.put("price", String.valueOf(price));
        registrationAttributes.put("model_year", String.valueOf(model_year));
        registrationAttributes.put("in_stock", String.valueOf(inStock));
        registrationAttributes.put("category", categoryId);
        registrationAttributes.put("state", condition);
        // add image
        String fileName;
        try {
            Part filePart;
            filePart = req.getPart("image_url");
            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            InputStream fileContent;
            fileContent = filePart.getInputStream();
            String imageAddress = (String) req.getServletContext().getAttribute("path");
            imageAddress = imageAddress + fileName;
            Files.copy(fileContent, Paths.get(imageAddress), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException | ServletException e) {
            logger.error(imageError, e);
            registrationAttributes.put("errorMessage",imageError);
            return passAttributesToSession(req, resp, registrationAttributes);
        }
        // validate params
        if (!Validation.isTitleValid(title)) {
            registrationAttributes.put("titleMessage", "2-10 length, title only letters");
            return passAttributesToSession(req, resp, registrationAttributes);
        }
        if (!Validation.isDescValid(description)) {
            registrationAttributes.put("descriptionMessage", "60-500 length");
            return passAttributesToSession(req, resp, registrationAttributes);
        }
        if (!Validation.isPriceValid(price)) {
            registrationAttributes.put("priceMessage", "1-10000 price");
            return passAttributesToSession(req, resp, registrationAttributes);
        }
        if (!Validation.isYearValid(String.valueOf(model_year))) {
            registrationAttributes.put("modelYearMessage", "only 4 digits");
            return passAttributesToSession(req, resp, registrationAttributes);
        }
        if (!Validation.isStockValid(inStock)) {
            registrationAttributes.put("inStockMessage", "min 0 in stock");
            return passAttributesToSession(req, resp, registrationAttributes);
        }
        if (!Validation.isStateValid(Integer.parseInt(condition))) {
            registrationAttributes.put("conditionMessage", "1-10 condition");
            return passAttributesToSession(req, resp, registrationAttributes);
        }
        // add product
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(fileName);
        product.setModelYear(model_year);
        product.setInStock(inStock);
        product.setCategory(categoryId);
        product.setCondition(condition);
        Connection con = DbHelper.getInstance().getConnection();
        try {
            productDao.add(con, product);
        } catch (DbException ex) {
            logger.error(error);
            req.getSession().setAttribute("errorMessage", "Can't add product!!");
            address = "addProduct.jsp";
        }

        return address;

    }

    private String passAttributesToSession(HttpServletRequest request, HttpServletResponse response, Map<String, String> viewAttributes) {
        for (Map.Entry<String, String> entry : viewAttributes.entrySet()) {
            request.getSession().setAttribute(entry.getKey(), entry.getValue());
        }
        return "addProduct.jsp";
    }
}
