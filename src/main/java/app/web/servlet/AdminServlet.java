package app.web.servlet;

import app.model.Category;
import app.model.Customer;
import app.model.Order;
import app.model.OrderStatus;
import app.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


@WebServlet(urlPatterns = {"/admin", "/admin/*"})
public class AdminServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null || "/".equals(path)) {
            req.setAttribute("stats", services().statisticsService().buildStats());
            render(req, resp, "admin/index.jsp");
            return;
        }
        switch (path) {
            case "/customers" -> {
                req.setAttribute("customers", services().customerService().findAll());
                render(req, resp, "admin/customers.jsp");
            }
            case "/products" -> {
                req.setAttribute("products", services().productService().findAll());
                req.setAttribute("categories", services().categoryService().findAll());
                Map<Integer, String> categoryNames = new LinkedHashMap<>();
                services().categoryService().findAll().forEach(category -> categoryNames.put(category.getId(), category.getName()));
                req.setAttribute("categoryNames", categoryNames);
                render(req, resp, "admin/products.jsp");
            }
            case "/orders" -> {
                List<Order> orders = services().orderService().findAll();
                req.setAttribute("orders", orders);
                req.setAttribute("statuses", services().orderStatusService().findAll());
                Map<Integer, String> customerNames = new LinkedHashMap<>();
                services().customerService().findAll().forEach(customer -> customerNames.put(customer.getId(), customer.getName()));
                Map<Integer, String> statusNames = new LinkedHashMap<>();
                services().orderStatusService().findAll().forEach(status -> statusNames.put(status.getId(), status.getName()));
                Map<Integer, Date> orderCreatedAtDates = new LinkedHashMap<>();
                orders.forEach(order -> {
                    if (order.getCreatedAt() != null) {
                        orderCreatedAtDates.put(order.getId(), Timestamp.valueOf(order.getCreatedAt()));
                    }
                });
                req.setAttribute("customerNames", customerNames);
                req.setAttribute("statusNames", statusNames);
                req.setAttribute("orderCreatedAtDates", orderCreatedAtDates);
                render(req, resp, "admin/orders.jsp");
            }
            case "/categories" -> {
                req.setAttribute("categories", services().categoryService().findAll());
                render(req, resp, "admin/categories.jsp");
            }
            case "/order-statuses" -> {
                req.setAttribute("statuses", services().orderStatusService().findAll());
                render(req, resp, "admin/order-statuses.jsp");
            }
            case "/statistics" -> {
                req.setAttribute("stats", services().statisticsService().buildStats());
                render(req, resp, "admin/statistics.jsp");
            }
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String entity = req.getParameter("entity");
        String action = req.getParameter("action");
        if ("customer".equals(entity)) {
            customerAction(req, action);
            resp.sendRedirect(req.getContextPath() + "/admin/customers");
            return;
        }
        if ("product".equals(entity)) {
            productAction(req, action);
            resp.sendRedirect(req.getContextPath() + "/admin/products");
            return;
        }
        if ("order".equals(entity)) {
            orderAction(req, action);
            resp.sendRedirect(req.getContextPath() + "/admin/orders");
            return;
        }
        if ("category".equals(entity)) {
            categoryAction(req, action);
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
            return;
        }
        if ("status".equals(entity)) {
            statusAction(req, action);
            resp.sendRedirect(req.getContextPath() + "/admin/order-statuses");
            return;
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void customerAction(HttpServletRequest req, String action) {
        if ("create".equals(action)) {
            services().customerService().save(new Customer(
                    null,
                    req.getParameter("name"),
                    req.getParameter("email"),
                    req.getParameter("password"),
                    Boolean.parseBoolean(req.getParameter("isStaff"))
            ));
            return;
        }
        Integer id = intParam(req, "id");
        if ("update".equals(action)) {
            services().customerService().update(new Customer(
                    id,
                    req.getParameter("name"),
                    req.getParameter("email"),
                    req.getParameter("password"),
                    Boolean.parseBoolean(req.getParameter("isStaff"))
            ));
            return;
        }
        if ("delete".equals(action)) {
            services().customerService().delete(id);
        }
    }

    private void productAction(HttpServletRequest req, String action) {
        if ("create".equals(action)) {
            services().productService().create(new Product(
                    null,
                    req.getParameter("name"),
                    new BigDecimal(req.getParameter("price")),
                    Integer.parseInt(req.getParameter("quantity")),
                    Integer.parseInt(req.getParameter("categoryId")),
                    req.getParameter("imageUrl")
            ));
            return;
        }
        Integer id = intParam(req, "id");
        if ("update".equals(action)) {
            services().productService().update(new Product(
                    id,
                    req.getParameter("name"),
                    new BigDecimal(req.getParameter("price")),
                    Integer.parseInt(req.getParameter("quantity")),
                    Integer.parseInt(req.getParameter("categoryId")),
                    req.getParameter("imageUrl")
            ));
            return;
        }
        if ("delete".equals(action)) {
            services().productService().delete(id);
        }
    }

    private void orderAction(HttpServletRequest req, String action) {
        Integer id = intParam(req, "id");
        if ("update".equals(action)) {
            if (id == null) {
                req.getSession().setAttribute("flashMessage", "Не удалось обновить заказ: отсутствует id.");
                return;
            }
            Order existingOrder = services().orderService().findById(id).orElse(null);
            if (existingOrder == null) {
                req.getSession().setAttribute("flashMessage", "Не удалось обновить заказ: заказ не найден.");
                return;
            }
            Order order = new Order();
            order.setId(existingOrder.getId());
            order.setCustomerId(existingOrder.getCustomerId());
            order.setStatusId(Integer.parseInt(req.getParameter("statusId")));
            order.setCreatedAt(existingOrder.getCreatedAt());
            services().orderService().update(order);
            return;
        }
        if ("delete".equals(action)) {
            services().orderService().delete(id, null, true);
        }
    }

    private void categoryAction(HttpServletRequest req, String action) {
        if ("create".equals(action)) {
            services().categoryService().save(new Category(null, req.getParameter("name")));
            return;
        }
        Integer id = intParam(req, "id");
        if ("update".equals(action)) {
            services().categoryService().update(new Category(id, req.getParameter("name")));
            return;
        }
        if ("delete".equals(action)) {
            services().categoryService().delete(id);
        }
    }

    private void statusAction(HttpServletRequest req, String action) {
        if ("create".equals(action)) {
            services().orderStatusService().save(new OrderStatus(null, req.getParameter("name")));
            return;
        }
        Integer id = intParam(req, "id");
        if ("update".equals(action)) {
            services().orderStatusService().update(new OrderStatus(id, req.getParameter("name")));
            return;
        }
        if ("delete".equals(action)) {
            services().orderStatusService().delete(id);
        }
    }
}
