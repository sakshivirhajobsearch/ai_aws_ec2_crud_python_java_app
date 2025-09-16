package com.ai.aws.ec2;

import com.ai.aws.ec2.controller.EC2Controller;

public class AiAwsEc2Application {

	public static void main(String[] args) throws Exception {

		EC2Controller controller = new EC2Controller();

		// Create
		controller.createEC2Instance("i-0b3d181b7eefe0e01", "running");

		// Read
		controller.readAllEC2Instances();

		// Update
		// controller.updateEC2State("i-0b3d181b7eefe0e01", "stopped");

		// Delete
		// controller.deleteEC2Instance("i-0abcd1234efgh5678");

		// Read again to verify
		controller.readAllEC2Instances();
	}
}