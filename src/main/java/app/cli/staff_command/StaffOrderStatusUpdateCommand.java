package app.cli.staff_command;

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
        return "order_status update <id> <name> — обновить статус заказа (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        if (args.length < 4) {
            ctx.getOut().println("Использование: order_status update <id> <name>");
            return;
        }
        try {
            int id = Integer.parseInt(args[2]);
            Optional<OrderStatus> opt = ctx.getOrderStatusService().findById(id);
            if (opt.isEmpty()) {
                ctx.getOut().println("Статус не найден.");
                return;
            }
            OrderStatus s = opt.get();
            s.setName(args[3]);
            ctx.getOrderStatusService().update(s);
            ctx.getOut().println("Статус обновлён.");
        } catch (NumberFormatException e) {
            ctx.getOut().println("Неверный id.");
        }
    }
}
