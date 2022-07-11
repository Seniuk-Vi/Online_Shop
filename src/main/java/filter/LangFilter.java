//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;

@WebFilter({"/*"})
public class LangFilter implements Filter {
    public static final String EN_LANG = "en";
    public static final String UK_LANG = "uk";
    public static final String LANG_COOKIE_NAME = "lang";
    public static final String ENCODING = "UTF-8";


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        response.setCharacterEncoding("UTF-8");
        HttpServletRequest req = (HttpServletRequest) request;
        String locale = (String) req.getSession().getAttribute("userLocale");
        if (locale != null && (locale.equals(EN_LANG) || locale.equals(UK_LANG))) {
            //response.setLocale(new Locale(locale));
            req.getSession().setAttribute("currentLocale",locale);
            Config.set( req.getSession(), Config.FMT_LOCALE, new java.util.Locale(locale) );
            System.out.println("userLocale ==> "+locale);
        }
        try {
            chain.doFilter(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }


    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}
