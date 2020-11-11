package com.revature.menus;

import java.util.ArrayList;
import java.util.List;

import com.revature.exceptions.NegativeValueException;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.services.BankAccountService;
import com.revature.services.BankTransactionService;
import com.revature.services.JHBankAccountService;
import com.revature.services.JHBankTransactionService;

public class MainMenu extends AbstractMenu{
	
	private static BankAccountService bas = new JHBankAccountService();
	private static BankTransactionService bts = new JHBankTransactionService();

	@Override
	public void handleInput() {
		
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
		MenuLine l1 = new MenuLine(0, ()->"1: Access Accounts", ()->{
				MenuSelector.getMenuSelector().traverse(0);
			});
		MenuLine l2 = new MenuLine(1, ()->"2: Apply For New Account", ()->{
				try {
					Account newAccount = new Account();
					System.out.print("Enter name of account: ");
					newAccount.setAcc_name(this.getInputReader().nextLine());
					System.out.print("Starting Balance? (0.00): ");
					String input = this.getInputReader().nextLine();
				
					double startingBalance;
					if(input == "") {
						startingBalance = 0.00;
					}else {
						startingBalance = Double.parseDouble(input);
					}
					if(startingBalance<0) {
						throw new NegativeValueException();
					}
					newAccount.setBalance(startingBalance);
					newAccount.setAcc_status("P");
					
					newAccount = bas.createNewAccount(
							MenuSelector.getMenuSelector().getCurrentUser().getUser_id(),
							newAccount);
					System.out.println("Account Created! " + newAccount.display());
					
					//creating record of transaction on system
					Transaction newTrans = bts.writeTransaction(MenuSelector.getMenuSelector().getCurrentUser(),
																newAccount, 
																"new account");
					newTrans = bts.createNewTransaction(newTrans);
					MenuSelector.getMenuSelector().updateAllChildren(false);
					
				}catch (NumberFormatException e) {
					System.out.println("Invaild starting balance, going back to main menu");
				}catch (NegativeValueException e) {
					System.out.println("You can't start account with negative money in it");
				}
			});
		MenuLine l3 = new MenuLine(2, ()->"3: Money Transfer", ()->{
			MenuSelector.getMenuSelector().traverse(1);
			});
		MenuLine l4 = new MenuLine(3, ()->"4: Go Back", ()->{
				MenuSelector.getMenuSelector().traverse(-1); 
			});
		MenuLine l5 = new MenuLine(4, ()->"5: Exit Banking App", ()->{
			System.out.println("Exiting...");
			System.exit(0); 
		});
		List<MenuLine> ret = new ArrayList<MenuLine>();
		ret.add(l1);
		ret.add(l2);
		ret.add(l3);
		ret.add(l4);
		ret.add(l5);
		
		return ret;
	}

}
