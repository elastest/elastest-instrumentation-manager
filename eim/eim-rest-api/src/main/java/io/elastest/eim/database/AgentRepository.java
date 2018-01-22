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

import io.elastest.eim.database.mysql.EimDbAgentManager;
import io.swagger.model.AgentFull;
import io.swagger.model.Host;

public class AgentRepository {

	private static Logger logger = Logger.getLogger(AgentRepository.class);
	
	//MySQL
	private EimDbAgentManager eimDbManager;
	
	public AgentRepository(){
		eimDbManager = new EimDbAgentManager();
	}
	
	public boolean existHost(String ipAddress){		
		logger.info("Verifying if host with ipAddress = " + ipAddress + " exists");
		System.out.println("Verifying if host with ipAddress = " + ipAddress + " exists");
		if (eimDbManager.getAgentByIpAddress(ipAddress) == null){
			logger.info("Not exists any host with ipAddress = " + ipAddress);
        	System.out.println("Not exists any host with ipAddress = " + ipAddress);
			return false;
		}
		else {
			logger.info("Host with ipAddress = " + ipAddress + " exists");
        	System.out.println("Host with ipAddress = " + ipAddress + " exists");
			return true;
		}       
	}
	
	public AgentFull getAgentByIpAddress(String ipAddress){
		return eimDbManager.getAgentByIpAddress(ipAddress);
	}
	
	public AgentFull getAgentByAgentId(String agentId){
		return eimDbManager.getAgentByAgentId(agentId);
	}
	
	public AgentFull addHost(Host host){
		return eimDbManager.addHost(host);
	}	
	
	public AgentFull setMonitored(String agentId, boolean monitored){
		return eimDbManager.setMonitored(agentId, monitored);
	}
	
	public List<AgentFull> findAll(){
		return eimDbManager.getAgents();
	}
	
	public boolean deleteAgent(String agentId) {
		return eimDbManager.deleteAgent(agentId);    
	}
	
	
}
