from flask import Flask, request, jsonify
from ai.predict import predict
from ec2_operations import create_instance, list_instances, terminate_instance

app = Flask(__name__)

@app.route('/predict', methods=['POST'])
def predict_cost():
    hours = float(request.json['hours'])
    cost = predict(hours)
    return jsonify({'predicted_cost': cost})

@app.route('/ec2/create', methods=['POST'])
def ec2_create():
    instance_id = create_instance()
    return jsonify({'instance_id': instance_id})

@app.route('/ec2/list', methods=['GET'])
def ec2_list():
    return jsonify(list_instances())

@app.route('/ec2/terminate', methods=['POST'])
def ec2_terminate():
    instance_id = request.json['instance_id']
    result = terminate_instance(instance_id)
    return jsonify({'message': result})

if __name__ == "__main__":
    app.run(port=5000)
