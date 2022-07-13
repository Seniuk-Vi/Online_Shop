package com.shop.tag;

import com.shop.models.entity.Product;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.ArrayList;

public class ProductListTag extends SimpleTagSupport {
    //Sample 01: Define the books as a collection
    @SuppressWarnings("rawtypes")
    ArrayList products;

    //FragAT 01: TitlePrice
    JspFragment print_product;

    @SuppressWarnings("rawtypes")
    public ArrayList getProducts() {
        return products;
    }
    @SuppressWarnings("rawtypes")
    public void setProducts(ArrayList products) {
        this.products = products;
    }


    @Override
    public void doTag() throws JspException, IOException {
        JspContext jc = getJspContext();
        for(Object obj : products)
        {
            Product product = (Product) obj;
            jc.setAttribute("OneProduct", product);
            getJspBody().invoke(null);
        }
    }
}