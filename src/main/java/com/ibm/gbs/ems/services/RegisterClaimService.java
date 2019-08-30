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
	public boolean approveClaim(String id) {
		ExpenseClaim expenseClaim=expenseClaimRepo.findById(id).get();
		expenseClaim.setStatus("approved");
		expenseClaimRepo.save(expenseClaim);
		return true;
	}
	public boolean rejectClaim(String id) {
		ExpenseClaim expenseClaim=expenseClaimRepo.findById(id).get();
		expenseClaim.setStatus("rejected");
		expenseClaimRepo.save(expenseClaim);
		return true;
	}

}
