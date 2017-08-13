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

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;

import io.swagger.api.AgentApiService;
import io.swagger.api.ApiResponseMessage;
import io.swagger.api.NotFoundException;
import io.swagger.model.Events1;


@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-06-23T09:46:23.390Z")
public class AgentApiServiceImpl extends AgentApiService {
	
	private static Logger logger = Logger.getLogger(AgentApiServiceImpl.class);
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");

	
    @Override
    public Response deleteAgent(String agentId, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "This method will delete the specified agent")).build();
    }
    @Override
    public Response getAgent(String agentId, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "This method will retrieve the specified agent")).build();
    }
    @Override
    public Response postAction(String agentId, String actionId, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
    	int resultCode = -1;
    	if (actionId.equals("monitor")){
    		String s;
        	Process p;
        	//String cmd = "ansible-playbook /var/ansible/beats/ansible-beats-master/playbook-beats-output-logstash.yml --extra-vars \"ansible_become_pass=elastest\"";
        	String cmd = "/var/ansible/beats/run-beats.sh";
        	System.out.println("Command to execute: " + cmd);
        	logger.info("Command to execute: " + cmd);
        	try {
                p = Runtime.getRuntime().exec(cmd);
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                File logFile = new File("/var/log/eim/beats_" + sdf.format(timestamp) + ".log");
                PrintWriter writer = new PrintWriter(logFile);
                
                while ((s = br.readLine()) != null){
                	System.out.println(s);
                	logger.info(s);
                	writer.println(s);
                }                
                p.waitFor();
                resultCode = p.exitValue();
                System.out.println ("exit: " + resultCode);
                logger.info("exit: " + resultCode);
                writer.println("Result code: " + resultCode);
                writer.close();
                p.destroy();
            } catch (Exception e) {
            	logger.error("ERROR: " + e.getLocalizedMessage());
            	logger.error(e.getStackTrace());
            	System.out.println("ERROR: " + e.getLocalizedMessage());
            	System.out.println(e.getStackTrace());
            	return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Result of the execution has been: " + resultCode + " " + e.getLocalizedMessage())).build();
            }
    		return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "Result of the execution has been: " + resultCode)).build();
    	}
    	else { 
    		return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "This method will execute the action " + actionId +  "!")).build();
    	}
    }
    @Override
    public Response postAgent(Events1 events, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
    	int resultCode = -1;
    	String s;
    	Process p;
    	try {
            p = Runtime.getRuntime().exec("/var/ansible/ssh/run-ssh.sh");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
           
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            File logFile = new File("/var/log/eim/ssh_" + sdf.format(timestamp) + ".log");
            PrintWriter writer = new PrintWriter(logFile);
            
            while ((s = br.readLine()) != null){
            	System.out.println(s);
            	logger.info(s);
            	writer.println(s);
            }                
            p.waitFor();
            resultCode = p.exitValue();
            System.out.println ("exit: " + resultCode);
            logger.info("exit: " + resultCode);
            writer.println("Result code: " + resultCode);
            writer.close();
            p.destroy();
        } catch (Exception e) {
        	logger.error("ERROR: " + e.getLocalizedMessage());
        	logger.error(e.getStackTrace());
        	System.out.println("ERROR: " + e.getLocalizedMessage());
        	System.out.println(e.getStackTrace());
        	return Response.serverError().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Result of the execution has been: " + resultCode + " " + e.getLocalizedMessage())).build();
        }
    	
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "Result of the execution has been: " + resultCode)).build();
    }
    @Override
    public Response putAgent(String agentId, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
    	
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "This method will update an existing agent!")).build();
    }
}
