/**
 * 
 */
package com.basic.spring.ssl.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpClient implements CommandLineRunner {

	@Autowired
	private RestTemplate template;

	@Autowired
	KeysUtil keysUtil;

	@Autowired
	Environment environment;

	@Autowired
	RSAUtilitySSL4 rsaUtility;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("--------------------------Calling service 3 from service 4------------------------");

		String requestBody = rsaUtility.getRequestBody();
		System.out.println("the request body is:" + requestBody);
		HttpHeaders headers = createHttpHeaders(requestBody);
		 HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		String url = "http://localhost:8081/service3";
		System.out.println("URL is:" + url);
		ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, entity, String.class);
		System.out.println(response.getBody());
		System.out.println(response.getStatusCodeValue());
	}

	private HttpHeaders createHttpHeaders(String requestBody) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", requestBody);
		return headers;
	}

}
