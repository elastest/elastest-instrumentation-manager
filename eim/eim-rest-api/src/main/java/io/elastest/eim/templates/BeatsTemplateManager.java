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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import io.elastest.eim.config.Dictionary;
import io.elastest.eim.database.AgentConfigurationControlRepository;
import io.elastest.eim.database.mysql.EimDbAgentCfgControlManager;
import io.elastest.eim.utils.TemplateUtils;
import io.swagger.model.AgentConfiguration;
import io.swagger.model.AgentFull;

public class BeatsTemplateManager {

	private static Logger logger = Logger.getLogger(BeatsTemplateManager.class);

	private String executionDate = "";
	private AgentFull agent;
	private String action;
	private AgentConfiguration agentCfg;
	private AgentConfigurationControlRepository generateBeatsScriptRemovePacketLossCommand;
	private String cfgFilePath = "";

	public BeatsTemplateManager(AgentFull agent, String executionDate, String action, String cfgFilePath) {
		this.agent = agent;
		this.executionDate = executionDate;
		this.action = action;
		this.cfgFilePath = cfgFilePath;
	}

	/**
	 * Only needed when the beats are going to be installed. It must be called
	 * before call execute method
	 * 
	 * @param agentCfg
	 */
	public void setConfiguration(AgentConfiguration agentCfg) {
		this.agentCfg = agentCfg;
		System.out.println(this.agentCfg.toString());
		logger.info(this.agentCfg.toString());
	}
	
	private List<String> setDefaultIptablesRules(){
		
		List<String> defaultIptablesRules = new ArrayList<String>();
		/*String a = "-P INPUT ACCEPT";
		String b = "-P FORWARD ACCEPT";
		String c = "-P OUTPUT ACCEPT";
		String d = "-t nat -F";
		String e = "-t mangle -F";
		String f = "iptables -F";
		String g = "iptables -X";*/
		String f = "iptables -F";
		
		/*defaultIptablesRules.add(a);
		defaultIptablesRules.add(b);
		defaultIptablesRules.add(c);
		defaultIptablesRules.add(d);
		defaultIptablesRules.add(e);
		defaultIptablesRules.add(f);
		defaultIptablesRules.add(g);*/
		
		defaultIptablesRules.add(f);

		
		return defaultIptablesRules;
		
	}

