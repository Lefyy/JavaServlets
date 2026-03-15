package app.cli.staff_command;

import app.model.Order;
import app.model.OrderItem;
import app.cli.Command;
import app.cli.CommandContext;
import app.service.OrderService;

import java.util.List;

public class StaffOrderListCommand implements Command {

    @Override
    public String getName() {
        return "orders_all";
    }

    @Override
    public String getDescription() {
        return "orders_all — список всех заказов (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        List<Order> orders = ctx.getOrderService().findAll();
        for (Order o : orders) {
            ctx.getOut().printf("  id=%d customer_id=%d status_id=%d created_at=%s%n",
                    o.getId(), o.getCustomerId(), o.getStatusId(), o.getCreatedAt());
            List<OrderItem> items = ctx.getOrderService().getItemsByOrderId(o.getId());
            for (OrderItem item : items) {
                ctx.getOut().printf("    product_id=%d quantity=%d price=%s%n",
                        item.getProductId(), item.getQuantity(), item.getPriceAtPurchase());
            }
        }
    }
}
