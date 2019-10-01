package com.ibm.gbs.ems.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.gbs.ems.JWTUtils;
import com.ibm.gbs.ems.bo.User;
import com.ibm.gbs.ems.services.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	@Autowired
	JWTUtils jWTUtils;
	@GetMapping("/")
	public String helloWorld() {
		return "hello-world";
	}
	
	@PostMapping("/user")
	public ResponseEntity<User> upsertUser(@Valid @RequestBody User user,Errors errors){
		URI uri=null;
		try {
			if(user!=null && !errors.hasErrors()) {
				User dupUser=userService.findUserByUserEmailId(user.getEmailId());
				if(dupUser==null || dupUser.getId().equals(user.getId()) ) {
					User u=userService.saveUser(user);
					if(u!=null) {
						uri = new URI("");
						String jwtToken=jWTUtils.signJWTToken(u);
						HttpHeaders responseHeaders = new HttpHeaders();
						responseHeaders.add("message", "Registration Successful");
						responseHeaders.add("access_token",jwtToken);
					    return new ResponseEntity<>(u, responseHeaders, HttpStatus.CREATED);
					}
				}else {
					String errorMessage="User already exists";
					return ResponseEntity.status(HttpStatus.CONFLICT).header("error", errorMessage).build();
				}
				
			}else if(errors!=null) {
				String errorMessage=errors.getAllErrors().stream().map(p->p.getDefaultMessage()).collect(Collectors.joining(","));
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
						.header("message", errorMessage).build();
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.created(uri).build();
		
	}
	@PostMapping("/login")
	public ResponseEntity<User> login(HttpServletRequest httpServletRequest){
		URI uri=null;
		try {
			String userName=httpServletRequest.getParameter("username");
			String password=httpServletRequest.getParameter("password");
			if(userName!=null && !userName.isEmpty() && password!=null && !password.isEmpty()) {
				User u=userService.findUserByUserName(userName,password);
				if(u!=null) {
					String jwtToken=jWTUtils.signJWTToken(u);
					uri = new URI("");
					HttpHeaders responseHeaders = new HttpHeaders();
					responseHeaders.add("message", "Login Successful");
					responseHeaders.add("access_token",jwtToken);
				    return new ResponseEntity<>(u, responseHeaders, HttpStatus.OK);
				}
			}else {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
						.header("message", "Invalid Credentails").build();
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.notFound().build();
		
	}
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserForId(@PathVariable String id){
		try {
			if(id!=null && !id.isEmpty()) {
				User u=userService.getUeserForId(id);
				if(u!=null) {
					return ResponseEntity.status(HttpStatus.OK).header("message", "User retrieved successfully.").body(u);
				}
			}else  {
				String errorMessage="ID can not be empty";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
						.header("message", errorMessage).build();
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.notFound().build();
		
	}
	@GetMapping("/user")
	public ResponseEntity<List<User>> getUsers(){
		try {
				List<User> list=userService.getUesers();
					return ResponseEntity.ok(list);
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.notFound().build();
		
	}
	@DeleteMapping("/user/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable String id){
		try {
			if(id!=null && !id.isEmpty()) {
				boolean isDeleted=userService.deleteUser(id);
				if(isDeleted) {
					return ResponseEntity.ok().build();
				}
			}else  {
				String errorMessage="ID can not be empty";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
						.header("message", errorMessage).build();
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.notFound().build();
		
	}

}
