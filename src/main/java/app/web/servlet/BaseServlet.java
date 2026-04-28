package app.web.servlet;

import app.web.AppServices;
import app.web.WebUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class BaseServlet extends HttpServlet {
    protected AppServices services() {
        return WebUtils.services(getServletContext());
    }

    protected void render(HttpServletRequest req, HttpServletResponse resp, String jsp) throws ServletException, IOException {
        Object flash = req.getSession().getAttribute("flashMessage");
        if (flash != null) {
            req.setAttribute("flashMessage", flash);
            req.getSession().removeAttribute("flashMessage");
        }
        req.setAttribute("currentCustomer", WebUtils.currentCustomer(req));
        req.getRequestDispatcher("/WEB-INF/jsp/" + jsp).forward(req, resp);
    }

    protected Integer intParam(HttpServletRequest req, String key) {
        String value = req.getParameter(key);
        if (value == null || value.isBlank()) {
            return null;
        }
        return Integer.parseInt(value.trim());
    }
}
