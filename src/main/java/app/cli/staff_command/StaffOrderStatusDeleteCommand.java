package app.cli.staff_command;

import app.cli.Command;
import app.cli.CommandContext;
import app.cli.FlagArgs;
import app.service.OrderStatusService;

public class StaffOrderStatusDeleteCommand implements Command {

    @Override
    public String getName() {
        return "order_status delete";
    }

    @Override
    public String getDescription() {
        return "order_status delete --id <id> — удалить статус заказа (staff)";
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
            int id = Integer.parseInt(rawId);
            boolean ok = ctx.getOrderStatusService().delete(id);
            ctx.getOut().println(ok ? "Статус удалён." : "Статус не найден.");
        } catch (NumberFormatException e) {
            ctx.getOut().println("Неверный id.");
        }
    }
}
