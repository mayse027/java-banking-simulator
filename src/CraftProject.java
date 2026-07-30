import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class CraftProject {

    private String name;
    private String craftType;
    private ProjectStatus status;
    private List<Transaction> transactions;
    private LocalDate startDate;
    private LocalDate lastUpdated;
    private LocalDate endDate;

    public CraftProject(String name, String craftType) {
        this.name = name;
        this.craftType = craftType;
        this.status = ProjectStatus.PENDING;
        this.transactions = new ArrayList<>();
        this.startDate = LocalDate.now();
        this.lastUpdated = LocalDate.now();
        this.endDate = null;
    }
    public enum ProjectStatus {
        IN_PROGRESS, FINISHED, DROPPED, ON_HOLD
}

    public void addTransaction(Transaction t) {
        transactions.add(t);
        lastUpdated = LocalDate.now();
    }

    public double getTotalCost() {
        double totalCost = 0.0;
        for (Transaction t : transactions) {
            totalCost += t.getAmount();
        }
        return totalCost;
    }

    public boolean isStale(int monthsThreshold) {
        long monthsSinceLastUpdate = java.time.temporal.ChronoUnit.MONTHS.between(lastUpdated, LocalDate.now());
        return monthsSinceLastUpdate > monthsThreshold;
    }

    public boolean isFinished() {
        if (status == ProjectStatus.FINISHED) {
            return status == ProjectStatus.FINISHED;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public ProjectStatus getStatus() {
        return status;
    }
}
