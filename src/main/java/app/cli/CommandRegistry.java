package app.cli;

import app.model.Customer;

import java.util.*;

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

    public CommandLookup lookup(String[] parts) {
        String relation = parts.length > 0 ? normalizeKey(parts[0]) : "";
        if (SYSTEM_COMMANDS.contains(relation)) {
            return new CommandLookup(systemKey(relation), "<" + relation + ">");
        }

        String action = parts.length > 1 ? normalizeKey(parts[1]) : null;
        String displayName = "<" + relation + " " + (action != null ? action : "?") + ">";
        String key = action != null ? relation + " " + action : relation;
        return new CommandLookup(key, displayName);
    }

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
