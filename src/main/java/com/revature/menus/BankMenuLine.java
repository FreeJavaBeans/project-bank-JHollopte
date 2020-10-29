package com.revature.menus;

import com.revature.models.Account;
import com.revature.models.AccountHoldings;
import com.revature.repositories.BankAccountRepository;

//a single line on the food menu
public class BankMenuLine {
	
	private int lineNumber;
	
	private Account account;
	
	//we should give the service to the menu
	//menu can pass it to each line
	private BankAccountRepository ros;
	
	
	public String display() {
		return lineNumber + ". " + account.getAcc_id() + 
				" - " + "with balance of " + account.getBalance();
	}
	
	public void doAction() {
		//what happens when you choose a food from the menu
		//probably we add it to your order
		Account a = ros.createNewAccountforUser(account); // This needs to be changed
		System.out.println("your order is : " + a);
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public Account getFood() {
		return account;
	}

	public void setFood(Account account) {
		this.account = account;
	}

	public BankMenuLine() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BankMenuLine(int lineNumber, Account account, BankAccountRepository ros) {
		super();
		this.lineNumber = lineNumber;
		this.account = account;
		this.ros = ros;
	}

	@Override
	public String toString() {
		return "FoodMenuLine [lineNumber=" + lineNumber + ", food=" + account + "]";
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
