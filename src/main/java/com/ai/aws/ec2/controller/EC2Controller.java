package com.ai.aws.ec2.controller;

import java.sql.SQLException;
import java.util.List;

import com.ai.aws.ec2.model.EC2Instance;
import com.ai.aws.ec2.repository.EC2Repository;

public class EC2Controller {
	private EC2Repository repository;

	// ✅ Constructor with proper exception handling
	public EC2Controller() {
		try {
			repository = new EC2Repository();
		} catch (SQLException e) {
			System.err.println("Database connection failed: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// ✅ Add EC2 instance
	public void addInstance(String instanceId, String state) {
		try {
			repository.addInstance(instanceId, state);
		} catch (SQLException e) {
			System.err.println("Failed to add instance: " + e.getMessage());
		}
	}

	// ✅ Get all EC2 instances
	public void listAllInstances() {
		try {
			List<EC2Instance> instances = repository.getAllInstances();
			if (instances.isEmpty()) {
				System.out.println("No EC2 instances in DB.");
			} else {
				for (EC2Instance instance : instances) {
					System.out.println("Instance ID: " + instance.getInstanceId() + ", State: " + instance.getState());
				}
			}
		} catch (SQLException e) {
			System.err.println("Failed to retrieve instances: " + e.getMessage());
		}
	}

	// ✅ Update EC2 instance state
	public void updateInstance(String instanceId, String newState) {
		try {
			repository.updateInstanceState(instanceId, newState);
		} catch (SQLException e) {
			System.err.println("Failed to update instance: " + e.getMessage());
		}
	}

	// ✅ Delete EC2 instance
	public void deleteInstance(String instanceId) {
		try {
			repository.deleteInstance(instanceId);
		} catch (SQLException e) {
			System.err.println("Failed to delete instance: " + e.getMessage());
		}
	}

	// ✅ Close DB connection
	public void close() {
		repository.close();
	}
}
