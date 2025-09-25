package com.ai.aws.ec2;

import com.ai.aws.ec2.controller.EC2Controller;

public class AiAwsEc2Application {

	public static void main(String[] args) {
		
		EC2Controller controller = new EC2Controller();

		controller.addInstance("i-0abc1234def567890", "running");
		controller.listAllInstances();
		controller.updateInstance("i-0abc1234def567890", "stopped");
		controller.listAllInstances();
		controller.deleteInstance("i-0abc1234def567890");
		controller.listAllInstances();

		controller.close();
	}
}