package com.ibm.gbs.ems.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.gbs.ems.bo.ExpenseClaim;
import com.ibm.gbs.ems.repos.ExpenseClaimRepo;

@Service
public class RegisterClaimService {
	
	@Autowired
	ExpenseClaimRepo expenseClaimRepo;
	
	public ExpenseClaim saveExpenseClaim(ExpenseClaim expenseClaim) {
		return expenseClaimRepo.save(expenseClaim);
	}
	public boolean  deleteExpenseClaim(String id) {
		expenseClaimRepo.deleteById(id);
		 return true;
	}
	public ExpenseClaim getExpenseClaimId(String userId) {
		return expenseClaimRepo.findById(userId).get();
	}

}
