package com.ibm.gbs.ems.controllers;

import java.net.URI;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.gbs.ems.bo.ExpenseClaim;
import com.ibm.gbs.ems.services.RegisterClaimService;
@RestController
public class RegisterClaimController {
	@Autowired
	RegisterClaimService registerClaimService;
	
	@PostMapping("/expenseClaim")
	public ResponseEntity<String> upsertUser(@Valid @RequestBody ExpenseClaim expenseClaim,Errors errors){
		URI uri=null;
		try {
			if(expenseClaim!=null && !errors.hasErrors()) {
				ExpenseClaim u=registerClaimService.saveExpenseClaim(expenseClaim);
				if(u!=null) {
					uri = new URI("");
					return ResponseEntity.created(uri).header("message", "Expense Claim created successfully with ID:"+u.getId()).build();
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
	@GetMapping("/expenseClaim/{id}")
	public ResponseEntity<ExpenseClaim> getUserForId(@PathVariable String id){
		try {
			if(id!=null && !id.isEmpty()) {
				ExpenseClaim u=registerClaimService.getExpenseClaimId(id);
				if(u!=null) {
					return ResponseEntity.ok(u);
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
	@DeleteMapping("/expenseClaim/{id}")
	public ResponseEntity<ExpenseClaim> deleteUser(@PathVariable String id){
		try {
			if(id!=null && !id.isEmpty()) {
				boolean isDeleted=registerClaimService.deleteExpenseClaim(id);
				if(isDeleted) {
					return ResponseEntity.status(HttpStatus.OK).header("message", "Deleted successfully").build();
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
	@GetMapping("/expenseClaim/approve/{id}")
	public ResponseEntity<String> approveClaim(@PathVariable String id){
		try {
			if(id!=null && !id.isEmpty()) {
				boolean approved=registerClaimService.approveClaim(id);
				if(approved) {
					return ResponseEntity.ok("");
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
	@GetMapping("/expenseClaim/reject/{id}")
	public ResponseEntity<String> rejectClaim(@PathVariable String id){
		try {
			if(id!=null && !id.isEmpty()) {
				boolean approved=registerClaimService.rejectClaim(id);
				if(approved) {
					return ResponseEntity.ok("");
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
