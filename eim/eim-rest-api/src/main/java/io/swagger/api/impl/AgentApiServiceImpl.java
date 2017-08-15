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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;

import io.elastest.eim.database.AgentRepository;
import io.elastest.eim.utils.FileTextUtils;
import io.swagger.api.AgentApiService;
import io.swagger.api.ApiResponseMessage; 
import io.swagger.api.NotFoundException;
import io.swagger.model.AgentFull;
import io.swagger.model.Host;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-15T11:10:32.030+02:00")
public class AgentApiServiceImpl extends AgentApiService {

	private static Logger logger = Logger.getLogger(AgentApiServiceImpl.class);

	private AgentRepository agentDb = new AgentRepository();
	
    @Override
    public Response deleteAgentByID(String agentId, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response getAgentByID(String agentId, SecurityContext securityContext) throws NotFoundException {
    	AgentFull agent = agentDb.getAgentByIpAgentId(agentId);
        if (agent != null){
    		return Response.ok().entity(agent).build();
        }
        else {
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "not inserted, check logs please!")).build();
        }
//        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
    @Override
    public Response getAllAgents(SecurityContext securityContext) throws NotFoundException {
        // do some magic!
    	List<AgentFull> agents = agentDb.findAll();
        if (agents != null){
    		return Response.ok().entity(agents).build();
        }
        else {
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "not inserted, check logs please!")).build();
        }
    }
    @Override
    public Response postAction(String agentId, String actionId, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
    	
    	AgentFull agent = agentDb.setMonitored(agentId);
    	logger.info("iAgent " + agent.getAgentId() + " monitored succesfully");
        return Response.ok().entity(agent).build();
        
    }
    @Override
    public Response postAgent(Host body, SecurityContext securityContext) throws NotFoundException {
        // do some magic!    
    	if (agentDb.existHost(body.getAddress())){
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "not inserted! The host exists in database")).build();
    	} else {
			try {
				File f = new File("/etc/ansible/hosts");
				if (FileTextUtils.containsText(f, body.getAddress())){
					return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "not inserted! The host exists in ansible configuration hosts file")).build();
				}
				else {
					AgentFull agent = agentDb.addHost(body.getAddress());
			        if (agent != null){
			        	//add to ansible cfg file
			        	FileWriter fw = new FileWriter("/etc/ansible/hosts", true);
			            BufferedWriter bw = new BufferedWriter(fw);
			            PrintWriter out = new PrintWriter(bw);
			            //add to ansible cfg file
			            //	[agentId]
			            //	ipaddress
			            // to identify the host
			            out.println("[" + agent.getAgentId() + "]");
			            out.println(agent.getHost());
			            out.close();
			    		return Response.ok().entity(agent).build();
			        }
			        else {
			        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "not inserted, check logs please!")).build();
			        }
				}
			} catch ( IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e.getMessage());
		    	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Check logs please!")).build();
			}
    	}
    	
//    		return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
}
