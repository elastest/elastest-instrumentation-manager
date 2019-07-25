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
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class EimApiRestTest {

	private String sut_address = System.getenv("ET_SUT_HOST");
	public JsonElement agentID ;
	
	private String server = "http://nightly.elastest.io:37004/eim/api/agent/";
	
	
	public RestTemplate restTemplate = new RestTemplate();
	public HttpHeaders headers = new HttpHeaders();

	// TODO - registerAgent_then200OK()
	@Test
	public void a_Test() throws InterruptedException {
		
		String privateKey = "-----BEGIN RSA PRIVATE KEY-----\nMIIEpQIBAAKCAQEAyon9m2lne1ZYrTyZPkc5AF/He040ymCGNfsKYhGgRsyKhkM2\nt8hHryrOEwink9ts3UlfFdVu3w3AHGD3N6vm9AepIDlHHVZziC3Pz9JO3V0CT0VF\nl89k15tNKgAVlXfY9Ya5255TAL44D031u4cdRXo4HMv3SZimujras0sTfhhQDVpg\npjpb1BTcOCyXbQHUJJeL81sbMxMWo/ojSUlpVqQQemPtY7iz/+JEsct9J29xyCAq\nab0t2d/CAOA+kO2fwmxIxShJvugYcnlwTZS1wCeolv/ZFvfVCoSCeWtCmi9Hw4Td\nFoLXKQKdHU3mgrC6cWG+duHX5mdUesSbkcAWawIDAQABAoIBAQCOvenKWeLgfd5J\nWWf3CGMX7Gi+ckOqTZTI/oA21Y1L2GPYdA5gP/qlbVmG+JaCEicLXeZxkNZuxVYb\nqgsRZUmDutJrL3L7Li6GTyMiqGmEgURbccq2TygW/BDKBP0xNiHmCGl8any9DVKd\nFqiU3Yi3qodZZDaH29nFbi0sJ0E5n8+vsM3fOS6SlWQcKgNjVlhxA3aU76u38rGQ\nu6hMJ2ZfXN4E1oZ0EKViZuH73edeC34u/44aXYZ6TWHQ8rsUuiIFKuRRI8o4E7c5\nzr/vlscn+pFOUhmM1tAc2wOx5CNHGLpavZm9xim4wIwy+xub8SNYxmu166CqbxQz\nJAHjuuQhAoGBAO1CRhbH4M9ew6y38gx+g1jm8WInct8O7WpFkXT4/f+cxOZjk81A\nFEih6U955U2RrxhzlFbyQwl3z3cV4c++xOycUOfE1t1znMZ1xCJg+QwUmPo8BiLd\nWC7UWhXdC3Eye18EYWF3EvJnWeBU2Vhxjv2pBk9MbABs58OpoSDbUVvfAoGBANqJ\noUK1GJ/KwvLKQ/1d9N8wGoPZkuA0B6V6HW3o+5u0Zl+IO6e9h/HbbrljQ8r/r+tl\nc9TrV0kqG0+ENaL9lXOt5Q21GAFvJ6yiBVn+fAK2ekyrFHMqerVVYuOMnLaVVEI0\n4pZ/IrMUNWcH9GstG0SFQpnRzhv9z9RhLXjwwhb1AoGBAMLNBGEV8ZYx13VLfngV\ns+BdldkiTKWqSvJTdk9VVK73147WOXdvYngUQEyZ59SdhecMlsIgnTv73CKJm+MH\nXgZrfd4d9tDSaOllrgkQF8t8afIjMGKV3B5vChwjZo6lhTgJj68Hpk3S369Z2y5L\n5ryMd/rJ428h+9ThxMMGb7F/AoGBAL4A82ggM+yaSaz2Fu7vBbwXAraoMH8mPY+u\nAHBTJI9X5bohpFxO+Sda9YXRvFt+uuEbL/5rL1S5e01DUa8IcyxEgOXOEbUNg50g\nsS5xiDiDlwmZpQoMYOvP9U6KLqbAZqW5fVgD6ZNxeoy96dBVQ2PryOAb/etwXYX1\nh6ejC90RAoGADAzpLFggmyJi6Aga7ja2zDyFWe2js4t+kp8QIRwrnp99Gd0JCbUv\n8Gc25iURw3LkuRlz57Uw9nac6qHCa4dyw390H81zfn/qX2VyYamGYObP6fcKgNkt\n5JH3RgZ7mUJFLlfNlAb+JVOmfBvVqGmze8C15n24G5ro3MDCcPM9F0E=\n-----END RSA PRIVATE KEY-----";

		System.out.println("SUT Address: "+sut_address);
		System.out.println("Private key: "+privateKey);
		
		JsonObject obj = new JsonObject();
		obj.addProperty("address", new String(sut_address));
		obj.addProperty("user", "root");
		obj.addProperty("private_key", new String(privateKey));
		obj.addProperty("logstash_ip", "172.20.0.4");
		obj.addProperty("logstash_port", "5044");
		obj.addProperty("password", "elastest");
		
		System.out.println("Payload: "+obj.toString());
		
		String URL = server;
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		

		HttpEntity<String> request = new HttpEntity<String>(
				obj.toString(), headers);
		
		ResponseEntity<JsonObject> response = restTemplate.exchange(URL,  HttpMethod.POST, request, JsonObject.class);
		agentID = response.getBody().get("agentId");
		
		System.out.println("############ Response for Test1: ############");
		System.out.println(response);
		
		TimeUnit.SECONDS.sleep(160); 

		
		Assertions.assertEquals(200, response.getStatusCode().value());
		
		
	}

	 @Test
	 public void b_Test() throws InterruptedException {
		 
		String uri_packetloss_action = "controllability/"+agentID+"/packetloss";
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

		TimeUnit.SECONDS.sleep(160); 
		
		Assertions.assertEquals(200, response.getStatusCode().value());
	  
	 }
	 
	 @Test
	 public void c_Test() throws InterruptedException {
		 
		String uri_packetloss_action = "controllability/"+agentID+"/packetloss";
		String URL = server + uri_packetloss_action;
		 
		JsonObject obj = new JsonObject();
		obj.addProperty("exec", "EXECBEAT");
		obj.addProperty("component", "EIM");
		obj.addProperty("packetLoss", "");
		obj.addProperty("stressNg", "4");
		obj.addProperty("dockerized", "yes");
		obj.addProperty("cronExpression", "@every 60s");
		
		System.out.println("Payload: "+obj.toString());
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		

		HttpEntity<String> request = new HttpEntity<String>(
				obj.toString(), headers);
		
		ResponseEntity<String> response = restTemplate.exchange(URL,  HttpMethod.POST, request, String.class);
		System.out.println("############ Response for Test3: ############");
		System.out.println(response);
		
		TimeUnit.SECONDS.sleep(160); 
		
		Assertions.assertEquals(200, response.getStatusCode().value());
	  
	 }
	
	 @Test
	 public void d_Test() throws InterruptedException {
		String uri_unistall_agent = agentID+"/unmonitor"; 
		TimeUnit.SECONDS.sleep(160);
		 
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		 
		String URL = server +  uri_unistall_agent;
		 
		HttpEntity<String> request = new HttpEntity<String>("", headers);
		ResponseEntity<String> response = restTemplate.exchange(URL,  HttpMethod.DELETE, request, String.class);
		
		System.out.println("############ Response for Test4: ############");
		System.out.println(response);
		
		
		Assertions.assertEquals(200, response.getStatusCode().value());
 
	 }
	 
	 @Test
	 public void e_Test() throws InterruptedException {
		 
		 TimeUnit.SECONDS.sleep(160);
		 
		 headers.setContentType(MediaType.APPLICATION_JSON);
		 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		 
		 String URL = server+agentID;
		 
		 HttpEntity<String> request = new HttpEntity<String>("", headers);
		 ResponseEntity<String> response = restTemplate.exchange(URL,  HttpMethod.DELETE, request, String.class);
		 
		 System.out.println("############ Response for Test5: ############");
		 System.out.println(response);
		 
	 }

}
