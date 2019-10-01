package com.ibm.gbs.ems.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ibm.gbs.ems.bo.User;
import com.ibm.gbs.ems.repos.UserRepo;

@Service
public class UserService {
	
	@Autowired
	UserRepo userRepo;
	
	public User saveUser(User user) {
		User bo=userRepo.save(user);
		if(user.getId()==null && user.getEmailId()!=null && !user.getEmailId().isEmpty()
				&& bo!=null) {
			requestEmailConfirmation(bo);
		}
		return bo;
	}
	public void requestEmailConfirmation(User user) {
		String toEmailId="";
		String subject="Verify Email Id";
		String body="Please verfiy your email id by clicking below link";
		
	}
	public boolean  deleteUser(String id) {
		 userRepo.deleteById(id);
		 return true;
	}
	
	public User getUeserForId(String userId) {
		return userRepo.findById(userId).get();
	}
	public List<User> getUesers() {
		return userRepo.findAll(Sort.by("mobileNo"));
	}
	public User findUserByUserName(String userName, String password) {
		// TODO Auto-generated method stub
		return userRepo.findByEmailIdAndPassword(userName,password);
	}
	public User findUserByUserEmailId(String emailId) {
		return userRepo.findByEmailId(emailId);
	}
	

}
