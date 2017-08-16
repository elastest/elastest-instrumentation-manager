package io.elastest.eim.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import io.elastest.eim.config.Properties;
import io.swagger.model.AgentFull;

public class TemplateUtils {

	private static Logger logger = Logger.getLogger(TemplateUtils.class);
	
	public static String generatePlaybook(String type, String executionDate, AgentFull agent) {
		if (type.equalsIgnoreCase("ssh")) {
			
			String playbookTemplatePath = Properties.getValue("templates.ssh.executionPath") + 
					Properties.getValue("templates.ssh.playbook");
			String playbookToExecutePath = Properties.getValue("templates.ssh.executionPath") + 
					Properties.getValue("templates.ssh.execution_playbook_prefix") + agent.getAgentId() + 
					"-" + executionDate + ".yml";
			String jokerTemplates = Properties.getValue("templates.playbook.joker");
			
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
		else if (type.equalsIgnoreCase("beats")) {			

			String playbookTemplatePath = Properties.getValue("templates.beats.playbookPath") + 
					Properties.getValue("templates.beats.playbook");
			String playbookToExecutePath = Properties.getValue("templates.beats.playbookPath") + 
					Properties.getValue("templates.beats.execution_playbook_prefix") + agent.getAgentId() + 
					"-" + executionDate + ".yml";
			String jokerTemplates = Properties.getValue("templates.playbook.joker");
			
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
	
	
	public static String generateScript(String type, String executionDate, AgentFull agent, String playbookPath) {
		if (type.equalsIgnoreCase("ssh")) {
			
			String scriptTemplatePath = Properties.getValue("templates.ssh.executionPath") + 
					Properties.getValue("templates.ssh.launcher");
			String scriptToExecutePath = Properties.getValue("templates.ssh.executionPath") + 
					Properties.getValue("templates.ssh.execution_launcher_prefix") + agent.getAgentId() +"-" + executionDate + ".sh";
			String jokerTemplates = Properties.getValue("templates.script.joker");
			
			try {
				//Generate the execution playbook
				FileTextUtils.copyFile(scriptTemplatePath, scriptToExecutePath);
				logger.info("Generated successfully the SSH script for agent" + agent.getAgentId() + ": " + scriptToExecutePath);
				//Fill the playbook with the agentId of the agent
				FileTextUtils.replaceTextInFile(scriptToExecutePath, jokerTemplates, playbookPath);
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
		else if (type.equalsIgnoreCase("beats")) {
			
			
			String scriptTemplatePath = Properties.getValue("templates.beats.executionPath") + 
					Properties.getValue("templates.beats.launcher");
			String scriptToExecutePath = Properties.getValue("templates.beats.executionPath") + 
					Properties.getValue("templates.beats.execution_launcher_prefix") + agent.getAgentId() +"-" + executionDate + ".sh";
			String jokerTemplates = Properties.getValue("templates.script.joker");
			
			try {
				//Generate the execution playbook
				FileTextUtils.copyFile(scriptTemplatePath, scriptToExecutePath);
				logger.info("Generated successfully the Beats script for agent" + agent.getAgentId() + ": " + scriptToExecutePath);
				//Fill the playbook with the agentId of the agent and the command necessary to be able to execute the playbook
				FileTextUtils.replaceTextInFile(scriptToExecutePath, jokerTemplates, playbookPath + 
						"  --extra-vars \"ansible_become_pass= " +  Properties.getValue("user.elastest.password")+"\"");
				//set the file as executable
				FileTextUtils.setAsExecutable(scriptToExecutePath);
				logger.info("Modified successfully the generated Beats script for agent " + agent.getAgentId() + ". Ready to execute!");
				return scriptToExecutePath;
				
			} catch (IOException e) {
				logger.error("ERROR while triying to generate Beats script for agent " + agent.getAgentId() + " with execution date: " + executionDate);
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
			logFilePath = Properties.getValue("execution.logs.path") + 
					Properties.getValue("execution.logs.ssh.prefix") + 
					agent.getAgentId() +"_" + executionDate + ".log";
		}
		else if (type.equalsIgnoreCase("beats")) {
			logFilePath = Properties.getValue("execution.logs.path") + 
					Properties.getValue("execution.logs.beats.prefix") + 
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
