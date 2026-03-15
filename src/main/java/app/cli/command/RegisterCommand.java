package app.cli.command;

import app.cli.Command;
import app.cli.CommandContext;
import app.cli.FlagArgs;
import app.model.Customer;
import app.service.CustomerService;

public class RegisterCommand implements Command {

    @Override
    public String getName() {
        return "register";
    }

    @Override
    public String getDescription() {
        return "register --name <name> --email <email> --password <password> — регистрация нового пользователя";
    }

    @Override
    public boolean isStaffOnly() {
        return false;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        FlagArgs flags = FlagArgs.parse(args, 1);
        if (flags.getError() != null) {
            ctx.getOut().println(flags.getError());
            return;
        }

        String name = flags.require("--name");
        if (name == null) {
            ctx.getOut().println(flags.getError());
            return;
        }
        String email = flags.require("--email");
        if (email == null) {
            ctx.getOut().println(flags.getError());
            return;
        }
        String password = flags.require("--password");
        if (password == null) {
            ctx.getOut().println(flags.getError());
            return;
        }

        CustomerService customerService = ctx.getCustomerService();
        if (customerService.existsByEmail(email.trim())) {
            ctx.getOut().println("Пользователь с таким email уже зарегистрирован.");
            return;
        }
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email.trim());
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
