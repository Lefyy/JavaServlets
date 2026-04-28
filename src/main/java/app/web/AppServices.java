package app.web;

import app.repository.CategoryRepository;
import app.repository.CustomerRepository;
import app.repository.OrderItemRepository;
import app.repository.OrderRepository;
import app.repository.OrderStatusRepository;
import app.repository.ProductRepository;
import app.repository.impl.CategoryRepositoryImpl;
import app.repository.impl.CustomerRepositoryImpl;
import app.repository.impl.OrderItemRepositoryImpl;
import app.repository.impl.OrderRepositoryImpl;
import app.repository.impl.OrderStatusRepositoryImpl;
import app.repository.impl.ProductRepositoryImpl;
import app.service.CategoryService;
import app.service.CustomerService;
import app.service.OrderService;
import app.service.OrderStatusService;
import app.service.ProductService;
import app.service.impl.CategoryServiceImpl;
import app.service.impl.CustomerServiceImpl;
import app.service.impl.OrderServiceImpl;
import app.service.impl.OrderStatusServiceImpl;
import app.service.impl.ProductServiceImpl;
import app.service.impl.security.Pbkdf2PasswordHasher;
import app.service.security.PasswordHasher;

public class AppServices {
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CategoryRepository categoryRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final PasswordHasher passwordHasher;

    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderService orderService;
    private final CategoryService categoryService;
    private final OrderStatusService orderStatusService;
    private final StatisticsService statisticsService;

    public AppServices() {
        this.customerRepository = new CustomerRepositoryImpl();
        this.productRepository = new ProductRepositoryImpl();
        this.orderRepository = new OrderRepositoryImpl();
        this.orderItemRepository = new OrderItemRepositoryImpl();
        this.categoryRepository = new CategoryRepositoryImpl();
        this.orderStatusRepository = new OrderStatusRepositoryImpl();
        this.passwordHasher = new Pbkdf2PasswordHasher();
        this.customerService = new CustomerServiceImpl(customerRepository, passwordHasher);
        this.productService = new ProductServiceImpl(productRepository);
        this.orderService = new OrderServiceImpl(orderRepository, orderItemRepository, productRepository);
        this.categoryService = new CategoryServiceImpl(categoryRepository);
        this.orderStatusService = new OrderStatusServiceImpl(orderStatusRepository);
        this.statisticsService = new StatisticsService(orderService, orderItemRepository, productRepository, customerRepository);
    }

    public CustomerRepository customerRepository() {
        return customerRepository;
    }

    public PasswordHasher passwordHasher() {
        return passwordHasher;
    }

    public CustomerService customerService() {
        return customerService;
    }

    public ProductService productService() {
        return productService;
    }

    public OrderService orderService() {
        return orderService;
    }

    public CategoryService categoryService() {
        return categoryService;
    }

    public OrderStatusService orderStatusService() {
        return orderStatusService;
    }

    public StatisticsService statisticsService() {
        return statisticsService;
    }
}
