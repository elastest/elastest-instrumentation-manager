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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;

import io.elastest.eim.config.Dictionary;
import io.elastest.eim.config.Properties;
import io.elastest.eim.database.AgentRepository;
import io.elastest.eim.templates.BeatsTemplateManager;
import io.elastest.eim.templates.SshTemplateManager;
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
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
	
    @Override
    public Response deleteAgentByID(String agentId, SecurityContext securityContext) throws NotFoundException {
        //verify that agent exists
    	AgentFull agent = agentDb.getAgentByAgentId(agentId);
        if (agent != null){
    		if (agent.isMonitored()) {
    			//remove beats
    			int status = -1;
	    		//beats installation
	    		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	            String executionDate = sdf.format(timestamp);
	            BeatsTemplateManager beatsTemplateManager = new BeatsTemplateManager(agent, executionDate, Dictionary.REMOVE);
	            
	            status = beatsTemplateManager.execute();
	            if (status == 0) {
	            	logger.info("Successful execution for the beats script generated to agent " + agent.getAgentId());
	            	//TODO remove ssh key
	            	//remove from database
	        		boolean deleted = agentDb.deleteAgent(agentId);
	        		if (deleted) {
	        			return Response.ok().entity(agent).build();
	        		}
	        		else {
	        			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Agent " + agentId + " cannot be deleted from database, check logs please")).build();
	        		}
	            }
	            else {
	            	
	            	logger.error("ERROR executing the beats script for agent " + agent.getAgentId() + ". Check logs please");
	            	return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Result of the execution has been: " + status + " " )).build();
	            }
    		}
    		
    		//remove ssh key
    		
    		//delete from db
    		boolean deleted = agentDb.deleteAgent(agentId);
    		if (deleted) {
    			return Response.ok().entity(agent).build();
    		}
    		else {
    			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Agent " + agentId + " cannot be deleted from database, check logs please")).build();
    		}
        }
        else {
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "No agent with id " + agentId + " exists in the system")).build();
        }
    }
    
    @Override
    public Response getAgentByID(String agentId, SecurityContext securityContext) throws NotFoundException {
    	AgentFull agent = agentDb.getAgentByAgentId(agentId);
        if (agent != null){
    		return Response.ok().entity(agent).build();
        }
        else {
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "it has not been possible to retrieve info, check logs please!")).build();
        }
    }
    
    
    @Override
    public Response getAllAgents(SecurityContext securityContext) throws NotFoundException {
    	List<AgentFull> agents = agentDb.findAll();
        if (agents != null){
    		return Response.ok().entity(agents).build();
        }
        else {
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "it has not been possible to retrieve info, check logs please!")).build();
        }
    }
    @Override
    public Response postAction(String agentId, String actionId, SecurityContext securityContext) throws NotFoundException {    	
    	
    	if (actionId.equals("monitor")){
	    	//verify that agent exists in database and it is not monitored
	    	AgentFull agent = agentDb.getAgentByAgentId(agentId);
	    	if (agent == null) {
	    		//agent not exists in db
	    		logger.error("No exists any agent in the system with agentId " + agentId);
	    		return Response.status(Response.Status.NOT_FOUND).entity("No exists any agent in the system with agentId " + agentId).build();
	    	}
	    	else if (agent != null && agent.isMonitored()) {
	    		logger.error("Agent " + agentId + " is already monitored");
	    		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Agent " + agentId + " is already monitored").build();
	    	}
	    	else {
	        	//exits and it is not monitored --> launch process
	    		
	    		int status = -1;
	    		//beats installation
	    		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	            String executionDate = sdf.format(timestamp);
	            BeatsTemplateManager beatsTemplateManager = new BeatsTemplateManager(agent, executionDate, Dictionary.INSTALL);
	            
	            status = beatsTemplateManager.execute();
	            if (status == 0) {
	            	logger.info("Successful execution for the beats script generated to agent " + agent.getAgentId());
	            	//set host as monitored in db    	
		        	agent = agentDb.setMonitored(agentId, true);
		        	logger.info("iAgent " + agent.getAgentId() + " monitored succesfully");
		          	return Response.ok().entity(agent).build();
	            }
	            else {
	            	
	            	logger.error("ERROR executing the beats script for agent " + agent.getAgentId() + ". Check logs please");
	            	return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Result of the execution has been: " + status + " " )).build();
	            }		
	    	}
    	} 
    	else {
    		return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "This method will execute the action " + actionId +  "!")).build();
    	}    	
        
    }
    
    
    @Override
    public Response postAgent(Host body, SecurityContext securityContext) throws NotFoundException {
        
    	
    	if (agentDb.existHost(body.getAddress())){
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "not inserted! The host exists in database")).build();
    	} else {
			try {
				File f = new File("/etc/ansible/hosts");
				if (FileTextUtils.containsText(f, body.getAddress())){
					return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "not inserted! The host exists in ansible configuration hosts file")).build();
				}
				else {
					int status = -1;
					AgentFull agent = agentDb.addHost(body);
			        if (agent != null){
			        	//create folder for host with the name of the agentId
			        	new File(Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_EXECUTIONPATH) + 
			        			Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_HOSTS_FOLDER) +
			        			agent.getAgentId()).mkdir();
			        	
			        	//write private key on a file
			        	String privateKeyFileName = "host_" + agent.getAgentId() + "_private_key";
			        	String privateKeyPath = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_EXECUTIONPATH) + 
			        			Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_HOSTS_FOLDER) +
			        			agent.getAgentId() + "/" + privateKeyFileName;
			        	FileWriter keyFileWriter = new FileWriter(privateKeyPath, true);
			        	BufferedWriter keyBufferedWriter = new BufferedWriter(keyFileWriter);
			        	PrintWriter keyPrintWriter = new PrintWriter(keyBufferedWriter);
			        	keyPrintWriter.println(body.getPrivateKey());
			        	keyPrintWriter.close();
			        	
			        	//set the privileges to the private key in order that the file is read only
			        	FileTextUtils.setAsReadOnly(privateKeyPath);
			        	
			        	//write ansible cfg file for the host
			        	String ansibleFileCfgPath = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_EXECUTIONPATH) + 
			        			Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_HOSTS_FOLDER) +
			        			agent.getAgentId() + "/" + "host_" + agent.getAgentId() + "_cfg";
			        	FileWriter fileWriter = new FileWriter(ansibleFileCfgPath, true);
			        	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			        	PrintWriter printWriter = new PrintWriter(bufferedWriter);
			        	printWriter.println("[" + agent.getAgentId() + "]");
			        	printWriter.println(agent.getHost());
			        	printWriter.println("[" + agent.getAgentId() + ":vars]");
			        	printWriter.println("ansible_connection=ssh");
			        	printWriter.println("ansible_user=" + body.getUser());
			        	printWriter.println("ansible_ssh_private_key_file=" + privateKeyPath);
			        	printWriter.println("ansible_ssh_extra_args=\"-o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null\"");
			        	printWriter.close();
			        		            
			            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			            String executionDate = sdf.format(timestamp);
			            SshTemplateManager sshTemplateManager = new SshTemplateManager(agent, executionDate, ansibleFileCfgPath, body.getUser(), Dictionary.INSTALL);
			            status = sshTemplateManager.execute();
			            if (status == 0) {
			            	logger.info("Successful execution for the script generated to agent " + agent.getAgentId());
			            	
			            	logger.info("Adding new host to general ansible config file: /etc/ansible/hosts");
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
				            logger.info("Added new host " + agent.getHost() + " to /etc/ansible/hosts");
				            
			            	return Response.ok().entity(agent).build();
			            	
			            }
			            else {
			            	//delete from DB
			            	agentDb.deleteAgent(agent.getAgentId());
			            	//remove from ansible.cfg file
			            	FileTextUtils.removeAgentFromAnsibleCfg("/etc/ansible/hosts", agent.getAgentId());
			            	logger.error("ERROR executing the script for agent " + agent.getAgentId() + ". Check logs please");
			            	return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Result of the execution has been: " + status + " " )).build();
			            }			    		
			        }
			        else {
			        	logger.error("ERROR adding the new agent to the database. Check logs please");
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
    }
    
}
