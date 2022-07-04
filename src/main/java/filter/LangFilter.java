//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@WebFilter({"/*"})
public class LangFilter implements Filter {
    public static final String EN_LANG = "en";
    public static final String UK_LANG = "uk";
    public static final String LANG_COOKIE_NAME = "lang";
    public static final String ENCODING = "UTF-8";


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpServletRequest req = (HttpServletRequest)request;
        String lang = this.getCookieValue(req);
        if (lang == null || !lang.equals(EN_LANG) && !lang.equals(UK_LANG)) {
            request.setAttribute(LANG_COOKIE_NAME, UK_LANG);
        } else {
            request.setAttribute(LANG_COOKIE_NAME, lang);
        }
        chain.doFilter(request, response);
    }

    private String getCookieValue(HttpServletRequest request) {
        if (request != null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                Cookie[] cookies1 = cookies;
                int length = cookies.length;
                for(int i = 0; i < length; ++i) {
                    Cookie cookie = cookies1[i];
                    if (LANG_COOKIE_NAME.equalsIgnoreCase(cookie.getName())) {
                        return cookie.getValue();
                    }
                }
            }
        }
        return null;
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}
