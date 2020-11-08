package com.revature.models;

import com.revature.menus.Displayable;

public class Transaction implements Displayable {
	
	int userId;
	int accountId1;
	int accountId2;
	String transactionType;
	String dateTime;

	@Override
	public String display() {
		if(accountId2==0) {
			return "User Id: " + this.userId + " made " + this.transactionType + 
					" on Account " + this.accountId1 + 
					" at " + this.dateTime;
		}else {
			return "User Id: " + this.userId + " made " + this.transactionType + 
					" to Account" + this.accountId1 + " from " + " Account " + this.accountId2 +
					" at " + this.dateTime;
		}
	}

	public Transaction(int userId, int accountId1, int accountId2, String transactionType, String dateTime) {
		super();
		this.userId = userId;
		this.accountId1 = accountId1;
		this.accountId2 = accountId2;
		this.transactionType = transactionType;
		this.dateTime = dateTime;
	}

	public Transaction() {
		super();
	}

	@Override
	public String toString() {
		return "Transaction [" +
				"userId=" + userId + 
				", accountId1=" + accountId1 + 
				", accountId2=" + accountId2 + 
				", transactionType=" + transactionType + 
				", dateTime=" + dateTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountId1;
		result = prime * result + accountId2;
		result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result + ((transactionType == null) ? 0 : transactionType.hashCode());
		result = prime * result + userId;
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
		Transaction other = (Transaction) obj;
		if (accountId1 != other.accountId1)
			return false;
		if (accountId2 != other.accountId2)
			return false;
		if (dateTime == null) {
			if (other.dateTime != null)
				return false;
		} else if (!dateTime.equals(other.dateTime))
			return false;
		if (transactionType == null) {
			if (other.transactionType != null)
				return false;
		} else if (!transactionType.equals(other.transactionType))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAccountId1() {
		return accountId1;
	}

	public void setAccountId1(int accountId1) {
		this.accountId1 = accountId1;
	}

	public int getAccountId2() {
		return accountId2;
	}

	public void setAccountId2(int accountId2) {
		this.accountId2 = accountId2;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
}
