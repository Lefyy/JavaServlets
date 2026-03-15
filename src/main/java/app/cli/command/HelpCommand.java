package app.cli.command;

import app.cli.Command;
import app.cli.CommandContext;
import app.model.Customer;

import java.util.Collection;

public class HelpCommand implements Command {

    private final Collection<Command> allCommands;

    public HelpCommand(Collection<Command> allCommands) {
        this.allCommands = allCommands;
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "help — показать доступные команды";
    }

    @Override
    public boolean isStaffOnly() {
        return false;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        Customer current = ctx.getCurrentCustomer();
        boolean isStaff = current != null && current.isStaff();
        ctx.getOut().println("Доступные команды:");
        for (Command cmd : allCommands) {
            if (cmd.isStaffOnly() && !isStaff) {
                continue;
            }
            ctx.getOut().println("  " + cmd.getDescription());
        }
        ctx.getOut().println("  help — эта справка");
    }
}
