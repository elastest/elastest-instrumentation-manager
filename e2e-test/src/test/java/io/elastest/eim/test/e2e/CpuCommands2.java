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

public class CpuCommands2 {
	private String sut_address = System.getenv("ET_SUT_HOST");
	private String logstash_ip = System.getenv("ET_MON_LSBEATS_HOST");
	private String logstash_port = System.getenv("ET_MON_LSBEATS_PORT");
	private String URL_API = "http://"+sut_address+":"+"5000";
	private String server = "http://nightly.elastest.io:37004/eim/api/agent/";	
	private Double latency = 150.0;
	
	public RestTemplate restTemplate = new RestTemplate();
	public HttpHeaders headers = new HttpHeaders();
	
	static String agentId;
	
	// TODO - registerAgent_then200OK()
	@Test
	public void a_Test() throws InterruptedException, IOException {
		
		System.out.println("############ Running Test1 - Register Agent: ############");
		
		
		String privateKey = "-----BEGIN RSA PRIVATE KEY-----\nMIIEpAIBAAKCAQEAp299wI94HsLE5TwFG84RvjtUHHLD1U5F2vTWc4CfOAj0/9kT\ny0xBUi1JCb7D8xK7gM8XMhSfAv305We85/G44pyNLX5btTZok3tagHrk11qTcxdI\ng+xOoUOQjEL5oFobFcmEOd41qyinxFJyjEoH3FdE7mMD7FyO79pUyn7GWjhWzeF2\nkZXbVj7CTVInBfIx3f0cja5tTMCDA7pnUC47OaHbts9YVhTGNH3lFOl54JsAbXl3\nE+5tTyc4kQp1LfZ7aH4hDLqLUXrzoyjs0cTVuMqJx05WYiPu31R62kxYY4ZOtw32\nUJ+aUb3tLAQrgHpC2uPExUxzjR86z5P+HZFtjQIDAQABAoIBACWLC1BtGwsSsyGP\ndrnIWBQmq3KBjUW7+k/hTGCzu3/OCll/7D9OhusNOm5T9w3+6ko0pUfWdd0u4oW5\n4BLGEaXGYqWLyrZ0T7iaFS3v4HYlWiCZXOovx2XDh5rbvatl6OLWv65WFASf5hZQ\nQl0QkHionM0zKIMMMgS4GQEashEyaeoketEORrfgM5A5yQbA/HpfDEyWO3P379SW\nbpAXWig0+JxTe31My/X6kNmY6TI0yAl1WSvTRRlA2e0OZLNU6V1sITP/2xdQdw7x\niVUrj1c9jUi17DhsbnoFjuWkBlxfczL0bYAs7r0EKAmhAF21Ohk5sTOd5dXFfQQV\nyLIy+MECgYEA1QKXXlh5P2yiRQNMzBZNYz7+xm0WKvq81UyQonGgnIIIs1Mcc4dS\nUIGmWUCbiddE2XuTjgvB9MPkjyfpEevDlY3m/X/hkpvExXtHzjgqqBSz6+A3ZOpJ\n5xBKXgjlFhlooxZPThK/0sVqPPtRP636zCWIRx914k61FSzNVjPvQR0CgYEAyTo8\neOHdxpvGsUzslAzA/XWpd7Qoao8jMkv3xLiK1AzamARwk15oRGs3LCajxiMergW3\nYrJH8ef+H/YhKl3jKvB2YG8XdWxKUjFGuxRegLmElfRGDO9UiTQHHhqJoV2l5E71\nzcXw8MjHe+V+A5bkBT0ABhC/mUfVbbaoHvMrIzECgYAh61u3RldoZiAg5Tmhdhu0\nph9j8ZMKHQtc2+hcNcPhqENCawWoz++nqj2XENazyHfKOgdxIyYrl9YZhb1zgRuQ\nARy7WVXORse6urrgd8kzIrjT4sxvYW+LP+jXuIriTgF/ltniENJC+fTE6TAy971s\nLL3atYPMGcR0LsIz6+k5cQKBgQCEowxFKawDQ56+M1QlahqzdqETs/6H7n1mo8hX\nNMTdbPORDCwgFzRnFLyzL2z4JyIL1tzAA3+EpkRNUPEfee9I2GNOwSsXTR/X+X8D\nxTNdaetI5FBgKkjwfwjKAPgDEzVLvfgrgHOGYvGKawSa3RTDlyey18tS/5Rg0usS\nK3qdoQKBgQCWsWuwmemJOz607WWgzxggZxpjUn2wByMu9tXThxRqne4O0NG32rb+\n1C4qDt+NqlUZqpMh6xRfZT62G+oTk3pHHNfIWzvSNzHS3V1Ej5RdidByP9KqSul+\nxzYcQ3VJpgNDovReXWWunpXahgcyPX2Yz7XRUyb1WMd3b7rQcrwDuw==\n-----END RSA PRIVATE KEY-----";
		
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
		String body = "";
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		int responseCode = -1;
		
		try {
			
			HttpEntity<String> request = new HttpEntity<String>(obj.toString(), headers);
			ResponseEntity<String> response = restTemplate.exchange(URL,  HttpMethod.POST, request, String.class);
			body = response.getBody();
			JsonParser parser = new JsonParser();
			JsonObject json = (JsonObject) parser.parse(body);
			agentId = json.get("agentId").getAsString();
			System.out.println("############ Response for Test1: ############");
			System.out.println(response);
			responseCode= response.getStatusCode().value();
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
		
		Assertions.assertEquals(200, responseCode);

	}
	@Test
	public void b_Test() throws InterruptedException, IOException{
		System.out.println("############ Running Test2: No injection cpu commands : ############");
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
	 public void c_Test() throws InterruptedException {
		System.out.println("############ Running test 3: Cpu test stress with stressor=68 over 30 seconds : ############");

		String uri_packetloss_action = "controllability/"+agentId+"/stress";
		String URL = server + uri_packetloss_action;
				
		JsonObject obj = new JsonObject();
		obj.addProperty("exec", "EXECBEAT");
		obj.addProperty("component", "EIM");
		obj.addProperty("packetLoss", "");
		obj.addProperty("stressNg", "68");
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
	 public void d_Test() throws InterruptedException, IOException{
			System.out.println("############ Running Test4: Timing for a [cpu stressor=68]  Max.timing "+latency+" ms: ############");
			long start = System.nanoTime();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			double elapesedTimeInMiliSeconds = 0;
			
			try {
				HttpEntity<String> request = new HttpEntity<String>("", headers);
				ResponseEntity<String> response = restTemplate.exchange(URL_API,  HttpMethod.GET, request, String.class);
				System.out.println("############ Response for Test4: ############");
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
	 public void e_Test() throws InterruptedException {
		System.out.println("############ Running Test5: ############");
		String uri_unistall_agent = agentId+"/unmonitor"; 
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		String URL = server +  uri_unistall_agent;
		int responseCode = -1;
		
		try {
			HttpEntity<String> request = new HttpEntity<String>("", headers);
			ResponseEntity<String> response = restTemplate.exchange(URL,  HttpMethod.DELETE, request, String.class);
			System.out.println("############ Response for Test5: ############");
			System.out.println(response);
			//TimeUnit.SECONDS.sleep(180);
			responseCode = response.getStatusCode().value();

		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
		
		Assertions.assertEquals(200, responseCode);

	 }
	 
	 @Test
	 public void f_Test() throws InterruptedException {
		 
		 System.out.println("############ Running Test6: ############");
		 headers.setContentType(MediaType.APPLICATION_JSON);
		 headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		 String URL = server+agentId;
		 int responseCode = -1;
		 
		 try {
			 HttpEntity<String> request = new HttpEntity<String>("", headers);
			 //TimeUnit.SECONDS.sleep(500);
			 ResponseEntity<String>response= restTemplate.exchange(URL,  HttpMethod.DELETE, request, String.class);
			 System.out.println("############ Response for Test6: ############");
			 System.out.println(response);
			 responseCode = response.getStatusCode().value();
			 
		 }catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
		 
        Assertions.assertEquals(200,responseCode);
        
	 }

}
