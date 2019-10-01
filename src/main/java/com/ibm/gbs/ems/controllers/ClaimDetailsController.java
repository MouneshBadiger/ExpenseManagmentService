package com.ibm.gbs.ems.controllers;

import java.net.URI;
import java.util.stream.Collectors;

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

import com.ibm.gbs.ems.bo.ExpenseClaim;
import com.ibm.gbs.ems.bo.ExpenseClaimDetails;
import com.ibm.gbs.ems.services.ClaimDetailsService;
import com.ibm.gbs.ems.services.RegisterClaimService;
@RestController
public class ClaimDetailsController {
	@Autowired
	ClaimDetailsService claimDetailsService;
	
	@PostMapping("/expenseClaimDetails")
	public String upsertUser(@Valid @RequestBody ExpenseClaimDetails expenseClaimDetails,Errors errors){
		try {
			if(expenseClaimDetails!=null && !errors.hasErrors()) {
				
				String valStr=claimDetailsService.validateClaimDetails(expenseClaimDetails);
				if(valStr.isEmpty()) {
					ExpenseClaimDetails u=claimDetailsService.saveExpenseClaim(expenseClaimDetails);
					if(u!=null) {
						String message="Claim Details Added";
						return message;
					}
				}else {
				   return valStr;
				}
				
			}else if(errors!=null) {
				String errorMessage=errors.getAllErrors().stream().map(p->p.getDefaultMessage()).collect(Collectors.joining(","));
				return errorMessage;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
		
	}
	@GetMapping("/expenseClaimDetails/{id}")
	public ResponseEntity<ExpenseClaimDetails> getUserForId(@PathVariable String id){
		try {
			if(id!=null && !id.isEmpty()) {
				ExpenseClaimDetails u=claimDetailsService.getExpenseClaimId(id);
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
	@DeleteMapping("/expenseClaimDetails/{id}")
	public ResponseEntity<ExpenseClaimDetails> deleteUser(@PathVariable String id){
		try {
			if(id!=null && !id.isEmpty()) {
				boolean isDeleted=claimDetailsService.deleteExpenseClaim(id);
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

}
