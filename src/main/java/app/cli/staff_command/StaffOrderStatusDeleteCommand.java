package app.cli.staff_command;

import app.cli.Command;
import app.cli.CommandContext;
import app.service.OrderStatusService;

public class StaffOrderStatusDeleteCommand implements Command {

    @Override
    public String getName() {
        return "order_status_delete";
    }

    @Override
    public String getDescription() {
        return "order_status_delete <id> — удалить статус заказа (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        if (args.length < 2) {
            ctx.getOut().println("Использование: order_status_delete <id>");
            return;
        }
        try {
            int id = Integer.parseInt(args[1]);
            boolean ok = ctx.getOrderStatusService().delete(id);
            ctx.getOut().println(ok ? "Статус удалён." : "Статус не найден.");
        } catch (NumberFormatException e) {
            ctx.getOut().println("Неверный id.");
        }
    }
}
