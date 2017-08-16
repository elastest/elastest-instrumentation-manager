package io.elastest.eim.templates;

import org.apache.log4j.Logger;

import io.elastest.eim.utils.TemplateUtils;
import io.swagger.model.AgentFull;

public class BeatsTemplateManager {

private static Logger logger = Logger.getLogger(BeatsTemplateManager.class);
	
	String executionDate = "";
	
	private AgentFull agent;
	
	public BeatsTemplateManager(AgentFull agent, String executionDate) {
		this.agent = agent;
		this.executionDate = executionDate;
	}
	
	public int execute() {
		logger.info("Preparing the execution of beat playbook for agent " + agent.getAgentId());
		//generate files for execution: playbook and script
		String generatedPlaybookPath = TemplateUtils.generatePlaybook("beats", executionDate, agent);
		if (generatedPlaybookPath != "") {
			String generatedScriptPath = TemplateUtils.generateScript("beats", executionDate, agent, generatedPlaybookPath);	
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
	
}
