package com.basic.spring.ssl.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;

@Component
public class JWSUtil {

	public JWSObject signJWSwithRSA(byte[] data, PublicKey publicKey, RSAPrivateKey privKey) {

		JWK jwk = new RSAKey.Builder((RSAPublicKey) publicKey).build();
		System.out.println("Printing key type---");
		
		// Prepare JWS object with simple string as payload
		JWSObject jwsObject = new JWSObject(new JWSHeader.Builder(JWSAlgorithm.RS256).jwk(jwk).build(),
				new Payload(data));

		// Create RSA-signer with the private key
		JWSSigner signer = new RSASSASigner(privKey);

		try {
			// Compute the RSA signature
			jwsObject.sign(signer);

		} catch (JOSEException e) {
		}
		return jwsObject;
	}

	public String computeHashfromPublicKey(PublicKey publicKey) {
		byte[] decodekey = publicKey.getEncoded();
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
		}
		byte[] hash = digest.digest(decodekey);
		return Base64.encodeBase64URLSafeString(hash);
	}
}
