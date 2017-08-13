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

import io.swagger.model.*;
import io.swagger.api.AgentApiService;
import io.swagger.api.factories.AgentApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import io.swagger.model.Events1;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;

@Path("/agent")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@io.swagger.annotations.Api(description = "the agent API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-06-23T09:46:23.390Z")
public class AgentApi  {
   private final AgentApiService delegate = AgentApiServiceFactory.getAgentApi();

    @DELETE
    @Path("/{agentId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Delete a agent", notes = "A client delete a agent", response = void.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Agent successfully deleted", response = void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "Unexpected error", response = void.class) })
    public Response deleteAgent(@ApiParam(value = "Id of agent to delete",required=true) @PathParam("agentId") String agentId
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.deleteAgent(agentId,securityContext);
    }
    @GET
    @Path("/{agentId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Retrieve a agent", notes = "A client retrieves a agent", response = void.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Agent info retrieved successfully", response = void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "Unexpected error", response = void.class) })
    public Response getAgent(@ApiParam(value = "Id of agent to return",required=true) @PathParam("agentId") String agentId
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getAgent(agentId,securityContext);
    }
    @POST
    @Path("/{agentId}/{actionId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Submit an action agent", notes = "A client submit an action to agent", response = void.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Action sent retrieved successfully", response = void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "Unexpected error", response = void.class) })
    public Response postAction(@ApiParam(value = "Id of agent to return",required=true) @PathParam("agentId") String agentId
,@ApiParam(value = "Id of action to apply",required=true) @PathParam("actionId") String actionId
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.postAction(agentId,actionId,securityContext);
    }
    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Register an agent", notes = "A client registers an agent", response = void.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Agent created successfully", response = void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "Unexpected error", response = void.class) })
    public Response postAgent(@ApiParam(value = "Events to be published" ,required=true) Events1 events
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.postAgent(events,securityContext);
    }
    @PUT
    @Path("/{agentId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "update a agent", notes = "A client updates a agent", response = void.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Information successfully retrieved", response = void.class),
        
        @io.swagger.annotations.ApiResponse(code = 200, message = "Unexpected error", response = void.class) })
    public Response putAgent(@ApiParam(value = "Id of agent to return",required=true) @PathParam("agentId") String agentId
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.putAgent(agentId,securityContext);
    }
}
