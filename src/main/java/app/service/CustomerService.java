package app.service;

import app.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Optional<Customer> findById(Integer id);

    List<Customer> findAll();

    Customer save(Customer customer);

    void update(Customer customer);

    boolean delete(Integer id);

    void updateOwnProfile(Customer currentCustomer, String name, String email, String password);

    boolean existsByEmail(String email);

    List<Customer> findForAdmin(String query, int limit, int offset);

    int countForAdmin(String query);
}
