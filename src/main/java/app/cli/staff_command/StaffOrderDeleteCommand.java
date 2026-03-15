package app.cli.staff_command;

import app.cli.FlagArgs;
import app.model.Customer;
import app.cli.Command;
import app.cli.CommandContext;

public class StaffOrderDeleteCommand implements Command {

    @Override
    public String getName() {
        return "order delete_staff";
    }

    @Override
    public String getDescription() {
        return "order delete_staff --id <orderId> — удалить любой заказ (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        FlagArgs flags = FlagArgs.parse(args, 2);
        if (flags.getError() != null) {
            ctx.getOut().println(flags.getError());
            return;
        }
        String rawId = flags.require("--id");
        if (rawId == null) {
            ctx.getOut().println(flags.getError());
            return;
        }

        try {
            int orderId = Integer.parseInt(rawId);
            Customer current = ctx.getCurrentCustomer();
            int requestingId = current != null ? current.getId() : 0;
            boolean deleted = ctx.getOrderService().delete(orderId, requestingId, true);
            ctx.getOut().println(deleted ? "Заказ удалён." : "Заказ не найден.");
        } catch (NumberFormatException e) {
            ctx.getOut().println("Неверный id заказа.");
        }
    }
}
