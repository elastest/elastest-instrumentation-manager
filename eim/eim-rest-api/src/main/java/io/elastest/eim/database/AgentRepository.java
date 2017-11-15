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
import io.swagger.model.AgentFull;
import io.swagger.model.Host;

public class AgentRepository {

	private static Logger logger = Logger.getLogger(AgentRepository.class);
	
	private DBCollection collection; 
	
	public AgentRepository(){
		MongoClient mongoClient = new MongoClient( 
				Properties.getValue(Dictionary.PROPERTY_MONGODB_HOST), 
				27017);
		DB db = mongoClient.getDB("eim");
		collection = db.getCollection("agent");
	}
	
	private DBCollection getAgentTable(){
		return collection;
	}
	
	public boolean existHost(String ipAddress){		
		logger.info("Verifying if host with ipAddress = " + ipAddress + " exists");
		System.out.println("Verifying if host with ipAddress = " + ipAddress + " exists");
        BasicDBObject query = new BasicDBObject();
        query.put("host", ipAddress);

        DBCursor cursor = getAgentTable().find(query);
        if (cursor.hasNext()){
        	logger.info("Host with ipAddress = " + ipAddress + " exists");
        	System.out.println("Host with ipAddress = " + ipAddress + " exists");
        	return true;
        }
        else {
        	logger.info("Not exists any host with ipAddress = " + ipAddress);
        	System.out.println("Not exists any host with ipAddress = " + ipAddress);
        	return false;
        }        
	}
	
	public AgentFull getAgentByIpAddress(String ipAddress){
		logger.info("Searching host in DB with ipAddress = " + ipAddress);
		System.out.println("Searching host in DB with ipAddress = " + ipAddress);
		AgentFull agent = null;
		
		BasicDBObject query = new BasicDBObject();
        query.put("host", ipAddress);

        DBCursor cursor = getAgentTable().find(query);
        if (cursor.hasNext()){
        	agent = new AgentFull();
        	agent.setAgentId((String) cursor.next().get("agentId"));
        	agent.setHost((String) cursor.curr().get("host"));
        	agent.setMonitored((boolean) cursor.curr().get("monitored"));
        	agent.setLogstashIp((String) cursor.curr().get("logstashIp"));
        	agent.setLogstashPort((String) cursor.curr().get("logstashPort"));
        	logger.info("Host finded in DB with ipAddress = " + ipAddress + " with ID " + agent.getAgentId());
        	System.out.println("Host finded in DB with ipAddress = " + ipAddress + " with ID " + agent.getAgentId());
        }
        else {
        	logger.error("Host doesn't exists in DB with ipAddress = " + ipAddress);
        	System.out.println("Host doesn't exists in DB with ipAddress = " + ipAddress);
        	return null;
        }		
		return agent;
	}
	
	public AgentFull getAgentByAgentId(String agentId){
		System.out.println("Searching host in DB with agentId = " + agentId);
		logger.info("Searching host in DB with agentId = " + agentId);
		AgentFull agent = null;
		BasicDBObject query = new BasicDBObject();
        query.put("agentId", agentId);

        DBCursor cursor = getAgentTable().find(query);
        if (cursor.hasNext()){
        	agent = new AgentFull();
        	agent.setAgentId((String) cursor.next().get("agentId"));
        	agent.setHost((String) cursor.curr().get("host"));
        	agent.setMonitored((boolean) cursor.curr().get("monitored"));
        	agent.setLogstashIp((String) cursor.curr().get("logstashIp"));
        	agent.setLogstashPort((String) cursor.curr().get("logstashPort"));
        	logger.info("Host finded in DB with agentId = " + agentId + " with ipAddress " + agent.getHost());
        	System.out.println("Host finded in DB with agentId = " + agentId + " with ipAddress " + agent.getHost());
        }
        else {
        	logger.info("Host doesn't exists in DB with agentId = " + agentId);
        	System.out.println("Host doesn't exists in DB with agentId = " + agentId);
        	return null;
        }		
		return agent;
	}
	
	private String getNewAgentId(){
		String newId = "iagent";
		int elements = (int) (collection.count() + 1);
		newId += elements;
		return newId;
	}
	
	public AgentFull addHost(Host host){
		logger.info("Adding new host to DB, host with ipAddress = " + host.getAddress());
		System.out.println("Adding new host to DB, host with ipAddress = " + host.getAddress());
		BasicDBObject newHost = new BasicDBObject();
		newHost.put("agentId", getNewAgentId());
		newHost.put("host", host.getAddress());
		newHost.put("monitored", false);
		newHost.put("logstashIp", host.getLogstashIp());
		newHost.put("logstashPort", host.getLogstashPort());
		getAgentTable().insert(newHost);
		AgentFull inserted = getAgentByIpAddress(host.getAddress());
		return inserted;
	}
	
	
	public AgentFull setMonitored(String agentId, boolean monitored){
		AgentFull agent = getAgentByAgentId(agentId);
		if (agent != null){
			agent.setMonitored(monitored);
			
			 //Preparing to insert new field
	        BasicDBObject updateMonitored = new BasicDBObject();
	        updateMonitored.append("$set", new BasicDBObject().append("monitored", monitored));
	 
	        //Find the register with the specified ID
	        BasicDBObject searchById = new BasicDBObject();
	        searchById.append("agentId", agentId);
	 
	        //Launch the update
	        getAgentTable().updateMulti(searchById, updateMonitored);
	        
	        return getAgentByAgentId(agentId);
			
		}
		else {
			return null;
		}
	}
	
	public List<AgentFull> findAll(){
		ArrayList<AgentFull> agents = null;
		logger.info("Getting all agents from database...");
        DBCursor cur = getAgentTable().find();
        AgentFull agent = null;
        while (cur.hasNext()) {
        	if (agents == null) {
        		agents = new ArrayList<AgentFull>();
        	}
        	agent = new AgentFull();
        	agent.setAgentId((String) cur.next().get("agentId"));
        	agent.setHost((String) cur.curr().get("host") );
        	agent.setMonitored((boolean) cur.curr().get("monitored"));
        	agents.add(agent);    	
        }
        if (agents != null){
        	logger.info("Retrieved " + agents.size() + " agents from database");
        }
        else {
        	logger.info("Retrieved " + 0 + " agents from database");
        }
		return agents;
	}
	
	public boolean deleteAgent(String agentId) {
		logger.info("Deleting agent from DB, host with agentId = " + agentId);
        getAgentTable().remove(new BasicDBObject().append("agentId", agentId));
        
        //Verify that the element has been deleted
        if (getAgentByAgentId(agentId) == null) {
        	return true;
        }
        else {
        	return false;
        }    
	}
	
	
}
