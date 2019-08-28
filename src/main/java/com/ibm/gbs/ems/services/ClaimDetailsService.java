package com.ibm.gbs.ems.services;

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
	
	public ExpenseClaimDetails saveExpenseClaim(ExpenseClaimDetails expenseClaim) {
		return expenseClaimDetailsRepo.save(expenseClaim);
	}
	public boolean  deleteExpenseClaim(String id) {
		expenseClaimDetailsRepo.deleteById(id);
		 return true;
	}
	public ExpenseClaimDetails getExpenseClaimId(String userId) {
		return expenseClaimDetailsRepo.findById(userId).get();
	}

}
