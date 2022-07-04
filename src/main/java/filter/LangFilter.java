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

    public LangFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpServletRequest req = (HttpServletRequest)request;
        String lang = this.getCookieValue(req);
        if (lang == null || !lang.equals("en") && !lang.equals("uk")) {
            request.setAttribute("lang", "uk");
        } else {
            request.setAttribute("lang", lang);
        }

        chain.doFilter(request, response);
    }

    private String getCookieValue(HttpServletRequest request) {
        if (request != null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                Cookie[] var3 = cookies;
                int var4 = cookies.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    Cookie cookie = var3[var5];
                    if ("lang".equalsIgnoreCase(cookie.getName())) {
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
