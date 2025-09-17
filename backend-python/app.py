from flask import Flask, request, jsonify
from ai.predict import predict
from ec2_operations import create_instance, list_instances, terminate_instance, get_all_instance_details

app = Flask(__name__)

# Health check
@app.route('/', methods=['GET'])
def home():
    return jsonify({"message": "AI + EC2 Flask API is running."})

# Predict EC2 cost using AI model
@app.route('/predict', methods=['POST'])
def predict_cost():
    try:
        data = request.get_json()
        hours = float(data.get('hours', 0))
        predicted_cost = predict(hours)
        return jsonify({'predicted_cost': round(predicted_cost, 2)})
    except Exception as e:
        return jsonify({'error': str(e)}), 500

# Launch a new EC2 instance
@app.route('/ec2/create', methods=['POST'])
def ec2_create():
    try:
        instance_id = create_instance()
        return jsonify({'instance_id': instance_id})
    except Exception as e:
        return jsonify({'error': str(e)}), 500

# List all EC2 instances (ID + State only)
@app.route('/ec2/list', methods=['GET'])
def ec2_list():
    try:
        instances = list_instances()
        return jsonify(instances)
    except Exception as e:
        return jsonify({'error': str(e)}), 500

# Get full EC2 instance details
@app.route('/ec2/details', methods=['GET'])
def ec2_details():
    try:
        details = get_all_instance_details()
        return jsonify(details)
    except Exception as e:
        return jsonify({'error': str(e)}), 500

# Terminate a specific EC2 instance
@app.route('/ec2/terminate', methods=['POST'])
def ec2_terminate():
    try:
        data = request.get_json()
        instance_id = data.get('instance_id')
        if not instance_id:
            return jsonify({'error': 'Missing instance_id'}), 400
        result = terminate_instance(instance_id)
        return jsonify({'message': result})
    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
