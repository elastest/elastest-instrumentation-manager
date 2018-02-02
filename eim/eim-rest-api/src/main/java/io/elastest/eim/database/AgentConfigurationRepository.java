/**
 * Copyright (c) 2017 Atos
 * This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0
 * which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 *    @author David Rojo Antona (Atos)
 *    
 * Developed in the context of ElasTest EU project http://elastest.io 
 */
 

package io.elastest.eim.database;

import java.util.List;

import org.apache.log4j.Logger;

import io.elastest.eim.database.mysql.EimDbAgentCfgManager;
import io.swagger.model.AgentConfiguration;
import io.swagger.model.AgentConfigurationDatabase;

public class AgentConfigurationRepository {

	private static Logger logger = Logger.getLogger(AgentConfigurationRepository.class);
	
	//MySQL
	private EimDbAgentCfgManager eimDbCfgManager;
	
	public AgentConfigurationRepository(){
		eimDbCfgManager = new EimDbAgentCfgManager();
	}
	

	public boolean existConfiguration(String agentId){	
		if (eimDbCfgManager.getAgentConfigurationByAgentId(agentId) == null) {
			return false;
		}
		else {
			return true;
		}  
	}
	
	public AgentConfigurationDatabase getAgentConfigurationByAgentId(String agentId){
		return eimDbCfgManager.getAgentConfigurationByAgentId(agentId);
	}
	
	public AgentConfigurationDatabase addAgentCfg(String agentId, AgentConfiguration agentCfgObj){
		return eimDbCfgManager.addAgentConfiguration(agentId, agentCfgObj);
	}
		
	public List<AgentConfigurationDatabase> findAll(){
		return eimDbCfgManager.getAgentsConfigurations();
	}
	
	public boolean deleteAgentCfg(String agentId) {
		return eimDbCfgManager.deleteAgentConfiguration(agentId);    
	}	
	
}
