package io.elastest.eim.test.e2e;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.apache.http.util.EntityUtils;
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

public class PacketLossTests {
	private String sut_address = System.getenv("ET_SUT_HOST");
	private String logstash_ip = System.getenv("ET_MON_LSBEATS_HOST");
	private String logstash_port = System.getenv("ET_MON_LSBEATS_PORT");
	private String URL_API = "http://"+sut_address+":"+"5000";
	private String server = "http://nightly.elastest.io:37004/eim/api/agent/";	
	
	public RestTemplate restTemplate = new RestTemplate();
	public HttpHeaders headers = new HttpHeaders();
	
	static String agentId;
	
	// TODO - registerAgent_then200OK()
	@Test
	public void a_Test() throws InterruptedException, IOException {
		
		System.out.println("############ Running Test1 - Register Agent: ############");
		
		String privateKey = "-----BEGIN RSA PRIVATE KEY-----\nMIIEpgIBAAKCAQEArZI1xoUduNlXUKA9CprHQ4R+so53rfMyNoURWO4IIbfE/FY6\n7Cr4rLE8iimqvveoN7qdyMwzYAldYoMY284vanolVOpncrd+7yLMiGPhuXIfwgbe\nngVLy0H4HHeOIoYd0oDDxqc3wOKOOSAiZ1VGnuODNdUwRce4tDFUi3V+a2Kxl0MH\nLQEoKB6lk2Wxv/zrPBxxuqSBRxZXQTxjMoYbUsssPuSOXBtpoNv2YH3uxOO+PyWg\n5EH507EaVaEelCQnDL64VtWbMI39ppIVCeM/Yb9riMkc2fWlLlN9jEiGGTxzLP6D\nATPn7I8OE+MqIcVX+0IIFZbU2cknSRe9Ar1/IQIDAQABAoIBAQCeMbsnY13Sguxs\nxI694pGofNLIxMZZWWUzgZZs+g2ZRZeY8LKlWwjoxGTL++vuP2Qm3sQ+JbbRWdeu\nGJQIieR3ZDowKa/9QzbCl3HZAUfSsCw3t/EgDu2kpEyRDXoTBy+ZEtjcbr2G5DYB\n5MLWcUd7Oxr0boifoMc8HBXVQVHmZj31wioJgqaEc1bniNaARAbpVaypYbCQoOqY\nGUJznZcip2lSvFgt9CEgk1/QGwQ2ochEltngWfnvj6ZyMwxag+gRGe02bfQqlhjT\npzX7s00M8x7S5XqURSnJs4WrlTNOxWi9d0yfHte9bgRcfWoAJ7F4ub30Ko/E+QC3\nZcEyCkMBAoGBANVnhG6BmKYc2k1D+TFbE4pdsVAVybanRUDBs4yVVCHx8yBG+ZOJ\ng/6rKnW1/iIdQavyhu/wUqjGKIW91eKU41+WOJUO+xiHP8SpKsqdUV3UCUVtrk3j\n3dymf4X2eJI1L07eWnk1Mj9jZExwQCOkSjNLhqp3JXxssu6C7xI1olxRAoGBANA3\nTeVMWUWEsxCd1uTktK7GqeKkKcJUacV+tT3zk7THBXOUOJw9ke55KR/KSLdGKMgN\nqf/qQyFNMqGfZ0s2vcHbZYIPuZVtSgrH0jvNEc2HYoPfULxTzggYVojwwC5eFZ8T\nqZ9Vc+UTtqYCnxoG7SDADk/OUFgqq2J4PVBkMtHRAoGBAJwtB/Q/j9FxqCmHp2UB\nfEl0zAEKEPdFbx83Iwmtd0boZL+ocZoRt7G23noK2JNqydbmxO3v+O5fJuTJyy/p\nOoWH0Jz1u7l1sathCloBy7xZIvfWjwudyY5jo0wM1qxZk+eqPpgZ4E4XlR2DwMzU\nvSDRExrJ23s1qbV3yTKQYeQhAoGBAMw48lYtBIKDHX720YfAKtgiUw7RJYRNd1EW\nbn5NIlr1ugsbHktguHnGrOdZaOkaD3XosQ0poJ5RMZAE4TOMhaJWpsFi2wwNHcpl\nI3c0NHn6iE4AAxg0uqiF9ppqSJUyAtL8XirBjeLp6wP5HkRQ9tRgi6kLGxLfByqV\n9M2c+ZuBAoGBANGNGzm3740XNYUpLkrYlkSBIb975CRA+xT+QKV3470+Z6X7x+ZM\nVKQYQWs3AcxO0wPRvuR3BYFL0tWdLK6e+VQSssw56OYaNiD28sRLeYQIOb/vMMkc\nlX00YAGV3FosCgJMQuXHQ8oOCaVtQfDA7e5mibLrqnL4JaatjicxLfAx\n-----END RSA PRIVATE KEY-----";

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
	public void b_Test() throws InterruptedException, IOException{
		System.out.println("############ Running Test2: No injection packetloss rule: ############");
		long start = System.nanoTime();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		HttpEntity<String> request = new HttpEntity<String>("", headers);
		
		ResponseEntity<String> response = restTemplate.exchange(URL_API,  HttpMethod.GET, request, String.class);
		System.out.println(response);
		
		EntityUtils.consume((org.apache.http.HttpEntity) response);
		long elapsedTime = System.nanoTime() - start ;
		System.out.println("Timing of http request nanoseconds" + elapsedTime);
		
		System.out.println("Timing of http request seconds:" + TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.SECONDS));
		
		Assertions.assertEquals(200, response.getStatusCode().value());

	}

