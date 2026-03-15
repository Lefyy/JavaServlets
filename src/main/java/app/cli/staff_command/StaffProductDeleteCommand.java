package app.cli.staff_command;

import app.cli.Command;
import app.cli.CommandContext;

public class StaffProductDeleteCommand implements Command {

    @Override
    public String getName() {
        return "product delete";
    }

    @Override
    public String getDescription() {
        return "product delete <id> — удалить товар (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        if (args.length < 3) {
            ctx.getOut().println("Использование: product delete <id>");
            return;
        }
        try {
            int id = Integer.parseInt(args[2]);
            boolean ok = ctx.getProductService().delete(id);
            ctx.getOut().println(ok ? "Товар удалён." : "Товар не найден.");
        } catch (NumberFormatException e) {
            ctx.getOut().println("Неверный id.");
        }
    }
}
