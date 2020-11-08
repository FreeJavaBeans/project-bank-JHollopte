package com.revature.services;

import java.util.List;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.NegativeValueException;
import com.revature.exceptions.OverdraftException;
import com.revature.models.Account;
import com.revature.models.MoneyTransfer;
import com.revature.repositories.BankMoneyTransferDAO;
import com.revature.repositories.JHBankMoneyTransferDAO;

public class JHBankMoneyTransferService implements BankMoneyTransferService {
	
	private BankMoneyTransferDAO bmtd = new JHBankMoneyTransferDAO();

	@Override
	public MoneyTransfer createMoneyTransfer(MoneyTransfer mt) throws OverdraftException, NegativeValueException {
		return bmtd.createMoneyTransfer(mt);
	}

	@Override
	public MoneyTransfer createMoneyTransfer(Account a1, int a2, double amount) {
		MoneyTransfer mt = new MoneyTransfer(a1.getAcc_id(),a2,amount);
		return mt;
	}

	@Override
	public List<Account> getAllCurrentUsersAccounts(int currentUserId) {
		return bmtd.getAllCurrentUsersAccounts(currentUserId);
	}

	@Override
	public String[] getAccountHolderName(int accId) throws AccountNotFoundException {
		return bmtd.getAccountHolderName(accId);
	}

	@Override
	public List<MoneyTransfer> viewAllPendingTransfers(int currentUserId) {
		return bmtd.viewAllPendingTransfers(currentUserId);
	}

	@Override
	public List<MoneyTransfer> viewAllYetToAcceptTransfers(int currentUserId) {
		return bmtd.viewAllYetToAcceptTransfers(currentUserId);
	}

	@Override
	public Account acceptTransfer(MoneyTransfer mt) {
		return bmtd.acceptTransfer(mt);
	}

	@Override
	public Account cancelTransfer(MoneyTransfer mt) {
		return bmtd.cancelTransfer(mt);
	}

}
