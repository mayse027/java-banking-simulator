# Personal Finance Platform

A multi-account personal finance simulator built in Java, with a Python analytics pipeline and live stock investing.

## Features

- **Multi-account banking** — create and manage multiple Savings and Checking accounts under a single user, each with distinct behavior (interest accrual vs. monthly fees)
- **Transaction tracking** — every deposit, withdrawal, and round-up gets logged with a timestamp and category
- **Budgeting tools** — set a monthly budget, track spending by category, and check whether you're over/under your limit
- **Round-up savings** — optionally round up withdrawals to the nearest dollar and stash the difference in savings
- **Data export & analysis** — export full transaction history to CSV, then analyze spending patterns using a Python script (pandas + matplotlib)
- **Live stock investing** — buy stocks using real-time prices pulled from the Alpha Vantage API, and track portfolio value and gain/loss over time

## Tech Stack

- **Java** — core application logic (OOP: abstraction, inheritance, polymorphism)
- **Python** (pandas, matplotlib) — transaction data analysis and visualization
- **Alpha Vantage API** — live stock price data
- **org.json** — JSON parsing for API responses

## Project Structure
src/
├── Main.java # entry point, menu-driven CLI
├── User.java # manages a user's list of accounts
├── Account.java # abstract base class for accounts
├── SavingsAccount.java # interest-bearing account
├── CheckingAccount.java # fee-based account
├── Transaction.java # transaction record with category/timestamp
├── Portfolio.java # stock holdings + live price lookups
└── Stock.java # a single stock holding record
analyze.py # Python script for spending analysis/visualization
transactions.csv # exported transaction data (generated at runtime)
## How to Run

1. Make sure you have a JDK (17+) installed
2. Compile and run `Main.java` from your IDE or terminal
3. Follow the prompts to create accounts, make transactions, and (optionally) invest in stocks
4. To analyze your spending: export transactions to CSV from the app, then run:
## Notes

- The stock investing feature requires a free [Alpha Vantage API key](https://www.alphavantage.co/support/#api-key)
- This is a learning project built incrementally while studying object-oriented design, external API integration, and cross-language data pipelines

## Planned Improvements

- Adding a web frontend
- Sell stock functionality
- Password-based user authentication
- Basic fraud/anomaly detection on transactions