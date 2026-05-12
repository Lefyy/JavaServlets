package app.service.impl;

import app.model.Customer;
import app.repository.CustomerRepository;
import app.service.CustomerService;
import app.service.security.PasswordHasher;

import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordHasher passwordHasher;

    public CustomerServiceImpl(CustomerRepository customerRepository, PasswordHasher passwordHasher) {
        this.customerRepository = customerRepository;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        if (customer.getPassword() != null) {
            customer.setPassword(passwordHasher.hash(customer.getPassword()));
        }
        return customerRepository.save(customer);
    }

    @Override
    public void update(Customer customer) {
        customerRepository.update(customer);
    }

    @Override
    public boolean delete(Integer id) {
        return customerRepository.delete(id);
    }

    @Override
    public void updateOwnProfile(Customer currentCustomer, String name, String email, String password) {
        if (currentCustomer == null || currentCustomer.getId() == null) {
            throw new IllegalArgumentException("Текущий пользователь не задан");
        }
        String passwordToStore = password != null
                ? passwordHasher.hash(password)
                : currentCustomer.getPassword();
        Customer toUpdate = new Customer(
                currentCustomer.getId(),
                name != null ? name : currentCustomer.getName(),
                email != null ? email : currentCustomer.getEmail(),
                passwordToStore,
                currentCustomer.isStaff()
        );
        customerRepository.update(toUpdate);
        currentCustomer.setName(toUpdate.getName());
        currentCustomer.setEmail(toUpdate.getEmail());
        currentCustomer.setPassword(toUpdate.getPassword());
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public List<Customer> findForAdmin(String query, int limit, int offset) {
        return customerRepository.findForAdmin(query, limit, offset);
    }

    @Override
    public int countForAdmin(String query) {
        return customerRepository.countForAdmin(query);
    }
}
