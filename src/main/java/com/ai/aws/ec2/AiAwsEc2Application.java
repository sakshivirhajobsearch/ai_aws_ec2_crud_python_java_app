package com.ai.aws.ec2;

import com.ai.aws.ec2.controller.EC2Controller;

public class AiAwsEc2Application {

	public static void main(String[] args) {

		EC2Controller controller = new EC2Controller();

		// Create
		controller.addInstance("i-0abc123def456ghi7", "running");

		// Read
		controller.listAllInstances();

		// Update
		controller.updateInstance("i-0abc123def456ghi7", "stopped");

		// Delete
		controller.deleteInstance("i-0abc123def456ghi7");

		// Cleanup
		controller.close();
	}
}