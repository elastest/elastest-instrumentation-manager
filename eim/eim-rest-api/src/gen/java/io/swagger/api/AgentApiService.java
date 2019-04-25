/**
 * Copyright (c) 2019 Atos
 * This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0
 * which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 *    @author David Rojo Antona (Atos)
 *    @author Fernando Mendez Requena (Atos Research Innovation Lab - Cloud Domain )
 *    
 * Developed in the context of ElasTest EU project http://elastest.io 
 */

 
package io.swagger.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.model.AgentConfiguration;
import io.swagger.model.AgentConfigurationControl;
import io.swagger.model.Host;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-15T11:10:32.030+02:00")
public abstract class AgentApiService {
    public abstract Response deleteAgentByID(String agentId,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getAgentByID(String agentId,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getAllAgents(SecurityContext securityContext) throws NotFoundException;
    public abstract Response postAction(String agentId,String actionId,AgentConfiguration body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response postControlAction(String agentId,String actionId,AgentConfigurationControl body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response postAgent(Host body,SecurityContext securityContext) throws NotFoundException;
	public abstract Response deleteAction(String agentId, String actionId, SecurityContext securityContext) throws NotFoundException;
}
