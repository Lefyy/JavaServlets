package app.service.impl;

import app.model.Customer;
import app.repository.CustomerRepository;
import app.service.auth.AuthContext;
import app.service.auth.AuthService;

import java.util.Optional;

public class AuthServiceImpl implements AuthService {

    private final CustomerRepository customerRepository;
    private final AuthContext authContext;

    public AuthServiceImpl(CustomerRepository customerRepository, AuthContext authContext) {
        this.customerRepository = customerRepository;
        this.authContext = authContext;
    }

    @Override
    public boolean login(String email, String password) {
        if (email == null || password == null) {
            return false;
        }
        Optional<Customer> byEmail = customerRepository.findByEmail(email.trim());
        if (byEmail.isEmpty()) {
            return false;
        }
        Customer customer = byEmail.get();
        if (!password.equals(customer.getPassword())) {
            return false;
        }
        authContext.setCurrentCustomer(customer);
        return true;
    }

    @Override
    public void logout() {
        authContext.clear();
    }

    @Override
    public Customer getCurrentCustomer() {
        return authContext.getCurrentCustomer();
    }
}
