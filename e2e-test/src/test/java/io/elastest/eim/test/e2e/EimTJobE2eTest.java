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
	
	private String sutName = "EIMe2eSut";
	final int timeOut  = 800;
	private String sut_ip = System.getenv("ET_SUT_HOST");
	

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

			createNewSutDeployedByElastestWithImage(driver, sutName, sutDesc, sutImage, null, null, false);
			
			
			
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
			
			
			String privateKey = "-----BEGIN RSA PRIVATE KEY-----\nMIIEpAIBAAKCAQEAvHZDbd9Ecl4SL/5SrOSF6NyzaSUrncH5oJ/7DoqyDWuei5HC\no/NDpa5ww5oyfrnwGFpcYl"
					+ "dC65e2J67kN5XkbLKO8pDq6MYXPgse6/01Qqk48hO0\nVL08C1nmX+AMT+7rGi724Y6BHLinO/N5pjP+KIllWRGY/RulVTED2GK5ODh4WGvq\n3RjeLS8lJb4HisKrcq2oiP"
					+ "69tRgubykqI7pA3VpP+utTU/solvDsGNxZzIlB8fdP\ndLWdGfor8L1WgBcM8Q2J6OcrNbMsKuxmtAEXGcxMsszu9Sjko/DCc2Ds05T0D+sw\nfgyueJ82Ny8jy/a16F7PIg/"
					+ "g7TOyc78IST4viQIDAQABAoIBAFrBeOdIRWXt90Q1\nbQrlYbcp0RwpUj++UcAQSo5OLEBaOxdrMhhR5Zm7z4Hil75/PCf+G88fxpI9lPK3\nPh+mh0HxGvWk4/sEqdyu7k0m"
					+ "uqdHZqzs4EKOfoPY3x/8fPFhVMZfJ3Snc3WpVp+A\nOGzZTOOohq3F067PyWalG/zwTdHMS9uxLsXwG1grbLCYVu0j0O+7UI5zvpVBMOwG\nplknHC/SaEnfjSOhbhNVAXDs/"
					+ "Rcnor5BpLJdKjI4LiVncTHgv5Q7o3VxQYoxf8WW\ndIxXyujP7BszXfEsOlvAj+1FakANWsfLVAa5R3042zrnOtHrr9B7hPTHbnKZYq3X\njrPjyECgYEA6zRk6EmLZGICHXZj"
					+ "WV2c7hfPH00qqLXp+IrmefY1cglwI4IZsRgp\nGXuDyV4UGJ0+xGvWTHG/9IwkFZ4PKb7r855/isX6/SvyAuW0cgtck3LUOunL/zdl\naRP"
					+ "6cWc1MTuq6hA3v0Yw3+mVfnwq0cjS2kTmf154AJeidurvZAUO41sCgYEAzR/l\n9bwekIDy+hcC4pCa4sTeZ2kWnuJ//VQCKoBXiOHxYDikSDPJFwzdjiSiX6aGTjwN\nIXp53"
					+ "zLF7RIyb8cOy0gUtymA2zUkj2sQrbV0wnaJUsgqEaSh+Dh2tWN49V46auf3\nOUOw3Njh72CquPypt3WxodmvmcemNv+K2vKqYesCgYEApVCTKA0hEzIryGeAlrxM\nY7CKXLbR"
					+ "J/mMdKu0KL7be8aUcfCsfCO+J3IAA9XDDDXhew9MdThyMcEaT7NjdvUB\nRJ7/iBt1OOLdXBn1hT7lF6Ha93mCb2SRO5TWL0TaRztn+tHhTOhZy9eSwm8lbYPY\nZ5+L3rQYZlot"
					+ "z6V7EvLXLwcCgYBg06XhkaN74uLPWv1ppYj3cqbpeZnL9m8kTfuX\nxG2Nl9ow6Yvi6U8LKm+LIY/kRt8vHvmzy/Srf3QerHBSg0xgeO4OZ/EDiNpxOMDu\nvrEW+o1oHkR9f5FVc"
					+ "pHWndYDPS+mN0lkJqfeGCLOtHriR6J1j4ECPZe+p5e2bnpF\nUDdNcQKBgQCrulfWQ2cbbaNTXxwa+j+TPWHVi7K2HaplCkRUBVOHanyFr/Cq0slj\nrDFDZRA8KXMtE2fELpcgVK"
					+ "t390PEPEyG+HS0hkc9l2pnsx53WJ8Z59FjPqMj8Mez\nLUtB0FAiwrQTdkQNiUjXU37AXoLXPAjo4bOIGPdvpDRU9JBsyj53Tw==\n-----END RSA PRIVATE KEY-----";

			String SUT_IP = sut_ip;
			
			String commands = "git clone https://github.com/elastest/elastest-instrumentation-manager.git; "
					+ "cd elastest-instrumentation-manager/e2e-test/; mvn package -DskipTests=true; "
					+ "mvn -B -Dtest=io.elastest.eim.test.e2e.EimApiRestTest test -DargLine='-Dprivate_key="+privateKey+" -Dsut_address="+SUT_IP+"'; "
					+ "docker stop $(docker ps -a -q);"
					+ "docker rm $(docker ps -a -q);"
					+ "exit";
			
			System.out.println("Commands: "+commands);
			
			createNewTJob(driver, tJobName, tJobTestResultPath, sutName, tJobImage, false, commands, null, null, null,null);
			
		}
		// Run the TJob
		runTJobFromProjectPage(driver, tJobName);
		this.checkFinishTJobExec(driver, timeOut, "SUCCESS", true);

	}

}
