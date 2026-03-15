package app.service.impl;

import app.model.Customer;
import app.repository.CustomerRepository;
import app.service.CustomerService;

import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
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
        Customer toUpdate = new Customer(
                currentCustomer.getId(),
                name != null ? name : currentCustomer.getName(),
                email != null ? email : currentCustomer.getEmail(),
                password != null ? password : currentCustomer.getPassword(),
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
}
