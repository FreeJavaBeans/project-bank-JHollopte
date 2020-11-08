package com.revature.services;

import java.util.List;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.InternalErrorException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;

public interface BankEmployeeService {
	
	public List<Account> getAllAccountsByUser(User u);
	
	public List<Account> getAllPendingAccounts();
	
	public User getUserbyUserId(int userId) throws UserNotFoundException,InternalErrorException;
	
	public Account approveDenyAccountbyAccountId(Account a, boolean approved) throws AccountNotFoundException;
	
	public List<Transaction> getTransactionLog(); 

}
