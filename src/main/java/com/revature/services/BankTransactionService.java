package com.revature.services;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;

/*
 * Interface to create all the methods
 * needed to create new transactions and
 * for employees to retrieve said transaction
 * 
 * transaction should hold user_id of who made transaction
 * account(s) affected by transaction
 * what the transaction was (deposit, withdraw, money order, etc)
 * 			all the user actions at that effect an account
 * date/time transaction took place
 */
public interface BankTransactionService {
	
	public Transaction writeTransaction(User u, Account a, String transType);
	
	public Transaction writeTransaction(User u, Account a1, int a2, String transType);
	
	public Transaction createNewTransaction(Transaction t);
	
	public List<Transaction> getAllTransactions();

	public Transaction writeTransaction(User u, int a1, int a2, String transType);

}
