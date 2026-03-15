package app.cli.staff_command;

import app.model.OrderStatus;
import app.cli.Command;
import app.cli.CommandContext;

public class StaffOrderStatusCreateCommand implements Command {

    @Override
    public String getName() {
        return "order_status create";
    }

    @Override
    public String getDescription() {
        return "order_status create <name> — создать статус заказа (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        if (args.length < 3) {
            ctx.getOut().println("Использование: order_status create <name>");
            return;
        }
        OrderStatus s = new OrderStatus();
        s.setName(args[2]);
        ctx.getOrderStatusService().save(s);
        ctx.getOut().println("Статус создан, id=" + s.getId());
    }
}
