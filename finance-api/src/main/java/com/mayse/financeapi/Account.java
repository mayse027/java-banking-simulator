package com.mayse.financeapi;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public abstract class Account {

    protected double balance;
    protected double monthlyBudget;
    protected double roundupSavings;
    protected List<Transaction> transactions;
    protected List<CraftProject> craftProjects;
    protected AnomalyDetector detector;

    public Account(double initialBalance) {
        balance = initialBalance;
        craftProjects = new ArrayList<>();
        detector = new AnomalyDetector();
        transactions = new ArrayList<>();
        transactions.add(new Transaction("Initial Deposit", initialBalance, "Income"));
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid amount. Must be greater than 0.");
        }
        balance += amount;
        transactions.add(new Transaction("Deposit", amount, "Income"));
    }

    public String getAccountType() {
        return this.getClass().getSimpleName();
    }

    public double getBalance() {
        return balance;
    }

    /**
     * Withdraw money from the account.
     * @param amount how much to withdraw
     * @param category the spending category (e.g. "Groceries", "Fabric")
     * @param enableRoundup whether to round up to the nearest dollar into savings
     * @param craftProjectName optional - if the category is a craft category, link this transaction
     *                          to an existing craft project by name (pass null to skip)
     */
    public void withdraw(double amount, String category, boolean enableRoundup, String craftProjectName) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid amount. Must be greater than 0.");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Withdrawal amount exceeds current balance.");
        }

        balance -= amount;
        Transaction t = new Transaction("Withdrawal", amount, category);
        transactions.add(t);
        detector.checkTransaction(t);

        List<String> craftCategories = List.of("Fabric", "Floss/Thread", "Patterns", "Craft Tools/Notions");
        if (craftCategories.contains(category) && craftProjectName != null) {
            CraftProject project = findCraftProjectByName(craftProjectName);
            if (project != null) {
                project.addTransaction(t);
            }
        }

        if (enableRoundup) {
            double roundUpAmount = Math.ceil(amount) - amount;
            roundupSavings += roundUpAmount;
            balance -= roundUpAmount;
            transactions.add(new Transaction("Round-up Savings", roundUpAmount, "Savings"));
        }
    }

    public double checkBalance() {
        return balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactions;
    }

    public void exportToCSV(String filename) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename));
            writer.println("timestamp,type,category,amount");
            for (Transaction t : transactions) {
                writer.println(t.getTimestamp() + "," + t.getType() + "," + t.getCategory() + "," + t.getAmount());
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Error exporting file: " + e.getMessage());
        }
    }

    public abstract void applyMonthlyEffect();

    public double getSpendingByCategory(String category) {
        double total = 0;
        for (Transaction t : transactions) {
            if (t.getCategory().equalsIgnoreCase(category)) {
                total += t.getAmount();
            }
        }
        return total;
    }

    public void setMonthlyBudget(double budget) {
        this.monthlyBudget = budget;
    }

    public double getMonthlyBudget() {
        return monthlyBudget;
    }

    public double getRoundupSavings() {
        return roundupSavings;
    }

    /**
     * Returns true if spending has exceeded the monthly budget.
     * Returns null-equivalent (-1 total) if no budget has been set.
     */
    public double getTotalSpending() {
        double totalSpending = 0;
        for (Transaction t : transactions) {
            if (t.getType().equalsIgnoreCase("Withdrawal")) {
                totalSpending += t.getAmount();
            }
        }
        return totalSpending;
    }

    public boolean isOverBudget() {
        return monthlyBudget > 0 && getTotalSpending() > monthlyBudget;
    }

    // ---------- Craft project management (no prompts, all parameter-based) ----------

    public List<CraftProject> getCraftProjects() {
        return craftProjects;
    }

    public CraftProject findCraftProjectByName(String name) {
        for (CraftProject project : craftProjects) {
            if (project.getName().equalsIgnoreCase(name)) {
                return project;
            }
        }
        return null;
    }

    public CraftProject addCraftProject(String name, String craftType) {
        CraftProject newProject = new CraftProject(name, craftType);
        craftProjects.add(newProject);
        return newProject;
    }

    public boolean markCraftProjectFinished(String name) {
        CraftProject project = findCraftProjectByName(name);
        if (project == null) return false;
        project.markAsFinished();
        return true;
    }

    public boolean markCraftProjectDropped(String name) {
        CraftProject project = findCraftProjectByName(name);
        if (project == null) return false;
        project.markDropped();
        return true;
    }
}
