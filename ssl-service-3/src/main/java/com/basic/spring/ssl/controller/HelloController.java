package com.basic.spring.ssl.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@RequestMapping("/service3")
	public String hello() {
		return "Hello from service 3";
	}
}
