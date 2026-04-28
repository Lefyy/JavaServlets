package app.web.servlet;

import app.model.Customer;
import app.model.Order;
import app.web.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet(urlPatterns = "/orders/success")
public class OrderServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer orderId = intParam(req, "id");
        if (orderId == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Optional<Order> orderOpt = services().orderService().findById(orderId);
        if (orderOpt.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Order order = orderOpt.get();
        Customer current = WebUtils.currentCustomer(req);
        if (!current.isStaff() && !order.getCustomerId().equals(current.getId())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        Map<Integer, String> statusNames = new LinkedHashMap<>();
        services().orderStatusService().findAll().forEach(status -> statusNames.put(status.getId(), status.getName()));
        Map<Integer, String> productNames = new LinkedHashMap<>();
        services().productService().findAll().forEach(product -> productNames.put(product.getId(), product.getName()));
        req.setAttribute("order", order);
        req.setAttribute("orderCreatedAtDate", order.getCreatedAt() != null ? Timestamp.valueOf(order.getCreatedAt()) : null);
        req.setAttribute("items", services().orderService().getItemsByOrderId(orderId));
        req.setAttribute("statusNames", statusNames);
        req.setAttribute("productNames", productNames);
        render(req, resp, "shop/order-success.jsp");
    }
}
