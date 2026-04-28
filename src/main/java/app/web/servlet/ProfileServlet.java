package app.web.servlet;

import app.model.Customer;
import app.model.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/profile")
public class ProfileServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Customer current = (Customer) req.getSession().getAttribute("currentCustomer");
        List<Order> orders = services().orderService().findByCustomerId(current.getId());
        Map<Integer, List<?>> orderItems = new LinkedHashMap<>();
        for (Order order : orders) {
            orderItems.put(order.getId(), services().orderService().getItemsByOrderId(order.getId()));
        }
        Map<Integer, String> statusNames = new LinkedHashMap<>();
        services().orderStatusService().findAll().forEach(status -> statusNames.put(status.getId(), status.getName()));
        req.setAttribute("orders", orders);
        req.setAttribute("orderItems", orderItems);
        req.setAttribute("statusNames", statusNames);
        render(req, resp, "shop/profile.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Customer current = (Customer) req.getSession().getAttribute("currentCustomer");
        services().customerService().updateOwnProfile(
                current,
                req.getParameter("name"),
                req.getParameter("email"),
                req.getParameter("password")
        );
        req.getSession().setAttribute("currentCustomer", current);
        resp.sendRedirect(req.getContextPath() + "/profile");
    }
}
