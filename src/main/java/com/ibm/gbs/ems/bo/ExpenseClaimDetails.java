package com.ibm.gbs.ems.bo;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
@Document
@Getter
@Setter
public class ExpenseClaimDetails  implements Serializable{
	
	private String id;
	
	private String expenseDetailName;
	private String description;
	private String typeOfExpense;
	private String paymentMode;
	
	private ExpenseClaim expenseClaimId;
	private User userId;
	
	@CreatedDate
	private LocalDate createdDate;
	@LastModifiedDate
	private LocalDate  lastModifiedDate;

}
