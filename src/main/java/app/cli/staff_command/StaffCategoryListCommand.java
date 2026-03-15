package app.cli.staff_command;

import app.model.Category;
import app.cli.Command;
import app.cli.CommandContext;
import app.service.CategoryService;

import java.util.List;

public class StaffCategoryListCommand implements Command {

    @Override
    public String getName() {
        return "categories";
    }

    @Override
    public String getDescription() {
        return "categories — список категорий (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        List<Category> list = ctx.getCategoryService().findAll();
        for (Category c : list) {
            ctx.getOut().printf("  id=%d name=%s%n", c.getId(), c.getName());
        }
    }
}
