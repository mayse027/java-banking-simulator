import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        System.out.print("Enter your name: ");
        String name = scanner.next();
        User user = new User(name);
        Portfolio portfolio = new Portfolio();

        System.out.print("Enter initial balance for a Savings account: ");
        double initialBalance = scanner.nextDouble();
        System.out.print("Enter interest rate (e.g. 0.02): ");
        double interestRate = scanner.nextDouble();

        SavingsAccount savings = new SavingsAccount(initialBalance, interestRate);
        user.addAccount(savings);

        System.out.print("Enter initial balance for a Checking account: ");
        double initialCheckingsBalance = scanner.nextDouble();
        System.out.print("Enter monthly fee: ");
        double monthlyFee = scanner.nextDouble();

        CheckingAccount checking = new CheckingAccount(initialCheckingsBalance, monthlyFee);
        user.addAccount(checking);

        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("\n" + user.getName() + "'s accounts:");
            user.listAccounts();
            System.out.println("P. Investment Portfolio");
            System.out.println("Which account would you like to use? (enter number, P for portfolio, or 0 to quit): ");
            String selection = scanner.next();

            if (selection.equals("0")) {
                keepGoing = false;
            } else if (selection.equalsIgnoreCase("P")) {
                int portfolioChoice = 0;
                while (portfolioChoice != 3) {
                    System.out.println("1. Buy Stock\n2. View Portfolio\n3. Done with portfolio");
                    portfolioChoice = scanner.nextInt();

                    if (portfolioChoice == 1) {
                        System.out.print("Enter stock symbol (e.g. AAPL): ");
                        String symbol = scanner.next();
                        System.out.print("Enter quantity: ");
                        double quantity = scanner.nextDouble();
                        portfolio.buyStock(symbol, quantity);
                    } else if (portfolioChoice == 2) {
                        portfolio.viewPortfolio();
                    } else if (portfolioChoice == 3) {
                        // do nothing, exits
                    } else {
                        System.out.println("Invalid option. Please try again.");
                    }
                }
            } else {
                int accountIndex = Integer.parseInt(selection);
                Account chosen = user.getAccounts().get(accountIndex - 1);
                System.out.println("You selected: " + chosen.getClass().getSimpleName());

                int choice = 0;
                while (choice != 4) {
                    System.out.println("1. Deposit\n2. Withdraw\n3. Check Balance\n4. Done with this account");
                    choice = scanner.nextInt();

                    if (choice == 1) {
                        System.out.print("Enter amount to deposit: ");
                        double amount = scanner.nextDouble();
                        chosen.deposit(amount);
                        System.out.println("Remaining balance: " + chosen.checkBalance());
                    } else if (choice == 2) {
                        System.out.print("Enter amount to withdraw: ");
                        double amount = scanner.nextDouble();
                        chosen.withdraw(amount);
                        System.out.println("Remaining balance: " + chosen.checkBalance());
                    } else if (choice == 3) {
                        System.out.println("Current balance: " + chosen.checkBalance());
                    } else if (choice == 4) {
                        // do nothing, exits
                    } else {
                        System.out.println("Invalid option. Please try again.");
                    }
                }
            }
        }

        System.out.println("Thank you for using our services, " + name + "!");
        scanner.close();
    }
}