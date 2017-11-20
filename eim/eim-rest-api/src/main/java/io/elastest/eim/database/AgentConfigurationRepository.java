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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

import io.elastest.eim.config.Dictionary;
import io.elastest.eim.config.Properties;
import io.swagger.model.AgentConfiguration;
import io.swagger.model.AgentConfigurationDatabase;

public class AgentConfigurationRepository {

	private static Logger logger = Logger.getLogger(AgentConfigurationRepository.class);
	
	private DBCollection collection; 
	
	public AgentConfigurationRepository(){
		MongoClient mongoClient = new MongoClient( 
				Properties.getValue(Dictionary.PROPERTY_MONGODB_HOST), 
				27017);
		DB db = mongoClient.getDB("eim");
		collection = db.getCollection("agentConfiguration");
	}
	
	private DBCollection getAgentConfigurationTable(){
		return collection;
	}
	
	public boolean existConfiguration(String agentId){		
		logger.info("Verifying if agent with agentId = " + agentId + " has a configuration stored in DB");
		System.out.println("Verifying if agent with agentId = " + agentId + " has a configuration stored in DB");
        BasicDBObject query = new BasicDBObject();
        query.put("agentId", agentId);

        DBCursor cursor = getAgentConfigurationTable().find(query);
        if (cursor.hasNext()){
        	logger.info("Configuration for agent with agentId = " + agentId + " exists");
        	System.out.println("Configuration for agent with agentId = " + agentId + " exists");
        	return true;
        }
        else {
        	logger.info("Not exists any configuration for an agent with agentId = " + agentId);
        	System.out.println("Not exists any configuration for an agent with agentId = " + agentId);
        	return false;
        }        
	}
	
	public AgentConfigurationDatabase getAgentConfigurationByAgentId(String agentId){
		System.out.println("Searching agent cfg in DB with agentId = " + agentId);
		logger.info("Searching host in DB with agentId = " + agentId);
		AgentConfigurationDatabase agent = null;
		BasicDBObject query = new BasicDBObject();
        query.put("agentId", agentId);

        DBCursor cursor = getAgentConfigurationTable().find(query);
        if (cursor.hasNext()){
        	agent = new AgentConfigurationDatabase();
        	agent.setAgentConfiguration((AgentConfiguration) cursor.next().get("agentConfiguration"));
        	logger.info("Agent cfg finded in DB with agentId = " + agentId);
        	System.out.println("Agent cfg finded in DB with agentId = " + agentId);
        }
        else {
        	logger.info("Agent cfg doesn't exists in DB with agentId = " + agentId);
        	System.out.println("Agent cfg doesn't exists in DB with agentId = " + agentId);
        	return null;
        }		
		return agent;
	}

	
	public AgentConfigurationDatabase addAgentCfg(String agentId, AgentConfiguration agentCfg){
		logger.info("Adding new agent configuration to DB, agent with agentId = " + agentId);
		System.out.println("Adding new agent configuration to DB, agent with agentId = " + agentId);
		BasicDBObject agentCfgDb = new BasicDBObject();
		agentCfgDb.put("agentId", agentId);
		agentCfgDb.put("agentConfiguration", agentCfg);
		getAgentConfigurationTable().insert(agentCfgDb);
		AgentConfigurationDatabase inserted = getAgentConfigurationByAgentId(agentId);
		return inserted;
	}
	
	
	public List<AgentConfigurationDatabase> findAll(){
		ArrayList<AgentConfigurationDatabase> agents = null;
		logger.info("Getting all agent cfgs from database...");
        DBCursor cur = getAgentConfigurationTable().find();
        AgentConfigurationDatabase agent = null;
        while (cur.hasNext()) {
        	if (agents == null) {
        		agents = new ArrayList<AgentConfigurationDatabase>();
        	}
        	agent = new AgentConfigurationDatabase();
        	agent.setAgentId((String) cur.next().get("agentId"));
        	agent.setAgentConfiguration((AgentConfiguration) cur.curr().get("agentConfiguration"));
        	agents.add(agent);    	
        }
        if (agents != null){
        	logger.info("Retrieved " + agents.size() + " agent configurations from database");
        }
        else {
        	logger.info("Retrieved " + 0 + " agent configurations from database");
        }
		return agents;
	}
	
	public boolean deleteAgentCfg(String agentId) {
		logger.info("Deleting agent configuration from DB, agentId = " + agentId);
		getAgentConfigurationTable().remove(new BasicDBObject().append("agentId", agentId));
        
        //Verify that the element has been deleted
        if (getAgentConfigurationByAgentId(agentId) == null) {
        	return true;
        }
        else {
        	return false;
        }    
	}
	
	
}
