package com.klef.jfsd.spd.tourisum.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;


import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtStorage {
	
	public static Map<String,String> storeObject(Object data) throws JsonProcessingException  {
		 
		 byte[] randomBytes = new byte[64];
	   new SecureRandom().nextBytes(randomBytes);
	   String secretKeyString = Base64.getEncoder().encodeToString(randomBytes);
	   SecretKey secretKey = Keys.hmacShaKeyFor(randomBytes);

	  // System.out.println("Generated Secret: " + secretKeyString);
	   
	   
	   ObjectMapper mapper = new ObjectMapper();
	   String jsonData = mapper.writeValueAsString(data);
	   
	  // System.out.println("object in string "+jsonData);
	   
	        Map<String, String> claims = new HashMap<>();
	        claims.put("data", jsonData);
	   
	  
	 
	 String jwtToken = Jwts.builder()
	         .setClaims(claims)
	         .signWith(secretKey, SignatureAlgorithm.HS256)
	         .compact();
	 
	// System.out.println(" Jwttocken "+jwtToken);
	 
	 String base64Secret = secretKeyString;
	 String jwttoken = jwtToken;
	 
	 Map<String,String> m1 = new HashMap<>();
	m1.put("base64Secret", base64Secret);
	m1.put("jwttoken", jwttoken);

	return m1;
		 
	 }
	 
	 public static Object getObject(Map<String,String> m1) {
		 // Decode the Base64 secret to bytes
	     byte[] decodedKey = Base64.getDecoder().decode(m1.get("base64Secret"));
	     SecretKey secretKey = Keys.hmacShaKeyFor(decodedKey);

	     // Verify & parse
	     Claims claims = Jwts.parserBuilder()
	             .setSigningKey(secretKey)
	             .build()
	             .parseClaimsJws(m1.get("jwttoken"))
	             .getBody();

	    return claims.get("data");
	     
	 }
	

}
