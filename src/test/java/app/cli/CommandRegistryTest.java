package app.cli;

import app.cli.command.RegisterCommand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandRegistryTest {

    @Test
    void lookupTreatsRegisterAsSystemCommandEvenWithFlags() {
        CommandRegistry registry = new CommandRegistry();
        registry.register(new RegisterCommand());

        CommandLookup lookup = registry.lookup(new String[]{"register", "--name", "Alice"});

        assertEquals(CommandRegistry.systemKey("register"), lookup.key());
        assertEquals("<register>", lookup.displayName());
    }

    @Test
    void lookupKeepsRelationActionFormatForNonSystemCommands() {
        CommandRegistry registry = new CommandRegistry();

        CommandLookup lookup = registry.lookup(new String[]{"staff", "customer-create", "--name", "Alice"});

        assertEquals("staff customer-create", lookup.key());
        assertEquals("<staff customer-create>", lookup.displayName());
    }
}

