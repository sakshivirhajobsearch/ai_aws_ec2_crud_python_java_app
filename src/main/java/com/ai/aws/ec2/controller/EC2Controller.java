package com.ai.aws.ec2.controller;

import java.util.List;

import com.ai.aws.ec2.model.EC2Instance;
import com.ai.aws.ec2.repository.EC2Repository;

public class EC2Controller {
	
	private final EC2Repository repo = new EC2Repository();

	// Create
	public void createEC2Instance(String instanceId, String state) throws Exception {
		EC2Instance instance = new EC2Instance(instanceId, state);
		repo.addInstance(instance);
		System.out.println("EC2 Instance added to DB: " + instanceId);
	}

	// Read
	public void readAllEC2Instances() throws Exception {
		List<EC2Instance> instances = repo.getAllInstances();
		for (EC2Instance instance : instances) {
			System.out.println("Instance ID: " + instance.getInstanceId() + ", State: " + instance.getState());
		}
	}

	// Update
	public void updateEC2State(String instanceId, String newState) throws Exception {
		repo.updateInstanceState(instanceId, newState);
		System.out.println("EC2 Instance updated: " + instanceId + " -> " + newState);
	}

	// Delete
	public void deleteEC2Instance(String instanceId) throws Exception {
		repo.deleteInstance(instanceId);
		System.out.println("EC2 Instance deleted from DB: " + instanceId);
	}
}