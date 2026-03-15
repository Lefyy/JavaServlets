package app.cli.command;

import app.cli.Command;
import app.cli.CommandContext;
import app.service.auth.AuthService;

public class LoginCommand implements Command {

    @Override
    public String getName() {
        return "login";
    }

    @Override
    public String getDescription() {
        return "login <email> <password> — войти в систему";
    }

    @Override
    public boolean isStaffOnly() {
        return false;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        if (ctx.getCurrentCustomer() != null) {
            ctx.getOut().println("Вы уже авторизованы. Сначала выполните logout.");
            return;
        }
        if (args.length < 3) {
            ctx.getOut().println("Использование: login <email> <password>");
            return;
        }
        String email = args[1];
        String password = args[2];
        AuthService auth = ctx.getAuthService();
        if (auth.login(email, password)) {
            ctx.getOut().println("Вход выполнен. Здравствуйте, " + auth.getCurrentCustomer().getName() + ".");
        } else {
            ctx.getOut().println("Неверный email или пароль.");
        }
    }
}
