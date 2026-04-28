package app.web.servlet;

import app.model.Customer;
import app.web.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = {"/auth/login", "/auth/signup", "/auth/logout"})
public class AuthServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if ("/auth/logout".equals(servletPath)) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        render(req, resp, ("/auth/signup".equals(servletPath) ? "/signup.jsp" : "/auth/login.jsp"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String servletPath = req.getServletPath();
        if ("/auth/signup".equals(servletPath)) {
            handleSignup(req, resp);
            return;
        }
        if ("/auth/login".equals(servletPath)) {
            handleLogin(req, resp);
            return;
        }
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

    private void handleSignup(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (services().customerService().existsByEmail(email)) {
            WebUtils.setFlash(req, "Пользователь с таким email уже существует.");
            resp.sendRedirect(req.getContextPath() + "/auth/signup");
            return;
        }
        Customer created = services().customerService().save(new Customer(null, name, email, password, false));
        req.getSession().setAttribute(WebUtils.SESSION_CUSTOMER, created);
        resp.sendRedirect(req.getContextPath() + "/products");
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Optional<Customer> customerOpt = services().customerRepository().findByEmail(email);
        if (customerOpt.isEmpty()) {
            WebUtils.setFlash(req, "Неверный email или пароль.");
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        Customer customer = customerOpt.get();
        if (!services().passwordHasher().matches(password, customer.getPassword()) && !password.equals(customer.getPassword())) {
            WebUtils.setFlash(req, "Неверный email или пароль.");
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        req.getSession().setAttribute(WebUtils.SESSION_CUSTOMER, customer);
        resp.sendRedirect(req.getContextPath() + "/products");
    }
}
