package app.cli.command;

import app.cli.Command;
import app.cli.CommandContext;
import app.model.Customer;
import app.model.Order;
import app.model.OrderItem;
import app.service.OrderService;
import app.service.ProductService;

import java.util.List;

public class ListMyOrdersCommand implements Command {

    @Override
    public String getName() {
        return "orders";
    }

    @Override
    public String getDescription() {
        return "orders — список моих заказов";
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
        OrderService orderService = ctx.getOrderService();
        List<Order> orders = orderService.findByCustomerId(current.getId());
        if (orders.isEmpty()) {
            ctx.getOut().println("У вас пока нет заказов.");
            return;
        }
        for (Order o : orders) {
            ctx.getOut().printf("  Заказ id=%d статус_id=%d создан=%s%n",
                    o.getId(), o.getStatusId(), o.getCreatedAt());
            List<OrderItem> items = orderService.getItemsByOrderId(o.getId());
            for (OrderItem item : items) {
                ctx.getOut().printf("    product_id=%d quantity=%d price=%s%n",
                        item.getProductId(), item.getQuantity(), item.getPriceAtPurchase());
            }
        }
    }
}
