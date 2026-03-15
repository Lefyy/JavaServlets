package app.cli.command;

import app.cli.Command;
import app.cli.CommandContext;
import app.model.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

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
        List<Command> sortedCommands = new ArrayList<>(allCommands);
        sortedCommands.sort(Comparator.comparing(Command::getName));

        for (Command cmd : sortedCommands) {
            if (cmd.isStaffOnly() && !isStaff) {
                continue;
            }
            ctx.getOut().println("  " + cmd.getDescription());
        }
    }
}
