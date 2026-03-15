package app.cli;

/**
 * Консольная команда. Выполнение через сервисы, без прямого доступа к БД.
 */
public interface Command {

    String getName();

    String getDescription();

    /**
     * @return true если команда доступна только staff
     */
    boolean isStaffOnly();

    void execute(CommandContext ctx, String[] args);
}
