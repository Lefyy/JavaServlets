package app.web;

import app.model.Customer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public final class WebUtils {
    public static final String SESSION_CUSTOMER = "currentCustomer";

    private WebUtils() {
    }

    public static AppServices services(ServletContext context) {
        return (AppServices) context.getAttribute(AppBootstrapListener.SERVICES_ATTR);
    }

    public static Customer currentCustomer(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (Customer) session.getAttribute(SESSION_CUSTOMER);
    }

    public static void setFlash(HttpServletRequest request, String message) {
        request.getSession().setAttribute("flashMessage", message);
    }
}
