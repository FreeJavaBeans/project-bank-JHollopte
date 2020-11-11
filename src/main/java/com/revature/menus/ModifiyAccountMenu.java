package com.revature.menus;

import java.util.ArrayList;
import java.util.List;

import com.revature.exceptions.NegativeValueException;
import com.revature.exceptions.OverdraftException;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.services.BankAccountService;
import com.revature.services.BankTransactionService;
import com.revature.services.JHBankAccountService;
import com.revature.services.JHBankTransactionService;

public class ModifiyAccountMenu extends AbstractMenu {
	
	private Account currentAccount;
	
	private static BankAccountService bas = new JHBankAccountService();
	private static BankTransactionService bts = new JHBankTransactionService();

	@Override
	public void handleInput() {
		System.out.println(currentAccount.getAcc_name() + " ID: " + currentAccount.getAcc_id() +
				" has a balance of " + currentAccount.getBalance() + "...");
		System.out.println("Enter a number, " + 1 + "-" + this.getLines().size() + ": ");
		String input = this.getInputReader().nextLine();
		try {
			int choice = Integer.parseInt(input) - 1;
			//in bounds
			if(choice >=0 && choice < this.getLines().size()) {
				this.getLines().get(choice).doAction();
			}else {
				System.out.println("Please Make a Valid Choice");
			}
		}catch (NumberFormatException e) {
			System.out.println("Please Make a Valid Choice");
		}
	}

	@Override
	public List<MenuLine> buildMenu() {
		MenuLine l1 = new MenuLine(0, ()->"1: Withdraw", ()->{
			try {
				System.out.print("Enter amount to withdraw: ");
				double transactionAmount = Double.parseDouble(this.getInputReader().nextLine());
				
				currentAccount = bas.UpdateBalanceForAccount(currentAccount, transactionAmount, true);
				
				//creating record of transaction on system
				Transaction newTrans = bts.writeTransaction(MenuSelector.getMenuSelector().getCurrentUser(),
															currentAccount, 
															"Withdraw $"+transactionAmount);
				newTrans = bts.createNewTransaction(newTrans);
				
			}catch(OverdraftException e) {
				System.out.println("You don't have enough money to make this withdraw");
			}catch(NumberFormatException e) {
				System.out.println("Could not read amount, please try again...");
			}catch(NegativeValueException e) {
				System.out.println("You can't make a negative withdraw");
			}
		});
		MenuLine l2 = new MenuLine(1, ()->"2: Deposit", ()->{
			try {
				System.out.print("Enter amount to deposit: ");
				double transactionAmount = Double.parseDouble(this.getInputReader().nextLine());
				
				currentAccount = bas.UpdateBalanceForAccount(currentAccount, transactionAmount, false);
				
				//creating record of transaction on system
				Transaction newTrans = bts.writeTransaction(MenuSelector.getMenuSelector().getCurrentUser(),
															currentAccount, 
															"Deposit $"+transactionAmount);
				newTrans = bts.createNewTransaction(newTrans);
				
			}catch(OverdraftException e) {
				System.out.println("?Please don't enter negative amounts?");
			}catch(NumberFormatException e) {
				System.out.println("Could not read amount, please try again...");
			}catch(NegativeValueException e) {
				System.out.println("You can't make a negative deposit");
			}
		});
		MenuLine l4 = new MenuLine(3, ()->"3: Go Back", ()->{
			MenuSelector.getMenuSelector().traverse(-1); 
		});
		MenuLine l5 = new MenuLine(4, ()->"4: Exit Banking App", ()->{
		System.out.println("Exiting...");
		System.exit(0); 
		});
		List<MenuLine> ret = new ArrayList<MenuLine>();
		ret.add(l1);
		ret.add(l2);
		ret.add(l4);
		ret.add(l5);
	
		return ret;
	}
	
	
	public ModifiyAccountMenu(Account currentAccount) {
		super();
		this.currentAccount = currentAccount;
	}

	public Account getCurrentAccount() {
		return currentAccount;
	}

	public void setCurrentAccount(Account currentAccount) {
		this.currentAccount = currentAccount;
	}

	
	
}
