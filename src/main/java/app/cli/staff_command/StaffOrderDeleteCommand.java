package app.cli.staff_command;

import app.model.Customer;
import app.cli.Command;
import app.cli.CommandContext;

public class StaffOrderDeleteCommand implements Command {

    @Override
    public String getName() {
        return "order_delete_staff";
    }

    @Override
    public String getDescription() {
        return "order_delete_staff <orderId> — удалить любой заказ (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        if (args.length < 2) {
            ctx.getOut().println("Использование: order_delete_staff <orderId>");
            return;
        }
        try {
            int orderId = Integer.parseInt(args[1]);
            Customer current = ctx.getCurrentCustomer();
            int requestingId = current != null ? current.getId() : 0;
            boolean deleted = ctx.getOrderService().delete(orderId, requestingId, true);
            ctx.getOut().println(deleted ? "Заказ удалён." : "Заказ не найден.");
        } catch (NumberFormatException e) {
            ctx.getOut().println("Неверный id заказа.");
        }
    }
}
