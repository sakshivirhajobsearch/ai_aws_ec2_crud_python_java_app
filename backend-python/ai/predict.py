import pickle
import numpy as np

def predict(hours):
    with open("ai_model.pkl", "rb") as f:
        model = pickle.load(f)
    prediction = model.predict(np.array([[hours]]))
    return prediction[0]

if __name__ == "__main__":
    hours = 6
    print(f"Predicted cost for {hours} hours: {predict(hours)}")
