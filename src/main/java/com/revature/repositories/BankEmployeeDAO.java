package com.revature.repositories;

import java.util.List;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.InternalErrorException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Account;
import com.revature.models.User;

public interface BankEmployeeDAO {
	
	public List<Account> getAccountsByUser(User u);
	
	public List<Account> getAllPendingAccounts();
	
	public User findUserByUserId(int userId) throws UserNotFoundException, InternalErrorException;
	
	public Account approveOrDenyAccount(Account a, boolean approved) throws AccountNotFoundException;

}
