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


package io.elastest.eim.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Scanner;

import org.apache.log4j.Logger;


public class FileTextUtils {

	private static Logger logger = Logger.getLogger(FileTextUtils.class);
	
	public static int countLines(File f) throws FileNotFoundException{
		int result = -1;
		LineNumberReader  lnr = new LineNumberReader(new FileReader(f));
		try {
			lnr.skip(Long.MAX_VALUE);
			result = lnr.getLineNumber() + 1; //Add 1 because line index starts at 0
			// Finally, the LineNumberReader object should be closed to prevent resource leak
			lnr.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return result;
	}
	
	public static boolean containsText(File f, String target) throws FileNotFoundException {
		try {
		    Scanner scanner = new Scanner(f);

		    //now read the file line by line...
		    int lineNum = 0;
		    while (scanner.hasNextLine()) {
		        String line = scanner.nextLine();
		        lineNum++;
		        if(line.equalsIgnoreCase(target)) { 
		        	logger.info("Target: " + target + " found in line: " + line);
		        	return true;
		        }
		    }		    
		} catch(FileNotFoundException e) { 
		    logger.error(e.getMessage());
		    e.printStackTrace();
		}
		return false;
	}
	
}
