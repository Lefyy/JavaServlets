package app.cli;

import app.model.Customer;

import java.util.*;

/**
 * Реестр команд. Выдача списка команд в зависимости от is_staff.
 */
public class CommandRegistry {

    private static final Set<String> SYSTEM_COMMANDS = Set.of("help", "login", "logout", "register");

    private final Map<String, Command> commandsByName = new HashMap<>();

    public void register(Command command) {
        String key = normalizeKey(command.getName());
        commandsByName.put(key, command);

        if (SYSTEM_COMMANDS.contains(key)) {
            commandsByName.put(systemKey(key), command);
        }
    }

    public Optional<Command> find(String name) {
        return Optional.ofNullable(commandsByName.get(normalizeKey(name)));
    }

    /**
     * Проверяет, доступна ли команда пользователю (для staff — все, для обычного — только не staff-only).
     */
    public boolean canExecute(Command command, Customer current) {
        if (command == null) {
            return false;
        }
        if (!command.isStaffOnly()) {
            return true;
        }
        return current != null && current.isStaff();
    }

    public Collection<Command> getAllCommands() {
        return Collections.unmodifiableCollection(new LinkedHashSet<>(commandsByName.values()));
    }

    public static String systemKey(String baseCommand) {
        return "system " + normalizeKey(baseCommand);
    }

    private static String normalizeKey(String name) {
        return name == null ? "" : name.trim().toLowerCase(Locale.ROOT);

    }
}
