package com.ai.aws.ec2.model;

public class EC2Instance {
	
	private String instanceId;
	private String state;

	public EC2Instance(String instanceId, String state) {
		this.instanceId = instanceId;
		this.state = state;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public String getState() {
		return state;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public void setState(String state) {
		this.state = state;
	}
}
