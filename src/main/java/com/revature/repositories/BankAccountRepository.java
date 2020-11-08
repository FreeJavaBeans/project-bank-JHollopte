package com.revature.repositories;

import java.util.List;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.OverdraftException;
import com.revature.models.Account;

public interface BankAccountRepository {
	
	
	/**
	 * So the Employee is aware of all accounts
	 * @return the full Set of all Accounts items
	 */
	public List<Account> getAllAccounts();
	
	public Account getAccountById(int id) throws AccountNotFoundException;
	
	public Account createNewAccountforUser(int userId, Account a);
	
	public List<Account> getAccountsByUserId(int id);
	
	public Account updateBalance
		(Account a, double transaction_amount, boolean withdraw) throws OverdraftException;
	
	public List<Account> getAllPendingAccounts();
	
	public Account approveOrDenyAccount(Account a, boolean approved) throws AccountNotFoundException;
	

}
