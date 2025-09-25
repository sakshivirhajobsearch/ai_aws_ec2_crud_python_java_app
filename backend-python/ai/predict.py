import pickle
import pandas as pd
import os

# Load the model only
model_path = os.path.join(os.path.dirname(__file__), "ai_model.pkl")
with open(model_path, "rb") as f:
    model = pickle.load(f)

# All possible features (manually define if not saved during training)
feature_names = [
    "InstanceType_t2.micro",
    "InstanceType_m5.large",
    "Region_ap-south-1",
    "Region_us-east-1",
    "Region_us-west-1",
    "UsageHours"
]

def predict_instance_cost(instance_type, region, usage_hours):
    # Create input DataFrame with zeroes
    input_data = pd.DataFrame([0]*len(feature_names), index=feature_names).T

    # Set appropriate fields
    input_data[f"InstanceType_{instance_type}"] = 1
    input_data[f"Region_{region}"] = 1
    input_data["UsageHours"] = usage_hours

    # Predict
    cost = model.predict(input_data)[0]
    return round(cost, 2)

if __name__ == "__main__":
    print(predict_instance_cost("t2.micro", "ap-south-1", 24))
