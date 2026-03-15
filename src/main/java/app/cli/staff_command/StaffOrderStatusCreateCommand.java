package app.cli.staff_command;

import app.cli.FlagArgs;
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
        return "order_status create --name <name> — создать статус заказа (staff)";
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
        String name = flags.require("--name");
        if (name == null) {
            ctx.getOut().println(flags.getError());
            return;
        }

        OrderStatus s = new OrderStatus();
        s.setName(name);
        ctx.getOrderStatusService().save(s);
        ctx.getOut().println("Статус создан, id=" + s.getId());
    }
}
