package app.service.impl;

import app.model.Order;
import app.model.OrderItem;
import app.model.Product;
import app.repository.OrderItemRepository;
import app.repository.OrderRepository;
import app.repository.ProductRepository;
import app.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private static final int DEFAULT_STATUS_ID = 1;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Order createOrder(int customerId, List<OrderItemDto> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Заказ должен содержать хотя бы одну позицию");
        }
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setStatusId(DEFAULT_STATUS_ID);
        order.setCreatedAt(LocalDateTime.now());
        order = orderRepository.save(order);

        for (OrderItemDto dto : items) {
            Optional<Product> productOpt = productRepository.findById(dto.productId());
            if (productOpt.isEmpty()) {
                throw new IllegalArgumentException("Продукт с id " + dto.productId() + " не найден");
            }
            Product product = productOpt.get();
            if (product.getQuantity() < dto.quantity()) {
                throw new IllegalArgumentException("Недостаточно товара: " + product.getName());
            }
            OrderItem item = new OrderItem();
            item.setOrderId(order.getId());
            item.setProductId(dto.productId());
            item.setQuantity(dto.quantity());
            item.setPriceAtPurchase(product.getPrice());
            orderItemRepository.save(item);
            productRepository.updateQuantity(product.getId(), product.getQuantity() - dto.quantity());
        }
        return order;
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findByCustomerId(Integer customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findForAdmin(String query, String sortDate, int limit, int offset) {
        return orderRepository.findForAdmin(query, sortDate, limit, offset);
    }

    @Override
    public int countForAdmin(String query) {
        return orderRepository.countForAdmin(query);
    }

    @Override
    public void update(Order order) {
        orderRepository.update(order);
    }

    @Override
    public boolean delete(Integer orderId, Integer requestingCustomerId, boolean isStaff) {
        if (!isStaff) {
            throw new SecurityException("Нет прав на удаление чужого заказа");
        }
        orderItemRepository.deleteByOrderId(orderId);
        return orderRepository.delete(orderId);
    }

    @Override
    public List<OrderItem> getItemsByOrderId(Integer orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
}
