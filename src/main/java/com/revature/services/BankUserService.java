package com.revature.services;

import java.util.List;

import com.revature.exceptions.InternalErrorException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.UsernameTakenException;
import com.revature.models.User;

public interface BankUserService {

	public User login(String username, String password) throws UserNotFoundException, InternalErrorException;
	
	public User createNewUser(User u) throws InternalErrorException, UsernameTakenException;
	
	public User findUserByUserId(int userId);
	
	public List<User> findAllUsers();
	
}
