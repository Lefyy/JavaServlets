package app.cli.staff_command;

import app.cli.FlagArgs;
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
        return "product update --id <id> --name <name> --price <price> --quantity <qty> --category-id <id> [--image-url <url>] — обновить товар (staff)";
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
        String rawId = flags.require("--id");
        if (rawId == null) {
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
            int id = Integer.parseInt(rawId);
            Optional<Product> opt = ctx.getProductService().findById(id);
            if (opt.isEmpty()) {
                ctx.getOut().println("Товар не найден.");
                return;
            }
            Product p = opt.get();
            p.setName(name);
            p.setPrice(new BigDecimal(rawPrice));
            p.setQuantity(Integer.parseInt(rawQuantity));
            p.setCategoryId(Integer.parseInt(rawCategoryId));

            String imageUrl = flags.optional("--image-url");
            if (imageUrl != null) {
                p.setImageUrl(imageUrl);
            }
            ctx.getProductService().update(p);
            ctx.getOut().println("Товар обновлён.");
        } catch (NumberFormatException e) {
            ctx.getOut().println("Ошибка формата числа: " + e.getMessage());
        }
    }
}
