package com.revature.models;

import java.util.List;

public class AccountHoldings {
	
	private List<Account> accountsOwned;
	
	private User accountsOwner;

	public AccountHoldings(List<Account> accountsOwned, User accountsOwner) {
		super();
		this.accountsOwned = accountsOwned;
		this.accountsOwner = accountsOwner;
	}

	@Override
	public String toString() {
		return "AccountHoldings [accountsOwned=" + accountsOwned + ", accountsOwner=" + accountsOwner + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountsOwned == null) ? 0 : accountsOwned.hashCode());
		result = prime * result + ((accountsOwner == null) ? 0 : accountsOwner.hashCode());
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
		AccountHoldings other = (AccountHoldings) obj;
		if (accountsOwned == null) {
			if (other.accountsOwned != null)
				return false;
		} else if (!accountsOwned.equals(other.accountsOwned))
			return false;
		if (accountsOwner == null) {
			if (other.accountsOwner != null)
				return false;
		} else if (!accountsOwner.equals(other.accountsOwner))
			return false;
		return true;
	}

	public List<Account> getAccountsOwned() {
		return accountsOwned;
	}

	public void setAccountsOwned(List<Account> accountsOwned) {
		this.accountsOwned = accountsOwned;
	}

	public User getAccountsOwner() {
		return accountsOwner;
	}

	public void setAccountsOwner(User accountsOwner) {
		this.accountsOwner = accountsOwner;
	}

	
}
