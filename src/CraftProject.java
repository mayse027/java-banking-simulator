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
        this.status = ProjectStatus.IN_PROGRESS;
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
    public void markAsFinished() {
        this.status = ProjectStatus.FINISHED;
        this.endDate = LocalDate.now();
    }
    public boolean isFinished() {
        return status == ProjectStatus.FINISHED;
    }
    public void markDropped() {
        this.status = ProjectStatus.DROPPED;
    }
    public String getName() {
        return name;
    }

    public ProjectStatus getStatus() {
        return status;
    }
    public String getCraftType() {
        return craftType;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
}
