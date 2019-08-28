package com.ibm.gbs.ems.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ibm.gbs.ems.bo.ExpenseClaim;

public interface ExpenseClaimRepo extends MongoRepository<ExpenseClaim, String> {

}
