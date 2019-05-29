package io.elastest.eim.test.e2e;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.elastest.eim.test.utils.RestClient;
import junit.framework.Assert;

public class EimApiRestTest {

	private String private_key = System.getenv("privateKey");
	private String sut_address = System.getenv("ipAddr");
	private String eim_api_rest = "http://nightly.elastest.io:37004/";
	private String user = "root";
	private String password = "elastest";
	private boolean secureElastest = false;
	private String uri = "http://" + eim_api_rest + "/eim/api/agent/";

	public RestClient client = new RestClient(eim_api_rest, user, password, secureElastest);

	// TODO - registerAgent_then200OK()
	@Test
	public void registerAgent_then200OK() {
		String payload = "{\"address\":" + sut_address + ",\"user\":" + user + ",\"private_key\":" + private_key
				+ ",\"logstash_ip\":\"172.20.0.4\",\"logstash_port\":\5044\",\"password\":\"elastest\"}";

		JsonParser parser = new JsonParser();
		JsonObject jsonObj = (JsonObject) parser.parse(payload);

		Assert.assertEquals(200, client.post(uri, payload).getStatusCode());
	}

	// TODO - request_packetloss_action_then200OK
	/**
	 * curl -i -X POST -H "Content-Type:application/json" -H
	 * "Accept:application/json"
	 * http://localhost:8080/eim/api/agent/controllability/iagent0/packetloss -d '{
	 * "exec": "EXECBEAT", "component": "EIM", "packetLoss": "0.01", "stressNg": "",
	 * "dockerized": "yes", "cronExpression": "@every 60s" }'
	 */

	@Test
	public void request_packetloss_action_then200OK() {
		String uri_packetloss_action = uri + "controllability/iagent0/packetloss";
		String payload = "{\"exec\":\"EXECBEAT\",\"component\":\"EIM\",\"packetLoss\":\"0.01\",\"stressNg\":\"\",\"dockerized\":\"yes\",\"cronExpression\":\"@every 60s\"}";

		Assert.assertEquals(200, client.post(uri_packetloss_action, payload).getStatusCode());

	}

	// TODO - request_stress_action_then200OK()

	/**
	 * curl -i -X POST -H "Content-Type:application/json" -H
	 * "Accept:application/json"
	 * http://localhost:8080/eim/api/agent/controllability/iagent0/stress -d '{
	 * "exec": "EXECBEAT", "component": "EIM", "packetLoss": "", "stressNg": "4",
	 * "dockerized": "yes", "cronExpression": "@every 60s" }'
	 **/

	@Test
	public void request_stress_action_then200OK() {
		String uri_stress_action = uri + "controllability/iagent0/stress";
		String payload = "{\"exec\":\"EXECBEAT\",\"component\":\"EIM\",\"packetLoss\":\"\",\"stressNg\":\"4\",\"dockerized\":\"yes\",\"cronExpression\":\"@every 60s\"}";

		Assert.assertEquals(200, client.post(uri_stress_action, payload).getStatusCode());
	}

	// TODO - Unistall Agent - request_unistall_agent

	/**
	 * curl -i -X DELETE -H "Content-Type:application/json" -H
	 * "Accept:application/json"
	 * http://localhost:8080/eim/api/agent/iagent0/unmonitor
	 */

	public void request_unistall_agent() {
		String uri_unistall_agent = uri + "iagent0/unmonitor";
		Assert.assertEquals(200, client.delete(uri_unistall_agent).getStatusCode());

	}

	// TODO - Remove Agent

	/**
	 * curl -i -X DELETE -H "Content-Type:application/json" -H
	 * "Accept:application/json" http://localhost:8080/eim/api/agent/iagent0
	 */

	public void request_delete_agent() {
		String uri_delete_agent = uri + "iagent0";
		Assert.assertEquals(200, client.delete(uri_delete_agent).getStatusCode());

	}

}
