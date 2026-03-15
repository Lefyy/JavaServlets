package app.cli.staff_command;

import app.cli.FlagArgs;
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
        return "order update --id <id> --customer-id <customer_id> --status-id <status_id> — обновить заказ (staff)";
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
        String rawCustomerId = flags.require("--customer-id");
        if (rawCustomerId == null) {
            ctx.getOut().println(flags.getError());
            return;
        }
        String rawStatusId = flags.require("--status-id");
        if (rawStatusId == null) {
            ctx.getOut().println(flags.getError());
            return;
        }

        try {
            int id = Integer.parseInt(rawId);
            int customerId = Integer.parseInt(rawCustomerId);
            int statusId = Integer.parseInt(rawStatusId);
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
