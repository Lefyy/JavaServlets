package app.cli.command;

import app.cli.Command;
import app.cli.CommandContext;
import app.model.Customer;
import app.service.OrderService;

public class DeleteOrderCommand implements Command {

    @Override
    public String getName() {
        return "order_delete";
    }

    @Override
    public String getDescription() {
        return "order_delete <orderId> — удалить свой заказ";
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
        if (args.length < 2) {
            ctx.getOut().println("Использование: order_delete <orderId>");
            return;
        }
        try {
            int orderId = Integer.parseInt(args[1]);
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