	 @Test
	 public void c_Test() throws InterruptedException {
		System.out.println("############ Running Test3 TCP 0.25%: ############");
		System.out.println("Inyection: Iptables -A INPUT -m statistic --mode random --probability 0.25 -j DROP");

		String uri_packetloss_action = "controllability/"+agentId+"/packetloss";
		String URL = server + uri_packetloss_action;
				
		JsonObject obj = new JsonObject();
		obj.addProperty("exec", "EXECBEAT");
		obj.addProperty("component", "EIM");
		obj.addProperty("packetLoss", "0.25");
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
		
		TimeUnit.SECONDS.sleep(60);
		
		Assertions.assertEquals(200, response.getStatusCode().value());
			  	  
	 }
	 
	 @Test
	 public void d_Test() throws InterruptedException, IOException{
			System.out.println("############ Running Test4: Timing for [0.25% packetloss] Max.timing 5ms: ############");
			long start = System.nanoTime();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			
			HttpEntity<String> request = new HttpEntity<String>("", headers);
			
			ResponseEntity<String> response = restTemplate.exchange(URL_API,  HttpMethod.GET, request, String.class);
			System.out.println(response);
			
			EntityUtils.consume((org.apache.http.HttpEntity) response);
			long elapsedTime = System.nanoTime() - start ;
			System.out.println("Timing of http request nanoseconds" + elapsedTime);
			System.out.println("Timing of http request seconds:" + TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.SECONDS));
			
			
			// 1 second  = 1_000ms
			TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.SECONDS);
			double elapesedTimeInMiliSeconds = (elapsedTime / 1000);
			
			Assertions.assertTrue(elapesedTimeInMiliSeconds <= 5000.0, "Max Timing is 5ms. Reported: " +elapesedTimeInMiliSeconds+" miliseconds" );
			
		}
	 
	
	 @Test
	 public void e_Test() throws InterruptedException {
		System.out.println("############ Running Test4: ############");

		String uri_unistall_agent = agentId+"/unmonitor"; 

		 
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		 
		String URL = server +  uri_unistall_agent;

		HttpEntity<String> request = new HttpEntity<String>("", headers);

		ResponseEntity<String> response = restTemplate.exchange(URL,  HttpMethod.DELETE, request, String.class);
		
		System.out.println("############ Response for Test4: ############");
		System.out.println(response);
		//TimeUnit.SECONDS.sleep(180);

		Assertions.assertEquals(200, response.getStatusCode().value());
 
	 }
	 
	 @Test
	 public void f_Test() throws InterruptedException {
		 
		 System.out.println("############ Running Test5: ############");

		 headers.setContentType(MediaType.APPLICATION_JSON);
		 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		 
		 String URL = server+agentId;
		 		 
		 HttpEntity<String> request = new HttpEntity<String>("", headers);
		 //TimeUnit.SECONDS.sleep(500);

		 ResponseEntity<String> response = restTemplate.exchange(URL,  HttpMethod.DELETE, request, String.class);
		 
		 System.out.println("############ Response for Test5: ############");
		 System.out.println(response);
		 
         Assertions.assertEquals(200, response.getStatusCode().value());
         
         agentId = "";
         
	 }

}
