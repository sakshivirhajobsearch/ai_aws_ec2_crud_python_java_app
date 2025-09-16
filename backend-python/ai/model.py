from sklearn.linear_model import LinearRegression
import numpy as np
import pickle

# Sample training data (e.g., predicting EC2 usage cost)
X = np.array([[1], [2], [3], [4], [5]])  # hours
y = np.array([10, 20, 30, 40, 50])      # cost

model = LinearRegression()
model.fit(X, y)

# Save the model
with open("ai_model.pkl", "wb") as f:
    pickle.dump(model, f)

print("Model trained and saved successfully.")
