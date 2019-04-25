package io.elastest.eim.templates;

import org.apache.log4j.Logger;

import io.elastest.eim.config.Dictionary;
import io.elastest.eim.utils.TemplateUtils;
import io.swagger.model.AgentConfiguration;
import io.swagger.model.AgentConfigurationControl;
import io.swagger.model.AgentFull;

public class BeatsTemplateManagerControl {
	
private static Logger logger = Logger.getLogger(BeatsTemplateManager.class);
	
	private String executionDate = "";
	private AgentFull agent;
	private String action;
	private AgentConfigurationControl agentCfg;
	private String cfgFilePath = "";
	
	public  BeatsTemplateManagerControl(AgentFull agent, String executionDate, String action, String cfgFilePath) {
		this.agent = agent;
		this.executionDate = executionDate;
		this.action = action;
		this.cfgFilePath = cfgFilePath;
	}
	
	/**
	 * Only needed when the beats are going to be installed. It must be called before call execute method
	 * @param agentCfg
	 */
	public void setConfigurationControl(AgentConfigurationControl agentCfg) {
//		logger.info(this.agentCfg.toString());
		logger.info("Antes de pintar agentCFG");
		this.agentCfg = agentCfg;
		
		System.out.println(this.agentCfg.toString());
		logger.info(this.agentCfg.toString());
	}
	
	public int execute() {
		logger.info("Currently action is :"+ action);
		
		if (action.equals(Dictionary.SUT_ACTION_PACKETLOSS)) {		
			logger.info("Preparing the execution of beats install playbook for agent " + agent.getAgentId());
			//generate files for execution: playbook and script
			//the fourth argument is the user, that is not used in this playbook. The agent is also registrated, so in this case,
			//elastest user is the one used.
			String generatedPlaybookPath = TemplateUtils.generatesBeatsPlaybookPacketLossCommand(executionDate, agent, action, agentCfg);
			if (generatedPlaybookPath != "") {
				String generatedScriptPath = TemplateUtils.generateBeatsScript(executionDate, agent, generatedPlaybookPath, cfgFilePath, action);	
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
		else if (action.equals(Dictionary.SUT_ACTION_STRESS_CPU)) {
			logger.info("Preparing the execution of beats remove playbook for agent " + agent.getAgentId());
			String generatedPlaybookPath = TemplateUtils.generatesBeatsPlaybookStressCommand(executionDate, agent, action, agentCfg);
			if (generatedPlaybookPath != "") {
				String generatedScriptPath = TemplateUtils.generateBeatsScript(executionDate, agent, generatedPlaybookPath, cfgFilePath, action);
				logger.info("generated_playbook_path is: "+ generatedPlaybookPath);
				logger.info("generated_script_path is:" + generatedScriptPath);
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
