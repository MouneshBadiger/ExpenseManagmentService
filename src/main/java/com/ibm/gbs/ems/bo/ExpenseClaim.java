package com.ibm.gbs.ems.bo;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
@Document
@Getter
@Setter
public class ExpenseClaim implements Serializable{
	
	@Id
	private String id;
	
	private String claimName;
	private String projectID;
	private String approverEmail; 
	private LocalDate travelStartDate;
	private LocalDate travelEndDate;
	private String status;//null,pending,approved
	
	private User userId;
	
	@CreatedDate
	private LocalDate createdDate;
	@LastModifiedDate
	private LocalDate  lastModifiedDate;

}
