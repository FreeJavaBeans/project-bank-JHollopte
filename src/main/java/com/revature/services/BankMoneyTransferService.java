package com.revature.services;

import java.util.List;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.NegativeValueException;
import com.revature.exceptions.OverdraftException;
import com.revature.models.Account;
import com.revature.models.MoneyTransfer;

public interface BankMoneyTransferService {
	
	// Adds money transfer to DB, deducts the money from current users account
	// for the transfer, returns the money transfer with id from DB
	public MoneyTransfer createMoneyTransfer(
			MoneyTransfer mt) throws OverdraftException, NegativeValueException;
	
	// converts a1 -> fromAccId, a2 -> toAccId, amount -> amount
	// in MoneyTransfer for it to be used in createMoneyTransfer(mt)
	// which makes the call to the server
	public MoneyTransfer createMoneyTransfer(
			Account a1, int a2, double amount);
	
	// when creating money transfer this is what menu first shows, this is
	// also where we get a1 from for createMoneyTransfer
	public List<Account> getAllCurrentUsersAccounts(int currentUserId);
	
	// Used when creating the money transfer to confirm that's
	// the person you wish to send the money to (returns string[first_name, last_name])
	public String[] getAccountHolderName(int accId) throws AccountNotFoundException;
	
	// returns all money transfers that the current user has created,
	// that are yet to be accepted by other account holder
	public List<MoneyTransfer> viewAllPendingTransfers(int currentUserId);
	
	// returns all money transfers the current user has yet to accept
	// which then can be selected by Accept Transfer Menu to accept them
	public List<MoneyTransfer> viewAllYetToAcceptTransfers(int currentUserId);
	
	// Takes in money transfer to process, updates the account with balance + amount
	// deletes money transfer from DB, returns account with updated balance
	public Account acceptTransfer(MoneyTransfer mt);
	
	// takes in current user pending money transfer, put amount back into account balance
	// deletes money transfer from DB, returns current user's account back with updated amount
	public Account cancelTransfer(MoneyTransfer mt);

}
