package app.cli.staff_command;

import app.model.Customer;
import app.cli.Command;
import app.cli.CommandContext;
import app.service.CustomerService;

import java.util.Optional;

public class StaffCustomerUpdateCommand implements Command {

    @Override
    public String getName() {
        return "customer_update";
    }

    @Override
    public String getDescription() {
        return "customer_update <id> <name> <email> <password> <is_staff true|false> — обновить покупателя (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        if (args.length < 6) {
            ctx.getOut().println("Использование: customer_update <id> <name> <email> <password> <is_staff>");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            ctx.getOut().println("Неверный id.");
            return;
        }
        String name = args[2];
        String email = args[3];
        String password = args[4];
        boolean isStaff = Boolean.parseBoolean(args[5]);
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
