package com.ibm.gbs.ems.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ibm.gbs.ems.bo.ExpenseClaimDetails;

public interface ExpenseClaimDetailsRepo extends MongoRepository<ExpenseClaimDetails, String> {

}
