package io.elastest.eim.database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import io.swagger.model.AgentFull;
import io.swagger.model.Host;

public class EimDbAgentManager {

	// JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static final String DB_URL = "jdbc:mariadb://localhost:3306/eim";

    //  Database credentials
    static final String USER = "elastest";
    static final String PASS = "elastest";

    private static Logger logger = Logger.getLogger(EimDbAgentManager.class);
    
    public EimDbAgentManager() {
    	
    }
    
    public Connection getConnection() {
    	Connection conn = null;
        try {
            //Register JDBC driver
            Class.forName(JDBC_DRIVER);

          //TODO USE CONTSTANTS DEFINED IN DICTIONARY
           // String dbUrl ="jdbc:mariadb://" + System.getenv("ET_EIM_MONGO_HOST") + ":3306/eim"; 
            String dbUrl ="jdbc:mariadb://" + "localhost" + ":3306/eim";
            
            //Open a connection
            logger.info("Connecting to EIM database...");
            conn = DriverManager.getConnection(
            		dbUrl, USER, PASS);
            logger.info("Connected to EIM database successfully...");
            

            return conn;
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
    
    
    private String getNewAgentId(Connection conn) throws SQLException{
		String newId = "iagent";
		List<AgentFull> agents = getAgents(conn);
		int elements;
		if (agents != null) {
			elements = agents.size();
			newId += elements;
		}
		else if (agents == null && conn != null){
			//first time that the method is called
			newId += 1;
			
		}
		return newId;
	}
    
    private boolean existsAgent(Connection conn, String agentId) throws SQLException {
    	String sqlSearchIagent = "SELECT AGENT_ID FROM agent WHERE AGENT_ID=?";
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
    
    public AgentFull addHost(Host host) {
    	AgentFull agent = null;
    	Connection conn = null;
    	try {
    		
    		conn = getConnection();
    		
    		if (!existsAgent(conn, host.getAddress())) {
    			agent = addHost(conn, host);
    			return agent;
    		}
    		else { 
    			logger.info("Agent with agentId = " + host.getAddress() + " exists in database");
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
		return agent;
    }
    
    
    private AgentFull addHost( Connection conn, Host host) throws SQLException{
    	AgentFull inserted = null;
    	PreparedStatement pstInsertHost = null;
    	try {
    		logger.info("Adding new host to DB, host with ipAddress = " + host.getAddress());
    		System.out.println("Adding new host to DB, host with ipAddress = " + host.getAddress());
    		
    		String sqlInsertHost = "INSERT INTO agent VALUES (?,?,?,?,?)";
    		pstInsertHost = conn.prepareStatement(sqlInsertHost);
    	
    		pstInsertHost.setString(1, getNewAgentId(conn));
    		pstInsertHost.setString(2, host.getAddress());
    		pstInsertHost.setBoolean(3, false);
    		pstInsertHost.setString(4, host.getLogstashIp());
    		pstInsertHost.setString(5, host.getLogstashPort());
    		pstInsertHost.executeUpdate();
    		logger.info("Agent inserted in database wiht ipAddress = " + host.getAddress());
    		
    		inserted = getAgentByIpAddress(conn, host.getAddress());
    		
    	}
		finally {
    		try{
    			if(pstInsertHost!=null)
    				pstInsertHost.close();
    		} catch(SQLException se){
    			logger.error(se.getMessage());
    			se.printStackTrace();
    		}
    	}
    	return inserted;
    }
   
    
	private AgentFull getAgentByIpAddress(Connection conn, String ipAddress) throws SQLException {
		logger.info("Searching host in DB with ipAddress = " + ipAddress);
		System.out.println("Searching host in DB with ipAddress = " + ipAddress);
		AgentFull agent = null;
		PreparedStatement pstSelectAgent = null;
		
		try {
			String selectSQL = "SELECT AGENT_ID, HOST, LOGSTASH_IP, LOGSTASH_PORT FROM AGENT WHERE HOST = ?";
			pstSelectAgent = conn.prepareStatement(selectSQL);
			pstSelectAgent.setString(1, ipAddress);
			ResultSet rs = pstSelectAgent.executeQuery(selectSQL );
			while (rs.next()) {
				agent = new AgentFull();
				agent.setAgentId(rs.getString("AGENT_ID"));
				agent.setHost(rs.getString("HOST"));
				agent.setLogstashIp(rs.getString("LOGSTASH_IP"));
				agent.setLogstashPort(rs.getString("LOGSTASH_PORT"));
	        	logger.info("Host finded in DB with ipAddress = " + ipAddress + " with ID " + agent.getAgentId());
	        	System.out.println("Host finded in DB with ipAddress = " + ipAddress + " with ID " + agent.getAgentId());
				return agent;
			}
		}
		finally {
    		try{
    			if(pstSelectAgent!=null)
    				pstSelectAgent.close();
    		} catch(SQLException se){
    			logger.error(se.getMessage());
    			se.printStackTrace();
    		}
    	}
		return agent;

	}
	
	private AgentFull getAgentByAgentId(Connection conn, String agentId) throws SQLException {
		logger.info("Searching host in DB with agentId = " + agentId);
		System.out.println("Searching host in DB with agentId = " + agentId);
		AgentFull agent = null;
		PreparedStatement pstSelectAgent = null;
		
		try {
			String selectSQL = "SELECT AGENT_ID, HOST, LOGSTASH_IP, LOGSTASH_PORT FROM AGENT WHERE AGENT_ID = ?";
			pstSelectAgent = conn.prepareStatement(selectSQL);
			pstSelectAgent.setString(1, agentId);
			ResultSet rs = pstSelectAgent.executeQuery(selectSQL );
			while (rs.next()) {
				agent = new AgentFull();
				agent.setAgentId(rs.getString("AGENT_ID"));
				agent.setHost(rs.getString("HOST"));
				agent.setLogstashIp(rs.getString("LOGSTASH_IP"));
				agent.setLogstashPort(rs.getString("LOGSTASH_PORT"));
	        	logger.info("Host finded in DB with agentId = " + agentId + " with ID " + agent.getAgentId());
	        	System.out.println("Host finded in DB with agentId = " + agentId + " with ID " + agent.getAgentId());
				return agent;
			}
		}
		finally {
    		try{
    			if(pstSelectAgent!=null)
    				pstSelectAgent.close();
    		} catch(SQLException se){
    			logger.error(se.getMessage());
    			se.printStackTrace();
    		}
    	}
		return agent;

	}
	
	
	public AgentFull getAgentByAgentId(String agentId) {
    	AgentFull agent = null;
    	Connection conn = null;
    	try {
    		
    		conn = getConnection();
    		return getAgentByAgentId(conn, agentId);
    		
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
		return agent;
    }
	
	private List<AgentFull> getAgents(Connection conn) throws SQLException{
		logger.info("Searching agents in DB");
		System.out.println("Searching agents in DB");
		List<AgentFull> agents = null;
		PreparedStatement pstSelectAgents = null;
		
		try {
			String selectSQL = "SELECT AGENT_ID, HOST, LOGSTASH_IP, LOGSTASH_PORT FROM AGENT";
			pstSelectAgents = conn.prepareStatement(selectSQL);
			ResultSet rs = pstSelectAgents.executeQuery(selectSQL);
			agents = new ArrayList<AgentFull>();
			while (rs.next()) {
				AgentFull agent = new AgentFull();
				agent.setAgentId(rs.getString("AGENT_ID"));
				agent.setHost(rs.getString("HOST"));
				agent.setLogstashIp(rs.getString("LOGSTASH_IP"));
				agent.setLogstashPort(rs.getString("LOGSTASH_PORT"));
	        	agents.add(agent);
			}
		}
		finally {
    		try{
    			if(pstSelectAgents!=null)
    				pstSelectAgents.close();
    		} catch(SQLException se){
    			logger.error(se.getMessage());
    			se.printStackTrace();
    		}
    	}
		return agents;
	}
	
	public List<AgentFull> getAgents() {
		List<AgentFull> agents = null;
    	Connection conn = null;
    	try {
    		
    		conn = getConnection();
    		return getAgents(conn);
    		
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
	
	public AgentFull getAgentByIpAddress(String ipAddress) {
		AgentFull agent = null;
    	Connection conn = null;
    	try {
    		
    		conn = getConnection();
    		return getAgentByIpAddress(conn, ipAddress);
    		
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
		return agent;
	}
	
	private boolean deleteAgent(Connection conn, String agentId) throws SQLException {
		logger.info("Deleting agent in DB with agentId = " + agentId);
		System.out.println("Deleting agent in DB with agentId = " + agentId);
		PreparedStatement pstDeleteAgent = null;
		
		try {
			String deleteSQL = "DELETE FROM AGENT WHERE AGENT_ID = ?";
			pstDeleteAgent = conn.prepareStatement(deleteSQL);
			pstDeleteAgent.setString(1, agentId);
			pstDeleteAgent.executeUpdate();
			if (getAgentByAgentId(conn, agentId) == null) {
				return true;
			}
			else {
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
	
	
	public boolean deleteAgent(String agentId) {
    	Connection conn = null;
    	try {
    		
    		conn = getConnection();
    		return deleteAgent(conn, agentId);
    		
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
	
	public AgentFull setMonitored(String agentId, boolean monitored) {
		AgentFull agent = null;
    	Connection conn = null;
    	try {
    		
    		conn = getConnection();
    		return setMonitored(conn, agentId, monitored);
    		
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
		return agent;
	}

	private AgentFull setMonitored(Connection conn, String agentId, boolean monitored) throws SQLException {
		AgentFull modified = null;
    	PreparedStatement pstSetMonitored = null;
    	try {
    		logger.info("Setting agent " + agentId + " monitored = " + monitored);
    		System.out.println("Setting agent " + agentId + " monitored = " + monitored);
    		
    		String sqlSetMonitored = "UPDATE agent SET monitored = ? WHERE agent_Id = ?";
    		pstSetMonitored = conn.prepareStatement(sqlSetMonitored);
    	
    		pstSetMonitored.setBoolean(1, monitored);
    		pstSetMonitored.setString(2, agentId);
    		pstSetMonitored.executeUpdate();
    		logger.info("Agent modified in database wiht agentId = " + agentId);
    		
    		modified = getAgentByAgentId(conn, agentId);
    		
    	}
		finally {
    		try{
    			if(pstSetMonitored!=null)
    				pstSetMonitored.close();
    		} catch(SQLException se){
    			logger.error(se.getMessage());
    			se.printStackTrace();
    		}
    	}
    	return modified;
	}
	
	public static void main (String args[]) {
		EimDbAgentManager manager = new EimDbAgentManager();
		Host host = new Host();
		host.setAddress("1.1.1.1");
		host.setLogstashIp("2.2.2.2");
		host.setLogstashPort("1234");
		host.setPrivateKey("myKey");
		host.setUser("user");
		manager.addHost(host);
	}
	
    
}
