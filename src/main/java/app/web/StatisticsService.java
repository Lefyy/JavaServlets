package app.web;

import app.model.Order;
import app.model.OrderItem;
import app.model.Product;
import app.repository.CustomerRepository;
import app.repository.OrderItemRepository;
import app.repository.ProductRepository;
import app.service.OrderService;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsService {
    private final OrderService orderService;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public StatisticsService(OrderService orderService,
                             OrderItemRepository orderItemRepository,
                             ProductRepository productRepository,
                             CustomerRepository customerRepository) {
        this.orderService = orderService;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public AdminStats buildStats() {
        List<Order> orders = orderService.findAll();
        Map<Integer, Integer> productQty = new HashMap<>();
        Map<Integer, Integer> customerOrders = new HashMap<>();
        BigDecimal revenue = BigDecimal.ZERO;

        for (Order order : orders) {
            customerOrders.merge(order.getCustomerId(), 1, Integer::sum);
            for (OrderItem item : orderItemRepository.findByOrderId(order.getId())) {
                productQty.merge(item.getProductId(), item.getQuantity(), Integer::sum);
                revenue = revenue.add(item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity())));
            }
        }

        String topProduct = productQty.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .flatMap(e -> productRepository.findById(e.getKey()).map(Product::getName))
                .orElse("N/A");

        String topCustomer = customerOrders.entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .flatMap(e -> customerRepository.findById(e.getKey()).map(c -> c.getName() + " (" + c.getEmail() + ")"))
                .orElse("N/A");

        return new AdminStats(orders.size(), revenue, topProduct, topCustomer);
    }

    public record AdminStats(int totalOrders, BigDecimal totalRevenue, String topProduct, String topCustomer) {
    }
}
