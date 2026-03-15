package app.cli.staff_command;

import app.cli.Command;
import app.cli.CommandContext;
import app.service.CategoryService;

public class StaffCategoryDeleteCommand implements Command {

    @Override
    public String getName() {
        return "category_delete";
    }

    @Override
    public String getDescription() {
        return "category_delete <id> — удалить категорию (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        if (args.length < 2) {
            ctx.getOut().println("Использование: category_delete <id>");
            return;
        }
        try {
            int id = Integer.parseInt(args[1]);
            boolean ok = ctx.getCategoryService().delete(id);
            ctx.getOut().println(ok ? "Категория удалена." : "Категория не найдена.");
        } catch (NumberFormatException e) {
            ctx.getOut().println("Неверный id.");
        }
    }
}
