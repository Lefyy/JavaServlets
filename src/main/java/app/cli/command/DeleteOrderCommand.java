package app.cli.command;

import app.cli.Command;
import app.cli.CommandContext;
import app.cli.FlagArgs;
import app.model.Customer;

public class DeleteOrderCommand implements Command {

    @Override
    public String getName() {
        return "order delete";
    }

    @Override
    public String getDescription() {
        return "order delete --id <orderId> — удалить свой заказ";
    }

    @Override
    public boolean isStaffOnly() {
        return false;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        Customer current = ctx.getCurrentCustomer();
        if (current == null) {
            ctx.getOut().println("Необходимо войти в систему (login).");
            return;
        }
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
            boolean deleted = ctx.getOrderService().delete(orderId, current.getId(), false);
            if (deleted) {
                ctx.getOut().println("Заказ удалён.");
            } else {
                ctx.getOut().println("Заказ не найден или нет прав на удаление.");
            }
        } catch (NumberFormatException e) {
            ctx.getOut().println("Неверный id заказа.");
        } catch (SecurityException e) {
            ctx.getOut().println(e.getMessage());
        }
    }
}
