package app.cli.staff_command;

import app.cli.Command;
import app.cli.CommandContext;
import app.service.ProductService;

public class StaffProductDeleteCommand implements Command {

    @Override
    public String getName() {
        return "product_delete";
    }

    @Override
    public String getDescription() {
        return "product_delete <id> — удалить товар (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        if (args.length < 2) {
            ctx.getOut().println("Использование: product_delete <id>");
            return;
        }
        try {
            int id = Integer.parseInt(args[1]);
            boolean ok = ctx.getProductService().delete(id);
            ctx.getOut().println(ok ? "Товар удалён." : "Товар не найден.");
        } catch (NumberFormatException e) {
            ctx.getOut().println("Неверный id.");
        }
    }
}
