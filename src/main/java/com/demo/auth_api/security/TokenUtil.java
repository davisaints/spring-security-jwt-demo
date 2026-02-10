package com.demo.auth_api.security;

import com.demo.auth_api.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import jakarta.servlet.http.HttpServletRequest;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class TokenUtil {
	public static Authentication decode(HttpServletRequest request) {
		try {
			String header = request.getHeader("Authorization");

			if (header != null) {
				SecretKey key = Keys.hmacShaKeyFor(_SECRET.getBytes());

				JwtParser parser = Jwts.parser().verifyWith(key).build();
				String token = header.replace("Bearer ", "");

				Claims claims = (Claims)parser.parse(token).getPayload();
				Date exp = claims.getExpiration();
				String issuer = claims.getIssuer();
				String subject = claims.getSubject();

			if (issuer.equals(_ISSUER) && !subject.isEmpty() && exp.after(new Date(System.currentTimeMillis())))
				return new UsernamePasswordAuthenticationToken("valido", null, Collections.emptyList());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static MyToken encode(User user) {
		try {
			Key key = Keys.hmacShaKeyFor(_SECRET.getBytes());
			String jwtToken = Jwts.builder()
				.subject(user.getUsername())
				.expiration(new Date (System.currentTimeMillis()+ _EXPIRATION))
				.issuer(_ISSUER)
				.signWith(key)
				.compact();

			return new MyToken(jwtToken);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return null;
	}

	public static final long _EXPIRATION = 60 * 60 * 1000;

	@Value("${api.security.token.issuer}")
	private static String _ISSUER;

	@Value("${api.security.token.secret}")
	private static String _SECRET;
}