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
import io.elastest.eim.database.AgentConfigurationControlRepository;
import io.elastest.eim.database.AgentConfigurationRepository;
import io.elastest.eim.database.AgentRepository;
import io.elastest.eim.templates.BeatsTemplateManager;
import io.elastest.eim.templates.BeatsTemplateManagerControl;
import io.elastest.eim.templates.SshTemplateManager;
import io.elastest.eim.utils.FileTextUtils;
import io.swagger.api.AgentApiService;
import io.swagger.api.ApiResponseMessage;
import io.swagger.api.NotFoundException;
import io.swagger.model.AgentConfiguration;
import io.swagger.model.AgentConfigurationControl;
import io.swagger.model.AgentDeleted;
import io.swagger.model.AgentFull;
import io.swagger.model.Host;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-15T11:10:32.030+02:00")
public class AgentApiServiceImpl extends AgentApiService {

	private static Logger logger = Logger.getLogger(AgentApiServiceImpl.class);

	private AgentRepository agentDb = new AgentRepository();
	private AgentConfigurationRepository agentCfgDb = new AgentConfigurationRepository();
	
	private AgentConfigurationControlRepository agentCfgControlDB = new AgentConfigurationControlRepository();
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
	
    @Override
    public Response deleteAgentByID(String agentId, SecurityContext securityContext) throws NotFoundException {
    	
    	logger.info("deleteAgentByID method invoked for agentId " + agentId);
    	System.out.println("deleteAgentByID method invoked for agentId " + agentId);
    	
    	//verify that agent exists
    	AgentFull agent = agentDb.getAgentByAgentId(agentId);
    	int status = 0;
    	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    	String executionDate = sdf.format(timestamp);
        if (agent != null){
    		if (agent.isMonitored() || agent.isCkecked()) {
    			//remove beats
    			status = -1;
	    		//beats uninstallation
	            BeatsTemplateManager beatsTemplateManager = new BeatsTemplateManager(agent, executionDate, Dictionary.REMOVE, getAnsibleCfgFilePathForAgent(agent));
	            logger.info("beatsTemplateManager" + beatsTemplateManager.toString());
	            System.out.println("beatsTemplateManager"+ beatsTemplateManager.toString());
	            
	            status = beatsTemplateManager.execute();
	            if (status == 0) {
	            	logger.info("Successful execution for the delete script generated to agent " + agent.getAgentId());
	            	
	            	//delete agent configuration
	            	boolean deleted = agentCfgDb.deleteAgentCfg(agentId);
	            	//delete agent configuration control
	            	boolean deleted_execbeat = agentCfgControlDB.deleteAgentCfg(agentId);
	            	
	        		if (deleted) {
	        			logger.info("Successful deleted from database agent_configuration table -->  agent " + agent.getAgentId());	        		
	        		}
	        		if (deleted_execbeat) {		
	        			logger.info("Successful deleted from database agent_configuration_control table -->  agent configuration" + agent.getAgentId());
	        		}
	        		else {
	        			logger.error("ERROR deleting agent configuration " + agent.getAgentId() + " from database");
	        			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Agent " + agentId + " cannot be deleted from database, check logs please")).build();
	        		}
	        	}
	            else {	            	
	            	logger.error("ERROR executing the beats script for agent " + agent.getAgentId() + ". Check logs please");
	            	return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Result of the execution has been: " + status + " " )).build();
	            }
    		}
    		/*else if(agent.isCkecked()) {
    			//remove beats
    			status = -1;
	    		//beats uninstallation
	            BeatsTemplateManager beatsTemplateManager = new BeatsTemplateManager(agent, executionDate, Dictionary.REMOVE, getAnsibleCfgFilePathForAgent(agent));
	            logger.info("beatsTemplateManager" + beatsTemplateManager.toString());
	            System.out.println("beatsTemplateManager"+ beatsTemplateManager.toString());
	            
	            status = beatsTemplateManager.execute();
	            if (status == 0) {
	            	logger.info("Successful execution for the delete script generated to agent " + agent.getAgentId());
	            	
	            	//delete agent configuration
	            	boolean deleted = agentCfgDb.deleteAgentCfg(agentId);
	            	//delete agent configuration control
	            	boolean deleted_execbeat = agentCfgControlDB.deleteAgentCfg(agentId);
	            	
	        		if (deleted) {
	        			logger.info("Successful deleted from database agent_configuration table -->  agent " + agent.getAgentId());	        		
	        		}
	        		if (deleted_execbeat) {		
	        			logger.info("Successful deleted from database agent_configuration_control table -->  agent configuration" + agent.getAgentId());
	        		}
	        		else {
	        			logger.error("ERROR deleting agent configuration " + agent.getAgentId() + " from database");
	        			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Agent " + agentId + " cannot be deleted from database, check logs please")).build();
	        		}
	        	}
	            else {	            	
	            	logger.error("ERROR executing the beats script for agent " + agent.getAgentId() + ". Check logs please");
	            	return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Result of the execution has been: " + status + " " )).build();
	            }
    		}*/
    		
    		//remove ssh key
    		if (status==0) {
    			String ansibleFileCfgPath = getAnsibleCfgFilePathForAgent(agent);
    			SshTemplateManager sshTemplateManager = new SshTemplateManager(agent, executionDate, ansibleFileCfgPath, Dictionary.REMOVE);
    			status = sshTemplateManager.execute();
    			if (status == 0) {
	            	logger.info("Successful execution for the ssh delete script generated to agent " + agent.getAgentId());
	            }
	            else {	            	
	            	logger.error("ERROR executing the delete ssh script for agent " + agent.getAgentId() + ". Check logs please");
	            	return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Result of the execution has been: " + status + " " )).build();
	            }
    		}
    
    		//Delete agent from db
    		boolean deleted = agentDb.deleteAgent(agentId);
        	boolean deleted_execbeat = agentCfgControlDB.deleteAgentCfg(agentId);

    		
    		if (deleted) {
    			logger.info("Successful deleted from database agent_configuration table -->  agent " + agent.getAgentId());
    			//return Response.ok().entity(agent).build();    		
    		}
    		if (deleted_execbeat) {
    			logger.info("Successful deleted from database agent_configuration_control table -->  agent " + agent.getAgentId());

    		}
    		
    		try {
    			//delete config files
    			File f = new File(getConfigDirForAgent(agent));
    			FileTextUtils.delete(f);
    			
    			//remove from /etc/ansible/hosts    		
				FileTextUtils.removeAgentFromAnsibleCfg("/etc/ansible/hosts", agent.getAgentId());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
		    	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Check logs please!")).build();
			}
    	
    		AgentDeleted agentDeleted = new AgentDeleted();
    		agentDeleted.setAgentId(agent.getAgentId());
    		agentDeleted.setDeleted("true");
    		
    		logger.info("deleteAgentByID method response: " + agentDeleted);
        	System.out.println("deleteAgentByID method response: " + agentDeleted);
        	
    		return Response.ok().entity(agentDeleted).build();
        }
        else {
        	logger.info("deleteAgentByID method response: No agent with id" + agentId + " exists in the system");
        	System.out.println("deleteAgentByID method response: No agent with id" + agentId + " exists in the system");
        	
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "No agent with id " + agentId + " exists in the system")).build();
        }
    }
    
    @Override
    public Response getAgentByID(String agentId, SecurityContext securityContext) throws NotFoundException {
    	logger.info("getAgentByID method invoked for agentId " + agentId);
    	System.out.println("getAgentByID method invoked for agentId " + agentId);
    	
    	AgentFull agent = agentDb.getAgentByAgentId(agentId);
        if (agent != null){
        	logger.info("getAgentByID method OK response: " + agent);
        	System.out.println("getAgentByID method OK response: " + agent);
        	
    		return Response.ok().entity(agent).build();
        }
        else {
        	
        	logger.info("getAgentByID method response: It has not been possible to retrieve agent with agentId " + agentId + " from DB , check logs please!");
        	System.out.println("getAgentByID method response: It has not been possible to retrieve agent with agentId " + agentId + " from DB , check logs please!");
 
        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "It has not been possible to retrieve agent with agentId " + agentId + " from DB , check logs please!")).build();
        }
    }
    
    
    @Override
    public Response getAllAgents(SecurityContext securityContext) throws NotFoundException {
    	logger.info("getAllAgents method invoked");
    	System.out.println("getAllAgents method invoked");
    	
    	List<AgentFull> agents = agentDb.findAll();
        if (agents != null){
        	logger.info("getAllAgents method OK response: " + agents);
        	System.out.println("getAllAgents method OK response: " + agents);
        	
    		return Response.ok().entity(agents).build();
        }
        else {
        	logger.info("getAgentByID method response: It has not been possible to retrieve agents from DB , check logs please!");
        	System.out.println("getAgentByID method response: It has not been possible to retrieve agents from DB , check logs please!");

        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "It has not been possible to retrieve info, check logs please!")).build();
        }
    }
    
    @Override
    public Response postAction(String agentId, String actionId, AgentConfiguration body, SecurityContext securityContext) throws NotFoundException {    	
    	
    	logger.info("PostAction method invoked for agent " + agentId + " with action " + actionId + " and body: " + body);
    	System.out.println("PostAction method invoked for agent " + agentId + " with action " + actionId + " and body: " + body);
    	
    	//set Dockerized default value if not specified
    	if (body.getDockerized() == null) {
    		body.setDockerized(Dictionary.DOCKERIZED_NO);
    	}
    	
    	if (actionId.equals(Dictionary.SUT_ACTION_MONITOR)){
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
	            BeatsTemplateManager beatsTemplateManager = new BeatsTemplateManager(agent, executionDate, Dictionary.INSTALL, getAnsibleCfgFilePathForAgent(agent));
	            beatsTemplateManager.setConfiguration(body);
	            status = beatsTemplateManager.execute();
	            if (status == 0) {
	            	logger.info("Successful execution for the beats script generated to agent " + agent.getAgentId());
	            	// store agent configuration in db
	            	agentCfgDb.addAgentCfg(agentId, body);
	            	//set host as monitored in db    	
		        	agent = agentDb.setMonitored(agentId, true);
		        	logger.info("iAgent " + agent.getAgentId() + " monitored succesfully");
		        	
		        	logger.info("postAction method with monitor param for agent " + agentId + " OK response: " + agent);
		        	System.out.println("postAction method with monitor param for agent " + agentId + " OK response: " + agent);
		          	
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
    public Response postControlAction(String agentId, String actionId, AgentConfigurationControl body, SecurityContext securityContext) throws NotFoundException {    	
		AgentFull agent = agentDb.getAgentByAgentId(agentId);

    	logger.info("PostAction method invoked for agent " + agentId + " with action " + actionId + " and body: " + body);
    	System.out.println("PostAction method invoked for agent " + agentId + " with action " + actionId + " and body: " + body);
    	
    	//set Dockerized default value if not specified
    	if (body.getDockerized() == null) {
    		body.setDockerized(Dictionary.DOCKERIZED_NO);
    	}
    	
    	
    	if (actionId.equals(Dictionary.SUT_ACTION_PACKETLOSS)){
	    	//verify that agent exists in database and it is not monitored
	    	if (agent == null) {
	    		//agent not exists in db
	    		logger.error("No exists any agent in the system with agentId " + agentId);
	    		return Response.status(Response.Status.NOT_FOUND).entity("No exists any agent in the system with agentId " + agentId).build();
	    	}
			/*
			 * logger.error("Agent " + agentId + " is already monitored"); return
			 * Response.status(Response.Status.NOT_ACCEPTABLE).entity("Agent " + agentId +
			 * " is already monitored").build();
			 */
    		//exits and it is not monitored --> launch process
    		int status = -1;
    		//beats installation
    		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String executionDate = sdf.format(timestamp);
            BeatsTemplateManagerControl beatsTemplateManager = new BeatsTemplateManagerControl(agent, executionDate, Dictionary.SUT_ACTION_PACKETLOSS, getAnsibleCfgFilePathForAgent(agent));
            logger.info("BeatsTemplateManagerControl" + beatsTemplateManager);
            logger.info("getAnsibleCfgFilePathForAgent(agent)" + getAnsibleCfgFilePathForAgent(agent));
            logger.info("beatsTemplateManagerControl is" + beatsTemplateManager);
            
            beatsTemplateManager.setConfigurationControl(body);
            status = beatsTemplateManager.execute();
            if (status == 0) {
            	logger.info("Successful execution for the beats script generated to agent " + agent.getAgentId());
            	// store agent configuration in db
            	logger.info("Store agent in db " + agentCfgControlDB.addAgentCfgControl(agentId, body));
            	agentCfgControlDB.addAgentCfgControl(agentId, body);
            	//set host as monitored in db    	
	        	agent = agentDb.setChecked(agentId, true);
	        	logger.info("iAgent " + agent.getAgentId() + " ckecked succesfully");
	        	
	        	logger.info("postAction method with check param for agent " + agentId + " OK response: " + agent);
	        	System.out.println("postAction method with check param for agent " + agentId + " OK response: " + agent);
	          	
	        	return Response.ok().entity(agent).build();
            }
            else {
            	logger.error("ERROR executing the beats script for agent " + agent.getAgentId() + ". Check logs please");
            	return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Result of the execution has been: " + status + " " )).build();
            }		
	    }
	    
	    else if (actionId.equals(Dictionary.SUT_ACTION_STRESS_CPU)) {
    		//exits and it is not monitored --> launch process
    		int status = -1;
    		//beats installation
    		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String executionDate = sdf.format(timestamp);
            BeatsTemplateManagerControl beatsTemplateManager = new BeatsTemplateManagerControl(agent, executionDate, Dictionary.SUT_ACTION_STRESS_CPU, getAnsibleCfgFilePathForAgent(agent));
            beatsTemplateManager.setConfigurationControl(body);
            status = beatsTemplateManager.execute();
            if (status == 0) {
            	logger.info("Successful execution for the beats script generated to agent " + agent.getAgentId());
            	// store agent configuration in db
            	
            	logger.info("Store agent in db " + agentCfgControlDB.addAgentCfgControl(agentId, body));
            	
            	agentCfgControlDB.addAgentCfgControl(agentId, body);
            	//set host as monitored in db    	
	        	agent = agentDb.setChecked(agentId, true);
	        	logger.info("iAgent " + agent.getAgentId() + " monitored succesfully");
	        	
	        	logger.info("postAction method with monitor param for agent " + agentId + " OK response: " + agent);
	        	System.out.println("postAction method with monitor param for agent " + agentId + " OK response: " + agent);
	          	
	        	return Response.ok().entity(agent).build();
            }
            else {
            	
            	logger.error("ERROR executing the beats script for agent " + agent.getAgentId() + ". Check logs please");
            	return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Result of the execution has been: " + status + " " )).build();
            }		
    	}
    	else if (actionId.equals(Dictionary.SUT_ACTION_STRESS_CPU) && agent.isMonitored()) {
    		int status = -1;
    		//beats installation
    		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String executionDate = sdf.format(timestamp);
            BeatsTemplateManagerControl beatsTemplateManager = new BeatsTemplateManagerControl(agent, executionDate, Dictionary.SUT_ACTION_STRESS_CPU, getAnsibleCfgFilePathForAgent(agent));
            logger.info("BeatsTemplateManagerControl" + beatsTemplateManager);
            logger.info("getAnsibleCfgFilePathForAgent(agent)" + getAnsibleCfgFilePathForAgent(agent));
            logger.info("beatsTemplateManagerControl is" + beatsTemplateManager);
            
            beatsTemplateManager.setConfigurationControl(body);
            status = beatsTemplateManager.execute();
            if (status == 0) {
            	logger.info("Successful execution for the beats script generated to agent " + agent.getAgentId());
            	// store agent configuration in db
            	logger.info("Store agent in db " + agentCfgControlDB.addAgentCfgControl(agentId, body));
            	agentCfgControlDB.addAgentCfgControl(agentId, body);
            	//set host as monitored in db    	
	        	agent = agentDb.setChecked(agentId, true);
	        	logger.info("iAgent " + agent.getAgentId() + " monitored succesfully");
	        	
	        	logger.info("postAction method with monitor param for agent " + agentId + " OK response: " + agent);
	        	System.out.println("postAction method with monitor param for agent " + agentId + " OK response: " + agent);
	          	
	        	return Response.ok().entity(agent).build();
            }
	    	else {
	    		
	    		return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "This method will execute the action " + actionId +  "!")).build();
	    	}
    	}
        else {
        	//exits and it is not monitored --> launch process
    		int status = -1;
    		//beats installation
    		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String executionDate = sdf.format(timestamp);
            BeatsTemplateManagerControl beatsTemplateManager = new BeatsTemplateManagerControl(agent, executionDate, Dictionary.SUT_ACTION_PACKETLOSS, getAnsibleCfgFilePathForAgent(agent));
            logger.info("BeatsTemplateManagerControl" + beatsTemplateManager);
            logger.info("getAnsibleCfgFilePathForAgent(agent)" + getAnsibleCfgFilePathForAgent(agent));
            logger.info("beatsTemplateManagerControl is" + beatsTemplateManager);
            
            beatsTemplateManager.setConfigurationControl(body);
            status = beatsTemplateManager.execute();
            if (status == 0) {
            	logger.info("Successful execution for the beats script generated to agent " + agent.getAgentId());
            	// store agent configuration in db
            	logger.info("Store agent in db " + agentCfgControlDB.addAgentCfgControl(agentId, body));
            	agentCfgControlDB.addAgentCfgControl(agentId, body);
            	//set host as checked in db    	
	        	agent = agentDb.setChecked(agentId, true);
	        	logger.info("iAgent " + agent.getAgentId() + " checked succesfully");
	        	
	        	logger.info("postAction method with check param for agent " + agentId + " OK response: " + agent);
	        	System.out.println("postAction method with check param for agent " + agentId + " OK response: " + agent);
	          	
	        	return Response.ok().entity(agent).build();
            }
            else {
            	
            	logger.error("ERROR executing the beats script for agent " + agent.getAgentId() + ". Check logs please");
            	return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Result of the execution has been: " + status + " " )).build();
            }
        }
    //logger.error("ERROR executing the beats script for agent " + agent.getAgentId() + ". Check logs please");
    //return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Result of the execution has been failure " )).build();
    
    }
    
    
    
    @Override
    public Response postAgent(Host body, SecurityContext securityContext) throws NotFoundException {
        
    	logger.info("PostAgent method invoked with body: " + body);
    	System.out.println("PostAgent method invoked with body: " + body);
    	
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
			        	FileWriter keyFileWriter = new FileWriter(privateKeyPath, false);
			        	BufferedWriter keyBufferedWriter = new BufferedWriter(keyFileWriter);
			        	PrintWriter keyPrintWriter = new PrintWriter(keyBufferedWriter);
			        	keyPrintWriter.println(body.getPrivateKey());
			        	keyPrintWriter.close();
			        	
			        	//set the privileges to the private key in order that the file is read only
			        	FileTextUtils.setAsReadOnly(privateKeyPath);
			        	
			        	//write ansible cfg file for the host
			        	String ansibleFileCfgPath = getAnsibleCfgFilePathForAgent(agent);
			        	FileWriter fileWriter = new FileWriter(ansibleFileCfgPath, false);
			        	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			        	PrintWriter printWriter = new PrintWriter(bufferedWriter);
			        	printWriter.println("[" + agent.getAgentId() + "]");
			        	printWriter.println(agent.getHost());
			        	printWriter.println("[" + agent.getAgentId() + ":vars]");
			        	printWriter.println("ansible_connection=ssh");
			        	printWriter.println("ansible_user=" + body.getUser());
			        	printWriter.println("ansible_become_pass=" + body.getPassword());
			        	printWriter.println("ansible_ssh_private_key_file=" + privateKeyPath);
			        	printWriter.println("ansible_ssh_extra_args=\"-o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null\"");
			        	printWriter.close();
			        		            
			            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			            String executionDate = sdf.format(timestamp);
			            SshTemplateManager sshTemplateManager = new SshTemplateManager(agent, executionDate, ansibleFileCfgPath, Dictionary.INSTALL);
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
				            
				            logger.info("PostAgent method OK response: " + agent);
				        	System.out.println("PostAgent method OK response: " + agent);
				        	
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
    
    private String getAnsibleCfgFilePathForAgent(AgentFull agent) {
    	//var/ansible/ssh/hosts/iagent01/host_iagent01_cfg"
    	String a  = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_EXECUTIONPATH) + 
    			Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_HOSTS_FOLDER) +
    			agent.getAgentId() + "/" + "host_" + agent.getAgentId() + "_cfg";
    	logger.info("getAnsibleCfgFilePathForAgent, class AgentApiServiceImpl"+a);
    	return a;
    }
    
    private String getConfigDirForAgent(AgentFull agent) {
    	String a = Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_EXECUTIONPATH) + 
    			Properties.getValue(Dictionary.PROPERTY_TEMPLATES_SSH_HOSTS_FOLDER) +
    			agent.getAgentId();
    	logger.info("getConfigDirForAgent, class AgentApiServiceImpl" + a);
    	
    	return a;
    }

	@Override
	public Response deleteAction(String agentId, String actionId, SecurityContext securityContext)
			throws NotFoundException {
		logger.info("deleteAction method invoked for agent " + agentId + " with action " + actionId);
    	System.out.println("deleteAction method invoked for agent " + agentId + " with action " + actionId);

    	if (actionId.equals(Dictionary.SUT_ACTION_UNMONITOR)){
    		//verify that agent exists in database and it is monitored
    		AgentFull agent = agentDb.getAgentByAgentId(agentId);
	    	if (agent == null) {
	    		//agent not exists in db
	    		logger.error("No exists any agent in the system with agentId " + agentId);
	    		return Response.status(Response.Status.NOT_FOUND).entity("No exists any agent in the system with agentId " + agentId).build();
	    	}
	    	else if (agent != null && !agent.isMonitored()) {
	    		logger.error("Agent " + agentId + " is not monitored");
	    		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Agent " + agentId + " is not monitored").build();
	    	}
	    	else {
	    		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	        	String executionDate = sdf.format(timestamp);
	    		int status = 0;
	    		BeatsTemplateManager beatsTemplateManager = new BeatsTemplateManager(agent, executionDate, Dictionary.REMOVE, getAnsibleCfgFilePathForAgent(agent));	            
	            status = beatsTemplateManager.execute();
	            if (status == 0) {
	            	logger.info("Successful execution for the delete script generated to agent " + agent.getAgentId());
	            	
	            	//delete agent configuration
	            	boolean cfgDeleted = agentCfgDb.deleteAgentCfg(agentId);
	            	if (cfgDeleted) {
	            		//set the agent in db as not monitored
		        		AgentFull agentNotMonitored = agentDb.setMonitored(agentId, false);
		        		if (agentNotMonitored == null) {
		        			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Agent " + agentId + " cannot be setted as not monitored in database, check logs please")).build();
		        		}
		        		else {
		        			logger.info("deleteAction method with unmonitor param for agent " + agentId + " OK response: " + agentNotMonitored);
				        	System.out.println("deleteAction method with unmonitor param for agent " + agentId + " OK response: " + agentNotMonitored);

		        			return Response.ok().entity(agentNotMonitored).build();
		        		}
	            	}
	            	else {
	            		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Agent " + agentId + " configuration cannot be deleted in database, check logs please")).build();
	            	}
	            }
	            else {	            	
	            	logger.error("ERROR executing the beats script for agent " + agent.getAgentId() + ". Check logs please");
	            	return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Result of the execution has been: " + status + " " )).build();
	            }
	    	}
    		
    	}else if (actionId.equals(Dictionary.SUT_ACTION_UNCHECKED)){
    		//verify that agent exists in database and it is monitored
    		AgentFull agent = agentDb.getAgentByAgentId(agentId);
	    	if (agent == null) {
	    		//agent not exists in db
	    		logger.error("No exists any agent in the system with agentId " + agentId);
	    		return Response.status(Response.Status.NOT_FOUND).entity("No exists any agent in the system with agentId " + agentId).build();
	    	}
	    	else if (agent != null && !agent.isCkecked()) {
	    		logger.error("Agent " + agentId + " is not checked");
	    		return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Agent " + agentId + " is not checked").build();
	    	}
	    	else {
	    		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	        	String executionDate = sdf.format(timestamp);
	    		int status = 0;
	    		BeatsTemplateManager beatsTemplateManager = new BeatsTemplateManager(agent, executionDate, Dictionary.REMOVE_CONTROL, getAnsibleCfgFilePathForAgent(agent));	            
	            status = beatsTemplateManager.execute();
	            if (status == 0) {
	            	logger.info("Successful execution for the delete script generated to agent " + agent.getAgentId());
	            	
	            	//delete agent configuration
	            	boolean cfgDeleted = agentCfgDb.deleteAgentCfg(agentId);
	            	if (cfgDeleted) {
	            		//set the agent in db as not monitored
		        		AgentFull agentNotChecked = agentDb.setChecked(agentId, false);
		        		if (agentNotChecked == null) {
		        			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Agent " + agentId + " cannot be setted as not monitored in database, check logs please")).build();
		        		}
		        		else {
		        			logger.info("deleteAction method with unchecked param for agent " + agentId + " OK response: " + agentNotChecked);
				        	System.out.println("deleteAction method with unchecked param for agent " + agentId + " OK response: " + agentNotChecked);

		        			return Response.ok().entity(agentNotChecked).build();
		        		}
	            	}
	            	else {
	            		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Agent " + agentId + " configuration cannot be deleted in database, check logs please")).build();
	            	}
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
}
