package com.revature.services;

import java.util.List;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.NegativeValueException;
import com.revature.exceptions.OverdraftException;
import com.revature.models.Account;

public interface BankAccountService {

	public Account createNewAccount(int userId, Account a);
	
	public Account findAccountbyAccId(int accId) throws AccountNotFoundException;
	
	public List<Account> findAllAccountsForUser(int userId);
	
	public Account UpdateBalanceForAccount(Account a, double amount, boolean withdraw) throws OverdraftException, NegativeValueException;
	
}
