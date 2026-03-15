package app.service;

import app.model.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderStatusService {

    Optional<OrderStatus> findById(Integer id);

    List<OrderStatus> findAll();

    OrderStatus save(OrderStatus orderStatus);

    void update(OrderStatus orderStatus);

    boolean delete(Integer id);
}
