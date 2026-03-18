package app.cli;

public interface Command {

    String getName();

    String getDescription();

    boolean isStaffOnly();

    void execute(CommandContext ctx, String[] args);
}
