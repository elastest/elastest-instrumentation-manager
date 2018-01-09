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

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import io.elastest.eim.config.Dictionary;
import io.elastest.eim.config.Properties;
import io.swagger.model.AgentConfiguration;
import io.swagger.model.AgentConfigurationDatabase;
import io.swagger.model.AgentConfigurationFilebeat;
import io.swagger.model.AgentConfigurationPacketbeat;
import io.swagger.model.AgentConfigurationTopbeat;

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
		BasicDBObject query = new BasicDBObject();
        query.put("agentId", agentId);

        DBCursor cursor = getAgentConfigurationTable().find(query);
        if (cursor.hasNext()){
        	logger.info("Agent cfg exists in DB with agentId = " + agentId);
        	return this.toAgentCfgDbObject(cursor.next());
        }
        else {
        	logger.info("Agent cfg doesn't exists in DB with agentId = " + agentId);
        	System.out.println("Agent cfg doesn't exists in DB with agentId = " + agentId);
        	return null;
        }		
	}

	
	public AgentConfigurationDatabase addAgentCfg(String agentId, AgentConfiguration agentCfgObj){
		logger.info("Adding new agent configuration to DB, agent with agentId = " + agentId);
		System.out.println("Adding new agent configuration to DB, agent with agentId = " + agentId);
		BasicDBObject agentCfgDb = new BasicDBObject();

		BasicDBObject packetbeat = new BasicDBObject();
		packetbeat.put("stream", agentCfgObj.getPacketbeat().getStream());
		
		BasicDBObject filebeat = new BasicDBObject();
		filebeat.put("stream", agentCfgObj.getFilebeat().getStream());
		BasicDBList paths = new BasicDBList();
		for (String path : agentCfgObj.getFilebeat().getPaths()) {
			paths.add(path);	
		}
		filebeat.put("paths", paths);
		
		BasicDBObject topbeat = new BasicDBObject();
		topbeat.put("stream", agentCfgObj.getTopbeat().getStream());
		
		BasicDBObject agentCfg = new BasicDBObject();
		agentCfg.put("exec", agentCfgObj.getExec());
		agentCfg.put("component", agentCfgObj.getComponent());
		agentCfg.put("packetbeat", packetbeat);
		agentCfg.put("filebeat", filebeat);
		agentCfg.put("topbeat", topbeat);
						
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
        while (cur.hasNext()) {
        	if (agents == null) {
        		agents = new ArrayList<AgentConfigurationDatabase>();
        	}
        	agents.add(this.toAgentCfgDbObject(cur.next()));    	
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
	
	private AgentConfigurationDatabase toAgentCfgDbObject(DBObject dbObject) {
		AgentConfigurationDatabase agentCfgDb = new AgentConfigurationDatabase();
		agentCfgDb.setAgentId((String) dbObject.get("agentId"));
		
		DBObject agentCfgDbObj = (DBObject) dbObject.get("agentConfiguration");
		AgentConfiguration ac = new AgentConfiguration();
		
		ac.setExec((String) agentCfgDbObj.get("exec"));
    	ac.setComponent((String) agentCfgDbObj.get("component"));
    	
    	AgentConfigurationPacketbeat packetbeat = new AgentConfigurationPacketbeat();
    	packetbeat.setStream((String) ((DBObject)agentCfgDbObj.get("packetbeat")).get("stream"));
    	ac.setPacketbeat(packetbeat);
    	
    	AgentConfigurationTopbeat topbeat = new AgentConfigurationTopbeat();
    	topbeat.setStream((String) ((DBObject)agentCfgDbObj.get("topbeat")).get("stream"));
    	ac.setTopbeat(topbeat);
    	
    	AgentConfigurationFilebeat filebeat = new AgentConfigurationFilebeat();
    	filebeat.setStream((String) ((DBObject)agentCfgDbObj.get("filebeat")).get("stream"));
    	BasicDBList paths = (BasicDBList) ((DBObject)agentCfgDbObj.get("filebeat")).get("paths");
    	String[] pathsArr = paths.toArray(new String[0]);
    	List<String> pathsList = new ArrayList<String>();
    	for(String path : pathsArr) {
    		// add to the list
    		pathsList.add(path);
    	}
    	filebeat.setPaths(pathsList);
    	ac.setFilebeat(filebeat);

    	agentCfgDb.setAgentConfiguration(ac);		
		return agentCfgDb;
		
	}
	
	
}
