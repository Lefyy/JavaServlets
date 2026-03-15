package app.service;

import app.model.Order;
import app.model.OrderItem;

import java.util.List;
import java.util.Optional;

/**
 * Сервис заказов. Для обычного пользователя: создание, удаление своих заказов, просмотр своих.
 * Для staff: полный CRUD по всем заказам.
 */
public interface OrderService {

    /**
     * Создать заказ для текущего покупателя. Статус по умолчанию (например, "Новый").
     *
     * @param customerId id покупателя
     * @param items      список позиций (productId, quantity); цена берётся текущая из продукта
     * @return созданный заказ
     */
    Order createOrder(int customerId, List<OrderItemDto> items);

    Optional<Order> findById(Integer id);

    List<Order> findByCustomerId(Integer customerId);

    List<Order> findAll();

    void update(Order order);

    /**
     * Удалить заказ. Для обычного пользователя — только свой заказ.
     */
    boolean delete(Integer orderId, Integer requestingCustomerId, boolean isStaff);

    List<OrderItem> getItemsByOrderId(Integer orderId);

    /** DTO для создания позиции заказа (productId + quantity). */
    record OrderItemDto(int productId, int quantity) {}
}
