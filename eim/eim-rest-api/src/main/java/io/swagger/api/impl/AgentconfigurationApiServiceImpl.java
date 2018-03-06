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

package io.swagger.api.impl;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;

import io.elastest.eim.database.AgentConfigurationRepository;
import io.swagger.api.AgentconfigurationApiService;
import io.swagger.api.ApiResponseMessage;
import io.swagger.api.NotFoundException;
import io.swagger.model.AgentConfigurationDatabase;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-11-28T00:11:53.581+01:00")
public class AgentconfigurationApiServiceImpl extends AgentconfigurationApiService {
   
	private static Logger logger = Logger.getLogger(AgentApiServiceImpl.class);

	private AgentConfigurationRepository agentCfgDb = new AgentConfigurationRepository();

	
	@Override
    public Response getAgentCfgByID(String agentId, SecurityContext securityContext) throws NotFoundException {
		logger.info("getAgentCfgByID method invoked for agentId " + agentId);
    	System.out.println("getAgentCfgByID method invoked for agentId " + agentId);
    	
		AgentConfigurationDatabase agent = agentCfgDb.getAgentConfigurationByAgentId(agentId);
        if (agent != null){
    		return Response.ok().entity(agent).build();
        }
        else {
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "it has not been possible to retrieve info, check logs please!")).build();
        }    
	}
	
	
    @Override
    public Response getAllAgentConfigurations(SecurityContext securityContext) throws NotFoundException {
    	logger.info("getAllAgentConfigurations method invoked");
    	System.out.println("getAllAgentConfigurations method invoked");
    	
    	List<AgentConfigurationDatabase> agents = agentCfgDb.findAll();
        if (agents != null){
    		return Response.ok().entity(agents).build();
        }
        else {
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "it has not been possible to retrieve info, check logs please!")).build();
        }
    }
    
    
}
