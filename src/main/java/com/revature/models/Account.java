package com.revature.models;

public class Account {
	
	// it's id in the JHBank scemea -> accounts
	private int acc_id;
	
	// it's current money balance in JHBank scemea -> accounts
	private double balance;
	
	// it's current status in the system [A,D,P] -> Accept/Ready to use/Approved Account,
	//	in JHBank scemea -> accounts     Denied/Can't Use/Not Approved, Pending/Awaiting Approval
	private String acc_status;

	
	
	public Account() {
		super();
		this.acc_id = 0;
		this.balance = 0;
		this.acc_status = "P";
	}
	
	
	public Account(int acc_id, double balance, String acc_status) {
		super();
		this.acc_id = acc_id;
		this.balance = balance;
		this.acc_status = acc_status;
	}

	/**
	 * toString Override, returns string of the Account's
	 * Account Number, Balance, Account Status [(A)pproved,(D)enied,(P)ending]
	 */
	@Override
	public String toString() {
		return "Account [acc_id=" + acc_id +
				", balance=" + balance + 
				", acc_status=" + acc_status + "]";
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + acc_id;
		result = prime * result + ((acc_status == null) ? 0 : acc_status.hashCode());
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Account other = (Account) obj;
		if (acc_id != other.acc_id)
			return false;
		if (acc_status == null) {
			if (other.acc_status != null)
				return false;
		} else if (!acc_status.equals(other.acc_status))
			return false;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		return true;
	}


	//~~~~~~~~~~~~~~~Getters and Setters Start Here~~~~~~~~~~~~~~~~~~~~~~~
	
	public int getAcc_id() {
		return acc_id;
	}

	public void setAcc_id(int acc_id) {
		this.acc_id = acc_id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getAcc_status() {
		return acc_status;
	}

	public void setAcc_status(String acc_status) {
		this.acc_status = acc_status;
	}
}
