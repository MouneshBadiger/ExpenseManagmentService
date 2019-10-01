package com.ibm.gbs.ems.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.gbs.ems.bo.ExpenseClaim;
import com.ibm.gbs.ems.bo.ExpenseClaimDetails;
import com.ibm.gbs.ems.repos.ExpenseClaimDetailsRepo;
import com.ibm.gbs.ems.repos.ExpenseClaimRepo;

@Service
public class ClaimDetailsService {
	
	@Autowired
	ExpenseClaimDetailsRepo expenseClaimDetailsRepo;
	
	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	public ExpenseClaimDetails saveExpenseClaim(ExpenseClaimDetails expenseClaim) {
		LocalDate createdDate=LocalDate.parse(LocalDate.now().format(formatter),formatter);
		 expenseClaim.setCreatedDate(createdDate);
		return expenseClaimDetailsRepo.save(expenseClaim);
	}
	public boolean  deleteExpenseClaim(String id) {
		expenseClaimDetailsRepo.deleteById(id);
		 return true;
	}
	public ExpenseClaimDetails getExpenseClaimId(String userId) {
		return expenseClaimDetailsRepo.findById(userId).get();
	}
	public String validateClaimDetails(@Valid ExpenseClaimDetails expenseClaimDetails) {
		String invalidStr="";
		 LocalDate createdDate=LocalDate.parse(LocalDate.now().format(formatter),formatter);
		List<ExpenseClaimDetails> cliamsDetailsList=expenseClaimDetailsRepo.findByUserId_IdAndCreatedDate(expenseClaimDetails.getUserId().getId(),createdDate);
		//a.	Only 1 Meal allowed, if travel starts after 3 PM
		//b.	Only 1 Breakfast allowed if travel starts before 8 AM
		//c.	Only 2 Meals allowed for the entire day.
		//d.	For every day of business travel, employee can at the most claim 1 Breakfast and 2 Meals
		//e.	Each expense detail should have attached invoice in the form of jpg or pdf
		//f.	Max amount for Breakfast claim should not exceed Rs. 400 / day
		//g.	Max amount for Meals claim should not exceed Rs. 800 / day.
		//h.	For every cash payment made, please ask for the reason.
		if(expenseClaimDetails.getTypeOfExpense().equalsIgnoreCase("meal")) {
			Long meals=cliamsDetailsList.stream().filter(p->p.getTypeOfExpense().equalsIgnoreCase("meal")).count();
			if(meals>=2) {
				invalidStr+="Only two meals are allowed per day";
				return invalidStr;
			}
			Double mealsSum=cliamsDetailsList.stream().filter(p->p.getTypeOfExpense().equalsIgnoreCase("meal")).mapToDouble(p->Double.parseDouble(p.getExpenseAmount())).sum();
			BigDecimal sum=new BigDecimal(mealsSum);
			if(sum.compareTo(new BigDecimal(800))>0) {
				invalidStr+="Max amount for Meals claim should not exceed Rs 800 day";
				return invalidStr;
				
			}
		}
		if(expenseClaimDetails.getTypeOfExpense().equalsIgnoreCase("Breakfast")) {
			Long breakfast=cliamsDetailsList.stream().filter(p->p.getTypeOfExpense().equalsIgnoreCase("Breakfast")).count();
			if(breakfast>=1 && expenseClaimDetails.getTypeOfExpense().equalsIgnoreCase("Breakfast")) {
				invalidStr+="Only one breakfast allowed per day";
				return invalidStr;
			}
			Double bfSum=cliamsDetailsList.stream().filter(p->p.getTypeOfExpense().equalsIgnoreCase("Breakfast")).mapToDouble(p->Double.parseDouble(p.getExpenseAmount())).sum();
			BigDecimal bfS=new BigDecimal(bfSum);
			if(bfS.compareTo(new BigDecimal(400))>0) {
				invalidStr+="Max amount for Breakfast claim should not exceed Rs 400 day";
				return invalidStr;
				
			}
		}
		
		return invalidStr;
	}

}
