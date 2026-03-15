package app.cli.staff_command;

import app.cli.Command;
import app.cli.CommandContext;
import app.service.CustomerService;

public class StaffCustomerDeleteCommand implements Command {

    @Override
    public String getName() {
        return "customer_delete";
    }

    @Override
    public String getDescription() {
        return "customer_delete <id> — удалить покупателя (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        if (args.length < 2) {
            ctx.getOut().println("Использование: customer_delete <id>");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            ctx.getOut().println("Неверный id.");
            return;
        }
        boolean ok = ctx.getCustomerService().delete(id);
        ctx.getOut().println(ok ? "Покупатель удалён." : "Покупатель не найден.");
    }
}
