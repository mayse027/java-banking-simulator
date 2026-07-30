package com.mayse.financeapi;

import org.springframework.stereotype.Component;

@Component
public class AppState {

    private User user;
    private Portfolio portfolio;

    public AppState() {
        user = new User("Mayse");
        user.addAccount(new SavingsAccount(1000.0, 0.02));
        user.addAccount(new CheckingAccount(500.0, 5.0));
        portfolio = new Portfolio();
    }

    public User getUser() {
        return user;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }
}