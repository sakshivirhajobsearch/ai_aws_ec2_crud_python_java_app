package com.ai.aws.ec2.controller;

import java.sql.SQLException;
import java.util.List;

import com.ai.aws.ec2.model.EC2Instance;
import com.ai.aws.ec2.repository.EC2Repository;

/**
 * Controller class for EC2 CRUD operations.
 */
public class EC2Controller {

	private EC2Repository repository;

	/**
	 * Constructor that initializes the EC2Repository connection.
	 */
	public EC2Controller() {
		try {
			repository = new EC2Repository();
		} catch (SQLException e) {
			System.err.println("❌ Database connection failed: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Adds a new EC2 instance to the database.
	 * 
	 * @param instanceId The instance ID
	 * @param state      The instance state
	 */
	public void addInstance(String instanceId, String state) {
		if (repository == null)
			return;
		try {
			repository.addInstance(instanceId, state);
		} catch (SQLException e) {
			System.err.println("❌ Failed to add instance: " + e.getMessage());
		}
	}

	/**
	 * Lists all EC2 instances from the database.
	 */
	public void listAllInstances() {
		if (repository == null)
			return;
		try {
			List<EC2Instance> instances = repository.getAllInstances();
			if (instances.isEmpty()) {
				System.out.println("ℹ️ No EC2 instances in DB.");
			} else {
				for (EC2Instance instance : instances) {
					System.out
							.println("✅ Instance ID: " + instance.getInstanceId() + ", State: " + instance.getState());
				}
			}
		} catch (SQLException e) {
			System.err.println("❌ Failed to retrieve instances: " + e.getMessage());
		}
	}

	/**
	 * Updates the state of an EC2 instance.
	 * 
	 * @param instanceId The instance ID
	 * @param newState   The new state to update
	 */
	public void updateInstance(String instanceId, String newState) {
		if (repository == null)
			return;
		try {
			repository.updateInstanceState(instanceId, newState);
		} catch (SQLException e) {
			System.err.println("❌ Failed to update instance: " + e.getMessage());
		}
	}

	/**
	 * Deletes an EC2 instance from the database.
	 * 
	 * @param instanceId The instance ID to delete
	 */
	public void deleteInstance(String instanceId) {
		if (repository == null)
			return;
		try {
			repository.deleteInstance(instanceId);
		} catch (SQLException e) {
			System.err.println("❌ Failed to delete instance: " + e.getMessage());
		}
	}

	/**
	 * Closes the repository/database connection.
	 */
	public void close() {
		if (repository != null) {
			repository.close();
		}
	}
}
