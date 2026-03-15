package app.cli.staff_command;

import app.model.Order;
import app.cli.Command;
import app.cli.CommandContext;
import app.service.OrderService;

import java.util.Optional;

public class StaffOrderUpdateCommand implements Command {

    @Override
    public String getName() {
        return "order_update";
    }

    @Override
    public String getDescription() {
        return "order_update <id> <customer_id> <status_id> — обновить заказ (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        if (args.length < 4) {
            ctx.getOut().println("Использование: order_update <id> <customer_id> <status_id>");
            return;
        }
        try {
            int id = Integer.parseInt(args[1]);
            int customerId = Integer.parseInt(args[2]);
            int statusId = Integer.parseInt(args[3]);
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
