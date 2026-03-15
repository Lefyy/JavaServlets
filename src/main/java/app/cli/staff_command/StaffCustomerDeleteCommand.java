package app.cli.staff_command;

import app.cli.Command;
import app.cli.CommandContext;
import app.service.CustomerService;

public class StaffCustomerDeleteCommand implements Command {

    @Override
    public String getName() {
        return "customer delete";
    }

    @Override
    public String getDescription() {
        return "customer delete <id> — удалить покупателя (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        if (args.length < 3) {
            ctx.getOut().println("Использование: customer delete <id>");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            ctx.getOut().println("Неверный id.");
            return;
        }
        boolean ok = ctx.getCustomerService().delete(id);
        ctx.getOut().println(ok ? "Покупатель удалён." : "Покупатель не найден.");
    }
}
