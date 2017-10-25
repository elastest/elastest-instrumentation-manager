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

package io.elastest.eim.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import io.elastest.eim.utils.FileTextUtils;

public class Properties {

	private static Logger logger = Logger.getLogger(Properties.class);
	
	private static java.util.Properties properties = new java.util.Properties();
	
	public static void load(InputStream propertiesFile, String filePath){
		try {
			//get hostname for mongo host
			String dbHost = System.getenv("ET_EIM_MONGO_HOST");
			//overwrite the mongo host in properties file
			FileTextUtils.replaceTextInFile("/var/lib/tomcat7/webapps/eim/WEB-INF/bootstrap.properties						", "##ET_EIM_MONGO_HOST##", dbHost);
			restartServer();
			properties.load((propertiesFile));
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error(e1.getMessage());
		}
		logger.info("Properties loaded from " + filePath);
//		Enumeration<?> e = properties.propertyNames();
//		while (e.hasMoreElements()) {
//			String key = (String) e.nextElement();
//			String value = properties.getProperty(key);
//			System.out.println("Key : " + key + ", Value : " + value);
//		}
	}
	
	public static String getValue(String key){
		return properties.getProperty(key);
	}
	
	private static int restartServer() {
		String scriptPath = "";
		int resultCode = -1;
		String s;
    	Process p;
    	try {
            p = Runtime.getRuntime().exec(scriptPath);
            logger.info("Restarting tomcat server...");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                     
            while ((s = br.readLine()) != null){
            	logger.info(s);            	
            }                
            p.waitFor();
            resultCode = p.exitValue();
            logger.info("exit: " + resultCode);
            p.destroy();
            return resultCode;
        } catch (Exception e) {
        	logger.error("ERROR: " + e.getLocalizedMessage());
        	logger.error(e.getStackTrace());
        	return resultCode;
        }		
	}
	
}
