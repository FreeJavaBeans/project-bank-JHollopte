package com.revature.repositories;

import java.util.List;

import com.revature.models.Transaction;

public interface BankTransactionDAO {
	
	public Transaction createNewTransaction(Transaction t);
	
	public List<Transaction> getAllTransactions();
	
}
