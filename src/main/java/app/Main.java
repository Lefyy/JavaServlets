package app;

import app.cli.*;
import app.cli.command.*;
import app.cli.staff_command.*;
import app.config.DatabaseConnection;
import app.repository.*;
import app.repository.impl.*;
import app.service.*;
import app.service.auth.*;
import app.service.impl.*;
import app.service.impl.auth.AuthContextImpl;
import app.service.impl.auth.AuthServiceImpl;

import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            DatabaseConnection.getConnection().close();
        } catch (Exception e) {
            System.err.println("Ошибка подключения к БД: " + e.getMessage());
            return;
        }

        AuthContext authContext = new AuthContextImpl();
        CustomerRepository customerRepository = new CustomerRepositoryImpl();
        ProductRepository productRepository = new ProductRepositoryImpl();
        OrderRepository orderRepository = new OrderRepositoryImpl();
        OrderItemRepository orderItemRepository = new OrderItemRepositoryImpl();
        CategoryRepository categoryRepository = new CategoryRepositoryImpl();
        OrderStatusRepository orderStatusRepository = new OrderStatusRepositoryImpl();

        PasswordHasher passwordHasher = new Pbkdf2PasswordHasher();
        AuthService authService = new AuthServiceImpl(customerRepository, authContext, passwordHasher);
        CustomerService customerService = new CustomerServiceImpl(customerRepository, passwordHasher);
        ProductService productService = new ProductServiceImpl(productRepository);
        OrderService orderService = new OrderServiceImpl(orderRepository, orderItemRepository, productRepository);
        CategoryService categoryService = new CategoryServiceImpl(categoryRepository);
        OrderStatusService orderStatusService = new OrderStatusServiceImpl(orderStatusRepository);

        CommandRegistry registry = new CommandRegistry();
        registry.register(new LoginCommand());
        registry.register(new LogoutCommand());
        registry.register(new RegisterCommand());
        registry.register(new ListProductsCommand());
        registry.register(new CreateOrderCommand());
        registry.register(new ListMyOrdersCommand());
        registry.register(new DeleteOrderCommand());
        registry.register(new UpdateProfileCommand());
        registry.register(new StaffCustomerListCommand());
        registry.register(new StaffCustomerCreateCommand());
        registry.register(new StaffCustomerUpdateCommand());
        registry.register(new StaffCustomerDeleteCommand());
        registry.register(new StaffProductCreateCommand());
        registry.register(new StaffProductUpdateCommand());
        registry.register(new StaffProductDeleteCommand());
        registry.register(new StaffOrderListCommand());
        registry.register(new StaffOrderUpdateCommand());
        registry.register(new StaffOrderDeleteCommand());
        registry.register(new StaffCategoryListCommand());
        registry.register(new StaffCategoryCreateCommand());
        registry.register(new StaffCategoryUpdateCommand());
        registry.register(new StaffCategoryDeleteCommand());
        registry.register(new StaffOrderStatusListCommand());
        registry.register(new StaffOrderStatusCreateCommand());
        registry.register(new StaffOrderStatusUpdateCommand());
        registry.register(new StaffOrderStatusDeleteCommand());
        registry.register(new HelpCommand(registry.getAllCommands()));

        Scanner scanner = new Scanner(System.in);
        PrintStream out = System.out;

        CommandContext ctx = new CommandContextImpl(
                authContext, authService, customerService, productService,
                orderService, categoryService, orderStatusService,
                scanner, out);

        out.println("Интернет-магазин. Введите help для списка команд. exit — выход.");

        while (true) {
            if (authContext.getCurrentCustomer() != null) {
                out.print(authContext.getCurrentCustomer().getName() + (authContext.getCurrentCustomer().isStaff() ? " (staff)" : "") + "> ");
            } else {
                out.print("> ");
            }
            if (!scanner.hasNextLine()) {
                break;
            }
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            if ("exit".equalsIgnoreCase(line) || "quit".equalsIgnoreCase(line)) {
                out.println("До свидания.");
                break;
            }
            String[] parts = line.split("\\s+");
            String cmdName = parts[0];
            Optional<Command> cmdOpt = registry.find(cmdName);
            if (cmdOpt.isEmpty()) {
                out.println("Неизвестная команда: " + cmdName + ". Введите help.");
                continue;
            }
            Command cmd = cmdOpt.get();
            if (!registry.canExecute(cmd, authContext.getCurrentCustomer())) {
                out.println("Нет доступа к команде: " + cmdName);
                continue;
            }
            try {
                cmd.execute(ctx, parts);
            } catch (Exception e) {
                out.println("Ошибка: " + e.getMessage());
            }
        }
    }
}
