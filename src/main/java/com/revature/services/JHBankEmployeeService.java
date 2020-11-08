package com.revature.services;

import java.util.List;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.InternalErrorException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.models.User;
import com.revature.repositories.BankEmployeeDAO;
import com.revature.repositories.JHBankEmployeeDAO;

public class JHBankEmployeeService implements BankEmployeeService {
	
	private BankEmployeeDAO bed = new JHBankEmployeeDAO();

	@Override
	public List<Account> getAllAccountsByUser(User u) {
		List<Account> userAccounts = bed.getAccountsByUser(u);
		return userAccounts;
	}

	@Override
	public List<Account> getAllPendingAccounts() {
		List<Account> pendingAccounts = bed.getAllPendingAccounts();
		return pendingAccounts;
	}

	@Override
	public Account approveDenyAccountbyAccountId(Account a, boolean approved) throws AccountNotFoundException {
		a = bed.approveOrDenyAccount(a, approved);
		return null;
	}

	@Override
	public List<Transaction> getTransactionLog() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserbyUserId(int userId) throws UserNotFoundException, InternalErrorException {
		return bed.findUserByUserId(userId);
	}

}
