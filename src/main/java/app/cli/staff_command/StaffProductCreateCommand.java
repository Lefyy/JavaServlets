package app.cli.staff_command;

import app.model.Product;
import app.cli.Command;
import app.cli.CommandContext;

import java.math.BigDecimal;

public class StaffProductCreateCommand implements Command {

    @Override
    public String getName() {
        return "product create";
    }

    @Override
    public String getDescription() {
        return "product create <name> <price> <quantity> <category_id> [image_url] — создать товар (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        if (args.length < 6) {
            ctx.getOut().println("Использование: product create <name> <price> <quantity> <category_id> [image_url]");
            return;
        }
        try {
            String name = args[2];
            BigDecimal price = new BigDecimal(args[3]);
            int quantity = Integer.parseInt(args[4]);
            int categoryId = Integer.parseInt(args[5]);
            String imageUrl = args.length > 6 ? args[6] : null;
            Product p = new Product();
            p.setName(name);
            p.setPrice(price);
            p.setQuantity(quantity);
            p.setCategoryId(categoryId);
            p.setImageUrl(imageUrl);
            ctx.getProductService().create(p);
            ctx.getOut().println("Товар создан, id=" + p.getId());
        } catch (NumberFormatException e) {
            ctx.getOut().println("Ошибка формата числа: " + e.getMessage());
        }
    }
}
