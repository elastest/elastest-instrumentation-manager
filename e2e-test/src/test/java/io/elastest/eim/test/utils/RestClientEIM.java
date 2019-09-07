package io.elastest.eim.test.utils;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class RestClientEIM {
	
	public RestTemplate restTemplate = new RestTemplate();
	public HttpHeaders headers = new HttpHeaders();
   
    public ResponseEntity<String> RegisterAgent(String privateKey, String SutAddress, String LogStashIp,
    		String LogStashPort, String server, String password, String user) {
    	
    	System.out.println("### Register Agent ###");
    	
    	JsonObject obj = new JsonObject();
		obj.addProperty("address", new String(SutAddress));
		obj.addProperty("user", user);
		obj.addProperty("private_key", new String(privateKey));
		obj.addProperty("logstash_ip", LogStashIp);
		obj.addProperty("logstash_port", LogStashPort);
		obj.addProperty("password", password);
		
		System.out.println("Payload: "+obj.toString());
		
		String URL = server;
		String body = "";
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		ResponseEntity<String> res = null;
		
		try {
			
			HttpEntity<String> request = new HttpEntity<String>(obj.toString(), headers);
			res = restTemplate.exchange(URL,  HttpMethod.POST, request, String.class);
			body = res.getBody();
			JsonParser parser = new JsonParser();
			JsonObject json = (JsonObject) parser.parse(body);
			System.out.println(json);
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
		return res;
    	
    }
    
    public HttpStatus DropPackets(String server, String agentId, String exec, String component, String dropValue, 
    		Boolean dockerized, String cronExpression ) {
    	
    	String uri_packetloss_action = "controllability/"+agentId+"/packetloss";
		String URL = server + uri_packetloss_action;
		String conversion = "";
		
		if (dockerized)
			conversion = "yes";
		else
			conversion="no";
		
		JsonObject obj = new JsonObject();
		obj.addProperty("exec", exec);
		obj.addProperty("component", component);
		obj.addProperty("packetLoss", dropValue);
		obj.addProperty("stressNg", "");
		obj.addProperty("dockerized", conversion);
		obj.addProperty("cronExpression", "cronExpression");
		
		System.out.println("Payload: "+obj.toString());
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		ResponseEntity<String> response = null;

		
		try {
			
			HttpEntity<String> request = new HttpEntity<String>(
					obj.toString(), headers);
			
			response = restTemplate.exchange(URL,  HttpMethod.POST, request, String.class);
			System.out.println(response.getStatusCode().value());
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
		return response.getStatusCode();
    }
    
    public HttpStatus StressCpu(String server, String agentId, String exec, String component, String stressValue, 
    		Boolean dockerized, String cronExpression) {
    	String uri_packetloss_action = "controllability/"+agentId+"/stress";
		String URL = server + uri_packetloss_action;
		String dockerizedValue = "no";
		
		JsonObject obj = new JsonObject();
		obj.addProperty("exec", exec);
		obj.addProperty("component", component);
		obj.addProperty("packetLoss", "");
		obj.addProperty("stressNg", stressValue);
		
		if (dockerized)
			dockerizedValue = "yes";
		
		obj.addProperty("dockerized", dockerizedValue);
		obj.addProperty("cronExpression", "cronExpression");
		
		System.out.println("Payload: "+obj.toString());
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		ResponseEntity<String> response = null;
		
		try {
			
			HttpEntity<String> request = new HttpEntity<String>(
					obj.toString(), headers);
			 response = restTemplate.exchange(URL,  HttpMethod.POST, request, String.class);
			System.out.println(response.getStatusCode().value());
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
		return response.getStatusCode();
    	
    }
    
    public HttpStatus monitor(String server, String agentId, String StreamPacketBeat, ArrayList<String> paths, 
    		String StreamFileBeat, String StreamMetricBeat, String exec, String component, Boolean dockerized) {
    	JsonObject obj = new JsonObject();
		JsonObject packetbeat = new JsonObject();
		packetbeat.addProperty("stream", new String(StreamPacketBeat));
		String dockerizedValue = "no";
		if (dockerized)
			dockerizedValue = "yes";
				
		ArrayList<String> path = new ArrayList<>();
		for (String e: paths) {
			path.add(e);
		}
		
		JsonObject filebeat = new JsonObject();
		filebeat.addProperty("stream", new String(StreamFileBeat));
		filebeat.addProperty("path",   new String(path.toString()));
		
		JsonObject metricbeat = new JsonObject();
		metricbeat.addProperty("stream", new String (StreamMetricBeat));
		
		obj.addProperty("exec", new String(exec));
		obj.addProperty("component", new String(component));
		obj.addProperty("dockerized", new String(dockerizedValue));
		
		obj.add("packetbeat", packetbeat);
		
		obj.add("filebeat", filebeat);
		
		obj.add("metricbeat", metricbeat);
		
		System.out.println("Payload data object: "+obj.getAsJsonObject());
		
		String URL = server+agentId+"/monitor";
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		ResponseEntity<String> response = null;
		
		try {
			
			HttpEntity<String> request = new HttpEntity<String>(obj.toString(), headers);
			 response = restTemplate.exchange(URL,  HttpMethod.POST, request, String.class);
			System.out.println(response.getStatusCode().value());
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
		return response.getStatusCode();
    	
    }
    
    public HttpStatus unmonitor(String server, String agentId, String StreamPacketBeat, ArrayList<String> paths, 
    		String StreamFileBeat, String StreamMetricBeat, String exec, String component, Boolean dockerized) {
    	JsonObject obj = new JsonObject();
		JsonObject packetbeat = new JsonObject();
		packetbeat.addProperty("stream", new String(StreamPacketBeat));
		String dockerizedValue = "no";
		if (dockerized)
			dockerizedValue = "yes";
				
		ArrayList<String> path = new ArrayList<>();
		for (String e: paths) {
			path.add(e);
		}
		
		JsonObject filebeat = new JsonObject();
		filebeat.addProperty("stream", new String(StreamFileBeat));
		filebeat.addProperty("path",   new String(path.toString()));
		
		JsonObject metricbeat = new JsonObject();
		metricbeat.addProperty("stream", new String (StreamMetricBeat));
		
		obj.addProperty("exec", new String(exec));
		obj.addProperty("component", new String(component));
		obj.addProperty("dockerized", new String(dockerizedValue));
		
		obj.add("packetbeat", packetbeat);
		
		obj.add("filebeat", filebeat);
		
		obj.add("metricbeat", metricbeat);
		
		System.out.println("Payload data object: "+obj.getAsJsonObject());
		
		String URL = server+agentId+"/unmonitor";
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		ResponseEntity<String> response = null;
		
		try {
			
			HttpEntity<String> request = new HttpEntity<String>(obj.toString(), headers);
			 response = restTemplate.exchange(URL,  HttpMethod.POST, request, String.class);
			System.out.println(response.getStatusCode().value());
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
		return response.getStatusCode();
    	
    }
    
    public HttpStatus delete(String server, String agentId) {
    	 headers.setContentType(MediaType.APPLICATION_JSON);
		 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		 String URL = server+agentId;
		 ResponseEntity<String>response = null;
		 try {
			 HttpEntity<String> request = new HttpEntity<String>("", headers);
			 //TimeUnit.SECONDS.sleep(500);
			 response= restTemplate.exchange(URL,  HttpMethod.DELETE, request, String.class);
			 System.out.println(response.getBody());
			 
		 }catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
		return response.getStatusCode();
    	
    }
    
    
   

}
