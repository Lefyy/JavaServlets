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

    /**
     * Обновление только своих полей (name, email, password) для текущего пользователя.
     * Проверка владельца выполняется по id текущего пользователя.
     */
    void updateOwnProfile(Customer currentCustomer, String name, String email, String password);

    boolean existsByEmail(String email);
}
