package app.web.filter;

import app.model.Customer;
import app.web.WebUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/profile", "/checkout", "/orders/*", "/cart/*", "/admin/*"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Customer customer = WebUtils.currentCustomer(req);
        if (customer == null) {
            WebUtils.setFlash(req, "Авторизуйтесь для доступа к странице.");
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        chain.doFilter(request, response);
    }
}
