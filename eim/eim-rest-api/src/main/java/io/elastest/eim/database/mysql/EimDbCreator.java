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
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import io.elastest.eim.config.Dictionary;

public class EimDbCreator {

	private Connection con;
	private Statement statement;
	private static Logger logger = Logger.getLogger(EimDbCreator.class);
	
	public EimDbCreator() {
		
	}
	
	public void createSchema() {
		createDatabase();
		if (!existsTableAgent()) {
			createTableAgent();	
		}
		if (!existsTableAgentCfg()) {
			createTableAgentCfg();	
		}
	}
	
	private void createDatabase() {
        try {
            Class.forName(Dictionary.JDBC_DRIVER);
            con = DriverManager.getConnection(Dictionary.DBURL, Dictionary.DBUSER, Dictionary.DBPASS);
            Statement s = con.createStatement();
            int myResult = s.executeUpdate("CREATE DATABASE IF NOT EXISTS " + Dictionary.DBNAME);
        } 
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            logger.error("Error creating " + Dictionary.DBNAME + " database: " + e.getLocalizedMessage());
        }
    }
	
	private void createTableAgent() {		
		String mysqlCreateCmd = "CREATE TABLE " + Dictionary.DBTABLE_AGENT + " ("
								  + "agent_id varchar(255) NOT NULL,"
								  + "host varchar(255) NOT NULL,"
								  + "monitored tinyint(1) NOT NULL,"
								  + "logstash_ip varchar(255) NOT NULL,"
								  + "logstash_port varchar(255) NOT NULL,"
								  + "user varchar(255) NOT NULL,"
								  + "password varchar(255) NOT NULL,"
								  + "PRIMARY KEY (agent_id)"
						+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		
		createTable(mysqlCreateCmd, Dictionary.DBTABLE_AGENT);
	}
	
	private void createTableAgentCfg() {
		String mysqlCreateCmd = "CREATE TABLE " + Dictionary.DBTABLE_AGENT_CONFIGURATION + " (" 
								  + "agent_id varchar(255) NOT NULL,"
								  + "exec varchar(255) NOT NULL,"
								  + "component varchar(255) NOT NULL,"
								  + "packetbeat_stream varchar(255) NOT NULL,"
								  + "metricbeat_stream varchar(255) NOT NULL,"
								  + "filebeat_stream varchar(255) NOT NULL,"
								  + "filebeat_paths text NOT NULL,"
								  + "dockerized varchar(255) NOT NULL,"
								  + "docker_path varchar(255),"
								  + "docker_metrics varchar(255),"
								  + "PRIMARY KEY (`agent_id`)"
							+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8";
		createTable(mysqlCreateCmd, Dictionary.DBTABLE_AGENT_CONFIGURATION);
	}
	
	private void dropTableAgent() {
		String mysqlDropCmd = "DROP TABLE IF EXISTS " + Dictionary.DBTABLE_AGENT;
		dropTableIfExists(mysqlDropCmd, Dictionary.DBTABLE_AGENT);
	}
	
	private void dropTableAgentCfg() {
		String mysqlDropCmd = "DROP TABLE IF EXISTS " + Dictionary.DBTABLE_AGENT_CONFIGURATION;
		dropTableIfExists(mysqlDropCmd, Dictionary.DBTABLE_AGENT_CONFIGURATION);
	}
	
	private void createTable(String mysqlCreateCmd, String tableName) { 
        try {
            Class.forName(Dictionary.JDBC_DRIVER);
            con = DriverManager.getConnection(Dictionary.DBURL, Dictionary.DBUSER, Dictionary.DBPASS);
            statement = con.createStatement();
            statement.executeUpdate(mysqlCreateCmd);
            System.out.println("table " + tableName + " created in DB");
            logger.info("table " + tableName + " created in DB");
        }
        catch (SQLException e ) {
            System.out.println("Error has occurred on table " + tableName + " creation");
            logger.error("Error has occurred on table " + tableName + " creation");
        }
        catch (ClassNotFoundException e) {
            System.out.println("MariaDB drivers were not found");
            logger.error("MariaDB drivers were not found");
        }
    }
	
	private void dropTableIfExists(String mysqlDropCmd, String tableName) {
		try {
            Class.forName(Dictionary.JDBC_DRIVER);
            con = DriverManager.getConnection(Dictionary.DBURL, Dictionary.DBUSER, Dictionary.DBPASS);
            statement = con.createStatement();
            statement.executeUpdate(mysqlDropCmd);
            System.out.println("table " + tableName + " dropped in DB");
            logger.info("table " + tableName + " dropped in DB");
        }
        catch (SQLException e ) {
            System.out.println("Error has occurred on table " + tableName + " drop");
            logger.error("Error has occurred on table " + tableName + " drop");
        }
        catch (ClassNotFoundException e) {
            System.out.println("MariaDB drivers were not found");
            logger.error("MariaDB drivers were not found");
        }
	}
	
	public boolean existsTableAgent() {
		return existsTable(Dictionary.DBTABLE_AGENT);
	}
	
	public boolean existsTableAgentCfg() {
		return existsTable(Dictionary.DBTABLE_AGENT_CONFIGURATION);
	}
	
	private boolean existsTable(String tableName) { 
        try {
            Class.forName(Dictionary.JDBC_DRIVER);
            con = DriverManager.getConnection(Dictionary.DBURL, Dictionary.DBUSER, Dictionary.DBPASS);

            logger.info("Checking if table " + tableName + " exists in DB");
            
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet rs = dbm.getTables(null, "EIM", tableName, null);
            if (!rs.next()) {
            	 System.out.println(tableName + " table no exists in DB");
            	 return false;
            }else{
                System.out.println(tableName + " table exists in DB");
                return true;
            }
        }
        catch (SQLException e ) {
            System.out.println("Error has occurred on table " + tableName + " check");
            logger.error("Error has occurred on table " + tableName + " check");
        }
        catch (ClassNotFoundException e) {
            System.out.println("MariaDB drivers were not found");
            logger.error("MariaDB drivers were not found");
        }
        return false;
    }
	

	public static void main (String args[]) {
		EimDbCreator creator = new EimDbCreator();
		creator.existsTableAgent();
		creator.existsTableAgentCfg();
		creator.createTableAgent();
		creator.createTableAgentCfg();
		creator.existsTableAgent();
		creator.existsTableAgentCfg();
	}
	
}
