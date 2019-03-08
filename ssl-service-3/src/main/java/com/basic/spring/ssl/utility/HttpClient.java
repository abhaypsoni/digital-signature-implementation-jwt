package com.basic.spring.ssl.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpClient implements CommandLineRunner {


	@Autowired
	private RestTemplate template;

	@Override
	public void run(String... args) {
		/*System.out.println("--------------------------Calling service 4 from service 3------------------------");
		ResponseEntity<String> response = template.getForEntity("http://localhost:8082/service4",String.class);
		System.out.println(response.getBody());*/
		System.out.println("Just printing");
	}

}
