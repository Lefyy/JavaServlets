package app.service.impl.auth;

import app.model.Customer;
import app.repository.CustomerRepository;
import app.service.auth.AuthContext;
import app.service.auth.AuthService;
import app.service.security.PasswordHasher;

import java.util.Optional;

public class AuthServiceImpl implements AuthService {

    private final CustomerRepository customerRepository;
    private final AuthContext authContext;
    private final PasswordHasher passwordHasher;

    public AuthServiceImpl(CustomerRepository customerRepository, AuthContext authContext, PasswordHasher passwordHasher) {
        this.customerRepository = customerRepository;
        this.authContext = authContext;
        this.passwordHasher = passwordHasher;
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
        String storedPassword = customer.getPassword();

        if (passwordHasher.matches(password, storedPassword)) {
            authContext.setCurrentCustomer(customer);
            return true;
        }

        if (password.equals(storedPassword)) {
            String migratedHash = passwordHasher.hash(password);
            customer.setPassword(migratedHash);
            customerRepository.update(customer);
            authContext.setCurrentCustomer(customer);
            return true;
        }

        return false;
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
