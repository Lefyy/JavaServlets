package app.service.auth;

import app.model.Customer;

/**
 * Контекст текущего пользователя (сессия). Позволяет не передавать Customer по всей цепочке вызовов.
 */
public interface AuthContext {

    Customer getCurrentCustomer();

    void setCurrentCustomer(Customer customer);

    void clear();

    boolean isAuthenticated();
}
