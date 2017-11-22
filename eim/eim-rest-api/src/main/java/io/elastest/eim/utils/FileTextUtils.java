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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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
	
	public static void copyFile(String source, String destination) throws IOException {
		Path sourcePath      = Paths.get(source);
		Path destinationPath = Paths.get(destination);
		
		logger.info("Copying files...");
		logger.info("Source: " + source);
		logger.info("Destination: " + destination);
		Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
		logger.info("Copy of files successfull");
	}
	
	public static void replaceTextInFile(String filePath, String textToFind, String replaceText) throws IOException {
		List<String> newLines = new ArrayList<>();
		for (String line : Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8)) {
		    if (line.contains(textToFind)) {
		       newLines.add(line.replace(textToFind, replaceText));
		       logger.info(textToFind + " found in the file. Replacing it by " + replaceText);
		    } else {
		       newLines.add(line);
		    }
		}
		logger.info("Updating with new content the file " + filePath);
		Files.write(Paths.get(filePath), newLines, StandardCharsets.UTF_8);
		logger.info("File " + filePath + " updated");
	}
	
	public static void replaceListInFile(String filePath, String textToFind, List<String> filePathsList) throws IOException {
		List<String> newLines = new ArrayList<>();
		for (String line : Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8)) {
		    if (line.contains(textToFind)) {
		    	logger.info(textToFind + " found in the file. Replacing it by filepaths...");
		    	for (String filepath : filePathsList) {
		    		String copy = line.substring(0, line.length());
			    	String newFilepath = copy.replace(textToFind, filepath) ;
			    	newLines.add(newFilepath);
		    		logger.info("Added new line to playbook: " + newFilepath);
		    	}		       
		    } else {
		       newLines.add(line);
		    }
		}
		logger.info("Updating with new content the file " + filePath);
		Files.write(Paths.get(filePath), newLines, StandardCharsets.UTF_8);
		logger.info("File " + filePath + " updated");
	}
	
	public static void setAsExecutable(String filePath) throws IOException {
		
		//using PosixFilePermission to set file permissions 755
        Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
        //add owners permission
        perms.add(PosixFilePermission.OWNER_READ);
        perms.add(PosixFilePermission.OWNER_WRITE);
        perms.add(PosixFilePermission.OWNER_EXECUTE);
        //add group permissions
        perms.add(PosixFilePermission.GROUP_READ);
        perms.add(PosixFilePermission.GROUP_EXECUTE);
        //add others permissions
        perms.add(PosixFilePermission.OTHERS_READ);
        perms.add(PosixFilePermission.OTHERS_EXECUTE);
        
        Files.setPosixFilePermissions(Paths.get(filePath), perms);
        logger.info("Modified as executable " + filePath);
		
	}
	
	/**
	 * Delete the given agentId form ansible.cfg file /etc/ansible/hosts
	 * 
	 * Example:
	 * localhost
	 * [agent1]
	 * 1.2.3.4
	 * 
	 * after the execution of the method:
	 * 
	 * localhost
	 * 
	 * remove the line where appears the agentId and the following one
	 * 
	 * @param ansibleCfgPath
	 * @param agentId
	 * @throws IOException 
	 */
	public static void removeAgentFromAnsibleCfg(String ansibleCfgPath, String agentId) throws IOException {

		List<String> newLines = new ArrayList<>();
		boolean addNextLine = true;
		for (String line : Files.readAllLines(Paths.get(ansibleCfgPath), StandardCharsets.UTF_8)) {
			if (line.contains("[" + agentId + "]")) {
				//this line is not added and flag to order that next line do not be added is activated
				addNextLine = false;
				logger.info(agentId + " found in the file. Removing it... ");
			} 
			else if (!addNextLine){
				addNextLine = true;
			}
			else {
				newLines.add(line);
			}
		}
		logger.info("Updating with new content the file " + ansibleCfgPath);
		Files.write(Paths.get(ansibleCfgPath), newLines, StandardCharsets.UTF_8);
		logger.info("File " + ansibleCfgPath + " updated");	
	}
	
	
	public static void setAsReadOnly(String filePath) throws IOException {
		
		//using PosixFilePermission to set file permissions 755
        Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
        //add owners permission
        perms.add(PosixFilePermission.OWNER_READ);
        
        Files.setPosixFilePermissions(Paths.get(filePath), perms);
        logger.info("Modified as readOnly by owner: " + filePath);
		
	}
	
	
	public static void replaceValueInPropertiesConf(String filePath, String textToFind, String replaceText) throws IOException {
		List<String> newLines = new ArrayList<>();
		boolean changed = false;
		for (String line : Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8)) {
		    if (line.contains(textToFind)) {
		       newLines.add(line.replace(textToFind, replaceText));
		       logger.info(textToFind + " found in the file. Replacing it by " + replaceText);
		       changed = true;
		    } else {
		       newLines.add(line);
		    }
		}
		logger.info("Updating with new content the file " + filePath);
		Files.write(Paths.get(filePath), newLines, StandardCharsets.UTF_8);
		logger.info("File " + filePath + " updated");
		if (changed) {
			restartServer();
		}
	}
	
	private static int restartServer() {
		String scriptPath = "/var/tomcat/restart_tomcat.sh";
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
	
	
	/**
	 * Delete a file or a directory and its children.
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static void delete(File f) throws IOException {
 
		try {
			//Deleting the directory recursively.
			deleteRecursive(f);
			System.out.println("Path " + f.getAbsolutePath() + " has been deleted recursively !");
		} catch (IOException e) {
			System.out.println("Problem occurs when deleting the path : " + f.getAbsolutePath());
			e.printStackTrace();
		}
	}	

	
	/**
	 * Delete a file or a directory and its children. Internal recursive method
	 * 
	 * @param file
	 * @throws IOException
	 */
	private static void deleteRecursive(File file) throws IOException {
		 
		for (File childFile : file.listFiles()) {
 
			if (childFile.isDirectory()) {
				deleteRecursive(childFile);
			} else {
				if (!childFile.delete()) {
					throw new IOException();
				}
			}
		}
 
		if (!file.delete()) {
			throw new IOException();
		}
	}
}
