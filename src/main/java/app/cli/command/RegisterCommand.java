package app.cli.command;

import app.cli.Command;
import app.cli.CommandContext;
import app.model.Customer;
import app.service.CustomerService;

public class RegisterCommand implements Command {

    @Override
    public String getName() {
        return "register";
    }

    @Override
    public String getDescription() {
        return "register <name> <email> <password> — регистрация нового пользователя";
    }

    @Override
    public boolean isStaffOnly() {
        return false;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        if (args.length < 4) {
            ctx.getOut().println("Использование: register <имя> <email> <пароль>");
            return;
        }
        String name = args[1];
        String email = args[2].trim();
        String password = args[3];
        CustomerService customerService = ctx.getCustomerService();
        if (customerService.existsByEmail(email)) {
            ctx.getOut().println("Пользователь с таким email уже зарегистрирован.");
            return;
        }
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setStaff(false);
        try {
            customerService.save(customer);
            ctx.getOut().println("Регистрация успешна. Выполните login для входа.");
        } catch (Exception e) {
            ctx.getOut().println("Ошибка регистрации: " + e.getMessage());
        }
    }
}
