import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class AnomalyDetector {

    private Map<String, List<Double>> categoryHistory;

    public AnomalyDetector() {
        this.categoryHistory = new HashMap<>();
    }

    public void checkTransaction(Transaction t) {
    String category = t.getCategory();
    List<Double> amounts = categoryHistory.get(category);

    if (amounts == null) {
        amounts = new ArrayList<>();
        categoryHistory.put(category, amounts);
    }

    if (amounts.size() < 5) {
        amounts.add(t.getAmount());
        return;
    }

    double avg = averageAmount(category);
    double stdDev = standardDeviation(category);
    if (stdDev > 0 && Math.abs(t.getAmount() - avg) > 3 * stdDev) {
        double multiple = t.getAmount() / avg;
        double numStdDevs = Math.abs(t.getAmount() - avg) / stdDev;
        String reason = String.format(
            "Amount $%.2f is %.1fx the %s category average ($%.2f), %.1f standard deviations away",
            t.getAmount(), multiple, category, avg, numStdDevs
        );
        flag(t, reason);
    }

    amounts.add(t.getAmount());
}

    private int countTransactions(String category) {
        List<Double> amounts = categoryHistory.get(category);
        return amounts != null ? amounts.size() : 0;
    }

    private double averageAmount(String category) {
        List<Double> amounts = categoryHistory.get(category);
        if (amounts == null || amounts.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (double amount : amounts) {
            sum += amount;
        }
        return sum / amounts.size();
    }

    private double standardDeviation(String category) {
        List<Double> amounts = categoryHistory.get(category);
        if (amounts == null || amounts.size() < 2) {
            return 0.0;
        }

        double mean = averageAmount(category);
        double varianceSum = 0.0;
        for (double amount : amounts) {
            double diff = amount - mean;
            varianceSum += diff * diff;
        }
        return Math.sqrt(varianceSum / amounts.size());
    }

    private void flag(Transaction t, String reason) {
        System.out.println("Flagging transaction: " + t + " - Reason: " + reason);
    }
}
 