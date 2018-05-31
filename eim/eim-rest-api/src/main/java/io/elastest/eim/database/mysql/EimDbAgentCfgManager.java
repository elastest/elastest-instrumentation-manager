/**
 * Copyright (c) 2018 Atos
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

package io.elastest.eim.database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import io.elastest.eim.config.Dictionary;
import io.swagger.model.AgentConfiguration;
import io.swagger.model.AgentConfigurationDatabase;
import io.swagger.model.AgentConfigurationFilebeat;
import io.swagger.model.AgentConfigurationPacketbeat;
import io.swagger.model.AgentConfigurationMetricbeat;

public class EimDbAgentCfgManager {

    private static Logger logger = Logger.getLogger(EimDbAgentCfgManager.class);
    
    public EimDbAgentCfgManager() {
    	
    }
    
    public Connection getConnection() throws SQLException {
    	Connection conn = null;
        try {
            //Register JDBC driver
            Class.forName(Dictionary.JDBC_DRIVER);
            
            //Open a connection
            logger.info("Connecting to EIM database...");
            conn = DriverManager.getConnection(
            		Dictionary.DBURL, Dictionary.DBUSER, Dictionary.DBPASS);
            logger.info("Connected to EIM database successfully...");
            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        } catch (SQLException se) {
            //Handle errors for JDBC
        	se.printStackTrace();
            logger.error(se.getMessage());
            return null;
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        } 
    }
    
    
    private boolean existsAgentCfg(Connection conn, String agentId) throws SQLException {
    	String sqlSearchIagent = "SELECT AGENT_ID FROM " + Dictionary.DBTABLE_AGENT_CONFIGURATION + " WHERE AGENT_ID=?";
		PreparedStatement pstSearchIagent = conn.prepareStatement(sqlSearchIagent);
		pstSearchIagent.setString(1, agentId);
		ResultSet rs = pstSearchIagent.executeQuery();
		if (!rs.next()) {
			return false;
		}
		else {
			return true;
		}
    }
    
    public AgentConfigurationDatabase addAgentConfiguration(String agentId, AgentConfiguration agentCfgObj) {
    	AgentConfigurationDatabase agentCfg = null;
    	Connection conn = null;
    	try {
    		
    		conn = getConnection();
    		
    		if (!existsAgentCfg(conn, agentId)) {
    			agentCfg = addAgentConfiguration(conn, agentId, agentCfgObj);
    			return agentCfg;
    		}
    		else { 
    			logger.info("Agent Configuration for agentId = " + agentId + " exists in database");
    			return null;
    		}
    		
    	} catch (SQLException ex) {
            logger.error("Error " + ex.getErrorCode() + ": " + ex.getMessage());
        }
    	finally {
    		try{
    			if(conn!=null)
    				conn.close();
    		} catch(SQLException se){
    			logger.error(se.getMessage());
    			se.printStackTrace();
    		}
    	}
		return agentCfg;
    }
    
    
    private AgentConfigurationDatabase addAgentConfiguration( Connection conn, String agentId, AgentConfiguration agentCfgObj) throws SQLException{
    	AgentConfigurationDatabase inserted = null;
    	PreparedStatement pstInsertAgentCfg = null;
    	try {
    		logger.info("Adding new agent configuration to DB for agent with agentId = " + agentId);
    		System.out.println("Adding new agent configuration to DB for agent with agentId = " + agentId);
    		
    		String sqlInsertHost = "INSERT INTO " + Dictionary.DBTABLE_AGENT_CONFIGURATION + " VALUES (?,?,?,?,?,?,?,?,?,?)";
    		pstInsertAgentCfg = conn.prepareStatement(sqlInsertHost);
    	
    		pstInsertAgentCfg.setString(1, agentId); 															//agentId
    		pstInsertAgentCfg.setString(2, agentCfgObj.getExec());												//exec
    		pstInsertAgentCfg.setString(3, agentCfgObj.getComponent());											//component
    		pstInsertAgentCfg.setString(4, agentCfgObj.getPacketbeat().getStream());							//packetbeat_stream
    		pstInsertAgentCfg.setString(5, agentCfgObj.getMetricbeat().getStream());							//metricbeat_stream
    		pstInsertAgentCfg.setString(6, agentCfgObj.getFilebeat().getStream());								//filebeat_stream
    		pstInsertAgentCfg.setString(7, getStringFromArrayList(agentCfgObj.getFilebeat().getPaths(), ","));	//filebeat_paths
    		
    		if (agentCfgObj.getDockerized() == null) {
    			//no Dockerized param is specified in the request --> default value = yes
    			logger.info("Not specified if SuT is dockerized or not. Using default value: yes");
    			System.out.println("Not specified if SuT is dockerized or not. Using default value: yes");
    			pstInsertAgentCfg.setString(8, Dictionary.DOCKERIZED_YES);										//dockerized
    			logger.info("Using default value for docker_path: " + Dictionary.DOCKERIZED_DEFAULT_DOCKER_PATH);
    			System.out.println("Using default value for docker_path: " + Dictionary.DOCKERIZED_DEFAULT_DOCKER_PATH);
    			pstInsertAgentCfg.setString(9, Dictionary.DOCKERIZED_DEFAULT_DOCKER_PATH);						//docker_path
    			logger.info("Using default value for docker_metrics: " + Dictionary.DOCKERIZED_DEFAULT_DOCKER_METRIC);
    			System.out.println("Using default value for docker_metrics: " + Dictionary.DOCKERIZED_DEFAULT_DOCKER_METRIC);
    			pstInsertAgentCfg.setString(10, Dictionary.DOCKERIZED_DEFAULT_DOCKER_METRIC);										//docker_metrics
    		}
    		else if (agentCfgObj.getDockerized().equals(Dictionary.DOCKERIZED_YES)) {
    			//isDockerized
    			logger.info("SuT is dockerized...");
    			System.out.println("SuT is dockerized...");
    			pstInsertAgentCfg.setString(8, agentCfgObj.getDockerized());									//dockerized
    			
    			//docker_path
    			try {
    				pstInsertAgentCfg.setString(9, agentCfgObj.getFilebeat().getDockerized().get(0));				
    				logger.info("docker_path = " + agentCfgObj.getFilebeat().getDockerized().get(0));
    				System.out.println("docker_path = " + agentCfgObj.getFilebeat().getDockerized().get(0));
    			}
    			catch (Exception e) {
    				//not docker path specified, using default value
    				logger.info("Not docker_path specified value. Using default value for docker_path: " + Dictionary.DOCKERIZED_DEFAULT_DOCKER_PATH);
    				System.out.println("Not docker_path specified value. Using default value for docker_path: " + Dictionary.DOCKERIZED_DEFAULT_DOCKER_PATH);
    				pstInsertAgentCfg.setString(9, Dictionary.DOCKERIZED_DEFAULT_DOCKER_PATH);								//docker_path
    			}
    			
    			//docker_metrics
    			try {
    				pstInsertAgentCfg.setString(10, agentCfgObj.getMetricbeat().getDockerized().get(0));				
    				logger.info("docker_metrics = " + agentCfgObj.getMetricbeat().getDockerized().get(0));
    				System.out.println("docker_metrics = " + agentCfgObj.getMetricbeat().getDockerized().get(0));
    			}
    			catch (Exception e) {
    				//not docker metrics specified, using default value
    				logger.info("Not docker_metrics specified value. Using default value for docker_metrics: " + Dictionary.DOCKERIZED_DEFAULT_DOCKER_METRIC);
    				System.out.println("Not docker_metrics specified value. Using default value for docker_metrics: " + Dictionary.DOCKERIZED_DEFAULT_DOCKER_METRIC);
    				pstInsertAgentCfg.setString(10, Dictionary.DOCKERIZED_DEFAULT_DOCKER_METRIC);								//docker_metrics
    			}
    		}
    		else {
    			//notDockerized
    			logger.info("SuT is not dockerized...");
    			System.out.println("SuT is not dockerized...docker_path & docker_metrics will be null");
    			
    			pstInsertAgentCfg.setString(8, Dictionary.DOCKERIZED_NO);										//dockerized
    			pstInsertAgentCfg.setString(9, null);															//docker_path
    			pstInsertAgentCfg.setString(10, null);															//docker_metrics
    		}
    		pstInsertAgentCfg.executeUpdate();				
    		logger.info("Agent Configuration inserted in database wiht agentId = " + agentId);
    		
    		inserted = getAgentConfigurationByAgentId(conn, agentId);
    		
    	}
		finally {
    		try{
    			if(pstInsertAgentCfg!=null)
    				pstInsertAgentCfg.close();
    		} catch(SQLException se){
    			logger.error(se.getMessage());
    			se.printStackTrace();
    		}
    	}
    	return inserted;
    }	
	
	private AgentConfigurationDatabase getAgentConfigurationByAgentId(Connection conn, String agentId) throws SQLException {
		logger.info("Searching agent configuration for agent with agentId = " + agentId);
		System.out.println("Searching agent configuration for agent with agentId = " + agentId);
		AgentConfigurationDatabase agentCfgDb = null;
		PreparedStatement pstSelectAgentCfg = null;
		
		try {
			String selectAgentCfgSQL = "SELECT AGENT_ID, EXEC, COMPONENT, PACKETBEAT_STREAM, METRICBEAT_STREAM, FILEBEAT_STREAM, FILEBEAT_PATHS, DOCKERIZED, DOCKER_PATH FROM " + Dictionary.DBTABLE_AGENT_CONFIGURATION + " WHERE AGENT_ID = ?";
			pstSelectAgentCfg = conn.prepareStatement(selectAgentCfgSQL);
			pstSelectAgentCfg.setString(1, agentId);
			ResultSet rs = pstSelectAgentCfg.executeQuery();
			while (rs.next()) {
				agentCfgDb = toAgentCfgDb(rs);
	        	logger.info("Agent configuration finded in DB for agent with agentId = " + agentId + " with ID " + agentId);
	        	System.out.println("Agent configuration finded in DB for agent with agentId = " + agentId + " with ID " + agentId);
				return agentCfgDb;
			}
		}
		finally {
    		try{
    			if(pstSelectAgentCfg!=null)
    				pstSelectAgentCfg.close();
    		} catch(SQLException se){
    			logger.error(se.getMessage());
    			se.printStackTrace();
    		}
    	}
		return agentCfgDb;

	}
	
	
	public AgentConfigurationDatabase getAgentConfigurationByAgentId(String agentId) {
		AgentConfigurationDatabase agentCfgDb = null;
    	Connection conn = null;
    	try {
    		
    		conn = getConnection();
    		return getAgentConfigurationByAgentId(conn, agentId);
    		
    	} catch (SQLException ex) {
            logger.error("Error " + ex.getErrorCode() + ": " + ex.getMessage());
        }
    	finally {
    		try{
    			if(conn!=null)
    				conn.close();
    		} catch(SQLException se){
    			logger.error(se.getMessage());
    			se.printStackTrace();
    		}
    	}
		return agentCfgDb;
    }
	
	private List<AgentConfigurationDatabase> getAgentsConfigurations(Connection conn) throws SQLException{
		logger.info("Searching agent configurations in DB");
		System.out.println("Searching agent configurations in DB");
		List<AgentConfigurationDatabase> agentsCfg = null;
		PreparedStatement pstSelectCfgAgents = null;
		
		try {
			String selectSQL = "SELECT AGENT_ID, EXEC, COMPONENT, PACKETBEAT_STREAM, METRICBEAT_STREAM, FILEBEAT_STREAM, FILEBEAT_PATHS, DOCKERIZED, DOCKER_PATH FROM " + Dictionary.DBTABLE_AGENT_CONFIGURATION;
			pstSelectCfgAgents = conn.prepareStatement(selectSQL);
			ResultSet rs = pstSelectCfgAgents.executeQuery();
			agentsCfg = new ArrayList<AgentConfigurationDatabase>();
			while (rs.next()) {
				AgentConfigurationDatabase agentCfg = toAgentCfgDb(rs);
				agentsCfg.add(agentCfg);
			}
		}
		finally {
    		try{
    			if(pstSelectCfgAgents!=null)
    				pstSelectCfgAgents.close();
    		} catch(SQLException se){
    			logger.error(se.getMessage());
    			se.printStackTrace();
    		}
    	}
		return agentsCfg;
	}
	
	public List<AgentConfigurationDatabase> getAgentsConfigurations() {
		List<AgentConfigurationDatabase> agents = null;
    	Connection conn = null;
    	try {
    		
    		conn = getConnection();
    		return getAgentsConfigurations(conn);
    		
    	} catch (SQLException ex) {
            logger.error("Error " + ex.getErrorCode() + ": " + ex.getMessage());
        }
    	finally {
    		try{
    			if(conn!=null)
    				conn.close();
    		} catch(SQLException se){
    			logger.error(se.getMessage());
    			se.printStackTrace();
    		}
    	}
		return agents;
    }
	
		private boolean deleteAgentConfiguration(Connection conn, String agentId) throws SQLException {
		logger.info("Deleting agent configuration in DB for agent with agentId = " + agentId);
		System.out.println("Deleting agent configuration in DB for agent with agentId = " + agentId);
		PreparedStatement pstDeleteAgent = null;
		
		try {
			String deleteSQL = "DELETE FROM " + Dictionary.DBTABLE_AGENT_CONFIGURATION + " WHERE AGENT_ID = ?";
			pstDeleteAgent = conn.prepareStatement(deleteSQL);
			pstDeleteAgent.setString(1, agentId);
			pstDeleteAgent.executeUpdate();
			if (getAgentConfigurationByAgentId(conn, agentId) == null) {
				logger.info("Agent configuration for agent " + agentId + " deleted");
				System.out.println("Agent configuration for agent " + agentId + " deleted");
				return true;
			}
			else {
				logger.info("Agent configuration for agent " + agentId + " not deleted");
				System.out.println("Agent configuration for agent " + agentId + " not deleted");
				return false;
			}
		}
		finally {
    		try{
    			if(pstDeleteAgent!=null)
    				pstDeleteAgent
    				.close();
    		} catch(SQLException se){
    			logger.error(se.getMessage());
    			se.printStackTrace();
    		}
    	}
	}
	
	
	public boolean deleteAgentConfiguration(String agentId) {
		Connection conn = null;
    	try {
    		
    		conn = getConnection();
    		return deleteAgentConfiguration(conn, agentId);
    		
    	} catch (SQLException ex) {
            logger.error("Error " + ex.getErrorCode() + ": " + ex.getMessage());
        }
    	finally {
    		try{
    			if(conn!=null)
    				conn.close();
    		} catch(SQLException se){
    			logger.error(se.getMessage());
    			se.printStackTrace();
    		}
    	}
		return false;
	}

	private String getStringFromArrayList(List<String> list, String separator) {
		String result = "";
		for (String s: list) {
			result += s + separator;
		}
		if (!result.equalsIgnoreCase("")) {
			result = result.substring(0,result.length()-separator.length());
		}
		return result;	
	}
	
	private List<String> getArrayListFromString(String text, String separator) {
		List<String> result = new ArrayList<>();
		String [] aux = text.split(separator);
		if (aux != null) {
			for (String s: aux) {
				result.add(s);
			}
		}
		return result;
	}
	
	private AgentConfigurationDatabase toAgentCfgDb (ResultSet rs) throws SQLException {
		AgentConfigurationDatabase agentCfg = new AgentConfigurationDatabase();
		agentCfg.setAgentId(rs.getString("AGENT_ID"));
		AgentConfiguration agentConfiguration = new AgentConfiguration();
		agentConfiguration.setExec(rs.getString("EXEC"));
		agentConfiguration.setComponent(rs.getString("COMPONENT"));

		agentConfiguration.setDockerized(rs.getString("DOCKERIZED"));
		
		AgentConfigurationMetricbeat metricbeat = new AgentConfigurationMetricbeat();
		metricbeat.setStream(rs.getString("METRICBEAT_STREAM"));
		List<String> dockerized_metricbeat = new ArrayList<>();
		dockerized_metricbeat.add(rs.getString("DOCKER_METRICS"));
		metricbeat.setDockerized(dockerized_metricbeat);
		agentConfiguration.setMetricbeat(metricbeat);

		AgentConfigurationPacketbeat packetbeat = new AgentConfigurationPacketbeat();
		packetbeat.setStream(rs.getString("PACKETBEAT_STREAM"));
		agentConfiguration.setPacketbeat(packetbeat);

		AgentConfigurationFilebeat filebeat = new AgentConfigurationFilebeat();
		filebeat.setStream(rs.getString("FILEBEAT_STREAM"));
		filebeat.setPaths(getArrayListFromString(rs.getString("FILEBEAT_PATHS"), ","));
		List<String> dockerized_filebeat = new ArrayList<>();
		dockerized_filebeat.add(rs.getString("DOCKER_PATH"));
		filebeat.setDockerized(dockerized_filebeat);
		agentConfiguration.setFilebeat(filebeat);
		
		agentCfg.setAgentConfiguration(agentConfiguration);

		return agentCfg;
	}
	
	
	public static void main (String args[]) {
		EimDbAgentCfgManager manager = new EimDbAgentCfgManager();

		AgentConfiguration agentCfg = new AgentConfiguration();
		agentCfg.setExec("exec_name");
		agentCfg.setComponent("component_name");
		
		AgentConfigurationFilebeat filebeat = new AgentConfigurationFilebeat();
		filebeat.addPathsItem("/home/logs/*");
		filebeat.setStream("filebeat_stream");
		agentCfg.setFilebeat(filebeat);
		
		AgentConfigurationPacketbeat packetbeat = new AgentConfigurationPacketbeat();
		packetbeat.setStream("packetbeat_stream");
		agentCfg.setPacketbeat(packetbeat);
		
		AgentConfigurationMetricbeat metricbeat = new AgentConfigurationMetricbeat();
		metricbeat.setStream("metricbeat_stream");
		agentCfg.setMetricbeat(metricbeat);
		
		manager.addAgentConfiguration("iagent0", agentCfg);
		AgentConfigurationDatabase agentCfgDb = manager.getAgentConfigurationByAgentId("iagent0");
		System.out.println(agentCfgDb.getAgentId());
		//manager.addHost(host);
//		manager.deleteAgentConfiguration("iagent0");
//		AgentFull agent = manager.getAgentByIpAddress("1.1.1.1");
//		System.out.println(agent.getAgentId());
//		manager.setMonitored("iagent0", false);
	}
	
    
}
