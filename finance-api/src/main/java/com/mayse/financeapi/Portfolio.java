package com.mayse.financeapi;
import java.util.ArrayList;
import java.util.List;
import java.net.http.HttpClient; // phone
import java.net.http.HttpRequest; // description of request
import java.net.http.HttpResponse; // what comes back from call
import java.net.URI;

public class Portfolio {
    private List<Stock> holdings;
    private static final String API_KEY = "WGMH6XKZSWK1RUAK";
    public Portfolio() {
        holdings = new ArrayList<>();
    }

    public double getCurrentPrice(String symbol) throws Exception {
        String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + API_KEY;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        String priceString = extractJsonString(body, "05. price");
        if (priceString == null || priceString.isBlank()) {
            throw new IllegalArgumentException("Invalid stock symbol: " + symbol);
        }

        return Double.parseDouble(priceString);
    }

    private String extractJsonString(String json, String key) {
        String quotedKey = '"' + key + '"';
        int keyIndex = json.indexOf(quotedKey);
        if (keyIndex < 0) {
            return null;
        }

        int colonIndex = json.indexOf(':', keyIndex + quotedKey.length());
        if (colonIndex < 0) {
            return null;
        }

        int valueStart = json.indexOf('"', colonIndex + 1);
        if (valueStart < 0) {
            return null;
        }

        int valueEnd = json.indexOf('"', valueStart + 1);
        if (valueEnd < 0) {
            return null;
        }

        return json.substring(valueStart + 1, valueEnd);
    }
   public void buyStock(String symbol, double quantity) throws Exception {
    try {
        double price = getCurrentPrice(symbol);
        holdings.add(new Stock(symbol, quantity, price));
        System.out.println("Bought " + quantity + " shares of " + symbol + " at $" + price + " each.");
    } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage() + ". Please check the symbol and try again.");
    }
}
    public void sellStock(String symbol, double quantity) {
        for (int i = 0; i < holdings.size(); i++) {
            Stock s = holdings.get(i);
            if (s.getSymbol().equals(symbol)) {
                if (s.getQuantity() >= quantity) {
                    s.setQuantity(s.getQuantity() - quantity);
                    System.out.println("Sold " + quantity + " shares of " + symbol + ".");
                    if (s.getQuantity() == 0) {
                        holdings.remove(i);
                    }
                    return;
                } else {
                    System.out.println("Not enough shares to sell. You have " + s.getQuantity() + " shares.");
                    return;
                }
            }
        }
        System.out.println("You do not own any shares of " + symbol + ".");
    }
    public void viewPortfolio() throws Exception {
        System.out.println("\n--- Your Portfolio ---");
        double totalValue = 0;
        for (Stock s : holdings) {
            double currentPrice = getCurrentPrice(s.getSymbol());
            double value = currentPrice * s.getQuantity();
            double gainLoss = (currentPrice - s.getPurchasePrice()) * s.getQuantity();
            totalValue += value;
            System.out.println("%s %.2f shares | Bought @ $%.2f | Current Price: $%.2f | Value: $%.2f | Gain/Loss: $%.2f".formatted(
                    s.getSymbol(), s.getQuantity(), s.getPurchasePrice(), currentPrice, value, gainLoss));
        }
        System.out.println("Total portfolio value: $" + totalValue);
    }
}
