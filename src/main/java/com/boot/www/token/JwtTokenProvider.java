package com.boot.www.token;

import java.util.Base64;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

//참고 사이트 https://yjh5369.tistory.com/entry/Spring-Boot%EC%97%90%EC%84%9C-JWT-%EC%82%AC%EC%9A%A9%ED%95%98%EB%8A%94-%EB%B0%A9%EB%B2%95
//토큰 프로바이더
@Slf4j
@Component
public class JwtTokenProvider {
	
	 private static final String JWT_SECRET = "mySecretKey!@";
	 
	 private static final int JWT_EXPIRATION_MS = 604800000;
	 
	 /*
	 public static String generateToken(Authentication authentication) {
		 Date now = new Date();
		 Date expiryDate = new Date(now.getTime()+JWT_EXPIRATION_MS);
		 
		 String token = Jwts.builder()
				 .setSubject((String) authentication.getPrincipal())
				 .setIssuedAt(new Date())
				 .setExpiration(expiryDate)
				 .claim("userId", authentication.getPrincipal())
				 .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
				 .compact(); 
		 return token;
	 }
	 */
	 
	
	 public static String generateToken(Authentication authentication) {
		 Date now = new Date();
		 Date expiryDate = new Date(now.getTime()+JWT_EXPIRATION_MS);
		 String jwt_secret =  JWT_SECRET;
		 //String jwt_secret =  Base64.getEncoder().encodeToString(JWT_SECRET.getBytes());
		//암호화 하라고 했는데... validateToken함수에서 인증할때 또 암호화 해야되서 일단 보류
		 
		 String token = Jwts.builder()
				 .setSubject((String) authentication.getPrincipal())
				 .setIssuedAt(new Date())
				 .setExpiration(expiryDate)
				 .claim("userId", authentication.getPrincipal())
				 .signWith(SignatureAlgorithm.HS512, jwt_secret)
				 .compact(); 
		 return token;
	 }
	 
	 public static String getUserIdFromJWT(String token) {
		 Claims claims = Jwts.parser()
				 .setSigningKey(JWT_SECRET)
				 .parseClaimsJws(token)
				 .getBody();
		 
		 return claims.getSubject();
	 }
	 
	 public static boolean validateToken(String token) {
		 try {
			 Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
			 return true;
		 } catch(SignatureException e) {
			 log.error("Invalid JWT signature",e);
		 } catch(MalformedJwtException e) {
			 log.error("Invalid JWT token",e);
		 } catch(ExpiredJwtException e) {
			 log.error("Expired JWT token",e);
		 } catch(UnsupportedJwtException e) {
			 log.error("Unsupported JWT token",e);
		 } catch(IllegalArgumentException e) {
			 log.error("JWT claims string is empty",e);
		 }
		 return false;
	 }
}
