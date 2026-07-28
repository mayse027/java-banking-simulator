public class SavingsAccount extends Account{
    private double interestRate;

    public SavingsAccount(double initialBalance){
        this(initialBalance, 0.0);
    }

    public SavingsAccount(double initialBalance, double interestRate){
        super(initialBalance);
        this.interestRate = interestRate;
    }

    public void applyMonthlyEffect(){
        if (interestRate < 0){
            System.out.println("Invalid interest rate. Please try again.");
            return;
        }
        balance += balance * interestRate;

    }
}
