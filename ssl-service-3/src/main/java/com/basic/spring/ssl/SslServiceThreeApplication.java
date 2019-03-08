package com.basic.spring.ssl;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@SpringBootApplication
public class SslServiceThreeApplication {
	static {
		//System.setProperty("javax.net.debug", "all");
		/*System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
		System.setProperty("https.protocols", "TLSv1.2");
		System.setProperty("javax.net.ssl.trustStore", "D:\\keys\\ssl-conf\\MyKeystore.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "password");
		System.setProperty("javax.net.ssl.keyStore", "D:\\keys\\ssl-conf\\MyKeystore.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", "password");
		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {

			public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
				if (hostname.equals("localhost")) {
					return true;
				}
				return false;
			}
		});
	*/}
	
	public static void main(String[] args) {
		SpringApplication.run(SslServiceThreeApplication.class, args);
	}

	@Bean
	public RestTemplate template() throws Exception {
		RestTemplate template = new RestTemplate();
		return template;
	}
	
	public static class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

        @Override
        protected Class<?>[] getRootConfigClasses() {
            return null;
        }

        @Override
        protected Class<?>[] getServletConfigClasses() {
            return null;
        }

        @Override
        protected String[] getServletMappings() {
            return null;
        }

        @Override
        protected javax.servlet.Filter[] getServletFilters() {
            DelegatingFilterProxy delegateFilterProxy = new DelegatingFilterProxy();
            delegateFilterProxy.setTargetBeanName("loggingFilter");
            return new Filter[] { delegateFilterProxy };
        }
    }

}
