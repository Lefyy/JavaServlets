package app.cli.staff_command;

import app.cli.Command;
import app.cli.CommandContext;
import app.cli.FlagArgs;

public class StaffProductDeleteCommand implements Command {

    @Override
    public String getName() {
        return "product delete";
    }

    @Override
    public String getDescription() {
        return "product delete --id <id> — удалить товар (staff)";
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
            boolean ok = ctx.getProductService().delete(id);
            ctx.getOut().println(ok ? "Товар удалён." : "Товар не найден.");
        } catch (NumberFormatException e) {
            ctx.getOut().println("Неверный id.");
        }
    }
}
