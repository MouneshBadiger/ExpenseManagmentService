package com.ibm.gbs.ems.repos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ibm.gbs.ems.bo.ExpenseClaimDetails;

public interface ExpenseClaimDetailsRepo extends MongoRepository<ExpenseClaimDetails, String> {
	List<ExpenseClaimDetails> findByUserId_IdAndCreatedDate(String id,LocalDate today);
}
