package app.service.impl;

import app.model.Customer;
import app.service.auth.AuthContext;

public class AuthContextImpl implements AuthContext {

    private Customer currentCustomer;

    @Override
    public Customer getCurrentCustomer() {
        return currentCustomer;
    }

    @Override
    public void setCurrentCustomer(Customer customer) {
        this.currentCustomer = customer;
    }

    @Override
    public void clear() {
        this.currentCustomer = null;
    }

    @Override
    public boolean isAuthenticated() {
        return currentCustomer != null;
    }
}
