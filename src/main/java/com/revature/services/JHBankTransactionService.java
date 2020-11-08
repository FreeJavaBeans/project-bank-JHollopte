package com.revature.services;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.repositories.BankTransactionDAO;
import com.revature.repositories.JHBankTransactionDAO;

public class JHBankTransactionService implements BankTransactionService {
	
	private BankTransactionDAO btd = new JHBankTransactionDAO();

	@Override
	public Transaction createNewTransaction(Transaction t) {
		return btd.createNewTransaction(t);
	}

	@Override
	public List<Transaction> getAllTransactions() {
		return btd.getAllTransactions();
	}

	@Override
	public Transaction writeTransaction(User u, Account a, String transType) {
		Transaction t = new Transaction();
		t.setUserId(u.getUser_id());
		t.setAccountId1(a.getAcc_id());
		t.setTransactionType(transType);
		return t;
	}
	
	@Override
	public Transaction writeTransaction(User u, Account a1, int a2, String transType) {
		Transaction t = writeTransaction(u,a1,transType);
		t.setAccountId2(a2);
		return t;
	}
	
	@Override
	public Transaction writeTransaction(User u, int a1, int a2, String transType) {
		Transaction t = new Transaction();
		t.setUserId(u.getUser_id());
		t.setAccountId1(a1);
		t.setAccountId2(a2);
		t.setTransactionType(transType);
		return t;
	}

}
