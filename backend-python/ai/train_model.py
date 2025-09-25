import pandas as pd
from sklearn.linear_model import LinearRegression
import pickle
import os

# Sample dataset
data = pd.DataFrame({
    "InstanceType": ["t2.micro", "t2.micro", "m5.large", "t2.micro", "m5.large"],
    "Region": ["ap-south-1", "us-east-1", "ap-south-1", "us-east-1", "us-west-1"],
    "UsageHours": [10, 20, 10, 30, 40],
    "Cost": [0.12, 0.25, 0.50, 0.36, 0.90]
})

# Encode categorical variables
data_encoded = pd.get_dummies(data[["InstanceType", "Region"]])
X = pd.concat([data_encoded, data["UsageHours"]], axis=1)
y = data["Cost"]

# Train model
model = LinearRegression()
model.fit(X, y)

# Save model to ai_model.pkl
model_path = os.path.join(os.path.dirname(__file__), "ai_model.pkl")
with open(model_path, "wb") as f:
    pickle.dump((model, list(X.columns)), f)

print(f"AI model trained and saved as: {model_path}")
