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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.annotations.ApiParam;
import io.swagger.api.factories.AgentApiServiceFactory;
import io.swagger.model.Agent;
import io.swagger.model.AgentConfiguration;
import io.swagger.model.AgentConfigurationControl;
import io.swagger.model.AgentFull;
import io.swagger.model.Host;

@Path("/agent")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@io.swagger.annotations.Api(description = "the agent API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-15T11:10:32.030+02:00")
public class AgentApi  {
   private final AgentApiService delegate;

   public AgentApi(@Context ServletConfig servletContext) {
      AgentApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("AgentApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (AgentApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = AgentApiServiceFactory.getAgentApi();
      }

      this.delegate = delegate;
   }

    @DELETE
    @Path("/{agentId}")
    @Consumes({ "application/json" })
    @Produces({ "*/*" })
    @io.swagger.annotations.ApiOperation(value = "Delete a agent", notes = "A client delete a agent", response = String.class, tags={ "Agent", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Network deleted successfully", response = String.class),
        
        @io.swagger.annotations.ApiResponse(code = 204, message = "No Content", response = String.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request", response = String.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = String.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = String.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Network not found", response = String.class) })
    public Response deleteAgentByID(@ApiParam(value = "Id of agent to delete",required=true) @PathParam("agentId") String agentId
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.deleteAgentByID(agentId,securityContext);
    }
    @GET
    @Path("/{agentId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Retrieve a agent", notes = "Returns the agent with the given ID. Returns all its details.", response = AgentFull.class, tags={ "Agent", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful operation", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Network not found", response = AgentFull.class) })
    public Response getAgentByID(@ApiParam(value = "ID of agent",required=true) @PathParam("agentId") String agentId
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getAgentByID(agentId,securityContext);
    }
    @GET
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Returns all existing agents", notes = "Returns all agents with all details", response = AgentFull.class, tags={ "Agent", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successful operation", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found", response = AgentFull.class) })
    public Response getAllAgents(@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getAllAgents(securityContext);
    }
    @POST
    @Path("/{agentId}/{actionId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Submit an action to an agent", notes = "A client submit an action to an agent", response = AgentFull.class, tags={ "Agent", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Action applied OK", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 204, message = "No Content", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Agent not found", response = AgentFull.class) })
    public Response postAction(@ApiParam(value = "Id of agent to that receives the action",required=true) @PathParam("agentId") String agentId
,@ApiParam(value = "action to apply",required=true) @PathParam("actionId") String actionId
,@ApiParam(value = "Configuration of the agent" ,required=true) AgentConfiguration body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.postAction(agentId,actionId,body,securityContext);
    }
    
    @POST
    @Path("/controllability/{agentId}/{actionId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Submit an action to an agent", notes = "A client submit an action to an agent", response = AgentFull.class, tags={ "Agent", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Action applied OK", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 204, message = "No Content", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Agent not found", response = AgentFull.class) })
    public Response postControlAction(@ApiParam(value = "Id of agent to that receives the action",required=true) @PathParam("agentId") String agentId
,@ApiParam(value = "action to apply",required=true) @PathParam("actionId") String actionId
,@ApiParam(value = "Configuration of the agent" ,required=true) AgentConfigurationControl body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.postControlAction(agentId,actionId,body,securityContext);
    }
    
    
    
    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Register an agent", notes = "A client registers an agent", response = Agent.class, tags={ "Agent", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Agent creation OK", response = Agent.class),
        
        @io.swagger.annotations.ApiResponse(code = 201, message = "Created", response = Agent.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request", response = Agent.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Agent.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = Agent.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Not Found", response = Agent.class) })
    public Response postAgent(@ApiParam(value = "Definition of an agent that is going to be created" ,required=true) Host body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.postAgent(body,securityContext);
    }
    
    @DELETE
    @Path("/{agentId}/{actionId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Submit a delete action to agent", notes = "A client submit a delete action to an agent", response = AgentFull.class, tags={ "Agent", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Action applied OK", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 204, message = "No Content", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 403, message = "Forbidden", response = AgentFull.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Agent not found", response = String.class) })
    public Response deleteAction(@ApiParam(value = "Id of agent to that receives the action",required=true) @PathParam("agentId") String agentId
    		,@ApiParam(value = "action to apply",required=true) @PathParam("actionId") String actionId
    		,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.deleteAction(agentId,actionId,securityContext);
    }
}
