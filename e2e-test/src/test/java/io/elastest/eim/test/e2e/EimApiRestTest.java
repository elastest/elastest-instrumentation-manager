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

import java.util.Collections;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class EimApiRestTest {

	private String sut_address = System.getenv("ET_SUT_HOST");
	private String server = "http://nightly.elastest.io:37004/eim/api/agent/";
	private String user = "root";
	
	// public RestClient client = new RestClient(server, user, password,
	// secureElastest);

	public RestTemplate restTemplate = new RestTemplate();
	public HttpHeaders headers = new HttpHeaders();

	// TODO - registerAgent_then200OK()
	@Test
	public void registerAgentTest() {
		
		String privateKey = "\"-----BEGIN RSA PRIVATE KEY-----\\nMIIEpQIBAAKCAQEAwX6nZRrCrBIBFQHT0EJOrloK/BBmm5GssTEl7vVVIZr4rn23\\nb14whvmkYHOYxlqK818/9Gjvi1Q0e+DHBWEVWFd9WAnccvptEOElRIb3pz/V794y\\n9fx83yQXKaC4eJj+3YDuoXXilpYHZnismgMml3rP+v2jgsUi6m6S8TfzfIhTW0qi\\nFeNom3JytpUJgpht6WIjJF3IL+uT2x8Xm6NuNrARf5mRsMzZivap0dmPVE1LYaAP\\nJNWm6TOSGvpbnzJcrvCtIZTvJ1SMUbmjmk1lJaP+knlEAJ9IYqfa22GSdarCsuEF\\n4qZhiD+xsJrdy0MWQjcsVNnrBgLRphF4Vc5WyQIDAQABAoIBAQC1T1rH0HWOCyjc\\naGP0B832bgVRAIVFTRGGz8j9ywFEhhR0XPTf9GCAebhfcLI9W2ZMenpRKWsdIYA0\\nfAHBtqDrsL+RGVxqmOJOKMplFhtFqvlq1Mjn2vmflg/mP+Xbi3F2WXRB81apSFgS\\n2wzRHBazZq8wPy9SQCthhM9IHOeZTfvDXOEjAvR2vzNEqJQiJJzLakoPHsoX1UHY\\nr+f+ZcTUNbM+8mFXC0217dD2YcrC87T43PKbSmWMo5EAlQUCO9bRXn0XIqnLEMmp\\nAowLaaEhB2no1v7+9LkzKERPbewvZ4jf5iPAik0zIY68t8oagIcOxawxme2uXQBu\\n7BPeRSUBAoGBAN6Ye4G+4zQ04lm9jrMsq57vG9rpzreKRSP/0VRI1sjUmQ/AnaUo\\nJiWeoqV3fV9EyU4CLtmffiDOkGxWAfEC3iKfe+RtIFOH+eLMs8OgcyhzLiH3c8G5\\nKmAGtomlwx+8p666QJlP6J7BNJLET66HAUEAK+eDXw+elyFuW4ul5c/xAoGBAN6I\\nMcffQjStUMQ9hGU/jXupbjhy0OzrL9JwOBufpXA9mmo4JzPFXqw/YyuyRDzp7bma\\noTn0k0qLsBaz/s36nPl/sny6Su8FzT+aIeR8l7o0pY5dxWuCh0H0EbOxL8du/EHB\\nFkQ1gPt1IjUmPqqxPbdYpupxn8+42We/zMpvhcxZAoGBALYrrrBxi7pXKYPuKZIj\\nldT9tRtEzCPTqmAa5bMH5Zf4vcdxiNL4d7fECzJGBznnrqQED1mVOQEabIHtJauc\\nADXvtdItKQ6TswNVKi2I65YIJZIw1PCPXMm31L30Biu8FrNdxK50AlminycaOGgK\\nHxGWVVgkYLEExoTar1sri1fRAoGAbtDr2Vi4o3rbiZ4I3FK1pitNlBa5LAJCokz3\\n4+mwfSBwUQz9mK5k4un9/LideqgTliYGu9Grt6ewXN3tua1flm+c9rfesQD2oQGk\\ndyAEftnQyACyW0N5D8L3PcCyxmHihOwepoDuZkqCam1NL7trvG4NURqcNtkaiqvc\\n08KNoqECgYEAtCcslkjIzmYYXyGmiE5hSvy6GvLslamJgnZks9kr/XzdoVpwW0OT\\nHS8gNT33F22reQQX/XwSia0ZZskKKB8t7f79GoccVwL9HbsbkYhU4p3lem49J+p0\\noIZn/7KUhUOjtPPqIavjr1kx5Bg5vVAl3tk6/RkZk2hY/xftb/vgxMg=\\n-----END RSA PRIVATE KEY-----\"";

		System.out.println("SUT Address: "+sut_address);
		System.out.print("Private key: "+privateKey);
		
		// Remove "\" last character
		String privateKey_modified = privateKey.substring(0, privateKey.length() - 1);		
		String payload = "{\"address\":\""+sut_address+"\",\"user\":\""+user+"\",\"private_key\":"+privateKey_modified+",\"logstash_ip\":\"172.20.0.4\",\"logstash_port\":\"5044\",\"password\":\"elastest\"}";

		System.out.println("Payload: "+payload);
		
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject)parser.parse(payload);
		
		
		String URL = server;
		System.out.println("############ Endpoint request ############ ");
		System.out.println(URL);
		System.out.println("############ Json: ############");
		System.out.println(json);
		System.out.println("############ Json to String: ############" );
		System.out.println(json.toString());

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<JsonObject> request = new HttpEntity<JsonObject>(json, headers);
		
		System.out.println("############ This is the request: ############");
		System.out.println(request.toString());
		
		ResponseEntity<String> response = restTemplate.exchange(URL,  HttpMethod.POST, request, String.class);
		
		System.out.println("############ Response for Test1: ############");
		System.out.println(response);

		Assertions.assertEquals(200, response.getStatusCodeValue());
		
		
	}

	/*
	 * // TODO - request_packetloss_action_then200OK
	 *//**
		 * curl -i -X POST -H "Content-Type:application/json" -H
		 * "Accept:application/json"
		 * http://localhost:8080/eim/api/agent/controllability/iagent0/packetloss -d '{
		 * "exec": "EXECBEAT", "component": "EIM", "packetLoss": "0.01", "stressNg": "",
		 * "dockerized": "yes", "cronExpression": "@every 60s" }'
		 */
	/*
	 * 
	 * @Test public void requestActionPacketLossTest() { String
	 * uri_packetloss_action = "controllability/iagent0/packetloss"; // String
	 * payload = //
	 * "{\"exec\":\"EXECBEAT\",\"component\":\"EIM\",\"packetLoss\":\"0.01\",\"stressNg\":\"\",\"dockerized\":\"yes\",\"cronExpression\":\"
	 * @every // 60s\"}";
	 * 
	 * Map<String, String> body = new HashMap<>(); body.put("exec", "EXECBEAT");
	 * body.put("component", "EIM"); body.put("packetLoss", "0.01");
	 * body.put("stressNg", ""); body.put("dockerized", "yes");
	 * body.put("cronExpression", "@every 60s");
	 * 
	 * String URL = server + uri_packetloss_action;
	 * 
	 * System.out.println("Endpoint request " + server + uri_packetloss_action);
	 * System.out.println("Payload:" + body);
	 * 
	 * headers.setContentType(MediaType.APPLICATION_JSON);
	 * headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	 * 
	 * HttpEntity<Map<String, String>> request = new HttpEntity<Map<String,
	 * String>>(body, headers);
	 * 
	 * Assertions.assertEquals(200, restTemplate.exchange(URL, HttpMethod.POST,
	 * request, Map.class).getStatusCodeValue());
	 * 
	 * }
	 * 
	 * // TODO - request_stress_action_then200OK()
	 * 
	 *//**
		 * curl -i -X POST -H "Content-Type:application/json" -H
		 * "Accept:application/json"
		 * http://localhost:8080/eim/api/agent/controllability/iagent0/stress -d '{
		 * "exec": "EXECBEAT", "component": "EIM", "packetLoss": "", "stressNg": "4",
		 * "dockerized": "yes", "cronExpression": "@every 60s" }'
		 **/
	/*
	 * 
	 * @Test public void requestActionStressTest() { String uri_stress_action =
	 * "controllability/iagent0/stress"; // String payload = //
	 * "{\"exec\":\"EXECBEAT\",\"component\":\"EIM\",\"packetLoss\":\"\",\"stressNg\":\"4\",\"dockerized\":\"yes\",\"cronExpression\":\"
	 * @every // 60s\"}"; Map<String, String> body = new HashMap<>();
	 * body.put("exec", "EXECBEAT"); body.put("component", "EIM");
	 * body.put("packetLoss", ""); body.put("stressNg", "4"); body.put("dockerized",
	 * "yes"); body.put("cronExpression", "@every 60s");
	 * 
	 * String URL = server + uri_stress_action;
	 * 
	 * System.out.println("Endpoint request: " + URL);
	 * System.out.println("Payload: " + body);
	 * 
	 * headers.setContentType(MediaType.APPLICATION_JSON);
	 * headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	 * 
	 * HttpEntity<Map<String, String>> request = new HttpEntity<Map<String,
	 * String>>(body, headers); Assertions.assertEquals(200,
	 * restTemplate.exchange(URL, HttpMethod.POST, request,
	 * Map.class).getStatusCodeValue()); }
	 * 
	 * // TODO - Unistall Agent - request_unistall_agent
	 * 
	 *//**
		 * curl -i -X DELETE -H "Content-Type:application/json" -H
		 * "Accept:application/json"
		 * http://localhost:8080/eim/api/agent/iagent0/unmonitor
		 * 
		 * @throws InterruptedException
		 */
	/*
	 * 
	 * @Test public void requestUnistallAgentTest() throws InterruptedException {
	 * String uri_unistall_agent = "iagent0/unmonitor"; TimeUnit.SECONDS.sleep(160);
	 * 
	 * headers.setContentType(MediaType.APPLICATION_JSON);
	 * headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	 * 
	 * Map<String, String> body = new HashMap<>(); String URL = server +
	 * uri_unistall_agent;
	 * 
	 * System.out.println("Endpoint request: " + URL);
	 * 
	 * HttpEntity<Map<String, String>> request = new HttpEntity<Map<String,
	 * String>>(body, headers);
	 * 
	 * Assertions.assertEquals(200, restTemplate.exchange(URL, HttpMethod.DELETE,
	 * request, Map.class).getStatusCodeValue());
	 * 
	 * }
	 * 
	 * // TODO - Remove Agent
	 * 
	 *//**
		 * curl -i -X DELETE -H "Content-Type:application/json" -H
		 * "Accept:application/json" http://localhost:8080/eim/api/agent/iagent0
		 * 
		 * @throws InterruptedException
		 *//*
			 * 
			 * @Test public void requestDeleteAgentTest() throws InterruptedException {
			 * String uri_delete_agent = "iagent0"; TimeUnit.SECONDS.sleep(160);
			 * 
			 * Map<String, String> body = new HashMap<>(); String URL = server +
			 * uri_delete_agent;
			 * 
			 * System.out.println("Endpoint request: " + URL);
			 * 
			 * HttpEntity<Map<String, String>> request = new HttpEntity<Map<String,
			 * String>>(body, headers); Assertions.assertEquals(200,
			 * restTemplate.exchange(URL, HttpMethod.DELETE, request,
			 * Map.class).getStatusCodeValue());
			 * 
			 * }
			 */

}
