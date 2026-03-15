package app.cli;

import app.model.Customer;
import app.service.*;
import app.service.auth.AuthContext;
import app.service.auth.AuthService;

import java.io.PrintStream;
import java.util.Scanner;

public class CommandContextImpl implements CommandContext {

    private final AuthContext authContext;
    private final AuthService authService;
    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderService orderService;
    private final CategoryService categoryService;
    private final OrderStatusService orderStatusService;
    private final Scanner scanner;
    private final PrintStream out;

    public CommandContextImpl(AuthContext authContext,
                              AuthService authService,
                              CustomerService customerService,
                              ProductService productService,
                              OrderService orderService,
                              CategoryService categoryService,
                              OrderStatusService orderStatusService,
                              Scanner scanner,
                              PrintStream out) {
        this.authContext = authContext;
        this.authService = authService;
        this.customerService = customerService;
        this.productService = productService;
        this.orderService = orderService;
        this.categoryService = categoryService;
        this.orderStatusService = orderStatusService;
        this.scanner = scanner;
        this.out = out;
    }

    @Override
    public Customer getCurrentCustomer() {
        return authContext.getCurrentCustomer();
    }

    @Override
    public AuthService getAuthService() {
        return authService;
    }

    @Override
    public CustomerService getCustomerService() {
        return customerService;
    }

    @Override
    public ProductService getProductService() {
        return productService;
    }

    @Override
    public OrderService getOrderService() {
        return orderService;
    }

    @Override
    public CategoryService getCategoryService() {
        return categoryService;
    }

    @Override
    public OrderStatusService getOrderStatusService() {
        return orderStatusService;
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }

    @Override
    public PrintStream getOut() {
        return out;
    }
}
