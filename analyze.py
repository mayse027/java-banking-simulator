import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv('transactions.csv')
print(df)

spending_by_category = df.groupby('category')['amount'].sum()
print("Spending by Category:")
print(spending_by_category)
spending_by_category.plot(kind="bar", title="Spending by Category")
plt.ylabel("Amount ($)")
plt.tight_layout()
plt.savefig("spending_chart.png")
plt.show()