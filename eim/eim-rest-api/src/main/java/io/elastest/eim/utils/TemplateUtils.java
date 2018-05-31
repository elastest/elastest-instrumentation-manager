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

package io.elastest.eim.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import io.elastest.eim.config.Dictionary;
import io.elastest.eim.config.Properties;
import io.swagger.model.AgentConfiguration;
import io.swagger.model.AgentFull;

public class TemplateUtils {

	private static Logger logger = Logger.getLogger(TemplateUtils.class);
	
	public static String generateSshPlaybook(String executionDate, AgentFull agent, String user, String action) {
		if (action.equalsIgnoreCase(Dictionary.INSTALL)) {
			
			String playbookTemplatePath = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_EXECUTIONPATH) + 
					Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_INSTALL_PLAYBOOK);
			String playbookToExecutePath = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_EXECUTIONPATH) + 
					Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_INSTALL_EXECUTION_PLAYBOOK_PREFIX) + agent.getAgentId() + 
					"-" + executionDate + ".yml";
			String jokerTemplates = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_PLAYBOOK_JOKER);
			String jokerUser = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_USER_JOKER);
			
			try {
				//Generate the execution playbook
				FileTextUtils.copyFile(playbookTemplatePath, playbookToExecutePath);
				logger.info("Generated successfully the SSH playbook for agent" + agent.getAgentId() + ": " + playbookToExecutePath);
				//Fill the playbook with the agentId of the agent
				FileTextUtils.replaceTextInFile(playbookToExecutePath, jokerTemplates, agent.getAgentId());
				//Fill the playbook with the user of the new host
				FileTextUtils.replaceTextInFile(playbookToExecutePath, jokerUser, user);
				logger.info("Modified successfully the generated SSH playbook for agent " + agent.getAgentId() + ". Ready to execute!");
				return playbookToExecutePath;				
			} catch (IOException e) {
				logger.error("ERROR while triying to generate SSH playbook for agent " + agent.getAgentId() + " with execution date: " + executionDate);
				e.printStackTrace();
				return "";
			}			
		}
		else if (action.equalsIgnoreCase(Dictionary.REMOVE)) {
			
			String playbookTemplatePath = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_EXECUTIONPATH) + 
					Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_REMOVE_PLAYBOOK);
			String playbookToExecutePath = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_EXECUTIONPATH) + 
					Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_REMOVE_EXECUTION_PLAYBOOK_PREFIX) + agent.getAgentId() + 
					"-" + executionDate + ".yml";
			String jokerTemplates = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_PLAYBOOK_JOKER);
						
			try {
				//Generate the execution playbook
				FileTextUtils.copyFile(playbookTemplatePath, playbookToExecutePath);
				logger.info("Generated successfully the SSH playbook for agent" + agent.getAgentId() + ": " + playbookToExecutePath);
				//Fill the playbook with the agentId of the agent
				FileTextUtils.replaceTextInFile(playbookToExecutePath, jokerTemplates, agent.getAgentId());
				logger.info("Modified successfully the generated SSH playbook for agent " + agent.getAgentId() + ". Ready to execute!");
				return playbookToExecutePath;				
			} catch (IOException e) {
				logger.error("ERROR while triying to generate SSH playbook for agent " + agent.getAgentId() + " with execution date: " + executionDate);
				e.printStackTrace();
				return "";
			}	
		}
		return "";
	}
	
	public static String generateBeatsPlaybook(String executionDate, AgentFull agent, String user, String action, AgentConfiguration agentCfg) {
		 if (action.equalsIgnoreCase(Dictionary.INSTALL)) {			

			String playbookTemplatePath = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_PLAYBOOKPATH) + 
					Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_INSTALL_PLAYBOOK);
			String playbookToExecutePath = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_PLAYBOOKPATH) + 
					Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_INSTALL_EXECUTION_PLAYBOOK_PREFIX) + agent.getAgentId() + 
					"-" + executionDate + ".yml";
			String jokerTemplates = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_PLAYBOOK_JOKER);
			String jokerLogstashIp = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_JOKER_LOGSTASH_IP);
			String jokerLogstashPort = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_JOKER_LOGSTASH_PORT);
			String jokerExecId = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_JOKER_EXEC);
			String jokerComponent = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_JOKER_COMPONENT);
			String jokerStreamFilebeat = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_JOKER_STREAM_FILEBEAT);
			String jokerStreamPacketbeat = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_JOKER_STREAM_PACKETBEAT);
			String jokerStreamMetricbeat = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_JOKER_STREAM_METRICBEAT);
			String jokerFilepaths = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_JOKER_FILEPATHS);
			String jokerPlaybookDockerizedFilebeat = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_JOKER_PLAYBOOK_DOCKERIZED_FILEBEAT);
			String jokerDockerPath = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_JOKER_DOCKER_PATH);
			String dockerConfFilebeat = 
	        		"          - type: log\n" + 
	        		"            paths:\n" + 
	        		"              - \"" + Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_JOKER_DOCKER_PATH) + "*/*.log\"\n" + 
	        		"            json.message_key: log\n" + 
	        		"            json.keys_under_root: true\n" + 
	        		"            processors:\n" + 
	        		"              - add_docker_metadata: ~";
			String jokerPlaybookDockerizedMetricbeat = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_JOKER_PLAYBOOK_DOCKERIZED_METRICBEAT);
			String jokerDockerMetrics = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_JOKER_DOCKER_METRICS);
			String dockerConfMetricbeat = 
					"        - module: docker\n" + 
					"          metricsets: [\"cpu\", \"diskio\", \"healthcheck\", \"info\", \"memory\", \"network\"]\n" + 
					"          hosts: [\"unix://" + Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_JOKER_DOCKER_METRICS) + "\"]\n" + 
					"          enabled: true\n" + 
					"          period: 10s";
			try {
				//Generate the execution playbook
				FileTextUtils.copyFile(playbookTemplatePath, playbookToExecutePath);
				logger.info("Generated successfully the beats installation playbook for agent" + agent.getAgentId() + ": " + playbookToExecutePath);
				//Fill the playbook with the agentId of the agent
				FileTextUtils.replaceTextInFile(playbookToExecutePath, jokerTemplates, agent.getAgentId());
				//Fill the playbook with the logstash IP of the agent
				FileTextUtils.replaceTextInFile(playbookToExecutePath, jokerLogstashIp, agent.getLogstashIp());
				//Fill the playbook with the logstash port of the agent
				FileTextUtils.replaceTextInFile(playbookToExecutePath, jokerLogstashPort, agent.getLogstashPort());
				//Fill the playbook with the exec-id of the agent
				FileTextUtils.replaceTextInFile(playbookToExecutePath, jokerExecId, agentCfg.getExec());
				//Fill the playbook with the component
				FileTextUtils.replaceTextInFile(playbookToExecutePath, jokerComponent, agentCfg.getComponent());
				//Fill the playbook with the stream for filebeat
				FileTextUtils.replaceTextInFile(playbookToExecutePath, jokerStreamFilebeat, agentCfg.getFilebeat().getStream());
				//Fill the playbook with the stream for metricbeat
				FileTextUtils.replaceTextInFile(playbookToExecutePath, jokerStreamMetricbeat, agentCfg.getMetricbeat().getStream());
				//Fill the playbook with the stream for packetbeat
				FileTextUtils.replaceTextInFile(playbookToExecutePath, jokerStreamPacketbeat, agentCfg.getPacketbeat().getStream());
				//Fill the playbook with the stream for packetbeat
				FileTextUtils.replaceListInFile(playbookToExecutePath, jokerFilepaths, agentCfg.getFilebeat().getPaths());
				if (agentCfg.isDockerized()) {
					//Filebeat
					//Fill the playbook with docker prospector (if is dockerized)
					FileTextUtils.replaceTextInFile(playbookToExecutePath, jokerPlaybookDockerizedFilebeat, dockerConfFilebeat);
					//Fill the playbook with docker_path on prospector info (if is dockerized)	
					//if the value is not defined in the object, default value is used
					String dockerPathValue = Dictionary.DOCKERIZED_DEFAULT_DOCKER_PATH;
					if (!agentCfg.getFilebeat().getDockerized().isEmpty()) {
						dockerPathValue = agentCfg.getFilebeat().getDockerized().get(0);
					}
					FileTextUtils.replaceTextInFile(playbookToExecutePath, jokerDockerPath, dockerPathValue);
					
					//Metricbeat
					//Fill the playbook with docker metrics conf (if is dockerized)
					FileTextUtils.replaceTextInFile(playbookToExecutePath, jokerPlaybookDockerizedMetricbeat, dockerConfMetricbeat);
					//Fill the playbook with docker_path on prospector info (if is dockerized)
					//if the value is not defined in the object, default value is used
					String dockerPathMetrics = Dictionary.DOCKERIZED_DEFAULT_DOCKER_METRIC;
					if (!agentCfg.getMetricbeat().getDockerized().isEmpty()) {
						dockerPathMetrics = agentCfg.getMetricbeat().getDockerized().get(0);
					}
					FileTextUtils.replaceTextInFile(playbookToExecutePath, jokerDockerMetrics, dockerPathMetrics);
				}
								
				logger.info("Modified successfully the generated beats installation playbook for agent " + agent.getAgentId() + ". Ready to execute!");
				return playbookToExecutePath;				
			} catch (IOException e) {
				logger.error("ERROR while triying to generate beats installation playbook for agent " + agent.getAgentId() + " with execution date: " + executionDate);
				e.printStackTrace();
				return "";
			}		
			
		}
		else if (action.equalsIgnoreCase(Dictionary.REMOVE)) {
			
			String playbookTemplatePath = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_PLAYBOOKPATH) + 
					Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_REMOVE_PLAYBOOK);
			String playbookToExecutePath = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_PLAYBOOKPATH) + 
					Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_REMOVE_EXECUTION_PLAYBOOK_PREFIX) + agent.getAgentId() + 
					"-" + executionDate + ".yml";
			String jokerTemplates = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_PLAYBOOK_JOKER);
			
			try {
				//Generate the execution playbook
				FileTextUtils.copyFile(playbookTemplatePath, playbookToExecutePath);
				logger.info("Generated successfully the beats remove playbook for agent" + agent.getAgentId() + ": " + playbookToExecutePath);
				//Fill the playbook with the agentId of the agent
				FileTextUtils.replaceTextInFile(playbookToExecutePath, jokerTemplates, agent.getAgentId());
				logger.info("Modified successfully the generated beats remove playbook for agent " + agent.getAgentId() + ". Ready to execute!");
				return playbookToExecutePath;				
			} catch (IOException e) {
				logger.error("ERROR while triying to generate beats remove playbook for agent " + agent.getAgentId() + " with execution date: " + executionDate);
				e.printStackTrace();
				return "";
			}	
		}
		
		return "";
		 
	}
	
	public static String generateSshScript(String executionDate, AgentFull agent, String playbookPath, String ansibleCfgFilePath, String action) {
		if (action.equalsIgnoreCase(Dictionary.INSTALL)) {
			
			String scriptTemplatePath = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_EXECUTIONPATH) + 
					Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_INSTALL_LAUNCHER);
			String scriptToExecutePath = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_EXECUTIONPATH) + 
					Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_INSTALL_EXECUTION_LAUNCHER_PREFIX) + agent.getAgentId() +"-" + executionDate + ".sh";
			String jokerPlaybookTemplates = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SCRIPT_JOKER_PLAYBOOK);
			String jokerCfgFileTemplates = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SCRIPT_JOKER_CONFIG);
			
			try {
				//Generate the execution playbook
				FileTextUtils.copyFile(scriptTemplatePath, scriptToExecutePath);
				logger.info("Generated successfully the SSH script for agent" + agent.getAgentId() + ": " + scriptToExecutePath);
				//Fill the playbook with the agentId of the agent
				FileTextUtils.replaceTextInFile(scriptToExecutePath, jokerPlaybookTemplates, playbookPath);
				//Fill the playbook with the specific ansible config file of the agent
				FileTextUtils.replaceTextInFile(scriptToExecutePath, jokerCfgFileTemplates, ansibleCfgFilePath);
				//set the file as executable
				FileTextUtils.setAsExecutable(scriptToExecutePath);
				logger.info("Modified successfully the generated SSH script for agent " + agent.getAgentId() + ". Ready to execute!");
				return scriptToExecutePath;
				
			} catch (IOException e) {
				logger.error("ERROR while triying to generate SSH script for agent " + agent.getAgentId() + " with execution date: " + executionDate);
				e.printStackTrace();
				return "";
			}				
			
		}
		else if (action.equalsIgnoreCase(Dictionary.REMOVE)) {
			
			String scriptTemplatePath = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_EXECUTIONPATH) + 
					Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_REMOVE_LAUNCHER);
			String scriptToExecutePath = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_EXECUTIONPATH) + 
					Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_REMOVE_EXECUTION_LAUNCHER_PREFIX) + agent.getAgentId() +"-" + executionDate + ".sh";
			String jokerPlaybookTemplates = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SCRIPT_JOKER_PLAYBOOK);
			String jokerCfgFileTemplates = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SCRIPT_JOKER_CONFIG);
			
			try {
				//Generate the execution playbook
				FileTextUtils.copyFile(scriptTemplatePath, scriptToExecutePath);
				logger.info("Generated successfully the delete SSH script for agent" + agent.getAgentId() + ": " + scriptToExecutePath);
				//Fill the playbook with the agentId of the agent
				FileTextUtils.replaceTextInFile(scriptToExecutePath, jokerPlaybookTemplates, playbookPath);
				//Fill the playbook with the specific ansible config file of the agent
				FileTextUtils.replaceTextInFile(scriptToExecutePath, jokerCfgFileTemplates, ansibleCfgFilePath);
				//set the file as executable
				FileTextUtils.setAsExecutable(scriptToExecutePath);
				logger.info("Modified successfully the generated  delete SSH script for agent " + agent.getAgentId() + ". Ready to execute!");
				return scriptToExecutePath;
				
			} catch (IOException e) {
				logger.error("ERROR while triying to generate delete SSH script for agent " + agent.getAgentId() + " with execution date: " + executionDate);
				e.printStackTrace();
				return "";
			}				
			
		}
		return "";
	}
	
	public static String generateBeatsScript(String executionDate, AgentFull agent, String playbookPath, String ansibleCfgFilePath, String action) {
		if (action.equalsIgnoreCase(Dictionary.INSTALL)) {
			
			
			String scriptTemplatePath = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_EXECUTIONPATH) + 
					Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_INSTALL_LAUNCHER);
			String scriptToExecutePath = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_EXECUTIONPATH) + 
					Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_INSTALL_EXECUTION_LAUNCHER_PREFIX) + agent.getAgentId() +"-" + executionDate + ".sh";
			String jokerTemplates = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SCRIPT_JOKER_PLAYBOOK);
			
			try {
				//Generate the execution playbook
				FileTextUtils.copyFile(scriptTemplatePath, scriptToExecutePath);
				logger.info("Generated successfully the beats installation script for agent" + agent.getAgentId() + ": " + scriptToExecutePath);
				
				FileTextUtils.replaceTextInFile(scriptToExecutePath, jokerTemplates, playbookPath);
				//set the file as executable
				FileTextUtils.setAsExecutable(scriptToExecutePath);
				logger.info("Modified successfully the generated beats installation script for agent " + agent.getAgentId() + ". Ready to execute!");
				return scriptToExecutePath;
				
			} catch (IOException e) {
				logger.error("ERROR while triying to generate beats installation script for agent " + agent.getAgentId() + " with execution date: " + executionDate);
				e.printStackTrace();
				return "";
			}	
			
		}
		else if (action.equalsIgnoreCase(Dictionary.REMOVE)) {

			String scriptTemplatePath = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_EXECUTIONPATH) + 
					Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_REMOVE_LAUNCHER);
			String scriptToExecutePath = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_EXECUTIONPATH) + 
					Properties.getValue(Dictionary.PROPERTY_TEMPLATES_BEATS_REMOVE_EXECUTION_LAUNCHER_PREFIX) + agent.getAgentId() +"-" + executionDate + ".sh";
			String jokerTemplates = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SCRIPT_JOKER_PLAYBOOK);
			
			try {
				//Generate the execution playbook
				FileTextUtils.copyFile(scriptTemplatePath, scriptToExecutePath);
				logger.info("Generated successfully the beats remove script for agent" + agent.getAgentId() + ": " + scriptToExecutePath);
				
				FileTextUtils.replaceTextInFile(scriptToExecutePath, jokerTemplates, playbookPath);
				//set the file as executable
				FileTextUtils.setAsExecutable(scriptToExecutePath);
				logger.info("Modified successfully the generated beats remove script for agent " + agent.getAgentId() + ". Ready to execute!");
				return scriptToExecutePath;
				
			} catch (IOException e) {
				logger.error("ERROR while triying to generate beats remove script for agent " + agent.getAgentId() + " with execution date: " + executionDate);
				e.printStackTrace();
				return "";
			}	
			
		}
		return "";
			 
	}


	public static int executeScript(String type, String scriptPath, String executionDate, AgentFull agent) {
		// TODO Auto-generated method stub
		int resultCode = -1;
		String logFilePath = "";
		if (type.equalsIgnoreCase("ssh")) { 
			logFilePath = Properties.getValue(Dictionary.PROPERTY_EXECUTION_LOGS_PATH) + 
					Properties.getValue(Dictionary.PROPERTY_EXECUTION_LOGS_SSH_PREFIX) + 
					agent.getAgentId() +"_" + executionDate + ".log";
		}
		else if (type.equalsIgnoreCase("beats")) {
			logFilePath = Properties.getValue(Dictionary.PROPERTY_EXECUTION_LOGS_PATH) + 
					Properties.getValue(Dictionary.PROPERTY_EXECUTION_LOGS_BEATS_PREFIX) + 
					agent.getAgentId() +"_" + executionDate + ".log";
		}
		else {
			logger.error("ERROR: not recognized type (" + type + ") to execute the script " + scriptPath);
			return resultCode;
		}

		String s;
    	Process p;
    	try {
            p = Runtime.getRuntime().exec(scriptPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
      
            File logFile = new File(logFilePath);
            PrintWriter writer = new PrintWriter(logFile);
            
            while ((s = br.readLine()) != null){
            	logger.info(s);
            	writer.println(s);
            }                
            p.waitFor();
            resultCode = p.exitValue();
            logger.info("exit: " + resultCode);
            writer.println("Result code: " + resultCode);
            writer.close();
            p.destroy();
            return resultCode;
        } catch (Exception e) {
        	logger.error("ERROR: " + e.getLocalizedMessage());
        	logger.error(e.getStackTrace());
        	return resultCode;
        }		
	}
}
