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


public class EimApiRestTest {

	private String sut_address = System.getenv("ET_SUT_HOST");
	private String logstash_ip = System.getenv("ET_MON_LSBEATS_HOST");
	private String logstash_port = System.getenv("ET_MON_LSBEATS_PORT");
	
	private String server = "http://nightly.elastest.io:37004/eim/api/agent/";	
	
	public RestTemplate restTemplate = new RestTemplate();
	public HttpHeaders headers = new HttpHeaders();
	
	static String agentId;
	
	// TODO - registerAgent_then200OK()
	@Test
	public void a_Test() throws InterruptedException, IOException {
		
		System.out.println("############ Running Test1: ############");
		
		String privateKey = "-----BEGIN RSA PRIVATE KEY-----\nMIIEogIBAAKCAQEAwg1kgRIjtiLGGs0mcdsOF0jK9vBSW8qyKHNmZN3legZMxcdP\nwkoSlGD4ZZ5jULnvyiOOm6kH3IvaFpfHW9ZS9dnR2B1urbqMB04Q1wLEKa4tMp+o\n693O49QYdqLUNCca6BdOr7zMMlVrgiDToexTpq5CMw/M1Ud82ZelpNJd1+TMfTgo\nBQXchSEi3H9rpPC+1QhLMdRMQlIshBv16KSJ3z6/PMQUrfm5nSs8/QpXF+GVjOFg\nZpMBuPjwmJQBaHVimH4a339JxTZtpPZXQUulWU7+SsDeXWJMna71GQUk2ug+OaVh\nG3Rtu58nzcfaWd5ulc8GE1PrNBKImDlatnqWhQIDAQABAoIBAE7NjYD00tfGK1j6\n8p/N61z9TJ8XXPq1x7+xjWVszyHMqSSwGe22kvD6qCTsOzyvJXebFPCVpgGkMX53\nSJg84xe/yv3OhgG6/d6Bj0khcHPUrbofKEQQoVHX8c9LtI0xTDXwMR4gt3PBSJVh\nq0/YXugTYALIadA65bKfaL8URtPC+eFchPxljNNZSIop64sMa9aWaLzmvgJwUBxi\njEJsbsw7bnESTxM7wpadP4x7HDqpPINjvoT+QKVP/YpnXDCeyCd9JL6TFRFNia7Q\n402w3FhmWGLvgb5WAA0SuScWkYFdxC9ZiIwpnMtn5vpjppYi7X3LiFUaoX9IST0Q\nJlMFoIECgYEA/STJp5lGZ4sHOIr7+/DVVcOmnxvDFTbkKwL36oDa1miXXwJrViYy\ny1iTBx1/DcDGJ9Ut74pDjvZvvcGhvMfOsyo89JRRseVyHu3zKPxXdW/iIZQrcipN\ndvFtL+f3EsHp01cN93uhmuk9vwwFei/ylXaGJuT3bgSWmN/2oc7lH1UCgYEAxD3q\n99dv4Hhg3bkMQoERqt8SYTMGgorb7S6zHH+n1JW9fjv+cJAtESSQnCAyV6fQz/XC\nntn+jlUG8vwk27kS8wLbA0iBdILNkhoo2PtUFpUeIfoXQNub0u5GNksp4hSJoMKQ\nGme5V9yqh3U5d/uBMj6wwXi09XFqMaeWUThEunECgYAJNV1gi2LUQWpx1Bx2fbxE\n8mkNQKVIEEcciW8bSLStOADJo/zjlWAjpYUzrQ8CIREUQdz56b2rZauK65BGnb4X\nJmIxQ3P1VLNiDKXbx9Imo3tGXW13KbLHsOCX7Yg/Vm57EZ+gcQ7f47wrRvSsDTJ1\n3Wnj4FBf4CraRMtj93K+QQKBgBgq4/AH2SBU0uEbc/YQvSNE+5d62DdlcHZCRQ6i\nbgHROxf7vXcRWmt9DE4d7PU8lou14XT8tcvDjuqHwOZKYZoJ5Pz/henaXgS7MgKe\n2Rk63g4jJY/4O7V7YKw5D3xnORfSPjryQTIf2+R4vE4ZbCzK6au5+NctU32v/OQd\nwOERAoGAXSGSL/RfgQfGwY2V3ZaJMlffBue4QFskN7YntZasNTAzQNJrcKmyomry\nfh3dsfXQOSPtCXaQ/ND8x+Tm6yBe3qSpvoH3pp+ZP3ZUjjZqjeQgXYgWDy1v0FwQ\nrVjY5DBhoqCznF7t+r8xrTU8/w/gd5VcLvw6sygBpw4z3ooiAcY=\n-----END RSA PRIVATE KEY-----";

		System.out.println("SUT Address: "+sut_address);
		//System.out.println("Private key: "+privateKey);
		
		JsonObject obj = new JsonObject();
		obj.addProperty("address", new String(sut_address));
		obj.addProperty("user", "root");
		obj.addProperty("private_key", new String(privateKey));
		obj.addProperty("logstash_ip", logstash_ip);
		obj.addProperty("logstash_port", logstash_port);
		obj.addProperty("password", "elastest");
		
		System.out.println("Payload: "+obj.toString());
		
		String URL = server;
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		HttpEntity<String> request = new HttpEntity<String>(obj.toString(), headers);
		ResponseEntity<String> response = restTemplate.exchange(URL,  HttpMethod.POST, request, String.class);
		
		
		String body = response.getBody();
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(body);
		
		agentId = json.get("agentId").getAsString();
		
		
		System.out.println("############ Response for Test1: ############");
		System.out.println(response);
		
		Assertions.assertEquals(200, response.getStatusCode().value());

		
	}

