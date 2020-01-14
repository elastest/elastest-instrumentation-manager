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

public class PacketLossGKETests25 {

	//private String sut_address = System.getenv("ET_SUT_HOST");
	private String sut_address = "35.240.45.54";
	private String URL_API = "http://"+sut_address+":5000";
	//private String logstash_ip = System.getenv("ET_MON_LSBEATS_HOST");
	private String logstash_ip = "nightly.elastest.io";
	//private String logstash_port = System.getenv("ET_MON_LSBEATS_PORT");
	private String logstash_port = "37502";
	private String server = "http://nightly.elastest.io:37004/eim/api/agent/";	
	public RestTemplate restTemplate = new RestTemplate();
	public HttpHeaders headers = new HttpHeaders();
	private Double latency = 150.0;
	static String agentId="iagent0";
	
	@Test
	public void a_Test() throws InterruptedException, IOException{
		System.out.println("############ Running Test 1: No injection cpu commands : ############");
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
			System.out.println("SLO latency is <= "+latency+". Actual latency is: "+elapesedTimeInMiliSeconds+" ms");
			responseCode= response.getStatusCode().value();

			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
		
		Assertions.assertEquals(200, responseCode);
	}
	
	@Test
	 public void b_Test() throws InterruptedException {
		System.out.println("############ Running test 2: Droping 0.25% packet (No Execbeat) ############");

		String uri_packetloss_action = "controllability/"+agentId+"/packetloss";
		String URL = server + uri_packetloss_action;
				
		JsonObject obj = new JsonObject();
		obj.addProperty("exec", "EXECBEAT");
		obj.addProperty("component", "EIM");
		obj.addProperty("packetLoss", "0.25");
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
			System.out.println("############ Running Test 3: Max.timing "+latency+" ms: ############");
			long start = System.nanoTime();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			double elapesedTimeInMiliSeconds = 0;
			
			try {
				HttpEntity<String> request = new HttpEntity<String>("", headers);
				ResponseEntity<String> response = restTemplate.exchange(URL_API,  HttpMethod.GET, request, String.class);
				System.out.println("############ Response for Test 3: ############");
				System.out.println(response);
				long elapsedTime = System.nanoTime() - start ;
				System.out.println("Timing of http request nanoseconds:" + elapsedTime);
				// 1 second  = 1_000ms
				elapesedTimeInMiliSeconds = TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
				System.out.println("SLO latency is <= "+latency+". Actual latency is: "+elapesedTimeInMiliSeconds+" ms");
								
			}catch (Exception e) {
				// TODO: handle exception
				elapesedTimeInMiliSeconds = 1_000_000;
			}
			
			Assertions.assertTrue(elapesedTimeInMiliSeconds <= latency, 
					"SLO latency is <= "+latency+" ms. Actual latency reported by user is: " +elapesedTimeInMiliSeconds+" ms" );
			
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
