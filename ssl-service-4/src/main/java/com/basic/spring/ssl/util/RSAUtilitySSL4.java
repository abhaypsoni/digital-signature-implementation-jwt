package com.basic.spring.ssl.util;

import java.io.File;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.nimbusds.jose.JWSObject;

@Component
public class RSAUtilitySSL4 {
	@Autowired
	KeysUtil keysUtil;

	@Autowired
	JWSUtil JWSUtil;

	@Autowired
	Environment environment;

	public String getRequestBody() {

		RSAPrivateKey privKey = getRSAPrivateKey();
		RSAPrivateCrtKey rsaPrivateCrtKey = getRSAPrivateCrtKey(privKey);

		System.out.println("rsaPrivateKey is:" + privKey);

		PublicKey publicKey = getPublicKey(rsaPrivateCrtKey);

		System.out.println("----Printing the public key file---------");
		System.out.println(publicKey);

		String hashBankPublicKey = JWSUtil.computeHashfromPublicKey(publicKey);
		System.out.println("----Printing hash Bank Public key---------");

		System.out.println(hashBankPublicKey);

		//Message to be encoded/payload
		String message = "Some message";
		
		Gson gson = new Gson();
		byte[] data = gson.toJson(message).getBytes();
		System.out.println(data);

		JWSObject jwsObject = JWSUtil.signJWSwithRSA(data, publicKey, privKey);

		String requestdata = jwsObject.serialize();

		/*
		 * JSONObject jwsObj = new JSONObject();
		 * 
		 * jwsObj.put("jws", requestdata); return jwsObj.toString();
		 */
		return requestdata;
	}

	public RSAPrivateKey getRSAPrivateKey() {
		String fileName = environment.getProperty("hyperledger.private.key");

		System.out.println("filename is::" + fileName);
		File privKeyFile = new File(fileName);

		RSAPrivateKey privKey = keysUtil.getPemPrivateKey(privKeyFile);
		return privKey;
	}

	public RSAPrivateCrtKey getRSAPrivateCrtKey(RSAPrivateKey privKey) {

		RSAPrivateCrtKey rsaPrivateKey = (RSAPrivateCrtKey) privKey;
		return rsaPrivateKey;
	}

	public PublicKey getPublicKey(RSAPrivateCrtKey rsaPrivateKey) {
		PublicKey publicKey = keysUtil.getPemPublicKey(rsaPrivateKey);
		return publicKey;

	}
}
