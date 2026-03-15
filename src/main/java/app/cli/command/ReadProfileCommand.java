package app.cli.command;

import app.cli.Command;
import app.cli.CommandContext;
import app.model.Customer;

public class ReadProfileCommand implements Command {

    @Override
    public String getName() {
        return "profile read";
    }

    @Override
    public String getDescription() {
        return "profile read — показать текущий профиль пользователя";
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

        ctx.getOut().println("id: " + current.getId());
        ctx.getOut().println("name: " + current.getName());
        ctx.getOut().println("email: " + current.getEmail());
        ctx.getOut().println("is_staff: " + current.isStaff());
    }
}

