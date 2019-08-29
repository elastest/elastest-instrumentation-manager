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
import io.elastest.eim.test.base.EimBaseTest.SutCommandsOptionEnum;
import io.github.bonigarcia.seljup.BrowserType;
import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumExtension;


/**
 * Check that the EMS works properly together with a TJob.
 */
@Tag("e2e")
@DisplayName("E2E tests of EIM through TORM")
@ExtendWith(SeleniumExtension.class)

public class EimTJobPacketLoss extends EimBaseTest {

	
		
		private String sutName = "EIM-SUT-TESTER-PACKETLOSS";
		final int timeOut  = 1300;
		

		final Logger log = getLogger(lookup().lookupClass());
		String projectName = "EIM_e2e_tester_packetloss";

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
				//String sutDesc = "SuT for E2E test";
				//String sutImage = "elastest/eim-sut:latest";
				String commands = "docker pull elastest/eim-sut-tester:latest; docker run --rm --cap-add=NET_ADMIN -d -p 5000:5000 --name $ET_SUT_CONTAINER_NAME elastest/eim-sut-tester:latest";
				SutCommandsOptionEnum option  = SutCommandsOptionEnum.IN_NEW_CONTAINER;
				String desc = "SuT for E2E packetloss test";
				String image = "elastest/test-etm-alpinedockerjava";

				//createNewSutDeployedByElastestWithImage(driver, sutName, sutDesc, sutImage, null, null, false);
				createNewSutDeployedByElastestWithCommands(driver, commands, option, sutName, desc, image, null, null, false);
				
			}
		}
		
		@Test
		@DisplayName("EIM in a TJob")
			void testTJob(@DockerBrowser(type = BrowserType.CHROME) RemoteWebDriver localDriver, TestInfo testInfo) throws Exception {
			setupTestBrowser(testInfo, BrowserType.CHROME, localDriver);

			// Setting up the TJob used in the test
			this.createProjectAndSut(driver);
			navigateToETProject(driver, projectName);
			String tJobName = "EIM e2e tjob packetloss";
			if (!etTJobExistsIntoProject(driver, projectName, tJobName)) {

				String tJobTestResultPath = "/elastest-instrumentation-manager";
				String tJobImage = "elastest/test-etm-alpinegitjava";
										
				String commands = "git clone https://github.com/elastest/elastest-instrumentation-manager.git; "
						+ "cd elastest-instrumentation-manager/e2e-test/; "
						+ "mvn package -DskipTests=true;"
						+ "mvn test -Dtest=io.elastest.eim.test.e2e.PacketLossTest25;"
						+ "mvn test -Dtest=io.elastest.eim.test.e2e.PacketLossTest50;"
						+ "mvn test -Dtest=io.elastest.eim.test.e2e.PacketLossTest75;"
						+ "exit";
				
				System.out.println("Commands: "+commands);
				
				createNewTJob(driver, tJobName, tJobTestResultPath, sutName, tJobImage, false, commands, null, null, null,null);
				
			}
			// Run the TJob
			runTJobFromProjectPage(driver, tJobName);
			this.checkFinishTJobExec(driver, timeOut, "SUCCESS", false);
		}

	}
