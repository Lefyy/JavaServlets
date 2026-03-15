package app.cli.staff_command;

import app.cli.FlagArgs;
import app.model.Customer;
import app.cli.Command;
import app.cli.CommandContext;
import app.service.CustomerService;

import java.util.Optional;

public class StaffCustomerUpdateCommand implements Command {

    @Override
    public String getName() {
        return "customer update";
    }

    @Override
    public String getDescription() {
        return "customer update --id <id> --name <name> --email <email> --password <password> [--is-staff <true|false>] — обновить покупателя (staff)";
    }

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

        String rawId = flags.require("--id");
        if (rawId == null) {
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

        int id;
        try {
            id = Integer.parseInt(rawId);
        } catch (NumberFormatException e) {
            ctx.getOut().println("Неверный id.");
            return;
        }

        boolean isStaff = Boolean.parseBoolean(flags.optional("--is-staff"));

        CustomerService svc = ctx.getCustomerService();
        Optional<Customer> opt = svc.findById(id);
        if (opt.isEmpty()) {
            ctx.getOut().println("Покупатель не найден.");
            return;
        }
        Customer c = opt.get();
        c.setName(name);
        c.setEmail(email);
        c.setPassword(password);
        c.setStaff(isStaff);
        svc.update(c);
        ctx.getOut().println("Покупатель обновлён.");
    }
}
