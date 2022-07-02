package com.shop.command;

import com.mysql.cj.Session;
import com.shop.db.DbHelper;
import com.shop.db.dao.CategoryDao;
import com.shop.db.dao.ProductDao;
import com.shop.models.entity.Category;
import com.shop.models.entity.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ShowHomePageCommand implements Command {
    private final int recordsPerPage = 8;

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String address = "homePage.jsp";

        ProductDao productDao = new ProductDao();
        Connection con = DbHelper.getInstance().getConnection();
        List<Product> table = null;
        List<Category> categories = null;

        CategoryDao categoryDao = new CategoryDao();


        int maxPrice = (int) productDao.getMaxPrice(con);
        int minPrice = (int) productDao.getMinPrice(con);
        req.getSession().setAttribute("priceMax",maxPrice);
        req.getSession().setAttribute("priceMin",minPrice);
        try {
            categories  = categoryDao.findAll(con);
            table = selection(req, con, maxPrice, minPrice);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (table.isEmpty()) {

            req.getSession().setAttribute("errorMessage", "No results for you");
        }
        System.out.println(table);


        req.getSession().setAttribute("categories",categories);
        req.getSession().setAttribute("products", table);
        return address;
    }

    private List<Product> selection(HttpServletRequest req, Connection con, int maxPrice, int minPrice) {
        ProductDao productDao = new ProductDao();

        //sorting
        String category = req.getParameter("category");
        String priceMinS = req.getParameter("priceMin");
        String priceMaxS = req.getParameter("priceMax");
        String order = req.getParameter("sort");
        String search = req.getParameter("search");

        System.out.println("category ==> " + category);
        // validate input sort data
        // if req params is null
        if (category == null) {
            category = String.valueOf(req.getSession().getAttribute("category"));
        }
        if (search == null) {
            search = String.valueOf(req.getSession().getAttribute("search"));
        }
        if (priceMinS == null) {
            priceMinS = String.valueOf(req.getSession().getAttribute("priceMinS"));
        }
        if (priceMaxS == null) {
            priceMaxS = String.valueOf(req.getSession().getAttribute("priceMaxS"));
        }
        if (order == null) {
            order = String.valueOf(req.getSession().getAttribute("order"));
        }

        int minPriceS=minPrice;
        int maxPriceS=maxPrice;
        String currentCategory;
        if (category.equals("null")) {
            category = "%";
        }
        currentCategory = category;
        if (category.equals("all")){
            category = "%";
        }
        if (category.equals("%")){
            currentCategory="all";
        }
        if (search.equals("null")){
            search="";
        }
        if (!priceMinS.equals("null")) {
            minPriceS = Integer.parseInt(priceMinS);
        }
        if (!priceMaxS.equals("null")) {
            maxPriceS = Integer.parseInt(priceMaxS);
        }
        if (order.equals("null")) {
            order = "price(asc)";
        }


        System.out.println("minPriceS ==> " + minPriceS);
        System.out.println("maxPriceS ==> " + maxPriceS);
        System.out.println("order ==> " + order);
        System.out.println("category ==> " + category);


        String way = "ASC";
        String sort;


        // todo validate data
        switch (order) {
            case "price(asc)":
                sort = "price";
                break;
            case "price(desc)":
                sort = "price";
                way = "DESC";
                break;
            case "newest":
                sort = "model_year";
                break;
            case "oldest":
                sort = "model_year";
                way = "DESC";
                break;
            case "a-z":
                sort = "title";
                break;
            case "z-a":
                sort = "title";
                way = "DESC";
                break;
            default:
                sort = "price";
        }

        //pagination
        Integer currentPage;
        currentPage = (Integer) req.getSession().getAttribute("currentPage");
        var page = req.getParameter("tPage");
        if (page != null) {
            currentPage = Integer.valueOf(page);
        }
        System.out.println("currentPage ==> " + currentPage);
        int rowsCount = productDao.getRowsCount(con, category, minPriceS, maxPriceS,search);
        System.out.println("rowsCount ==> " + rowsCount);
//        if (rowsCount == 0) {
//            req.getSession().setAttribute("errorMessage", "Show is empty");
//            //todo if empty
//        }
        int noOfPages = rowsCount / recordsPerPage;
        if ((noOfPages * rowsCount) % recordsPerPage > 0) {
            System.out.println("numberOfPages ==> " + noOfPages);
            noOfPages++;
        }
        System.out.println("numberOfPages ==> " + noOfPages);
        if (currentPage == null) {
            currentPage = 1;
        }

        req.getSession().setAttribute("category", currentCategory);
        req.getSession().setAttribute("sort", order);
        req.getSession().setAttribute("priceMinS", minPriceS);
        req.getSession().setAttribute("priceMaxS", maxPriceS);
        req.getSession().setAttribute("currentPage", currentPage);
        req.getSession().setAttribute("noOfPages", noOfPages);
        req.getSession().setAttribute("search", search);

        List<Product> table;
        table = productDao.findAllWithSelect(con, recordsPerPage, (currentPage - 1) * recordsPerPage, category, minPriceS, maxPriceS, sort, way,search);
        return table;
    }


}
