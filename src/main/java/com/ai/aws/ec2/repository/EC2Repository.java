package com.ai.aws.ec2.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ai.aws.ec2.model.EC2Instance;

public class EC2Repository {

	private final String url = "jdbc:mysql://localhost:3306/ai_ec2_crud";
	private final String user = "root";
	private final String password = "admin";

	// Create
	public void addInstance(EC2Instance instance) throws SQLException {
		
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			String sql = "INSERT INTO ec2_instances (instance_id, state) VALUES (?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, instance.getInstanceId());
			stmt.setString(2, instance.getState());
			stmt.executeUpdate();
		}
	}

	// Read
	public List<EC2Instance> getAllInstances() throws SQLException {
		
		List<EC2Instance> instances = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			String sql = "SELECT instance_id, state FROM ec2_instances";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString("instance_id");
				String state = rs.getString("state");
				instances.add(new EC2Instance(id, state));
			}
		}
		return instances;
	}

	// Update
	public void updateInstanceState(String instanceId, String newState) throws SQLException {
		
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			String sql = "UPDATE ec2_instances SET state = ? WHERE instance_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, newState);
			stmt.setString(2, instanceId);
			stmt.executeUpdate();
		}
	}

	// Delete
	public void deleteInstance(String instanceId) throws SQLException {
		
		try (Connection conn = DriverManager.getConnection(url, user, password)) {
			String sql = "DELETE FROM ec2_instances WHERE instance_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, instanceId);
			stmt.executeUpdate();
		}
	}
}
