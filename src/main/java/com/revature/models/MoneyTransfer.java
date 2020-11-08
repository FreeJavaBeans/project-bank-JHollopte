package com.revature.models;

import com.revature.menus.Displayable;

public class MoneyTransfer implements Displayable {
	
	private int transferId;
	
	private int transferFromAccId;
	
	private int transferToAccId;
	
	private double amount;
	
	@Override
	public String display() {
		return "Money Transfer " + this.transferId + ": \n"+
				this.amount + " From Account with id " + this.transferFromAccId +
				" to Account with id of " + this.transferToAccId;
	}

	public MoneyTransfer() {
		super();
	}
	
	public MoneyTransfer(int transferFromAccId, int transferToAccId, double amount) {
		super();
		this.transferFromAccId = transferFromAccId;
		this.transferToAccId = transferToAccId;
		this.amount = amount;
	}
	
	public MoneyTransfer(int transferId, int transferFromAccId, int transferToAccId, double amount) {
		super();
		this.transferId = transferId;
		this.transferFromAccId = transferFromAccId;
		this.transferToAccId = transferToAccId;
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "MoneyTransfer [" +
				"transferId=" + transferId +
				", transferFromAccId=" + transferFromAccId +
				", transferToAccId=" + transferToAccId +
				", amount=" + amount + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + transferFromAccId;
		result = prime * result + transferId;
		result = prime * result + transferToAccId;
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
		MoneyTransfer other = (MoneyTransfer) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (transferFromAccId != other.transferFromAccId)
			return false;
		if (transferId != other.transferId)
			return false;
		if (transferToAccId != other.transferToAccId)
			return false;
		return true;
	}

	public int getTransferId() {
		return transferId;
	}

	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}

	public int getTransferFromAccId() {
		return transferFromAccId;
	}

	public void setTransferFromAccId(int transferFromAccId) {
		this.transferFromAccId = transferFromAccId;
	}

	public int getTransferToAccId() {
		return transferToAccId;
	}

	public void setTransferToAccId(int transferToAccId) {
		this.transferToAccId = transferToAccId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
