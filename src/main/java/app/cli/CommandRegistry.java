package app.cli;

import app.model.Customer;

import java.util.*;

/**
 * Реестр команд. Выдача списка команд в зависимости от is_staff.
 */
public class CommandRegistry {

    private final Map<String, Command> commandsByName = new HashMap<>();

    public void register(Command command) {
        commandsByName.put(command.getName().toLowerCase(), command);
    }

    public Optional<Command> find(String name) {
        return Optional.ofNullable(commandsByName.get(name != null ? name.toLowerCase() : null));
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
        return Collections.unmodifiableCollection(commandsByName.values());
    }
}
