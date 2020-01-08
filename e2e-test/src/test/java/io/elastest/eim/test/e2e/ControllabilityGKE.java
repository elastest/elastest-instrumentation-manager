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

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class ControllabilityGKE {

	//private String sut_address = System.getenv("ET_SUT_HOST");
	private String sut_address = "35.240.45.54";
	//private String logstash_ip = System.getenv("ET_MON_LSBEATS_HOST");
	private String logstash_ip = "nightly.elastest.io";
	//private String logstash_port = System.getenv("ET_MON_LSBEATS_PORT");
	private String logstash_port = "37502";
	
	private String server = "http://nightly.elastest.io:37004/eim/api/agent/";	
	
	public RestTemplate restTemplate = new RestTemplate();
	public HttpHeaders headers = new HttpHeaders();
	
	static String agentId;
	
	
	 @Test
	 public void a_Test() throws InterruptedException {
		System.out.println("############ Running Test 2: PacketLoss feature (Execbeat) ############");
		
		String uri_packetloss_action = "controllability/"+agentId+"/packetloss";
		String URL = server + uri_packetloss_action;
		
		
		JsonObject obj = new JsonObject();
		obj.addProperty("exec", "EXECBEAT");
		obj.addProperty("component", "EIM");
		obj.addProperty("packetLoss", "0.01");
		obj.addProperty("stressNg", "");
		obj.addProperty("dockerized", "yes");
		obj.addProperty("cronExpression", "@every 60s");
		
		System.out.println("Payload: "+obj.toString());
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		HttpEntity<String> request = new HttpEntity<String>(
				obj.toString(), headers);
		
		ResponseEntity<String> response = restTemplate.exchange(URL,  HttpMethod.POST, request, String.class);
		System.out.println("############ Response for Test 2: PacketLoss feature (Execbeat) ############");
		System.out.println(response);
		
		TimeUnit.SECONDS.sleep(120);;

		Assertions.assertEquals(200, response.getStatusCode().value());
	  
	 }
	 
	 @Test
	 public void b_Test() throws InterruptedException {
		System.out.println("############ Running Test 3: Stress feature (Execbeat) ############");
		
		String uri_packetloss_action = "controllability/"+agentId+"/stress";
		String URL = server + uri_packetloss_action;
		 
		JsonObject obj = new JsonObject();
		obj.addProperty("exec", "EXECBEAT");
		obj.addProperty("component", "EIM");
		obj.addProperty("packetLoss", "");
		obj.addProperty("stressNg", "1");
		obj.addProperty("dockerized", "yes");
		obj.addProperty("cronExpression", "@every 60s");
		
		System.out.println("Payload: "+obj.toString());
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<String> request = new HttpEntity<String>(
				obj.toString(), headers);
				
		ResponseEntity<String> response = restTemplate.exchange(URL,  HttpMethod.POST, request, String.class);
		System.out.println("############ Response for Test3: Stress feature (Execbeat) ############");
		System.out.println(response);
		
		TimeUnit.SECONDS.sleep(120);

		Assertions.assertEquals(200, response.getStatusCode().value());
	  
	 }
	 
	 @Test
	 public void c_Test() throws InterruptedException {
		System.out.println("############ Running Test4: unchecked agent ############");

		String uri_unistall_agent = agentId+"/unchecked"; 

		 
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		 
		String URL = server +  uri_unistall_agent;

		HttpEntity<String> request = new HttpEntity<String>("", headers);

		ResponseEntity<String> response = restTemplate.exchange(URL,  HttpMethod.DELETE, request, String.class);
		
		System.out.println("############ Response for Test 4: unchecked agent ############");
		System.out.println(response);

		Assertions.assertEquals(200, response.getStatusCode().value());
 
	 }
	
	 
	 @Test
	 public void d_Test() throws InterruptedException {
		 
		 System.out.println("############ Running Test5: delete agent ############");

		 headers.setContentType(MediaType.APPLICATION_JSON);
		 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		 
		 String URL = server+agentId;
		 		 
		 HttpEntity<String> request = new HttpEntity<String>("", headers);

		 ResponseEntity<String> response = restTemplate.exchange(URL,  HttpMethod.DELETE, request, String.class);
		 
		 System.out.println("############ Response for Test5 delete agent: ############");
		 System.out.println(response);
		 
		 Assertions.assertEquals(200, response.getStatusCode().value());
         
         agentId = "";
         
	 }

}
