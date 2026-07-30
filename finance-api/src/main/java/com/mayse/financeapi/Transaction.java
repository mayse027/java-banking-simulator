package com.mayse.financeapi;
import java.time.LocalDateTime;
public class Transaction {
    private String type;
    private double amount;
    private LocalDateTime timestamp;
    private String category;

    public Transaction(String type, double amount, String category) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.category = category;
    }

    public Transaction(String category, double amount) {
        this("expense", amount, category);
    }

    public double getAmount() {
        return amount;
    }
    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String toString() {
        return timestamp + " - " + type + ": $" + amount + " [" + category + "]";
    }
}
