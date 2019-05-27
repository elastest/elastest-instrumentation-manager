/*
 * (C) Copyright 2017-2019 ElasTest (http://elastest.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */


package io.elastest.eim.test.e2e;



import static java.lang.invoke.MethodHandles.lookup;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.elastest.eim.test.base.EimBaseTest;
import io.github.bonigarcia.*;
import io.github.bonigarcia.seljup.BrowserType;
import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumExtension;
import static io.github.bonigarcia.seljup.BrowserType.CHROME;



/**
 * Check that the EMS works properly together with a TJob.
 */
@Tag("e2e")
@DisplayName("E2E tests of EMS through TORM")
@ExtendWith(SeleniumExtension.class)

public class EimTJobE2eTest extends EimBaseTest {
	
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
    
    private String sutName = "EIMe2esut";
    
    
    void createProjectAndSut(WebDriver driver) throws Exception {
        navigateToTorm(driver);       		
        
        if (!etProjectExists(driver, projectName)) {
            createNewETProject(driver, projectName);
        }
        if (!etSutExistsIntoProject(driver, projectName, sutName)) {
            
        	// Create SuT
            String sutDesc = "SuT for E2E test";
            String sutImage="elastest/eim-sut:latest";
            String port="8080";
            
            createNewSutDeployedByElastestWithImage(driver, sutName,sutDesc,sutImage,port,null,false);
        }

    }
    
    @Test
    @DisplayName("EIM in a TJob")
    void testTJob(@DockerBrowser(type = CHROME) RemoteWebDriver localDriver,
            TestInfo testInfo) throws Exception {
        setupTestBrowser(testInfo, BrowserType.CHROME, localDriver);

        // Setting up the TJob used in the test
        this.createProjectAndSut(driver);
        navigateToETProject(driver, projectName);
        String tJobName = "EIM e2e tjob";
        if (!etTJobExistsIntoProject(driver, projectName, tJobName)) {
            String tJobTestResultPath = "";
            String tJobImage = "elastest/test-etm-alpinegitjava";
            String ipAddr= System.getenv("ipAddr");
            String projID=System.getenv("projID");
            String privateKey=System.getenv("privateKey");
            		
            String commands = "git clone https://github.com/elastest/elastest-instrumentation-manager.git; cd e2e-test/; mvn -Dtest=EimApiRestTest.java -DipAddr="+ipAddr+" -DprojID="+projID+" -DprivateKey="+privateKey+" -Dbrowser=chrome test;";
            createNewTJob(driver, tJobName, tJobTestResultPath, sutName,
                    tJobImage, false, commands, null, tssMap, null, null);
        }
        // Run the TJob
        runTJobFromProjectPage(driver, tJobName);

    }

}
