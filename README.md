# Personal Finance Platform

A multi-account personal finance simulator built in Java, with a Python analytics pipeline, live stock investing, and project-based spending tracking for crafters.

## Features

- **Multi-account banking** — create and manage multiple Savings and Checking accounts under a single user, each with distinct behavior (interest accrual vs. monthly fees)
- **Transaction tracking** — every deposit, withdrawal, and round-up gets logged with a timestamp and category
- **Budgeting tools** — set a monthly budget, track spending by category, and check whether you're over/under your limit
- **Round-up savings** — optionally round up withdrawals to the nearest dollar and stash the difference in savings
- **Data export & analysis** — export full transaction history to CSV, then analyze spending patterns using a Python script (pandas + matplotlib)
- **Live stock investing** — buy and sell stocks using real-time prices pulled from the Alpha Vantage API, and track portfolio value and gain/loss over time
- **Fraud/anomaly detection** — flags withdrawals that are 3+ standard deviations above a category's historical average, using per-category rolling statistics. Includes cold-start handling so categories aren't evaluated until enough history exists (5+ transactions), and flagged transactions include detailed reasoning with the dollar multiple and standard-deviation distance from the norm
- **Craft project tracking** — built to solve a real problem in the crafting community: unfinished projects ("UFOs") quietly tying up money and materials. Track spending per project (not just per category), mark projects as in-progress, finished, dropped, or on hold, and flag projects that have gone stale (no activity in X months) before they turn into abandoned UFOs

## Tech Stack

- **Java** — core application logic (OOP: abstraction, inheritance, polymorphism)
- **Python** (pandas, matplotlib) — transaction data analysis and visualization
- **Alpha Vantage API** — live stock price data
- **org.json** — JSON parsing for API responses

## Project Structure
src
├── Main.java # entry point, menu-driven CLI
├── User.java # manages a user's list of accounts
├── Account.java # abstract base class for accounts
├── SavingsAccount.java # interest-bearing account
├── CheckingAccount.java # fee-based account
├── Transaction.java # transaction record with category/timestamp
├── AnomalyDetector.java # per-category outlier detection on withdrawals
├── CraftProject.java # tracks cost, status, and staleness for a craft project
├── ProjectStatus.java # enum: IN_PROGRESS, FINISHED, DROPPED, ON_HOLD
├── Portfolio.java # stock holdings + live price lookups
└── Stock.java # a single stock holding record
analyze.py # Python script for spending analysis/visualization
transactions.csv # exported transaction data (generated at runtime)
## How to Run

1. Make sure you have a JDK (17+) installed
2. Compile and run `Main.java` from your IDE or terminal
3. Follow the prompts to create accounts, make transactions, and (optionally) invest in stocks 
4. To analyze your spending: export transactions to CSV from the app, then run:
​```bash
python3 analyze.py transactions.csv
​```
This generates visualizations of your spending patterns by category.

## Notes

- The stock investing feature requires a free [Alpha Vantage API key](https://www.alphavantage.co/support/#api-key)
- This is a learning project built incrementally while studying object-oriented design, external API integration, and cross-language data pipelines

## Planned Improvements

- Adding a web frontend
- Password-based user authentication