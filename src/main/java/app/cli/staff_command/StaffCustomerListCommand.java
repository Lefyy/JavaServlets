package app.cli.staff_command;

import app.model.Customer;
import app.cli.Command;
import app.cli.CommandContext;
import app.service.CustomerService;

import java.util.List;

public class StaffCustomerListCommand implements Command {

    @Override
    public String getName() {
        return "customers";
    }

    @Override
    public String getDescription() {
        return "customers — список всех покупателей (staff)";
    }

    @Override
    public boolean isStaffOnly() {
        return true;
    }

    @Override
    public void execute(CommandContext ctx, String[] args) {
        List<Customer> list = ctx.getCustomerService().findAll();
        for (Customer c : list) {
            ctx.getOut().printf("  id=%d name=%s email=%s is_staff=%s%n",
                    c.getId(), c.getName(), c.getEmail(), c.isStaff());
        }
    }
}
