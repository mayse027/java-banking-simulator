
public class Stock {

    private String symbol;
    private double quantity;
    private double purchasePrice;

    public Stock(String symbol, double quantity, double purchasePrice) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }
}