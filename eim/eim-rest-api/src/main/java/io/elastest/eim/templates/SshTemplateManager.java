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


package io.elastest.eim.templates;

import org.apache.log4j.Logger;

import io.elastest.eim.config.Dictionary;
import io.elastest.eim.utils.TemplateUtils;
import io.swagger.model.AgentFull;

public class SshTemplateManager {

	private static Logger logger = Logger.getLogger(SshTemplateManager.class);
	
	private String executionDate = "";
	private AgentFull agent;
	private String cfgFilePath = "";
	private String action = "";
	
	public SshTemplateManager(AgentFull agent, String executionDate, String cfgFilePath, String action) {
		this.agent = agent;
		this.executionDate = executionDate;
		this.cfgFilePath = cfgFilePath;
		this.action = action;
	}
	
	public int execute() {
		if (action.equals(Dictionary.INSTALL)) {
			logger.info("Preparing the execution of SSH register playbook for agent " + agent.getAgentId());
			//generate files for execution: playbook and script
			String generatedPlaybookPath = TemplateUtils.generateSshPlaybook(executionDate, agent, action);
			if (generatedPlaybookPath != "") {
				String generatedScriptPath = TemplateUtils.generateSshScript(executionDate, agent, generatedPlaybookPath, cfgFilePath, action);	
				if (generatedScriptPath != null) {
					//execute generated files
					return TemplateUtils.executeScript("ssh", generatedScriptPath, executionDate, agent);
				}
				else {
					logger.error("ERROR generating script for execution for agent " + agent.getAgentId( )+ ". Check logs please");
					return -1;
				}
			}
			else {
				logger.error("ERROR generating playbook for execution for agent " + agent.getAgentId( )+ ". Check logs please");
				return -1;
			}		
			//TODO move template to history execution path			
		}
		else if (action.equals(Dictionary.REMOVE)) {
			logger.info("Preparing the execution of SSH delete playbook for agent " + agent.getAgentId());
			//generate files for execution: playbook and script
			String generatedPlaybookPath = TemplateUtils.generateSshPlaybook(executionDate, agent, action);
			if (generatedPlaybookPath != "") {
				String generatedScriptPath = TemplateUtils.generateSshScript(executionDate, agent, generatedPlaybookPath, cfgFilePath, action);	
				if (generatedScriptPath != null) {
					//execute generated files
					return TemplateUtils.executeScript("ssh", generatedScriptPath, executionDate, agent);
				}
				else {
					logger.error("ERROR generating script for delete execution for agent " + agent.getAgentId( )+ ". Check logs please");
					return -1;
				}
			}
			else {
				logger.error("ERROR generating playbook for delete execution for agent " + agent.getAgentId( )+ ". Check logs please");
				return -1;
			}
		}
		return -1;

	}
	
	
	
	
	
}
