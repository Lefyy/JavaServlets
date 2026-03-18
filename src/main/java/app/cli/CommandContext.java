package app.cli;

import app.model.Customer;
import app.service.*;
import app.service.auth.AuthService;

import java.io.PrintStream;
import java.util.Scanner;

public interface CommandContext {

    Customer getCurrentCustomer();

    AuthService getAuthService();

    CustomerService getCustomerService();

    ProductService getProductService();

    OrderService getOrderService();

    CategoryService getCategoryService();

    OrderStatusService getOrderStatusService();

    Scanner getScanner();

    PrintStream getOut();
}
