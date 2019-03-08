package com.basic.spring.ssl.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component("loggingFilter")
public class JwtAuthorizationTokenFilter implements Filter {

	@Override
    public void init(FilterConfig config) throws ServletException {
    }
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("inside doFilter");
		HttpServletRequest req = (HttpServletRequest) request;
		String header = req.getHeader("Authorization");
		System.out.println(header);
		chain.doFilter(request, response);
	}
}
