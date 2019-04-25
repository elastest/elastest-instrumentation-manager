package io.swagger.model;

public class AgentConfigurationDatabaseControl {
	
	private String agentId;
	private AgentConfigurationControl agentConfiguration;
	
	public AgentConfigurationDatabaseControl() {
		
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public AgentConfigurationControl getAgentConfiguration() {
		return agentConfiguration;
	}

	public void setAgentConfigurationControl(AgentConfigurationControl agentConfiguration) {
		this.agentConfiguration = agentConfiguration;
	}


}
