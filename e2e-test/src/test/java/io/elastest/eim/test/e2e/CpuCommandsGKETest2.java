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

public class CpuCommandsGKETest2 {
	private String sut_address = "35.240.45.54";
	private String URL_API = "http://"+sut_address+":5000";
	private String server = "http://nightly.elastest.io:37004/eim/api/agent/";	
	public RestTemplate restTemplate = new RestTemplate();
	public HttpHeaders headers = new HttpHeaders();
	public static Long latency;
	
	
	static String agentId="iagent0";
	
	
	@Test
	public void a_Test() throws InterruptedException, IOException{
		System.out.println("############ Running Test 1: Calculate Base SLO Latency: ############");
		long start = System.nanoTime();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		int responseCode = -1;
		long elapesedTimeInMiliSeconds = 0;
		
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
		System.out.println("Base SLO Latency before injection CPU commands is: "+latency);
		
		Assertions.assertEquals(200, responseCode);
		
	}
	
	@Test
	 public void b_Test() throws InterruptedException {
		System.out.println("############ Running test 2: Cpu test stress with stressor=50 over 30 seconds : ############");

		String uri_packetloss_action = "controllability/"+agentId+"/stress";
		String URL = server + uri_packetloss_action;
				
		JsonObject obj = new JsonObject();
		obj.addProperty("exec", "EXECBEAT");
		obj.addProperty("component", "EIM");
		obj.addProperty("packetLoss", "");
		obj.addProperty("stressNg", "100");
		obj.addProperty("dockerized", "yes");
		obj.addProperty("cronExpression", "@every 60s");
		
		
		System.out.println("Payload: "+obj.toString());
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		int responseCode = -1;
		try {
			
			HttpEntity<String> request = new HttpEntity<String>(
					obj.toString(), headers);
			
			ResponseEntity<String> response = restTemplate.exchange(URL,  HttpMethod.POST, request, String.class);
			System.out.println("############ Response for Test3: ############");
			System.out.println(response);
			TimeUnit.SECONDS.sleep(60);
			responseCode = response.getStatusCode().value();

			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
		
		Assertions.assertEquals(200, responseCode);
			  	  
	 }
	
	@Test
	public void c_Test() throws InterruptedException, IOException{
		System.out.println("############ Running Test 3: Calculate SLO Latency after CPU injection command: ############");
		long start = System.nanoTime();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		int responseCode = -1;
		long elapesedTimeInMiliSeconds = 0;
		
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
		System.out.println("SLO Latency after packetloss injection command is: "+latency);
		
		Assertions.assertEquals(200, responseCode);
		
		

	}
	
	 @Test
	 public void d_Test() throws InterruptedException {
		System.out.println("############ Running test 4: SLO Max.timing is: "+latency+". ############");
		long start = System.nanoTime();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		int responseCode = -1;
		long elapesedTimeInMiliSeconds = 0;
		
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
		
		Assertions.assertTrue(latency <= elapesedTimeInMiliSeconds, 
				"SLO latency is <= "+latency+". Actual latency is: "+elapesedTimeInMiliSeconds+" ms");
		
		
	 }
	
	 @Test
	 public void e_Test() throws InterruptedException {
		System.out.println("############ Running Test 5: unchecked agent ############");

		String uri_unistall_agent = agentId+"/unchecked"; 
		String URL = server +  uri_unistall_agent;
		 
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		 
		HttpEntity<String> request = new HttpEntity<String>("", headers);
		ResponseEntity<String> response = restTemplate.exchange(URL,  HttpMethod.DELETE, request, String.class);
		
		System.out.println("############ Response for Test 5: unchecked agent ############");
		System.out.println(response);

		Assertions.assertEquals(200, response.getStatusCode().value());
 
	 }


}
