package com.ibm.gbs.ems;

import java.util.Date;

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
			Date day7=new Date();
			day7.setDate(day7.getDate()+7);
		    Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
		    jwtToken = JWT.create()
		        .withIssuer("auth0")
		        .withExpiresAt(day7)
		        .sign(algorithm);
		} catch (JWTCreationException e){
		    //Invalid Signing configuration / Couldn't convert Claims.
			e.printStackTrace();
		}
		return jwtToken;
	}
	

}
