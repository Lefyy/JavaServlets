package app.cli.staff_command;

import app.model.Product;
import app.cli.Command;
import app.cli.CommandContext;

import java.math.BigDecimal;
import java.util.Optional;

public class StaffProductUpdateCommand implements Command {

    @Override
    public String getName() {
        return "product update";
    }

    @Override
    public String getDescription() {
        return "product update <id> <name> <price> <quantity> <category_id> [image_url] — обновить товар (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        if (args.length < 7) {
            ctx.getOut().println("Использование: product update <id> <name> <price> <quantity> <category_id> [image_url]");
            return;
        }
        try {
            int id = Integer.parseInt(args[2]);
            Optional<Product> opt = ctx.getProductService().findById(id);
            if (opt.isEmpty()) {
                ctx.getOut().println("Товар не найден.");
                return;
            }
            Product p = opt.get();
            p.setName(args[3]);
            p.setPrice(new BigDecimal(args[4]));
            p.setQuantity(Integer.parseInt(args[5]));
            p.setCategoryId(Integer.parseInt(args[6]));
            if (args.length > 7) {
                p.setImageUrl(args[7]);
            }
            ctx.getProductService().update(p);
            ctx.getOut().println("Товар обновлён.");
        } catch (NumberFormatException e) {
            ctx.getOut().println("Ошибка формата числа: " + e.getMessage());
        }
    }
}
