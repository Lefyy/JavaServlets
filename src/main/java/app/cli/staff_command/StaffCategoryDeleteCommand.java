package app.cli.staff_command;

import app.cli.Command;
import app.cli.CommandContext;
import app.cli.FlagArgs;
import app.service.CategoryService;

public class StaffCategoryDeleteCommand implements Command {

    @Override
    public String getName() {
        return "category delete";
    }

    @Override
    public String getDescription() {
        return "category delete --id <id> — удалить категорию (staff)";
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
        try {
            int id = Integer.parseInt(rawId);
            boolean ok = ctx.getCategoryService().delete(id);
            ctx.getOut().println(ok ? "Категория удалена." : "Категория не найдена.");
        } catch (NumberFormatException e) {
            ctx.getOut().println("Неверный id.");
        }
    }
}
