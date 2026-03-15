package app.cli.staff_command;

import app.model.OrderStatus;
import app.cli.Command;
import app.cli.CommandContext;

import java.util.List;

public class StaffOrderStatusListCommand implements Command {

    @Override
    public String getName() {
        return "order_status list";
    }

    @Override
    public String getDescription() {
        return "order_status list — список статусов заказов (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        List<OrderStatus> list = ctx.getOrderStatusService().findAll();
        for (OrderStatus s : list) {
            ctx.getOut().printf("  id=%d name=%s%n", s.getId(), s.getName());
        }
    }
}
