from flask import Flask, request, jsonify
from ai.predict import predict_instance_cost

app = Flask(__name__)

# ✅ Home route (for testing server status)
@app.route("/")
def home():
    return "✅ AI EC2 Cost Prediction API is Running!"

# ✅ AI cost prediction endpoint
@app.route("/predict-cost", methods=["GET"])
def predict_cost():
    instance_type = request.args.get("instance_type", "t2.micro")
    region = request.args.get("region", "ap-south-1")
    
    try:
        usage_hours = float(request.args.get("usage_hours", 24))
    except ValueError:
        return jsonify({"error": "Invalid usage_hours"}), 400

    try:
        cost = predict_instance_cost(instance_type, region, usage_hours)
        return jsonify({
            "instance_type": instance_type,
            "region": region,
            "usage_hours": usage_hours,
            "predicted_cost": cost
        })
    except Exception as e:
        return jsonify({"error": str(e)}), 500

# ✅ Start the server
if __name__ == "__main__":
    app.run(debug=True, port=5000)
