/**
 * Copyright (c) 2019 Atos
 * This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0
 * which accompanies this distribution, and is available at
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 *    @author Fernando Mendez Requena - fernando.mendez@atos.net
 * 
 * OpenAPI spec version: 1.0.0
 * 
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 * 
 * Developed in the context of ElasTest EU project http://elastest.io 
 */

package io.elastest.eim.test.e2e;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;

import io.elastest.eim.test.base.EimBaseTest;
import io.github.bonigarcia.seljup.BrowserType;
import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumExtension;

/**
 * Check that the EMS works properly together with a TJob.
 */
@Tag("e2e")
@DisplayName("E2E tests of EIM through TORM")
@ExtendWith(SeleniumExtension.class)

public class EimTJobE2eTest extends EimBaseTest {
	
	private String sutName = "EIMe2esut";
	final int timeOut  = 600;
	

	final Logger log = getLogger(lookup().lookupClass());
	String projectName = "EIMe2e";

	private static final Map<String, List<String>> tssMap;
	static {
		tssMap = new HashMap<String, List<String>>();
		tssMap.put("EIM", null);
	}
	
	
	
	

	void createProject(WebDriver driver) throws Exception {
		navigateToTorm(driver);
		if (!etProjectExists(driver, projectName)) {
			createNewETProject(driver, projectName);
		}
	}

	void createProjectAndSut(WebDriver driver) throws Exception {
		navigateToTorm(driver);

		if (!etProjectExists(driver, projectName)) {
			createNewETProject(driver, projectName);
		}
		if (!etSutExistsIntoProject(driver, projectName, sutName)) {

			// Create SuT
			String sutDesc = "SuT for E2E test";
			String sutImage = "elastest/eim-sut:latest";
			String port = "8080";

			createNewSutDeployedByElastestWithImage(driver, sutName, sutDesc, sutImage, port, null, false);
			
			
			
		}

	}
	
	@Test
	@DisplayName("EIM in a TJob")
		void testTJob(@DockerBrowser(type = BrowserType.CHROME) RemoteWebDriver localDriver, TestInfo testInfo) throws Exception {
		setupTestBrowser(testInfo, BrowserType.CHROME, localDriver);

		// Setting up the TJob used in the test
		this.createProjectAndSut(driver);
		navigateToETProject(driver, projectName);
		String tJobName = "EIM e2e tjob";
		if (!etTJobExistsIntoProject(driver, projectName, tJobName)) {

			String tJobTestResultPath = "";
			String tJobImage = "elastest/test-etm-alpinedockerjava";
			
			//tssMap parameter is null cause EIM is not a test support service

			
			String commands = "docker run -itd --network=elastest_elastest --name=sut-dockerized -v /var/run/docker.sock:/var/run/docker.sock -v /var/lib/docker/containers/:/var/lib/docker/containers/ elastest/eim-sut;"
					+ "docker exec -i sut-dockerized /bin/bash;"
					+ "cd /root/.ssh/;"
					+ "cp id_rsa.pub authorized_keys;"
					+ "awk \"{printf \\\"%s\\\\\\\\\\\\\\\\n\\\", \\$0}\" id_rsa;"
					+ "sed -i -e '\\$ s|.\\$||' -e '\\$ s|.\\$|| ' id_rsa;"
					+ "cat id_rsa > /var/lib/docker/containers/id_rsa;"
					+ "touch ipAddr;"
					+ "hostname -I | sed -e 's/ //g' > /var/lib/docker/containers/ipAddr;"
					+ "exit;"
					+ "cd /var/lib/docker/containers/;"
					+ "export privateKey=$(cat id_rsa);"
					+ "export ipAddr=$(cat ipAddr);"
					+ "git clone https://github.com/elastest/elastest-instrumentation-manager.git;"
					+ "cd e2e-test/; mvn -DskipTests=true -B package -Dprivate_key_sut=${privateKey} -Dsut_addres=${ipAddr};"
					+ "mvn -B -Dtest=io.elastest.eim.test.e2e.EimApiRestTest test"
					+ "docker stop -f sut-dockerized stop && docker rm -f sut-dockerized";
			
			System.out.println("Commands: "+commands);
			
			createNewTJob(driver, tJobName, tJobTestResultPath, null, tJobImage, false, commands, null, null, null,null);
			
		}
		// Run the TJob
		runTJobFromProjectPage(driver, tJobName);
		this.checkFinishTJobExec(driver, timeOut, "SUCCESS", true);

	}

}
