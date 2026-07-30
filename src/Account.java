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
            public Account(double initialBalance){
            balance = initialBalance;
            craftProjects = new ArrayList<>();
            detector = new AnomalyDetector(); 
            transactions = new ArrayList<>();
            transactions.add(new Transaction("Initial Deposit", initialBalance, "Income"));
        }

        public void deposit(double amount){
            if (amount <= 0){
                System.out.println("Invalid amount. Please try again.");
                return;
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

       public void withdraw(double amount){
    if (amount <= 0){
        System.out.println("Invalid amount. Please try again.");
        return;
    }
    if (amount > balance) {
        System.out.println("Withdrawal amount exceeds current balance. Please try again.");
        return;
    }

    balance -= amount;
    String category = promptForCategory();
    Transaction t = new Transaction("Withdrawal", amount, category);
    transactions.add(t);
    detector.checkTransaction(t);

    System.out.println("Withdrawal successful. Would you like to enable round-up savings for this transaction? (yes/no)");
    String response = Main.scanner.next();
    if (response.equalsIgnoreCase("yes")) {
        double roundUpAmount = Math.ceil(amount) - amount;
        roundupSavings += roundUpAmount;
        balance -= roundUpAmount;
        transactions.add(new Transaction("Round-up Savings", roundUpAmount, "Savings"));
        System.out.println("Round-up savings of $" + String.format("%.2f", roundUpAmount) + " has been added to your savings.");
    }
}
        private String promptForCategory() {
    String[] categories = {"Groceries", "Entertainment", "Bills", "Transportation", "Dining", "Shopping", "Other"};

    while (true) {
        System.out.println("Select a category:");
        for (int i = 0; i < categories.length; i++) {
            System.out.println((i + 1) + ". " + categories[i]);
        }
        System.out.print("Enter number: ");

        String input = Main.scanner.next();

        try {
            int choice = Integer.parseInt(input);
            if (choice >= 1 && choice <= categories.length) {
                return categories[choice - 1];
            }
        } catch (NumberFormatException e) {
            // falls through to the reprompt below
        }

        System.out.println("Invalid choice. Please enter a number between 1 and " + categories.length + ".");
        }
    }   

        public double checkBalance(){
            return balance;
        }

        public void viewTransactionHistory(){
            System.out.println("\n--- Transaction History ---");
            if (transactions.isEmpty()) {
                System.out.println("No transactions yet.");
            } else {
                for (Transaction t : transactions) {
                    System.out.println(t);
                }
            }
            System.out.println();
        }

        public void printStatement(){
            System.out.println("\n--- Account Statement ---");
            System.out.println("Current Balance: $" + String.format("%.2f", balance));
            System.out.println("Total Transactions: " + transactions.size());
            viewTransactionHistory();
        }

        public void exportToCSV(String filename) {
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename));
                writer.println("timestamp,type,category,amount");
                for (Transaction t : transactions) {
                    writer.println(t.getTimestamp() + "," + t.getType() + "," + t.getCategory() + "," + t.getAmount());
                }
                writer.close();
                System.out.println("Transactions exported to " + filename);
            } catch (IOException e) {
                System.out.println("Error exporting file: " + e.getMessage());
            }
        }

        public abstract void applyMonthlyEffect();

        public void askCategory(String type, double amount) {
            System.out.print("Enter category for this " + type + ": ");
            String category = Main.scanner.next();
            System.out.println("Category '" + category + "' recorded for this transaction.");
        }
        public void getSpendingByCategory(String category) {
            double total = 0;
            for (Transaction t : transactions) {
                if (t.getCategory().equalsIgnoreCase(category)) {
                    total += t.getAmount();
                }
            }
            System.out.println("Total spending in category '" + category + "': $" + String.format("%.2f", total));
        }
        public void checkBudgetStatus() {
            if (monthlyBudget <= 0) {
                System.out.println("No budget set. Please set a monthly budget first.");
                return;
            }
            double totalSpending = 0;
            for (Transaction t : transactions) {
                if (t.getType().equalsIgnoreCase("Withdrawal")) {
                    totalSpending += t.getAmount();
                }
            }
            if (totalSpending > monthlyBudget) {
                System.out.println("You have exceeded your monthly budget of $" + String.format("%.2f", monthlyBudget) + ". Total spending: $" + String.format("%.2f", totalSpending));
            } else {
                System.out.println("You are within your monthly budget of $" + String.format("%.2f", monthlyBudget) + ". Total spending: $" + String.format("%.2f", totalSpending));
            }
        }

        public void viewCraftProjects() {
            System.out.println("\n--- Craft Projects ---");
                if (craftProjects.isEmpty()) {
                    System.out.println("No craft projects yet.");
                } else {
                for (CraftProject project : craftProjects) {
                    String staleFlag = project.isStale(3) ? " ⚠️ STALE" : "";
                    System.out.println("Name: " + project.getName() + ", Type: " + project.getCraftType() + 
                    ", Status: " + project.getStatus() + ", Total Cost: $" + String.format("%.2f", project.getTotalCost()) + staleFlag);
        }
    }
}
}
