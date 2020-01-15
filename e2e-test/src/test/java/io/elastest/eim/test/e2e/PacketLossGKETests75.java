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

public class PacketLossGKETests75 {

	private String sut_address = "35.240.45.54";
	private String URL_API = "http://"+sut_address+":5000";
	private String server = "http://nightly.elastest.io:37004/eim/api/agent/";	
	public RestTemplate restTemplate = new RestTemplate();
	public HttpHeaders headers = new HttpHeaders();
	public static Double latency = 100.0;
	
	
	static String agentId="iagent0";
	
	
	@Test
	public void a_Test() throws InterruptedException, IOException{
		System.out.println("############ Running Test 1: Calculate Base SLO Latency: ############");
		long start = System.nanoTime();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		int responseCode = -1;
		double elapesedTimeInMiliSeconds = 0;
		
		try {
			HttpEntity<String> request = new HttpEntity<String>("", headers);
			ResponseEntity<String> response = restTemplate.exchange(URL_API,  HttpMethod.GET, request, String.class);
			System.out.println(response);
			long elapsedTime = System.nanoTime() - start ;
			elapesedTimeInMiliSeconds = TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
			responseCode= response.getStatusCode().value();
			

		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
		latency = elapesedTimeInMiliSeconds;
		System.out.println("Base SLO Latency is: "+latency);
		
		Assertions.assertEquals(200, responseCode);
	}
	
	@Test
	 public void b_Test() throws InterruptedException {
		System.out.println("############ Running test 2: Droping 0.75% packet (No Execbeat) ############");

		String uri_packetloss_action = "controllability/"+agentId+"/packetloss";
		String URL = server + uri_packetloss_action;
				
		JsonObject obj = new JsonObject();
		obj.addProperty("exec", "EXECBEAT");
		obj.addProperty("component", "EIM");
		obj.addProperty("packetLoss", "0.75");
		obj.addProperty("stressNg", "");
		obj.addProperty("dockerized", "yes");
		obj.addProperty("cronExpression", "");
		
		
		System.out.println("Payload: "+obj.toString());
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		int responseCode = -1;
		try {
			
			HttpEntity<String> request = new HttpEntity<String>(
					obj.toString(), headers);
			
			ResponseEntity<String> response = restTemplate.exchange(URL,  HttpMethod.POST, request, String.class);
			System.out.println("############ Response for Test 2: ############");
			System.out.println(response);
			responseCode = response.getStatusCode().value();

			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
		
		Assertions.assertEquals(200, responseCode);
			  	  
	 }
	
	 @Test
	 public void c_Test() throws InterruptedException {
		System.out.println("############ Running test 3: SLO Max.timing is: "+latency+"  Droping 0.0% packet (No Execbeat) ############");
		long start = System.nanoTime();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		int responseCode = -1;
		double elapesedTimeInMiliSeconds = 0;
		
		try {
			HttpEntity<String> request = new HttpEntity<String>("", headers);
			ResponseEntity<String> response = restTemplate.exchange(URL_API,  HttpMethod.GET, request, String.class);
			System.out.println(response);
			long elapsedTime = System.nanoTime() - start ;
			elapesedTimeInMiliSeconds = TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
			responseCode= response.getStatusCode().value();
			

		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
		
		Assertions.assertTrue(latency < elapesedTimeInMiliSeconds, 
				"SLO latency is <= "+latency+". Actual latency is: "+elapesedTimeInMiliSeconds+" ms");
		
		
	 }
	
	 @Test
	 public void d_Test() throws InterruptedException {
		System.out.println("############ Running Test 4: unchecked agent ############");

		String uri_unistall_agent = agentId+"/unchecked"; 
		String URL = server +  uri_unistall_agent;
		 
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		 
		HttpEntity<String> request = new HttpEntity<String>("", headers);
		ResponseEntity<String> response = restTemplate.exchange(URL,  HttpMethod.DELETE, request, String.class);
		
		System.out.println("############ Response for Test 4: unchecked agent ############");
		System.out.println(response);

		Assertions.assertEquals(200, response.getStatusCode().value());
 
	 }


}
