package app.web.servlet;

import app.model.Customer;
import app.model.Order;
import app.model.Product;
import app.service.OrderService;
import app.web.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet(urlPatterns = {"/cart", "/cart/add", "/cart/update", "/cart/remove", "/checkout"})
public class CartServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if ("/checkout".equals(servletPath)) {
            populateCart(req);
            render(req, resp, "shop/checkout.jsp");
            return;
        }
        populateCart(req);
        render(req, resp, "shop/cart.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getServletPath();
        if ("/cart/add".equals(path)) {
            addItem(req);
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }
        if ("/cart/update".equals(path)) {
            updateItem(req);
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }
        if ("/cart/remove".equals(path)) {
            removeItem(req);
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }
        if ("/checkout".equals(path)) {
            createOrder(req, resp);
            return;
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void createOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Customer customer = WebUtils.currentCustomer(req);
        Map<Integer, Integer> cart = cart(req.getSession());
        if (cart.isEmpty()) {
            WebUtils.setFlash(req, "Корзина пуста.");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        List<OrderService.OrderItemDto> items = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            items.add(new OrderService.OrderItemDto(entry.getKey(), entry.getValue()));
        }

        Order order = services().orderService().createOrder(customer.getId(), items);
        req.getSession().removeAttribute("cartItems");
        resp.sendRedirect(req.getContextPath() + "/orders/success?id=" + order.getId());
    }

    private void populateCart(HttpServletRequest req) {
        Map<Integer, Integer> cart = cart(req.getSession());
        List<Map<String, Object>> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            Optional<Product> productOpt = services().productService().findById(entry.getKey());
            if (productOpt.isEmpty()) {
                continue;
            }
            Product product = productOpt.get();
            int qty = entry.getValue();
            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(qty));
            total = total.add(lineTotal);
            Map<String, Object> item = new HashMap<>();
            item.put("product", product);
            item.put("qty", qty);
            item.put("lineTotal", lineTotal);
            items.add(item);
        }
        req.setAttribute("cartItems", items);
        req.setAttribute("cartTotal", total);
    }

    private void addItem(HttpServletRequest req) {
        Integer productId = intParam(req, "productId");
        int qty = Math.max(1, intParam(req, "quantity") == null ? 1 : intParam(req, "quantity"));
        Map<Integer, Integer> cart = cart(req.getSession());
        cart.merge(productId, qty, Integer::sum);
    }

    private void updateItem(HttpServletRequest req) {
        Integer productId = intParam(req, "productId");
        int qty = Math.max(0, intParam(req, "quantity") == null ? 0 : intParam(req, "quantity"));
        Map<Integer, Integer> cart = cart(req.getSession());
        if (qty == 0) {
            cart.remove(productId);
        } else {
            cart.put(productId, qty);
        }
    }

    private void removeItem(HttpServletRequest req) {
        Integer productId = intParam(req, "productId");
        cart(req.getSession()).remove(productId);
    }

    @SuppressWarnings("unchecked")
    private Map<Integer, Integer> cart(HttpSession session) {
        Object existing = session.getAttribute("cartItems");
        if (existing instanceof Map<?, ?> existingMap) {
            return (Map<Integer, Integer>) existingMap;
        }
        Map<Integer, Integer> created = new HashMap<>();
        session.setAttribute("cartItems", created);
        return created;
    }
}
