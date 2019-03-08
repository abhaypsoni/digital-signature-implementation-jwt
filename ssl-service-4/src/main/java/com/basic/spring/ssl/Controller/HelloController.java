package com.basic.spring.ssl.Controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping("/service4")
	public String hello() {
		return "Hello from ssl-service-4";
	}
}
