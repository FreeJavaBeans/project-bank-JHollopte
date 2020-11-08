package com.revature.menus;

import java.util.List;
import java.util.stream.Collectors;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.models.Account;
import com.revature.models.Transaction;
import com.revature.services.BankEmployeeService;
import com.revature.services.BankTransactionService;
import com.revature.services.JHBankEmployeeService;
import com.revature.services.JHBankTransactionService;

public class PendingAccountsMenu extends AbstractMenu {
	
	private static BankEmployeeService bes = new JHBankEmployeeService();
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
		List<Account> aList = bes.getAllPendingAccounts();
		
		List<MenuLine> lines = aList.stream()
				.map(ele -> { 
					return new MenuLine(aList.indexOf(ele),
										()->aList.indexOf(ele)+1 + ": " + ele.display(), 
										()->{ 
												System.out.println("(A)pproved/(D)enied?");
												String input = this.getInputReader().nextLine();
												input = input.toUpperCase();
												try {
												if(input.equals("A")||input.equals("APPROVED")) {
													bes.approveDenyAccountbyAccountId(ele,true);
													//creating record of transaction on system
													Transaction newTrans = bts.writeTransaction(MenuSelector.getMenuSelector().getCurrentUser(),
																								ele, 
																								ele.getAcc_name() + " Approved ");
													newTrans = bts.createNewTransaction(newTrans);
												}else if(input.equals("D")||input.equals("DENIED")) {
													bes.approveDenyAccountbyAccountId(ele,false);
													//creating record of transaction on system
													Transaction newTrans = bts.writeTransaction(MenuSelector.getMenuSelector().getCurrentUser(),
																								ele, 
																								ele.getAcc_name() + " Denied ");
													newTrans = bts.createNewTransaction(newTrans);
												}else {
													System.out.println("Sorry don't understand, " +
															input + "\nEnter only A or D...");
												}}catch(AccountNotFoundException e) {
													System.out.println("Unknown Account?");
												}
											});
				})
				.collect(Collectors.toList());
			
			
		MenuLine goBack = new MenuLine(lines.size(), ()->lines.size()-1 + ": Go Back",
				()->{ MenuSelector.getMenuSelector().traverse(-1); }); 
		lines.add(goBack);
		MenuLine exit = new MenuLine(lines.size(), ()->lines.size() +
			  ": Exit Bank App", ()->{ System.out.println("Exiting..."); System.exit(0);
			  }); 
		lines.add(exit);
		return lines;
	}
	
}
