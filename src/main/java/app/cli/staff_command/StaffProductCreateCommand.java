package app.cli.staff_command;

import app.cli.FlagArgs;
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
        return "product create --name <name> --price <price> --quantity <qty> --category-id <id> [--image-url <url>] — создать товар (staff)";    }

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
        String rawPrice = flags.require("--price");
        if (rawPrice == null) {
            ctx.getOut().println(flags.getError());
            return;
        }
        String rawQuantity = flags.require("--quantity");
        if (rawQuantity == null) {
            ctx.getOut().println(flags.getError());
            return;
        }
        String rawCategoryId = flags.require("--category-id");
        if (rawCategoryId == null) {
            ctx.getOut().println(flags.getError());
            return;
        }

        try {
            BigDecimal price = new BigDecimal(rawPrice);
            int quantity = Integer.parseInt(rawQuantity);
            int categoryId = Integer.parseInt(rawCategoryId);
            String imageUrl = flags.optional("--image-url");

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
