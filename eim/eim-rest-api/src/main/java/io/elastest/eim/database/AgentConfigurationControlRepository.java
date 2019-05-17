package io.elastest.eim.database;

import java.util.List;

import org.apache.log4j.Logger;

import io.elastest.eim.database.mysql.EimDbAgentCfgControlManager;
import io.swagger.model.AgentConfigurationControl;
import io.swagger.model.AgentConfigurationDatabaseControl;

public class AgentConfigurationControlRepository {
	
	private static Logger logger = Logger.getLogger(AgentConfigurationControlRepository.class);
	
	//MySQL
	private EimDbAgentCfgControlManager eimDbCfgManager;
	
	public AgentConfigurationControlRepository() {
		eimDbCfgManager = new EimDbAgentCfgControlManager();
	}
	
	public boolean existConfiguration(String agentId){	
		if (eimDbCfgManager.getAgentConfigurationByAgentId(agentId) == null) {
			return false;
		}
		else {
			return true;
		}  
	}
	
	public AgentConfigurationDatabaseControl getAgentConfigurationByAgentId(String agentId){
		return eimDbCfgManager.getAgentConfigurationByAgentId(agentId);
	}
	
	public AgentConfigurationDatabaseControl addAgentCfgControl(String agentId, AgentConfigurationControl agentCfgObj){
		return eimDbCfgManager.addAgentConfiguration(agentId, agentCfgObj);
	}
	
		
	public List<AgentConfigurationDatabaseControl> findAll(){
		return eimDbCfgManager.getAgentsConfigurations();
	}
	
	public boolean deleteAgentCfg(String agentId) {
		return eimDbCfgManager.deleteAgentConfiguration(agentId);    
	}

	public List<AgentConfigurationDatabaseControl> getPacketLoss(String agentId) {
		return eimDbCfgManager.getPacketLoss(agentId);
	}	
	


}
