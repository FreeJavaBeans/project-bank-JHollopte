package com.revature.services;

import java.util.List;

import com.revature.BankLauncher;
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
		BankLauncher.logger.info("User " + login.getUsername() + ", " +
				login.getFirst_name() + " " + login.getLast_name() + " has logged in");
		return login;
	}

	@Override
	public User createNewUser(User u) throws InternalErrorException, UsernameTakenException{
		u = bud.createNewUser(u);
		BankLauncher.logger.info("User " + u.getUsername() + ", " +
				u.getFirst_name() + " " + u.getLast_name() + " has been created and logged in");
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
