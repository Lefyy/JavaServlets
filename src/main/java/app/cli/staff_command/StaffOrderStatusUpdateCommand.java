package app.cli.staff_command;

import app.cli.FlagArgs;
import app.model.OrderStatus;
import app.cli.Command;
import app.cli.CommandContext;

import java.util.Optional;

public class StaffOrderStatusUpdateCommand implements Command {

    @Override
    public String getName() {
        return "order_status update";
    }

    @Override
    public String getDescription() {
        return "order_status update --id <id> --name <name> — обновить статус заказа (staff)";
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
        String name = flags.require("--name");
        if (name == null) {
            ctx.getOut().println(flags.getError());
            return;
        }

        try {
            int id = Integer.parseInt(rawId);
            Optional<OrderStatus> opt = ctx.getOrderStatusService().findById(id);
            if (opt.isEmpty()) {
                ctx.getOut().println("Статус не найден.");
                return;
            }
            OrderStatus s = opt.get();
            s.setName(name);
            ctx.getOrderStatusService().update(s);
            ctx.getOut().println("Статус обновлён.");
        } catch (NumberFormatException e) {
            ctx.getOut().println("Неверный id.");
        }
    }
}
