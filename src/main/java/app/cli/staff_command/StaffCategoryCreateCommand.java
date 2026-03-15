package app.cli.staff_command;

import app.cli.FlagArgs;
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
        return "category create --name <name> — создать категорию (staff)";
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
        String name = flags.require("--name");
        if (name == null) {
            ctx.getOut().println(flags.getError());
            return;
        }
        Category c = new Category();
        c.setName(name);
        ctx.getCategoryService().save(c);
        ctx.getOut().println("Категория создана, id=" + c.getId());
    }
}
