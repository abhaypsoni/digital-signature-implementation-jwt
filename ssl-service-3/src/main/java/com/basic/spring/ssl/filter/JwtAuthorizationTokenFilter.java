package com.basic.spring.ssl.filter;

import java.io.IOException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.basic.spring.ssl.utility.KeysUtil;
import com.basic.spring.ssl.utility.RSAUtility;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;

@Component("loggingFilter")
public class JwtAuthorizationTokenFilter implements Filter {

	@Autowired
	KeysUtil keysUtil;

	@Autowired
	Environment environment;

	@Autowired
	RSAUtility rsaUtility;

	private static final String AUTH_HEADER = "Authorization";
	private static final String AUTH_ERROR_101 = "error 101";

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("inside doFilter");
		HttpServletRequest req = (HttpServletRequest) request;
		String authHeader = req.getHeader(AUTH_HEADER);
		System.out.println("authHeader is::::"+authHeader);
		if (authHeader != null) {
			try {
				JWSObject jwsObj = JWSObject.parse(authHeader);

				RSAPrivateKey privKey = rsaUtility.getRSAPrivateKey();
				RSAPrivateCrtKey rsaPrivateCrtKey = rsaUtility.getRSAPrivateCrtKey(privKey);
				System.out.println("rsaPrivateKey is:" + privKey);
				PublicKey publicKey = rsaUtility.getPublicKey(rsaPrivateCrtKey);

				//Verify the signature
				JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
				boolean verify = jwsObj.verify(verifier);
				System.out.println("verify value is:"+verify);

				if (verify == true) {
					chain.doFilter(request, response);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (JOSEException e) {
				e.printStackTrace();
			}

		} else {
			sendErrorResponse(AUTH_ERROR_101, response);
			return;
		}
	}

	private void sendErrorResponse(String authError101, ServletResponse response) throws IOException {
		String errorResponse = "Invalid request";
		response.getOutputStream().write(errorResponse.getBytes());
	}
}
