package app.cli.staff_command;

import app.cli.FlagArgs;
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
        return "category update --id <id> --name <name> — обновить категорию (staff)";
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

        try {
            int id = Integer.parseInt(rawId);
            Optional<Category> opt = ctx.getCategoryService().findById(id);
            if (opt.isEmpty()) {
                ctx.getOut().println("Категория не найдена.");
                return;
            }
            Category c = opt.get();
            c.setName(name);
            ctx.getCategoryService().update(c);
            ctx.getOut().println("Категория обновлена.");
        } catch (NumberFormatException e) {
            ctx.getOut().println("Неверный id.");
        }
    }
}
