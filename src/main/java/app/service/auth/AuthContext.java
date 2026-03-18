package app.service.auth;

import app.model.Customer;

public interface AuthContext {

    Customer getCurrentCustomer();

    void setCurrentCustomer(Customer customer);

    void clear();

    boolean isAuthenticated();
}
