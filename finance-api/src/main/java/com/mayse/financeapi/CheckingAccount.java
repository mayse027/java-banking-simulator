package com.mayse.financeapi;
public class CheckingAccount extends Account {

    private double monthlyFee;

    public CheckingAccount(double initialBalance){
        this(initialBalance, 0.0);
    }

    public CheckingAccount(double initialBalance, double monthlyFee){
        super(initialBalance);
        this.monthlyFee = monthlyFee;
    }

    public void applyMonthlyEffect(){
        if (monthlyFee < 0){
            System.out.println("Invalid monthly fee. Please try again.");
            return;
        }
        balance -= monthlyFee;
    }
    
}
