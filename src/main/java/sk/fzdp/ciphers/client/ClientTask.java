package sk.fzdp.ciphers.client;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class ClientTask implements Runnable {
	private RestTemplate restTemplate;

	private String cezarUrl;
	
	private String reciprocalUrl;

	private String swappairsUrl;

	private boolean isRunning = false;

	public boolean isRunning() {
		return isRunning;
	}
	
	ClientTask(RestTemplate restTemplate, String cezarUrl, String reciprocalUrl, String swappairsUrl) {
		this.restTemplate = restTemplate;
		this.cezarUrl = cezarUrl;
		this.reciprocalUrl = reciprocalUrl;
		this.swappairsUrl = swappairsUrl;
	}

	@Override
	public void run() {
		isRunning = true;
		System.out.println("Starting task...");
		try {
		while (isRunning) {
			System.out.println(callCezar("TestStringAbCdEf"));
			sleep(3);
			System.out.println(callReciprocal("TestStringAbCdEf"));
			sleep(3);
			System.out.println(callSwappairs("TestStringAbCdEf"));
			sleep(3);
		}
		} catch (Exception e) {
			System.out.println("Exception in run: " + e.getMessage());
		}
	}

	public void stop() {
		isRunning = false;
		System.out.println("Finishing task...");
	}

	private void sleep(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
	}

	private String callCezar(String text) {
		try {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		System.out.println("Calling " + cezarUrl + "/cezar/encode/" + text);
		return restTemplate.exchange(cezarUrl + "/cezar/encode/" + text, HttpMethod.GET, entity, String.class)
				.getBody();
		}
		catch (Exception e) {
			System.out.println("Exception in run: " + e.getMessage());
			return null;
		}
	}

	private String callReciprocal(String text) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			System.out.println("Calling " + reciprocalUrl + "/reciprocal/encode/" + text);
			return restTemplate.exchange(reciprocalUrl + "/reciprocal/encode/" + text, HttpMethod.GET, entity, String.class)
					.getBody();
		}
		catch (Exception e) {
			System.out.println("Exception in run: " + e.getMessage());
			return null;
		}
	}

	private String callSwappairs(String text) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<String> entity = new HttpEntity<String>(headers);
			System.out.println("Calling " + swappairsUrl + "/swappairs/encode/" + text);
			return restTemplate.exchange(swappairsUrl + "/swappairs/encode/" + text, HttpMethod.GET, entity, String.class)
					.getBody();
		}
		catch (Exception e) {
			System.out.println("Exception in run: " + e.getMessage());
			return null;
		}
	}
}
