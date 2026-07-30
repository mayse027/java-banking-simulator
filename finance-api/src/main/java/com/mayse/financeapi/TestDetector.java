package com.mayse.financeapi;
public class TestDetector {
    public static void main(String[] args) {
        AnomalyDetector detector = new AnomalyDetector();

        detector.checkTransaction(new Transaction("groceries", 35.00));
        detector.checkTransaction(new Transaction("groceries", 40.00));
        detector.checkTransaction(new Transaction("groceries", 38.00));
        detector.checkTransaction(new Transaction("groceries", 42.00));
        detector.checkTransaction(new Transaction("groceries", 37.00));

        System.out.println("Testing normal transaction (should NOT flag):");
        detector.checkTransaction(new Transaction("groceries", 39.00));

        System.out.println("Testing outlier transaction (SHOULD flag):");
        detector.checkTransaction(new Transaction("groceries", 400.00));
    }
}
