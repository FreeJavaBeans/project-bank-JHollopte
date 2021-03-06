package com.revature.models;

import com.revature.menus.Displayable;

public class Account implements Displayable{
	
	// it's id in the JHBank scemea -> accounts
	private int acc_id;
	
	// it's current money balance in JHBank scemea -> accounts
	private double balance;
	
	// it's current status in the system [A,D,P] -> Accept/Ready to use/Approved Account,
	//	in JHBank scemea -> accounts     Denied/Can't Use/Not Approved, Pending/Awaiting Approval
	private String acc_status;
	
	private String acc_name;

	
	// default constructor
	public Account() {
		super();
		this.acc_id = 0;
		this.balance = 0;
		this.acc_status = "P";
		this.acc_name = null;
	}
	
	// Creating a new account on database should be this, name w/ pending status
	public Account(String name) {
		super();
		this.acc_id = 0;
		this.balance = 0;
		this.acc_status = "P";
		this.acc_name = name;
	}
	
	public Account(int acc_id, double balance, String acc_status, String acc_name) {
		super();
		this.acc_id = acc_id;
		this.balance = balance;
		this.acc_status = acc_status;
		this.acc_name = acc_name;
	}

	@Override
	public String display() {
		String status = "";
		if(this.acc_status.equals("A")) {
			status = "Approved";
		}else if(this.acc_status.equals("D")) {
			status = "Denied";
		}else {
			status = "Pending";
		}
		return "Account " + this.acc_name + ": Status " + status;
	}
	
	/**
	 * toString Override, returns string of the Account's
	 * Account Number, Balance, Account Status [(A)pproved,(D)enied,(P)ending], Account Name
	 */
	@Override
	public String toString() {
		return "Account [acc_id=" + acc_id + 
				", balance=" + balance + 
				", acc_status=" + acc_status + 
				", acc_name=" + acc_name + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + acc_id;
		result = prime * result + ((acc_name == null) ? 0 : acc_name.hashCode());
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
		if (acc_name == null) {
			if (other.acc_name != null)
				return false;
		} else if (!acc_name.equals(other.acc_name))
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

//~~~~~~~~~~~~~~~~~~~~Getters and Setters below here~~~~~~~~~~~~~~~~~~~~~~~~~~
	
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


	public String getAcc_name() {
		return acc_name;
	}


	public void setAcc_name(String acc_name) {
		this.acc_name = acc_name;
	}
}
