package app.web.servlet;

import app.model.Category;
import app.model.Customer;
import app.model.Order;
import app.model.OrderItem;
import app.model.OrderStatus;
import app.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/admin", "/admin/*"})
public class AdminServlet extends BaseServlet {
    private static final int DEFAULT_PAGE_SIZE = 20;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        if (path == null || "/".equals(path)) {
            req.setAttribute("stats", services().statisticsService().buildStats());
            render(req, resp, "admin/index.jsp");
            return;
        }

        String query = normalizeQuery(req.getParameter("q"));
        int page = parsePositiveInt(req.getParameter("page"), 1);
        String sortDate = resolveSortDate(req.getParameter("sortDate"));

        switch (path) {
            case "/customers" -> {
                int totalItems = services().customerService().countForAdmin(query);
                Pagination pagination = pagination(page, totalItems);
                req.setAttribute("customers", services().customerService().findForAdmin(query, DEFAULT_PAGE_SIZE, pagination.offset()));
                applyListState(req, query, sortDate, pagination, totalItems);
                render(req, resp, "admin/customers.jsp");
            }
            case "/products" -> {
                int totalItems = services().productService().countForAdmin(query);
                Pagination pagination = pagination(page, totalItems);
                req.setAttribute("products", services().productService().findForAdmin(query, DEFAULT_PAGE_SIZE, pagination.offset()));
                req.setAttribute("categories", services().categoryService().findAll());
                Map<Integer, String> categoryNames = new LinkedHashMap<>();
                services().categoryService().findAll().forEach(category -> categoryNames.put(category.getId(), category.getName()));
                req.setAttribute("categoryNames", categoryNames);
                applyListState(req, query, sortDate, pagination, totalItems);
                render(req, resp, "admin/products.jsp");
            }
            case "/orders" -> {
                int totalItems = services().orderService().countForAdmin(query);
                Pagination pagination = pagination(page, totalItems);
                List<Order> orders = services().orderService().findForAdmin(query, sortDate, DEFAULT_PAGE_SIZE, pagination.offset());
                req.setAttribute("orders", orders);
                req.setAttribute("statuses", services().orderStatusService().findAll());
                Map<Integer, List<OrderItem>> orderItemsByOrderId = new LinkedHashMap<>();
                orders.forEach(order -> orderItemsByOrderId.put(order.getId(), services().orderService().getItemsByOrderId(order.getId())));
                Map<Integer, String> productNames = new LinkedHashMap<>();
                services().productService().findAll().forEach(product -> productNames.put(product.getId(), product.getName()));
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
                req.setAttribute("orderItemsByOrderId", orderItemsByOrderId);
                req.setAttribute("productNames", productNames);
                applyListState(req, query, sortDate, pagination, totalItems);
                render(req, resp, "admin/orders.jsp");
            }
            case "/categories" -> {
                int totalItems = services().categoryService().countForAdmin(query);
                Pagination pagination = pagination(page, totalItems);
                req.setAttribute("categories", services().categoryService().findForAdmin(query, DEFAULT_PAGE_SIZE, pagination.offset()));
                applyListState(req, query, sortDate, pagination, totalItems);
                render(req, resp, "admin/categories.jsp");
            }
            case "/order-statuses" -> {
                int totalItems = services().orderStatusService().countForAdmin(query);
                Pagination pagination = pagination(page, totalItems);
                req.setAttribute("statuses", services().orderStatusService().findForAdmin(query, DEFAULT_PAGE_SIZE, pagination.offset()));
                applyListState(req, query, sortDate, pagination, totalItems);
                render(req, resp, "admin/order-statuses.jsp");
            }
            case "/statistics" -> {
                LocalDate[] period = resolveStatisticsPeriod(req);
                req.setAttribute("fromDate", period[0] != null ? period[0].toString() : "");
                req.setAttribute("toDate", period[1] != null ? period[1].toString() : "");
                req.setAttribute("stats", services().statisticsService().buildStats(period[0], period[1]));
                render(req, resp, "admin/statistics.jsp");
            }
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String entity = req.getParameter("entity");
        String action = req.getParameter("action");

        try {
            if ("customer".equals(entity)) {
                customerAction(req, action);
                resp.sendRedirect(adminPathWithState(req, "/admin/customers"));
                return;
            }
            if ("product".equals(entity)) {
                productAction(req, action);
                resp.sendRedirect(adminPathWithState(req, "/admin/products"));
                return;
            }
            if ("order".equals(entity)) {
                orderAction(req, action);
                resp.sendRedirect(adminPathWithState(req, "/admin/orders"));
                return;
            }
            if ("category".equals(entity)) {
                categoryAction(req, action);
                resp.sendRedirect(adminPathWithState(req, "/admin/categories"));
                return;
            }
            if ("status".equals(entity)) {
                statusAction(req, action);
                resp.sendRedirect(adminPathWithState(req, "/admin/order-statuses"));
                return;
            }
        } catch (IllegalArgumentException ex) {
            req.getSession().setAttribute("flashMessage", ex.getMessage());
            resp.sendRedirect(req.getHeader("Referer") != null ? req.getHeader("Referer") : req.getContextPath() + "/admin");
            return;
        }

        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void customerAction(HttpServletRequest req, String action) {
        if ("create".equals(action)) {
            String name = requireValue(req.getParameter("name"), "Name is required");
            String email = requireValue(req.getParameter("email"), "Email is required");
            String password = requireValue(req.getParameter("password"), "Password is required");
            services().customerService().save(new Customer(
                    null,
                    name,
                    email,
                    password,
                    Boolean.parseBoolean(req.getParameter("isStaff"))
            ));
            return;
        }
        Integer id = requirePositiveInt(req.getParameter("id"), "Invalid customer id");
        if ("update".equals(action)) {
            String name = requireValue(req.getParameter("name"), "Name is required");
            String email = requireValue(req.getParameter("email"), "Email is required");
            String password = trimToNull(req.getParameter("password"));
            if (password == null) {
                Customer existing = services().customerService().findById(id).orElse(null);
                if (existing == null) {
                    throw new IllegalArgumentException("Customer not found");
                }
                password = existing.getPassword();
            }
            services().customerService().update(new Customer(
                    id,
                    name,
                    email,
                    password,
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
            Product product = buildProductFromRequest(req, null);
            services().productService().create(product);
            return;
        }
        Integer id = requirePositiveInt(req.getParameter("id"), "Invalid product id");
        if ("update".equals(action)) {
            Product product = buildProductFromRequest(req, id);
            services().productService().update(product);
            return;
        }
        if ("delete".equals(action)) {
            services().productService().delete(id);
        }
    }

    private Product buildProductFromRequest(HttpServletRequest req, Integer id) {
        String name = requireValue(req.getParameter("name"), "Product name is required");
        BigDecimal price = requireNonNegativeDecimal(req.getParameter("price"), "Price must be a non-negative number");
        Integer quantity = requireNonNegativeInt(req.getParameter("quantity"), "Quantity must be a non-negative integer");
        Integer categoryId = requirePositiveInt(req.getParameter("categoryId"), "Category id must be a positive integer");
        String imageUrl = trimToNull(req.getParameter("imageUrl"));
        return new Product(id, name, price, quantity, categoryId, imageUrl);
    }

    private void orderAction(HttpServletRequest req, String action) {
        Integer id = requirePositiveInt(req.getParameter("id"), "Invalid order id");
        if ("update".equals(action)) {
            Order existingOrder = services().orderService().findById(id).orElse(null);
            if (existingOrder == null) {
                throw new IllegalArgumentException("Order not found");
            }
            Integer statusId = requirePositiveInt(req.getParameter("statusId"), "Status id must be a positive integer");
            Order order = new Order();
            order.setId(existingOrder.getId());
            order.setCustomerId(existingOrder.getCustomerId());
            order.setStatusId(statusId);
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
            String name = requireValue(req.getParameter("name"), "Category name is required");
            services().categoryService().save(new Category(null, name));
            return;
        }
        Integer id = requirePositiveInt(req.getParameter("id"), "Invalid category id");
        if ("update".equals(action)) {
            String name = requireValue(req.getParameter("name"), "Category name is required");
            services().categoryService().update(new Category(id, name));
            return;
        }
        if ("delete".equals(action)) {
            services().categoryService().delete(id);
        }
    }

    private void statusAction(HttpServletRequest req, String action) {
        if ("create".equals(action)) {
            String name = requireValue(req.getParameter("name"), "Status name is required");
            services().orderStatusService().save(new OrderStatus(null, name));
            return;
        }
        Integer id = requirePositiveInt(req.getParameter("id"), "Invalid status id");
        if ("update".equals(action)) {
            String name = requireValue(req.getParameter("name"), "Status name is required");
            services().orderStatusService().update(new OrderStatus(id, name));
            return;
        }
        if ("delete".equals(action)) {
            services().orderStatusService().delete(id);
        }
    }

    private void applyListState(HttpServletRequest req, String query, String sortDate, Pagination pagination, int totalItems) {
        req.setAttribute("q", query);
        req.setAttribute("sortDate", sortDate);
        req.setAttribute("pageSize", DEFAULT_PAGE_SIZE);
        req.setAttribute("totalItems", totalItems);
        req.setAttribute("currentPage", pagination.currentPage());
        req.setAttribute("totalPages", pagination.totalPages());
        req.setAttribute("hasPrevious", pagination.currentPage() > 1);
        req.setAttribute("hasNext", pagination.currentPage() < pagination.totalPages());
    }

    private Pagination pagination(int requestedPage, int totalItems) {
        int totalPages = Math.max(1, (int) Math.ceil((double) totalItems / DEFAULT_PAGE_SIZE));
        int currentPage = Math.min(Math.max(requestedPage, 1), totalPages);
        int offset = (currentPage - 1) * DEFAULT_PAGE_SIZE;
        return new Pagination(currentPage, totalPages, offset);
    }

    private String adminPathWithState(HttpServletRequest req, String basePath) {
        StringBuilder redirect = new StringBuilder(req.getContextPath()).append(basePath);
        String query = normalizeQuery(req.getParameter("q"));
        String page = req.getParameter("page");
        String sortDate = resolveSortDate(req.getParameter("sortDate"));

        boolean hasParams = false;
        if (query != null && !query.isBlank()) {
            redirect.append(hasParams ? "&" : "?").append("q=").append(urlEncode(query));
            hasParams = true;
        }
        if (page != null && !page.isBlank()) {
            redirect.append(hasParams ? "&" : "?").append("page=").append(page);
            hasParams = true;
        }
        if ("/admin/orders".equals(basePath)) {
            redirect.append(hasParams ? "&" : "?").append("sortDate=").append(sortDate);
        }
        return redirect.toString();
    }

    private String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private String resolveSortDate(String rawSortDate) {
        return "asc".equalsIgnoreCase(rawSortDate) ? "asc" : "desc";
    }

    private String normalizeQuery(String query) {
        if (query == null) {
            return "";
        }
        String normalized = query.trim();
        if (normalized.length() > 120) {
            return normalized.substring(0, 120);
        }
        return normalized;
    }

    private int parsePositiveInt(String value, int defaultValue) {
        Integer parsed = tryParseInt(value);
        if (parsed == null || parsed < 1) {
            return defaultValue;
        }
        return parsed;
    }

    private Integer requirePositiveInt(String value, String errorMessage) {
        Integer parsed = tryParseInt(value);
        if (parsed == null || parsed < 1) {
            throw new IllegalArgumentException(errorMessage);
        }
        return parsed;
    }

    private Integer requireNonNegativeInt(String value, String errorMessage) {
        Integer parsed = tryParseInt(value);
        if (parsed == null || parsed < 0) {
            throw new IllegalArgumentException(errorMessage);
        }
        return parsed;
    }

    private BigDecimal requireNonNegativeDecimal(String value, String errorMessage) {
        try {
            BigDecimal parsed = new BigDecimal(requireValue(value, errorMessage));
            if (parsed.signum() < 0) {
                throw new IllegalArgumentException(errorMessage);
            }
            return parsed;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private Integer tryParseInt(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private String requireValue(String value, String errorMessage) {
        String normalized = trimToNull(value);
        if (normalized == null) {
            throw new IllegalArgumentException(errorMessage);
        }
        return normalized;
    }

    private String trimToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private LocalDate[] resolveStatisticsPeriod(HttpServletRequest req) {
        String preset = req.getParameter("period");
        if (preset != null && !preset.isBlank()) {
            LocalDate today = LocalDate.now();
            return switch (preset) {
                case "day" -> new LocalDate[]{today, today};
                case "week" -> new LocalDate[]{today.minusDays(6), today};
                case "month" -> new LocalDate[]{today.minusMonths(1).plusDays(1), today};
                case "year" -> new LocalDate[]{today.minusYears(1).plusDays(1), today};
                default -> parseCustomPeriod(req.getParameter("from"), req.getParameter("to"));
            };
        }
        return parseCustomPeriod(req.getParameter("from"), req.getParameter("to"));
    }

    private LocalDate[] parseCustomPeriod(String from, String to) {
        LocalDate fromDate = parseDateSafely(from);
        LocalDate toDate = parseDateSafely(to);
        if (fromDate != null && toDate != null && fromDate.isAfter(toDate)) {
            LocalDate temp = fromDate;
            fromDate = toDate;
            toDate = temp;
        }
        return new LocalDate[]{fromDate, toDate};
    }

    private LocalDate parseDateSafely(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(value);
        } catch (Exception ignored) {
            return null;
        }
    }

    private record Pagination(int currentPage, int totalPages, int offset) {
    }
}

