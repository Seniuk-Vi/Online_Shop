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

    ArrayList<Product> products;


    public ArrayList<Product>  getProducts() {
        return products;
    }
    public void setProducts(ArrayList<Product>  products) {
        this.products = products;
    }


    @Override
    public void doTag() throws JspException, IOException {
        JspContext jc = getJspContext();
        for(var obj : products)
        {
            jc.setAttribute("OneProduct", obj);
            getJspBody().invoke(null);
        }
    }
}