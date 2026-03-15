package app.cli.staff_command;

import app.cli.FlagArgs;
import app.model.Customer;
import app.cli.Command;
import app.cli.CommandContext;

public class StaffCustomerCreateCommand implements Command {

    @Override
    public String getName() {
        return "customer create";
    }

    @Override
    public String getDescription() {
        return "customer create --name <name> --email <email> --password <password> [--is-staff <true|false>] — создать покупателя (staff)";    }

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

        String name = flags.require("--name");
        if (name == null) {
            ctx.getOut().println(flags.getError());
            return;
        }
        String email = flags.require("--email");
        if (email == null) {
            ctx.getOut().println(flags.getError());
            return;
        }
        String password = flags.require("--password");
        if (password == null) {
            ctx.getOut().println(flags.getError());
            return;
        }
        boolean isStaff = Boolean.parseBoolean(flags.optional("--is-staff"));

        if (ctx.getCustomerService().existsByEmail(email)) {
            ctx.getOut().println("Email уже занят.");
            return;
        }
        Customer c = new Customer();
        c.setName(name);
        c.setEmail(email);
        c.setPassword(password);
        c.setStaff(isStaff);
        ctx.getCustomerService().save(c);
        ctx.getOut().println("Покупатель создан, id=" + c.getId());
    }
}
