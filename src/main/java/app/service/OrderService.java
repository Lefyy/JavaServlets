package app.service;

import app.model.Order;
import app.model.OrderItem;

import java.util.List;
import java.util.Optional;


public interface OrderService {

    Order createOrder(int customerId, List<OrderItemDto> items);

    Optional<Order> findById(Integer id);

    List<Order> findByCustomerId(Integer customerId);

    List<Order> findAll();

    void update(Order order);

    boolean delete(Integer orderId, Integer requestingCustomerId, boolean isStaff);

    List<OrderItem> getItemsByOrderId(Integer orderId);

    record OrderItemDto(int productId, int quantity) {}
}
