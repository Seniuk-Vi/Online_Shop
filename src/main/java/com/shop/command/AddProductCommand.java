
package com.shop.command;

import com.shop.Validation;
import com.shop.db.DbException;
import com.shop.db.DbHelper;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.Product;

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

    public String execute(HttpServletRequest req, HttpServletResponse resp)  {
        req.getSession().removeAttribute("errorMessage");
        String address = "controller?command=showHomePage";
        ProductDao productDao = new ProductDao();
        Product product = new Product();
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        int price = Integer.parseInt(req.getParameter("price"));
        int model_year = Integer.parseInt(req.getParameter("model_year"));
        int inStock = Integer.parseInt(req.getParameter("in_stock"));
        String categoryId = req.getParameter("productCategory");
        String condition = req.getParameter("state");
        Part filePart;
        try {
            filePart = req.getPart("image_url");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        System.out.println(fileName);
        InputStream fileContent = null;
        try {
            fileContent = filePart.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String imageAddress = (String)req.getSession().getAttribute("path");
        imageAddress = imageAddress + fileName;
        System.out.println("address ==> " + imageAddress);
        try {
            Files.copy(fileContent, Paths.get(imageAddress), new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<String, String> registrationAttributes = new HashMap();
        registrationAttributes.put("title", title);
        registrationAttributes.put("description", description);
        registrationAttributes.put("price", String.valueOf(price));
        registrationAttributes.put("model_year", String.valueOf(model_year));
        registrationAttributes.put("in_stock", String.valueOf(inStock));
        registrationAttributes.put("category", categoryId);
        registrationAttributes.put("state", condition);
        if (!Validation.isTitleValid(title)) {
            registrationAttributes.put("titleMessage", "2-10 length, title only letters");
            System.out.println("2-10 length, title only letters");
            return this.passAttributesToSession(req, resp, registrationAttributes);
        } else if (!Validation.isDescValid(description)) {
            registrationAttributes.put("descriptionMessage", "60-500 length");
            System.out.println("description don't valid");
            return this.passAttributesToSession(req, resp, registrationAttributes);
        } else if (!Validation.isPriceValid(price)) {
            registrationAttributes.put("priceMessage", "1-10000 price");
            System.out.println("price don't valid");
            return this.passAttributesToSession(req, resp, registrationAttributes);
        } else if (!Validation.isYearValid(String.valueOf(model_year))) {
            registrationAttributes.put("modelYearMessage", "only 4 digits");
            System.out.println("model_year don't valid");
            return this.passAttributesToSession(req, resp, registrationAttributes);
        } else if (!Validation.isStockValid(inStock)) {
            registrationAttributes.put("inStockMessage", "min 0 in stock");
            System.out.println("inStock don't valid");
            return this.passAttributesToSession(req, resp, registrationAttributes);
        } else if (!Validation.isStateValid(Integer.parseInt(condition))) {
            registrationAttributes.put("conditionMessage", "1-10 condition");
            System.out.println("condition don't valid");
            return this.passAttributesToSession(req, resp, registrationAttributes);
        } else {
            product.setTitle(title);
            product.setDescription(description);
            product.setPrice((double)price);
            product.setImageUrl(fileName);
            product.setModelYear(model_year);
            product.setInStock(inStock);
            product.setCategory(categoryId);
            product.setCondition(condition);
            Connection con = DbHelper.getInstance().getConnection();

            try {
                productDao.add(con, product);
            } catch (DbException ex) {
                System.out.println("Can't add product ==> " + product);
                req.getSession().setAttribute("errorMessage", "Can't add product!!");
                address = "addProduct.jsp";
            }

            return address;
        }
    }

    private String passAttributesToSession(HttpServletRequest request, HttpServletResponse response, Map<String, String> viewAttributes) {
        Iterator iterator = viewAttributes.entrySet().iterator();

        while(iterator.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry)iterator.next();
            request.getSession().setAttribute(entry.getKey(), entry.getValue());
        }
        return "addProduct.jsp";
    }
}
