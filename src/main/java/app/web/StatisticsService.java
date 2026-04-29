package app.web;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.model.Order;
import app.model.OrderItem;
import app.model.Product;
import app.repository.CustomerRepository;
import app.repository.OrderItemRepository;
import app.repository.ProductRepository;
import app.service.OrderService;

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

    public AdminStats buildStats(LocalDate fromDate, LocalDate toDate) {
        List<Order> orders = orderService.findAll();
        Map<Integer, Integer> productQty = new HashMap<>();
        Map<Integer, Integer> customerOrders = new HashMap<>();
        BigDecimal revenue = BigDecimal.ZERO;

        LocalDateTime from = fromDate != null ? fromDate.atStartOfDay() : null;
        LocalDateTime to = toDate != null ? toDate.plusDays(1).atStartOfDay() : null;

        int filteredOrdersCount = 0;

        for (Order order : orders) {
            if (!isWithinRange(order.getCreatedAt(), from, to)) {
                continue;
            }

            filteredOrdersCount++;
            customerOrders.merge(order.getCustomerId(), 1, Integer::sum);
            for (OrderItem item : orderItemRepository.findByOrderId(order.getId())) {
                productQty.merge(item.getProductId(), item.getQuantity(), Integer::sum);
                revenue = revenue.add(item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity())));
            }
        }

        List<RankItem> topProducts = productQty.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .map(entry -> new RankItem(
                        productRepository.findById(entry.getKey()).map(Product::getName).orElse("Товар #" + entry.getKey()),
                        entry.getValue()
                ))
                .toList();

        List<RankItem> topCustomers = customerOrders.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .map(entry -> new RankItem(
                        customerRepository.findById(entry.getKey())
                                .map(c -> c.getName() + " (" + c.getEmail() + ")")
                                .orElse("Покупатель #" + entry.getKey()),
                        entry.getValue()
                ))
                .toList();

        return new AdminStats(filteredOrdersCount, revenue, topProducts, topCustomers);
    }

    public AdminStats buildStats() {
        return buildStats(null, null);
    }


    private boolean isWithinRange(LocalDateTime createdAt, LocalDateTime from, LocalDateTime to) {
        if (createdAt == null) {
            return from == null && to == null;
        }
        if (from != null && createdAt.isBefore(from)) {
            return false;
        }
        return to == null || createdAt.isBefore(to);
    }

    public static class AdminStats {
        private final int totalOrders;
        private final BigDecimal totalRevenue;
        private final List<RankItem> topProducts;
        private final List<RankItem> topCustomers;

        public AdminStats(int totalOrders, BigDecimal totalRevenue, List<RankItem> topProducts, List<RankItem> topCustomers) {
            this.totalOrders = totalOrders;
            this.totalRevenue = totalRevenue;
            this.topProducts = topProducts != null ? topProducts : new ArrayList<>();
            this.topCustomers = topCustomers != null ? topCustomers : new ArrayList<>();
        }

        public int getTotalOrders() {
            return totalOrders;
        }

        public BigDecimal getTotalRevenue() {
            return totalRevenue;
        }

        public List<RankItem> getTopProducts() {
            return topProducts;
        }

        public List<RankItem> getTopCustomers() {
            return topCustomers;
        }
    }

    public static class RankItem {
        private final String label;
        private final int value;

        public RankItem(String label, int value) {
            this.label = label;
            this.value = value;
        }

        public String getLabel() {
            return label;

        }

        public int getValue() {
            return value;
        }
    }
}
