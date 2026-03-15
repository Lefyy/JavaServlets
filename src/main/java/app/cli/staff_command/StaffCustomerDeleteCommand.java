package app.cli.staff_command;

import app.cli.Command;
import app.cli.CommandContext;
import app.cli.FlagArgs;
import app.service.CustomerService;

public class StaffCustomerDeleteCommand implements Command {

    @Override
    public String getName() {
        return "customer delete";
    }

    @Override
    public String getDescription() {
        return "customer delete --id <id> — удалить покупателя (staff)";
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

        int id;
        try {
            id = Integer.parseInt(rawId);
        } catch (NumberFormatException e) {
            ctx.getOut().println("Неверный id.");
            return;
        }
        boolean ok = ctx.getCustomerService().delete(id);
        ctx.getOut().println(ok ? "Покупатель удалён." : "Покупатель не найден.");
    }
}
