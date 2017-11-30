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
 * OpenAPI spec version: 1.0.0
 * 
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 * 
 * Developed in the context of ElasTest EU project http://elastest.io 
 */

package io.swagger.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.swagger.model.AgentConfiguration;
import io.swagger.model.AgentConfigurationDatabase;

public class AgentConfigurationDatabaseTest {

	@Test
	public void pojoTest() {
		AgentConfigurationDatabase agentCfgDb = new AgentConfigurationDatabase();
		agentCfgDb.setAgentId("iagent1");
		AgentConfiguration agentCfg = new AgentConfiguration();
		agentCfgDb.setAgentConfiguration(agentCfg);
		
		assertEquals("iagent1", agentCfgDb.getAgentId());
		assertEquals(agentCfg, agentCfgDb.getAgentConfiguration());
	}
}
