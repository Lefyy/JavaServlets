package app.cli.command;

import app.cli.Command;
import app.cli.CommandContext;

public class LogoutCommand implements Command {

    @Override
    public String getName() {
        return "logout";
    }

    @Override
    public String getDescription() {
        return "logout — выйти из системы";
    }

    @Override
    public boolean isStaffOnly() {
        return false;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        ctx.getAuthService().logout();
        ctx.getOut().println("Вы вышли из системы.");
    }
}
