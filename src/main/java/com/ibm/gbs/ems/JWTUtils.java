package com.ibm.gbs.ems;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.ibm.gbs.ems.bo.User;
@Component
public class JWTUtils {
	
	@Value("${app.jwt.secret}")
	String jwtSecret;
	
	public  String signJWTToken(User user) {
		String jwtToken=null;
		try {
		    Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
		    jwtToken = JWT.create()
		        .withIssuer("auth0")
		        .sign(algorithm);
		} catch (JWTCreationException e){
		    //Invalid Signing configuration / Couldn't convert Claims.
			e.printStackTrace();
		}
		return jwtToken;
	}
	

}
