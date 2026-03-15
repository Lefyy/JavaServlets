package app.cli.staff_command;

import app.model.Category;
import app.cli.Command;
import app.cli.CommandContext;

import java.util.Optional;

public class StaffCategoryUpdateCommand implements Command {

    @Override
    public String getName() {
        return "category update";
    }

    @Override
    public String getDescription() {
        return "category update <id> <name> — обновить категорию (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        if (args.length < 4) {
            ctx.getOut().println("Использование: category update <id> <name>");
            return;
        }
        try {
            int id = Integer.parseInt(args[2]);
            Optional<Category> opt = ctx.getCategoryService().findById(id);
            if (opt.isEmpty()) {
                ctx.getOut().println("Категория не найдена.");
                return;
            }
            Category c = opt.get();
            c.setName(args[3]);
            ctx.getCategoryService().update(c);
            ctx.getOut().println("Категория обновлена.");
        } catch (NumberFormatException e) {
            ctx.getOut().println("Неверный id.");
        }
    }
}