	private List<String> explorePacketLossCommand() {
		Connection conn = null;
		List<String> packetLossValues = new ArrayList<String>();

		PreparedStatement pstSelectPacketLoss = null;
		EimDbAgentCfgControlManager aux = new EimDbAgentCfgControlManager();

		try {
			conn = aux.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			String sql = "SELECT PACKETLOSS FROM " + Dictionary.DBTABLE_AGENT_CONFIGURATION_CONTROL
					+ " WHERE AGENT_ID = ?";
			logger.info("Sql is: " + sql);
			System.out.println("SQL is: " + sql);

			pstSelectPacketLoss = conn.prepareStatement(sql);
			pstSelectPacketLoss.setString(1, agent.getAgentId());

			logger.info("SQL is: " + sql);
			System.out.println("SQL is: " + sql);
			ResultSet rs = pstSelectPacketLoss.executeQuery();

			while (rs.next()) {
				logger.info("PACKETLOSS property for agentId: " + agent.getAgentId());
				System.out.println("PACKETLOSS property for agentId: " + agent.getAgentId());

				String value = rs.getString(rs.getRow()).replaceAll("-A", "");

				packetLossValues.add(value);
				logger.info("Packetloss captured from BBDD: " + value);
				System.out.println("Packetloss captured from BBDD: " + value);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (pstSelectPacketLoss != null)
				try {
					pstSelectPacketLoss.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return packetLossValues;
	}

	public int execute() {

		if (action.equals(Dictionary.INSTALL)) {
			logger.info("Preparing the execution of beats install playbook for agent " + agent.getAgentId());
			// generate files for execution: playbook and script
			// the fourth argument is the user, that is not used in this playbook. The agent
			// is also registrated, so in this case,
			// elastest user is the one used.
			String generatedPlaybookPath = TemplateUtils.generateBeatsPlaybook(executionDate, agent, action, agentCfg);
			if (generatedPlaybookPath != "") {
				String generatedScriptPath = TemplateUtils.generateBeatsScript(executionDate, agent,
						generatedPlaybookPath, cfgFilePath, action);
				if (generatedScriptPath != null) {
					// execute generated files
					return TemplateUtils.executeScript("beats", generatedScriptPath, executionDate, agent);
				} else {
					logger.error("ERROR generating script for execution for agent " + agent.getAgentId()
							+ ". Check logs please");
					return -1;
				}
			} else {
				logger.error("ERROR generating playbook for execution for agent " + agent.getAgentId()
						+ ". Check logs please");
				return -1;
			}
			// TODO move template to history execution path
		} else if (action.equals(Dictionary.REMOVE)) {

			List<String> packetLossValues = explorePacketLossCommand();

			if (packetLossValues != null) {
				String generatedPlaybookPath = TemplateUtils.generateBeatsPlaybookRemovePacketLossCommands(
						executionDate, agent, action, packetLossValues, null);
				if (generatedPlaybookPath != "") {
					String generatedScriptPath = TemplateUtils.generateBeatsScript(executionDate, agent,
							generatedPlaybookPath, cfgFilePath, action);
					return TemplateUtils.executeScript("beats", generatedScriptPath, executionDate, agent);
				}
				logger.error("ERROR generating script for execution for agent " + agent.getAgentId()
						+ ". Check logs please");
				return -1;
			}

			else {
				logger.info("Preparing the execution of beats remove playbook for agent " + agent.getAgentId());

				String generatedPlaybookPath = TemplateUtils.generateBeatsPlaybook(executionDate, agent, action, null);

				if (generatedPlaybookPath != "") {
					String generatedScriptPath = TemplateUtils.generateBeatsScript(executionDate, agent,
							generatedPlaybookPath, cfgFilePath, action);
					if (generatedScriptPath != null) {
						// execute generated files
						return TemplateUtils.executeScript("beats", generatedScriptPath, executionDate, agent);
					} else {
						logger.error("ERROR generating script for execution for agent " + agent.getAgentId()
								+ ". Check logs please");
						return -1;
					}
				}
			}
		}
		else if(action.equals(Dictionary.REMOVE_CONTROL)) {
			
			List<String> setDefaultIptablesRules = setDefaultIptablesRules();
			
			if(!setDefaultIptablesRules.isEmpty()) {
				
			String generatedPlaybookPath = TemplateUtils.generateBeatsPlaybookRemoveRestoreIptableCommands(
					executionDate, agent, action, setDefaultIptablesRules, null);
			if (generatedPlaybookPath != "") {
				String generatedScriptPath = TemplateUtils.generateBeatsScript(executionDate, agent,
						generatedPlaybookPath, cfgFilePath, action);
				return TemplateUtils.executeScript("beats", generatedScriptPath, executionDate, agent);
			}
			logger.error("ERROR generating script for execution for agent " + agent.getAgentId()
					+ ". Check logs please");
			return -1;
			
		}
	   }else {
			logger.info("Preparing the execution of beats remove playbook for agent " + agent.getAgentId());

			String generatedPlaybookPath = TemplateUtils.generateBeatsPlaybook(executionDate, agent, action, null);

			if (generatedPlaybookPath != "") {
				String generatedScriptPath = TemplateUtils.generateBeatsScript(executionDate, agent,
						generatedPlaybookPath, cfgFilePath, action);
				if (generatedScriptPath != null) {
					// execute generated files
					return TemplateUtils.executeScript("beats", generatedScriptPath, executionDate, agent);
				} else {
					logger.error("ERROR generating script for execution for agent " + agent.getAgentId()
							+ ". Check logs please");
					return -1;
				}
			}
		}
		return -1;
	}
}
