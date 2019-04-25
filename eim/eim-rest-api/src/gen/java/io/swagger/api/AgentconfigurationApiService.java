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

package io.swagger.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-11-28T00:11:53.581+01:00")
public abstract class AgentconfigurationApiService {
    public abstract Response getAgentCfgByID(String agentId,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getAllAgentConfigurations(SecurityContext securityContext) throws NotFoundException;
}
