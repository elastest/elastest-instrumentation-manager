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

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;

import io.elastest.eim.config.Properties;
import io.swagger.api.ApiResponseMessage;
import io.swagger.api.NotFoundException;
import io.swagger.api.PublickeyApiService;
import io.swagger.model.Publickey; 
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-08-15T11:10:32.030+02:00")
public class PublickeyApiServiceImpl extends PublickeyApiService {
   
	private static Logger logger = Logger.getLogger(PublickeyApiServiceImpl.class);
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
	
	@Override
    public Response getPublickey(SecurityContext securityContext) throws NotFoundException {
		int resultCode = -1;
		String publickeyfile = Properties.getValue("publickey.location");
		Publickey pubkeyResponse = null;
		String cmd = "more /root/.ssh/id_rsa.pub";
		logger.info("Getting the publickey from " + publickeyfile); 
		String pubkey = "";
		try {
		    Scanner scanner = new Scanner(new File(publickeyfile));

		    //now read the file line by line...
		    while (scanner.hasNextLine()) {
		        String line = scanner.nextLine();
		        pubkey += line;
		    }		    
		    logger.info("Key collected: " + pubkey);
		    pubkeyResponse = new Publickey();
		    pubkeyResponse.setPublickey(pubkey);
		    resultCode = 0;
		} catch(FileNotFoundException e) { 
		    logger.error(e.getMessage());
		    e.printStackTrace();
		    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Check logs please. Result of the execution has been: " + resultCode + " " + e.getLocalizedMessage())).build();
		}
		
//		String s;
//    	Process p;
//    	String publickey = "";
//		try { 
//            p = Runtime.getRuntime().exec(cmd);
//            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            
//            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            File logFile = new File("/var/log/eim/publickey_" + sdf.format(timestamp) + ".log");
//            PrintWriter writer = new PrintWriter(logFile);
//            
//            while ((s = br.readLine()) != null){
////            	System.out.println(s);
//            	logger.info(s);
//            	writer.println(s);
//            	publickey += s;
//            }                
//            p.waitFor();
//            resultCode = p.exitValue();
//            logger.info("exit: " + resultCode);
//            writer.println("Result code: " + resultCode);
//            writer.close();
//            p.destroy();
//        } catch (Exception e) {
//        	logger.error("ERROR: " + e.getLocalizedMessage());
//        	logger.error(e.getStackTrace());
//        	return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Result of the execution has been: " + resultCode + " " + e.getLocalizedMessage())).build();
//        }
    	if (resultCode == 0){
    		return Response.ok().entity(pubkeyResponse).build();
    	}
    	else {
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Result of the execution has been: " + resultCode + ". Check logs please")).build();
    	}
    }
}