	 @Test
	 public void b_Test() throws InterruptedException {
		System.out.println("############ Running Test2: ############");
		
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
		System.out.println("############ Response for Test2: ############");
		System.out.println(response);

		Assertions.assertEquals(200, response.getStatusCode().value());
	  
	 }
	 
	 @Test
	 public void c_Test() throws InterruptedException {
		System.out.println("############ Running Test3: ############");
		
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
		
		TimeUnit.SECONDS.sleep(600);
		
		ResponseEntity<String> response = restTemplate.exchange(URL,  HttpMethod.POST, request, String.class);
		System.out.println("############ Response for Test3: ############");
		System.out.println(response);
		
		Assertions.assertEquals(200, response.getStatusCode().value());
	  
	 }
	
	 @Test
	 public void d_Test() throws InterruptedException {
		System.out.println("############ Running Test4: ############");

		String uri_unistall_agent = agentId+"/unmonitor"; 

		 
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		 
		String URL = server +  uri_unistall_agent;

		HttpEntity<String> request = new HttpEntity<String>("", headers);
		TimeUnit.SECONDS.sleep(500);

		ResponseEntity<String> response = restTemplate.exchange(URL,  HttpMethod.DELETE, request, String.class);
		
		System.out.println("############ Response for Test4: ############");
		System.out.println(response);
		
		Assertions.assertEquals(200, response.getStatusCode().value());
 
	 }
	 
	 @Test
	 public void e_Test() throws InterruptedException {
		 
		 System.out.println("############ Running Test5: ############");

		 headers.setContentType(MediaType.APPLICATION_JSON);
		 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		 
		 String URL = server+agentId;
		 		 
		 HttpEntity<String> request = new HttpEntity<String>("", headers);
		 TimeUnit.SECONDS.sleep(500);

		 ResponseEntity<String> response = restTemplate.exchange(URL,  HttpMethod.DELETE, request, String.class);
		 
		 System.out.println("############ Response for Test5: ############");
		 System.out.println(response);
		 
         Assertions.assertEquals(200, response.getStatusCode().value());
         
         agentId = "";
         
	 }

}
