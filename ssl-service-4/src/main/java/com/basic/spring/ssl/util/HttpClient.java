/**
 * 
 */
package com.basic.spring.ssl.util;

import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

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

import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;

@Component
public class HttpClient implements CommandLineRunner {

	@Autowired
	private RestTemplate template;

	@Autowired
	RsaExample rsaEg;
	
	@Autowired
	KeysUtil keysUtil;
	
	@Autowired
	Environment environment;
	
	@Autowired
	RsaExample rsaUtility;
	

	@Override
	public void run(String... args) throws Exception {
		System.out.println("--------------------------Calling service 3 from service 4------------------------");

		String requestBody = rsaEg.getRequestBody();
		System.out.println("the request body is:"+requestBody);
		HttpHeaders headers = createHttpHeaders(requestBody);
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		String url = "http://localhost:8081/service3";
		System.out.println("URL is:"+url);
		ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, entity, String.class);
		
		System.out.println(response.getBody());
	/*	List<String> list = entity.getHeaders().get("Authorization");
		System.out.println("The list is"+list.get(0));
		list.get(0);
		String[] split = list.get(0).split(":");
		System.out.println("Value in the key is:"+split[1]);
		
		JWSObject jwsObjectNew = JWSObject.parse(split[1]);

		
		
		RSAPrivateKey privKey = rsaUtility.getRSAPrivateKey();
		RSAPrivateCrtKey rsaPrivateCrtKey =rsaUtility.getRSAPrivateCrtKey(privKey);
		System.out.println("rsaPrivateKey is:" + privKey);
		PublicKey publicKey = rsaUtility.getPublicKey(rsaPrivateCrtKey);
		
		
		JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
		System.out.println(jwsObjectNew.verify(verifier));
		System.out.println(jwsObjectNew.getPayload().toString());*/
		System.out.println(response.getStatusCodeValue());
	}

	private HttpHeaders createHttpHeaders(String requestBody) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", requestBody);
		return headers;
	}

}
