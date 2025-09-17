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
	private final String url = "jdbc:mysql://localhost:3306/ai_ec2_crud?useSSL=false";
	private final String user = "root";
	private final String password = "admin";

	private Connection conn;

	public EC2Repository() throws SQLException {
		this.conn = DriverManager.getConnection(url, user, password);
	}

	// ✅ Check if instance exists
	public boolean instanceExists(String instanceId) throws SQLException {
		String sql = "SELECT COUNT(*) FROM ec2_instances WHERE instance_id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, instanceId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			return rs.getInt(1) > 0;
		}
	}

	// ✅ Add EC2 instance
	public void addInstance(String instanceId, String state) throws SQLException {
		if (!instanceExists(instanceId)) {
			String sql = "INSERT INTO ec2_instances (instance_id, state) VALUES (?, ?)";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setString(1, instanceId);
				stmt.setString(2, state);
				stmt.executeUpdate();
				System.out.println("EC2 Instance added to DB: " + instanceId);
			}
		} else {
			System.out.println("Instance already exists in DB: " + instanceId);
		}
	}

	// ✅ Overload for EC2Instance object
	public void addInstance(EC2Instance instance) throws SQLException {
		addInstance(instance.getInstanceId(), instance.getState());
	}

	// ✅ Read all EC2 instances
	public List<EC2Instance> getAllInstances() throws SQLException {
		List<EC2Instance> instances = new ArrayList<>();
		String sql = "SELECT instance_id, state FROM ec2_instances";
		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				EC2Instance instance = new EC2Instance(rs.getString("instance_id"), rs.getString("state"));
				instances.add(instance);
			}
		}
		return instances;
	}

	// ✅ Update EC2 instance state
	public void updateInstanceState(String instanceId, String newState) throws SQLException {
		String sql = "UPDATE ec2_instances SET state = ? WHERE instance_id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, newState);
			stmt.setString(2, instanceId);
			stmt.executeUpdate();
			System.out.println("Updated instance state: " + instanceId + " -> " + newState);
		}
	}

	// ✅ Delete EC2 instance
	public void deleteInstance(String instanceId) throws SQLException {
		String sql = "DELETE FROM ec2_instances WHERE instance_id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, instanceId);
			stmt.executeUpdate();
			System.out.println("Deleted instance from DB: " + instanceId);
		}
	}

	// ✅ Close DB connection
	public void close() {
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
