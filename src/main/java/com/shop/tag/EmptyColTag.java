package com.shop.tag;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class EmptyColTag extends TagSupport {
    private int iterations;
    private int counter;

    @Override
    public int doStartTag() throws JspException {
        counter = 0;
        JspWriter jspWriter = pageContext.getOut();
        while (counter < iterations) {
            try {
                jspWriter.print("<td></td>");
                counter++;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        counter = 0;
        return EVAL_BODY_INCLUDE;
    }


    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
}
