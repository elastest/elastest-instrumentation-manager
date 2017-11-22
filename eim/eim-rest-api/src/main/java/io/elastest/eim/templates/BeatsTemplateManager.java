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
import io.swagger.model.AgentConfiguration;
import io.swagger.model.AgentFull;

public class BeatsTemplateManager {

private static Logger logger = Logger.getLogger(BeatsTemplateManager.class);
	
	private String executionDate = "";
	private AgentFull agent;
	private String action;
	private AgentConfiguration agentCfg;
	
	public BeatsTemplateManager(AgentFull agent, String executionDate, String action) {
		this.agent = agent;
		this.executionDate = executionDate;
		this.action = action;
	}
	
	/**
	 * Only needed when the beats are going to be installed. It must be called before call execute method
	 * @param agentCfg
	 */
	public void setConfiguration(AgentConfiguration agentCfg) {
		this.agentCfg = agentCfg;
		System.out.println(this.agentCfg.toString());
		logger.info(this.agentCfg.toString());
	}
	
	public int execute() {
		
		if (action.equals(Dictionary.INSTALL)) {		
			logger.info("Preparing the execution of beats install playbook for agent " + agent.getAgentId());
			//generate files for execution: playbook and script
			//the fourth argument is the user, that is not used in this playbook. The agent is also registrated, so in this case,
			//elastest user is the one used.
			String generatedPlaybookPath = TemplateUtils.generateBeatsPlaybook(executionDate, agent, "", action, agentCfg);
			if (generatedPlaybookPath != "") {
				String generatedScriptPath = TemplateUtils.generateBeatsScript(executionDate, agent, generatedPlaybookPath, "", action);	
				if (generatedScriptPath != null) {
					//execute generated files
					return TemplateUtils.executeScript("beats", generatedScriptPath, executionDate, agent);
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
			logger.info("Preparing the execution of beats remove playbook for agent " + agent.getAgentId());
			String generatedPlaybookPath = TemplateUtils.generateBeatsPlaybook(executionDate, agent, "", action, null);
			if (generatedPlaybookPath != "") {
				String generatedScriptPath = TemplateUtils.generateBeatsScript(executionDate, agent, generatedPlaybookPath, "", action);	
				if (generatedScriptPath != null) {
					//execute generated files
					return TemplateUtils.executeScript("beats", generatedScriptPath, executionDate, agent);
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
		}	
		return -1;
	}
	
	
}
