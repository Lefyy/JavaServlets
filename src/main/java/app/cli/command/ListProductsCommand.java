package app.cli.command;

import app.cli.Command;
import app.cli.CommandContext;
import app.model.Product;
import app.service.ProductService;

import java.util.List;

public class ListProductsCommand implements Command {

    @Override
    public String getName() {
        return "products";
    }

    @Override
    public String getDescription() {
        return "products [--category <id>] [--search <название>] — список товаров";
    }

    @Override
    public boolean isStaffOnly() {
        return false;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        ProductService productService = ctx.getProductService();
        List<Product> products;
        if (args.length >= 3 && "--category".equals(args[1])) {
            try {
                int categoryId = Integer.parseInt(args[2]);
                products = productService.findByCategoryId(categoryId);
            } catch (NumberFormatException e) {
                ctx.getOut().println("Неверный id категории.");
                return;
            }
        } else if (args.length >= 3 && "--search".equals(args[1])) {
            String name = args[2];
            products = productService.findByNameContaining(name);
        } else {
            products = productService.findAll();
        }
        if (products.isEmpty()) {
            ctx.getOut().println("Товары не найдены.");
            return;
        }
        for (Product p : products) {
            ctx.getOut().printf("  id=%d name=%s price=%s quantity=%d category_id=%s%n",
                    p.getId(), p.getName(), p.getPrice(), p.getQuantity(), p.getCategoryId());
        }
    }
}
