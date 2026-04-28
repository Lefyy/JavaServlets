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
        Integer categoryId = intParam(req, "categoryId");
        List<Product> products;
        if (search != null && !search.isBlank()) {
            products = services().productService().findByNameContaining(search);
        } else if (categoryId != null) {
            products = services().productService().findByCategoryId(categoryId);
        } else {
            products = services().productService().findAll();
        }
        req.setAttribute("products", products);
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
}
