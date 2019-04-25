/**
 * Copyright (c) 2018 Atos
 * This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0
 * which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 *    @author Fernando Mendez Requena (Atos)
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
import io.swagger.model.AgentConfigurationControl;
import io.swagger.model.AgentConfigurationDatabase;
import io.swagger.model.AgentConfigurationDatabaseControl;
import io.swagger.model.AgentConfigurationFilebeat;
import io.swagger.model.AgentConfigurationMetricbeat;
import io.swagger.model.AgentConfigurationPacketbeat;

public class EimDbAgentCfgControlManager {
	
	  private static Logger logger = Logger.getLogger(EimDbAgentCfgControlManager.class);
	  
	  public EimDbAgentCfgControlManager() {
		  
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
	    	String sqlSearchIagent = "SELECT AGENT_ID FROM " + Dictionary.DBTABLE_AGENT_CONFIGURATION_CONTROL + " WHERE AGENT_ID=?";
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
	    
	    public AgentConfigurationDatabaseControl addAgentConfiguration(String agentId, AgentConfigurationControl agentCfg2) {
	    	AgentConfigurationDatabaseControl agentCfg = null;
	    	Connection conn = null;
	    	try {
	    		
	    		conn = getConnection();
	    		
	    		if (!existsAgentCfg(conn, agentId)) {
	    			agentCfg = addAgentConfiguration(conn, agentId, agentCfg2);
	    			logger.info("New Agent Configuration for agent" + agentCfg);
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
	    
	    
	    
	    private AgentConfigurationDatabaseControl addAgentConfiguration( Connection conn, String agentId, AgentConfigurationControl agentCfgObj) throws SQLException{
	    	AgentConfigurationDatabaseControl inserted = null;
	    	PreparedStatement pstInsertAgentCfg = null;
	    	try {
	    		logger.info("Adding new agent configuration to DB for agent with agentId = " + agentId);
	    		System.out.println("Adding new agent configuration to DB for agent with agentId = " + agentId);
	    		
	    		String sqlInsertHost = "INSERT INTO " + Dictionary.DBTABLE_AGENT_CONFIGURATION_CONTROL + " VALUES (?,?,?,?,?,?,?)";
	    		pstInsertAgentCfg = conn.prepareStatement(sqlInsertHost);
	    	
	    		pstInsertAgentCfg.setString(1, agentId); 															//agentId
	    		pstInsertAgentCfg.setString(2, agentCfgObj.getExec());												//exec
	    		pstInsertAgentCfg.setString(3, agentCfgObj.getComponent());											//component
	    		pstInsertAgentCfg.setString(4, agentCfgObj.getPacketLoss());										//packetLoss
	    		pstInsertAgentCfg.setString(5, agentCfgObj.getStressNg());											//stressNg
	    		pstInsertAgentCfg.setString(6, agentCfgObj.getDockerized());										//dockerized
	    		pstInsertAgentCfg.setString(7, agentCfgObj.getCronExpression());
	    	
	    		if (agentCfgObj.getDockerized() == null) {
	    			//no Dockerized param is specified in the request --> default value = yes
	    			logger.info("Not specified if SuT is dockerized or not. Using default value: yes");
	    			System.out.println("Not specified if SuT is dockerized or not. Using default value: yes");
	    			pstInsertAgentCfg.setString(6, Dictionary.DOCKERIZED_YES);										//dockerized
	    			
	    			
	    		}
	    		else if (agentCfgObj.getDockerized().equals(Dictionary.DOCKERIZED_YES)) {
	    			//isDockerized
	    			logger.info("SuT is dockerized...");
	    			System.out.println("SuT is dockerized...");
	    			pstInsertAgentCfg.setString(6, agentCfgObj.getDockerized());									//dockerized
	    			
	    		}
	    		else if (agentCfgObj.getPacketLoss() == null) {
	    			logger.info("The action for the agent is stress CPU");
	    			System.out.println("The action for the agent is Stress CPU");
	    			pstInsertAgentCfg.setString(4, "Not executed");
	    		}
	    		else if (agentCfgObj.getStressNg()== null) {
	    			logger.info("The action for the agent is  packetLoss CPU");
	    			System.out.println("The action for the agent is Stress CPU");
	    			pstInsertAgentCfg.setString(5, "Not executed");
	    		}
	    		else {
	    			//notDockerized
	    			logger.info("SuT is not dockerized...");
	    			System.out.println("SuT is not dockerized...docker_path & docker_metrics will be null");
	    			
	    			pstInsertAgentCfg.setString(6, Dictionary.DOCKERIZED_NO);										//dockerized
	    		}
	    		
	    		
	    		logger.info("This is the SQL call sqlInsertHost= " + pstInsertAgentCfg);

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
		
		private AgentConfigurationDatabaseControl getAgentConfigurationByAgentId(Connection conn, String agentId) throws SQLException {
			logger.info("Searching agent configuration for agent with agentId = " + agentId);
			System.out.println("Searching agent configuration for agent with agentId = " + agentId);
			AgentConfigurationDatabaseControl agentCfgDb = null;
			PreparedStatement pstSelectAgentCfg = null;
			
			try {
				String selectAgentCfgSQL = "SELECT * FROM " + Dictionary.DBTABLE_AGENT + " WHERE AGENT_ID = ?";
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
		
		
		public AgentConfigurationDatabaseControl getAgentConfigurationByAgentId(String agentId) {
			AgentConfigurationDatabaseControl agentCfgDb = null;
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
		
		private List<AgentConfigurationDatabaseControl> getAgentsConfigurations(Connection conn) throws SQLException{
			logger.info("Searching agent configurations in DB");
			System.out.println("Searching agent configurations in DB");
			List<AgentConfigurationDatabaseControl> agentsCfg = null;
			PreparedStatement pstSelectCfgAgents = null;
			
			try {
				
				String selectSQL = "SELECT * FROM " + Dictionary.DBTABLE_AGENT_CONFIGURATION_CONTROL;
				pstSelectCfgAgents = conn.prepareStatement(selectSQL);
				ResultSet rs = pstSelectCfgAgents.executeQuery();
				agentsCfg = new ArrayList<AgentConfigurationDatabaseControl>();
				while (rs.next()) {
					AgentConfigurationDatabaseControl agentCfg = toAgentCfgDb(rs);
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
		
		public List<AgentConfigurationDatabaseControl> getAgentsConfigurations() {
			List<AgentConfigurationDatabaseControl> agents = null;
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
				String deleteSQL = "DELETE FROM " + Dictionary.DBTABLE_AGENT_CONFIGURATION_CONTROL + " WHERE AGENT_ID = ?";
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
		
		
		private AgentConfigurationDatabaseControl toAgentCfgDb (ResultSet rs) throws SQLException {
			AgentConfigurationDatabaseControl agentCfg = new AgentConfigurationDatabaseControl();
			agentCfg.setAgentId(rs.getString("AGENT_ID"));
			AgentConfigurationControl agentConfiguration = new AgentConfigurationControl();
			agentConfiguration.setExec(rs.getString("EXEC_NAME"));
			agentConfiguration.setComponent(rs.getString("COMPONENT_NAME"));
			agentConfiguration.setDockerized(rs.getString("DOCKERIZED"));
			agentConfiguration.setPacketLoss(rs.getString("PACKETLOSS"));
			agentConfiguration.setStressNg(rs.getString("STRESSNG"));
			agentConfiguration.setCronExpression(rs.getString("CRONEXPRESSION"));
			
			agentCfg.setAgentConfigurationControl(agentConfiguration);

			return agentCfg;
		}
		
		
		public static void main (String args[]) {
			EimDbAgentCfgControlManager manager = new EimDbAgentCfgControlManager();

			AgentConfigurationControl agentCfg = new AgentConfigurationControl();
			agentCfg.setExec("EXEC_NAME");
			agentCfg.setComponent("COMPONENT_NAME");
			agentCfg.setPacketLoss("PACKETLOSS");
			agentCfg.setStressNg("STRESSNG");
			agentCfg.setDockerized("DOCKERIZED");
			agentCfg.setCronExpression("CRONEXPRESSION");
			
			manager.addAgentConfiguration("iagent0", agentCfg);
			AgentConfigurationDatabaseControl agentCfgDb = manager.getAgentConfigurationByAgentId("iagent0");
			System.out.println(agentCfgDb.getAgentId());
		
		}
}
