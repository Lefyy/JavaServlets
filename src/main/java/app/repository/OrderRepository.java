package app.repository;

import app.model.Order;

import java.util.List;

public interface OrderRepository extends Repository<Order> {

    List<Order> findByCustomerId(Integer customerId);
}
