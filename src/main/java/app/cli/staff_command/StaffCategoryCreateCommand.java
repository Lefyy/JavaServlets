package app.cli.staff_command;

import app.model.Category;
import app.service.CategoryService;
import app.cli.Command;
import app.cli.CommandContext;

public class StaffCategoryCreateCommand implements Command {

    @Override
    public String getName() {
        return "category create";
    }

    @Override
    public String getDescription() {
        return "category create <name> — создать категорию (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        if (args.length < 3) {
            ctx.getOut().println("Использование: category create <name>");
            return;
        }
        Category c = new Category();
        c.setName(args[2]);
        ctx.getCategoryService().save(c);
        ctx.getOut().println("Категория создана, id=" + c.getId());
    }
}
