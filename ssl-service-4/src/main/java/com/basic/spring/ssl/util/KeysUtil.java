package com.basic.spring.ssl.util;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class KeysUtil {

	public RSAPrivateKey getPemPrivateKey(File privKeyFile) {
		// read private key file
		byte[] privKeyBytes = null;
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(privKeyFile));
			privKeyBytes = new byte[(int) privKeyFile.length()];
			dis.readFully(privKeyBytes);
			dis.close();
		} catch (IOException e) {
			e.getMessage();
		}

		String temp = new String(privKeyBytes);
		String privKeyPEM = temp.replace("-----BEGIN PRIVATE KEY-----\n", "");
		privKeyPEM = privKeyPEM.replace("-----END PRIVATE KEY-----", "");
		privKeyPEM = privKeyPEM.replace("\n", "");
		Base64 b64 = new Base64();
		byte[] decoded = b64.decode(privKeyPEM);

		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.getMessage();
		}

		// decode private key
		PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(decoded);
		RSAPrivateKey privKey = null;
		try {
			privKey = (RSAPrivateKey) keyFactory.generatePrivate(privSpec);

		} catch (InvalidKeySpecException e) {
			e.getMessage();
		}
		return privKey;
	}

	public PublicKey getPemPublicKey(RSAPrivateCrtKey rsaPrivateKey) {

		PublicKey publicKey = null;
		try {
			publicKey = KeyFactory.getInstance("RSA").generatePublic(
					new RSAPublicKeySpec(rsaPrivateKey.getModulus(), rsaPrivateKey.getPublicExponent()));
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.getMessage();
		}
		return publicKey;
	}
}
