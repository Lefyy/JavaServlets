package app.service.auth;

import app.model.Customer;

public interface AuthService {

    boolean login(String email, String password);

    void logout();

    Customer getCurrentCustomer();
}
