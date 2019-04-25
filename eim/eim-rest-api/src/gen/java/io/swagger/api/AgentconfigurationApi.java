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

import javax.servlet.ServletConfig;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.annotations.ApiParam;
import io.swagger.api.factories.AgentconfigurationApiServiceFactory;
import io.swagger.model.AgentConfigurationDatabase;

@Path("/agentconfiguration")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@io.swagger.annotations.Api(description = "the agentconfiguration API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-11-28T00:11:53.581+01:00")
public class AgentconfigurationApi  {
   private final AgentconfigurationApiService delegate;

   public AgentconfigurationApi(@Context ServletConfig servletContext) {
      AgentconfigurationApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("AgentconfigurationApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (AgentconfigurationApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = AgentconfigurationApiServiceFactory.getAgentconfigurationApi();
      }

      this.delegate = delegate;
   }

    @GET
    @Path("/{agentId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Retrieve an agent configuration", notes = "Returns the agent configuration with the given ID. Returns all its details.", response = AgentConfigurationDatabase.class, tags={ "AgentConfiguration", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful operation", response = AgentConfigurationDatabase.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = AgentConfigurationDatabase.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = AgentConfigurationDatabase.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Network not found", response = AgentConfigurationDatabase.class) })
    public Response getAgentCfgByID(@ApiParam(value = "ID of agent",required=true) @PathParam("agentId") String agentId
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getAgentCfgByID(agentId,securityContext);
    }
    @GET
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Returns all existing agent configurations", notes = "Returns all agent configurations with all details", response = AgentConfigurationDatabase.class, responseContainer = "List", tags={ "AgentConfiguration", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful operation", response = AgentConfigurationDatabase.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = AgentConfigurationDatabase.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = AgentConfigurationDatabase.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found", response = AgentConfigurationDatabase.class, responseContainer = "List") })
    public Response getAllAgentConfigurations(@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getAllAgentConfigurations(securityContext);
    }
}
