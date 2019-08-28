package com.ibm.gbs.ems;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AuthenticationFilter  implements Filter{
	
	@Value("${app.jwt.secret}")
	String jwtSecret;
	private static Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

        HttpServletRequest req = (HttpServletRequest) request;
        if(req.getRequestURI().contains("login")&& req.getRequestURI().contains("register")) {
        	chain.doFilter(request, response);
        }else {
        	String jwtToken=req.getHeader("access_token");
        	if(jwtToken!=null ) {
        		 try {
        			 Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
         		    JWTVerifier verifier = JWT.require(algorithm)
         		        .withIssuer("auth0")
         		        .build(); //Reusable verifier instance
         		    DecodedJWT jwt = verifier.verify(jwtToken);
         		   log.info("authenticating : {}", req.getRequestURI());
                   chain.doFilter(request, response);
				} catch (Exception e) {
					  log.error("authention failed", req.getRequestURI());
				}
        	}
        }
	}

}
