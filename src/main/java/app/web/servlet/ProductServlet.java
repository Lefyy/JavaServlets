package app.web.servlet;

import app.model.Product;
import app.web.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(urlPatterns = {"/products", "/products/*"})
public class ProductServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            int id = Integer.parseInt(pathInfo.substring(1));
            Optional<Product> product = services().productService().findById(id);
            if (product.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            req.setAttribute("product", product.get());
            render(req, resp, "shop/product-detail.jsp");
            return;
        }

        String search = req.getParameter("search");
        List<Product> products;
        if (search != null && !search.isBlank()) {
            products = services().productService().findByNameContaining(search);
            req.setAttribute("products", products);
            req.setAttribute("currentCategory", "");
            req.setAttribute("currentSort", "");
            req.setAttribute("currentPage", 1);
            req.setAttribute("totalPages", 1);
            req.setAttribute("hasPrevious", false);
            req.setAttribute("hasNext", false);
        } else {
            String category = resolveCategory(req);
            String sort = optionalParam(req, "sort");
            int page = Math.max(1, intParam(req, "page") == null ? 1 : intParam(req, "page"));
            int pageSize = 12;
            int totalCount = services().productService().countCatalog(category);
            int totalPages = Math.max(1, (int) Math.ceil((double) totalCount / pageSize));
            if (page > totalPages) {
                page = totalPages;
            }
            int offset = (page - 1) * pageSize;
            products = services().productService().findCatalog(category, sort, pageSize, offset);

            req.setAttribute("products", products);
            req.setAttribute("currentCategory", category);
            req.setAttribute("currentSort", sort);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("hasPrevious", page > 1);
            req.setAttribute("hasNext", page < totalPages);

        }
        req.setAttribute("categories", services().categoryService().findAll());
        render(req, resp, "shop/products.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (WebUtils.currentCustomer(req) == null) {
            resp.sendRedirect(req.getContextPath() + "/auth/login");
            return;
        }
        String action = req.getParameter("action");
        if ("delete".equals(action) && WebUtils.currentCustomer(req).isStaff()) {
            Integer id = intParam(req, "id");
            services().productService().delete(id);
            resp.sendRedirect(req.getContextPath() + "/admin/products");
            return;
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private String resolveCategory(HttpServletRequest req) {
        String category = optionalParam(req, "category");
        if (category.isBlank()) {
            category = optionalParam(req, "categoryId");
        }
        return category.matches("\\d+") ? category : "";
    }

    private String optionalParam(HttpServletRequest req, String name) {
        String value = req.getParameter(name);
        return value == null ? "" : value.trim();
    }

}
