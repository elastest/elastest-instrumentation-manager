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

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.log4j.Logger;

public class Properties {

	private static Logger logger = Logger.getLogger(Properties.class);
	
	private static java.util.Properties properties = new java.util.Properties();
	
	public static void load(InputStream propertiesFile){
		try {
			properties.load((propertiesFile));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error(e1.getMessage());
		}
		Enumeration<?> e = properties.propertyNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = properties.getProperty(key);
			System.out.println("Key : " + key + ", Value : " + value);
		}
	}
	
	public static String getFirstProp(){
		String res = "not worked";
		Enumeration<?> e = properties.propertyNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = properties.getProperty(key);
			logger.info("message returned -->  " + "Key : " + key + ", Value : " + value);
			return "Key : " + key + ", Value : " + value;
		}
		return res;
	}
	
}
