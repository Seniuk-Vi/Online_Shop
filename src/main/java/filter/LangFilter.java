//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package filter;

import org.apache.log4j.Logger;

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
    public static final String LANG_COOKIE_NAME = "userLocale";
    public static final String ENCODING = "UTF-8";
    final static Logger logger = Logger.getLogger(LangFilter.class);
    final String error = "Can't change charset to " + ENCODING;
    final String errorFilter = "Can't do chain filter";

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            request.setCharacterEncoding(ENCODING);
        } catch (UnsupportedEncodingException e) {
            logger.fatal(error);
            throw new RuntimeException(e);
        }
        response.setCharacterEncoding(ENCODING);
        HttpServletRequest req = (HttpServletRequest) request;
        String locale = (String) req.getSession().getAttribute(LANG_COOKIE_NAME);
        if (locale != null && (locale.equals(EN_LANG) || locale.equals(UK_LANG))) {
            req.getSession().setAttribute("currentLocale", locale);
            Config.set(req.getSession(), Config.FMT_LOCALE, new java.util.Locale(locale));
        }
        try {
            chain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            logger.fatal(errorFilter);
            throw new RuntimeException(e);
        }
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}
