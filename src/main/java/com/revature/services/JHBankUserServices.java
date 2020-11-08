package com.revature.services;

import java.util.List;

import com.revature.exceptions.InternalErrorException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.UsernameTakenException;
import com.revature.models.User;
import com.revature.repositories.BankUserDAO;
import com.revature.repositories.JHBankUserDAO;

public class JHBankUserServices implements BankUserService {
	
	private BankUserDAO bud = new JHBankUserDAO();

	@Override
	public User login(String username, String password) throws UserNotFoundException, InternalErrorException {
		User login = bud.findUserByUsernameAndPassword(username, password);
		return login;
	}

	@Override
	public User createNewUser(User u) throws InternalErrorException, UsernameTakenException{
		u = bud.createNewUser(u);
		return u;
	}

	@Override
	public User findUserByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

}
