package com.revature.xunusedclasses;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.models.Account;
import com.revature.models.AccountHoldings;
import com.revature.models.User;
import com.revature.repositories.BankAccountRepository;

//a single line on the bank menu
public class BankMenuLine {
	
	private int lineNumber;
	
	private Account account;
	
	private BankAccountRepository ros;
	
	
	public String display() {
		return lineNumber + ". " + account.getAcc_name() + 
				" - " + "with balance of " + account.getBalance();
	}
	
	public void doAction() {
		
		try {
			Account a = ros.getAccountById(account.getAcc_id());
			System.out.println("Account Found: " + a.getAcc_name());
		}catch(AccountNotFoundException e) {
			System.out.println("Account not found try again...");
		}
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public BankMenuLine() {
		super();
	}

	public BankMenuLine(int lineNumber, Account account, BankAccountRepository ros) {
		super();
		this.lineNumber = lineNumber;
		this.account = account;
		this.ros = ros;
	}

	@Override
	public String toString() {
		return "BankMenuLine [lineNumber=" + lineNumber + ", account=" + account + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + lineNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankMenuLine other = (BankMenuLine) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (lineNumber != other.lineNumber)
			return false;
		return true;
	}
	
	
	

}
