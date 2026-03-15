package app.repository;

import app.model.Customer;

import java.util.Optional;
import java.util.List;

public interface CustomerRepository extends Repository<Customer> {

    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Customer> findByNameContaining(String namePart);

    List<Customer> findByStaffStatus(boolean isStaff);

    List<Customer> findByName(String name);
}
