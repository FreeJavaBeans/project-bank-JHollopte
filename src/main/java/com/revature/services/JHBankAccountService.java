package com.revature.services;

import java.util.List;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.NegativeValueException;
import com.revature.exceptions.OverdraftException;
import com.revature.models.Account;
import com.revature.repositories.BankAccountRepository;
import com.revature.repositories.JHBankAccountDAO;

public class JHBankAccountService implements BankAccountService {
	
	private BankAccountRepository bar = new JHBankAccountDAO();

	@Override
	public Account createNewAccount(int userId,Account a) {
		Account newAcc = bar.createNewAccountforUser(userId, a);
		return newAcc;
	}

	@Override
	public Account findAccountbyAccId(int accId) throws AccountNotFoundException {
		Account a = bar.getAccountById(accId);
		return a;
	}

	@Override
	public List<Account> findAllAccountsForUser(int userId) {
		List<Account> accSet = bar.getAccountsByUserId(userId);
		return accSet;
	}

	@Override
	public Account UpdateBalanceForAccount(Account a, double amount, boolean withdraw) throws OverdraftException,NegativeValueException {
		a = bar.updateBalance(a, amount, withdraw);
		return a;
	}

}
