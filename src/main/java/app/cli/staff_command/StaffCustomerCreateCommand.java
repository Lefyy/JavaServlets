package app.cli.staff_command;

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
        return "customer create <name> <email> <password> <is_staff true|false> — создать покупателя (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        if (args.length < 6) {
            ctx.getOut().println("Использование: customer create <name> <email> <password> <is_staff true|false>");
            return;
        }
        String name = args[2];
        String email = args[3];
        String password = args[4];
        boolean isStaff = Boolean.parseBoolean(args[5]);
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
