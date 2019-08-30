package com.ibm.gbs.ems.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ibm.gbs.ems.bo.User;

public interface UserRepo extends MongoRepository<User, String>{
	
	User findByEmailIdAndPassword(String userName,String password);

}
