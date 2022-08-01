//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package filter;

import com.shop.models.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

@WebFilter({"/*"})
public class UserFilter implements Filter {
    final static Logger logger = Logger.getLogger(UserFilter.class);
    final String errorFilter = "Can't do chain filter";

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest req = (HttpServletRequest) request;
        User user = (User) req.getSession().getAttribute("currentUser");
        if (user != null && user.getRole() == 2) {
            req.getSession().invalidate();
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
