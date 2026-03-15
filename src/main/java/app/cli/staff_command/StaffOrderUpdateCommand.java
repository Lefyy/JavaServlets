package app.cli.staff_command;

import app.model.Order;
import app.cli.Command;
import app.cli.CommandContext;

import java.util.Optional;

public class StaffOrderUpdateCommand implements Command {

    @Override
    public String getName() {
        return "order update";
    }

    @Override
    public String getDescription() {
        return "order update <id> <customer_id> <status_id> — обновить заказ (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        if (args.length < 5) {
            ctx.getOut().println("Использование: order update <id> <customer_id> <status_id>");
            return;
        }
        try {
            int id = Integer.parseInt(args[2]);
            int customerId = Integer.parseInt(args[3]);
            int statusId = Integer.parseInt(args[4]);
            Optional<Order> opt = ctx.getOrderService().findById(id);
            if (opt.isEmpty()) {
                ctx.getOut().println("Заказ не найден.");
                return;
            }
            Order o = opt.get();
            o.setCustomerId(customerId);
            o.setStatusId(statusId);
            ctx.getOrderService().update(o);
            ctx.getOut().println("Заказ обновлён.");
        } catch (NumberFormatException e) {
            ctx.getOut().println("Ошибка формата числа.");
        }
    }
}
