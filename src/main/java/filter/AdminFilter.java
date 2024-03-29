//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package filter;

import com.shop.command.ShowUserCommand;
import com.shop.models.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

@WebFilter({"/*"})
public class AdminFilter implements Filter {
    final static Logger logger = Logger.getLogger(AdminFilter.class);
    final String message = "Current path ==> ";
    final String errorMessage = "Page not found";
    public static final String ADD_PRODUCT_PATH = "/addProduct.jsp";
    public static final String ADD_CATEGORY_PATH = "/addCategory.jsp";
    public static final String UPDATE_PRODUCT_PATH = "/editProduct.jsp";
    public static final String USERS_PATH = "/showUsers.jsp";
    public static final String ALL_ORDERS_PATH = "/orders.jsp";
    public static final String errorPage = "error.jsp";

    public static final List<String> ADMIN_PATHS = Arrays.asList(ADD_PRODUCT_PATH, USERS_PATH,
            ALL_ORDERS_PATH, ADD_CATEGORY_PATH, UPDATE_PRODUCT_PATH);

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest req = (HttpServletRequest) request;
        logger.info(message + req.getServletPath());
        // if we're trying to open admin page, but we are not admin then go to home page
        if (ADMIN_PATHS.contains(req.getServletPath())) {
            User user = (User) req.getSession().getAttribute("currentUser");
            if (user == null || user.getRole() != 1) {
                try {
                    req.getSession().setAttribute("errorMessage", errorMessage);
                    request.getRequestDispatcher(errorPage).forward(request, response);
                } catch (ServletException | IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
        }
        try {
            chain.doFilter(request, response);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }


    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}
