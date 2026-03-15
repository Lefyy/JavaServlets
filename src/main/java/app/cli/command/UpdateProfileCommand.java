package app.cli.command;

import app.cli.Command;
import app.cli.CommandContext;
import app.model.Customer;
import app.service.CustomerService;

public class UpdateProfileCommand implements Command {

    @Override
    public String getName() {
        return "profile update";
    }

    @Override
    public String getDescription() {
        return "profile update [--name <имя>] [--email <email>] [--password <пароль>] — обновить свой профиль";
    }

    @Override
    public boolean isStaffOnly() {
        return false;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        Customer current = ctx.getCurrentCustomer();
        if (current == null) {
            ctx.getOut().println("Необходимо войти в систему (login).");
            return;
        }

        String name = null;
        String email = null;
        String password = null;
        for (int i = 0; i < args.length; i++) {
            if ("--name".equals(args[i]) && i + 1 < args.length) {
                name = args[++i];
            } else if ("--email".equals(args[i]) && i + 1 < args.length) {
                email = args[++i];
            } else if ("--password".equals(args[i]) && i + 1 < args.length) {
                password = args[++i];
            }
        }

        if (name == null && email == null && password == null) {
            ctx.getOut().println("Использование: profile update [--name <имя>] [--email <email>] [--password <пароль>]");
            return;
        }

        CustomerService customerService = ctx.getCustomerService();
        if (email != null && !email.equals(current.getEmail()) && customerService.existsByEmail(email)) {
            ctx.getOut().println("Пользователь с таким email уже существует.");
            return;
        }
        try {
            customerService.updateOwnProfile(current, name, email, password);
            ctx.getOut().println("Профиль обновлён.");
        } catch (Exception e) {
            ctx.getOut().println("Ошибка: " + e.getMessage());
        }
    }
}
