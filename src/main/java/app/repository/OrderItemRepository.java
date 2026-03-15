package app.repository;

import app.model.OrderItem;

import java.util.List;

public interface OrderItemRepository extends Repository<OrderItem> {

    List<OrderItem> findByOrderId(Integer orderId);

    boolean deleteByOrderId(Integer orderId);
}
