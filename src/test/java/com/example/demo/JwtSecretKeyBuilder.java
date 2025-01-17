package com.example.demo;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;

public class JwtSecretKeyBuilder {

		@Test
		public void generateJwtSecretKey() {
			
			SecretKey secretKey = Jwts.SIG.HS512.key().build();
			String encryptedSecretKey = DatatypeConverter.printHexBinary(secretKey.getEncoded());
			System.out.println("Key " +encryptedSecretKey);
		}
}
