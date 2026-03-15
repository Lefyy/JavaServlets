package app.cli.command;

import app.cli.Command;
import app.cli.CommandContext;
import app.model.Customer;
import app.model.Order;
import app.service.OrderService;

import java.util.ArrayList;
import java.util.List;

public class CreateOrderCommand implements Command {

    @Override
    public String getName() {
        return "order create";
    }

    @Override
    public String getDescription() {
        return "order create <productId:quantity> [productId:quantity ...] — создать заказ";
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
        if (args.length < 3) {
            ctx.getOut().println("Использование: order create <productId:quantity> [productId:quantity ...]");
            return;
        }
        List<OrderService.OrderItemDto> items = new ArrayList<>();
        for (int i = 2; i < args.length; i++) {
            String part = args[i];
            int colon = part.indexOf(':');
            if (colon <= 0) {
                ctx.getOut().println("Формат позиции: productId:quantity, например 1:2");
                return;
            }
            try {
                int productId = Integer.parseInt(part.substring(0, colon));
                int quantity = Integer.parseInt(part.substring(colon + 1));
                if (quantity <= 0) {
                    ctx.getOut().println("Количество должно быть больше 0.");
                    return;
                }
                items.add(new OrderService.OrderItemDto(productId, quantity));
            } catch (NumberFormatException e) {
                ctx.getOut().println("Неверный формат числа в позиции: " + part);
                return;
            }
        }
        try {
            Order order = ctx.getOrderService().createOrder(current.getId(), items);
            ctx.getOut().println("Заказ создан, id=" + order.getId());
        } catch (IllegalArgumentException e) {
            ctx.getOut().println("Ошибка: " + e.getMessage());
        }
    }
}
