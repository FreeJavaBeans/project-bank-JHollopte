package com.revature.repositories;

import java.util.Set;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.models.Account;
import com.revature.models.User;

public interface BankAccountRepository {
	
	
	/**
	 * So the Employee is aware of all accounts
	 * @return the full Set of all Accounts items
	 */
	public Set<Account> getAllAccounts();
	
	public Account getAccountById(int id) throws AccountNotFoundException;
	
	public Account createNewAccountforUser(Account a);
	

}
