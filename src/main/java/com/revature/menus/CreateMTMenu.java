package com.revature.menus;

import java.util.List;
import java.util.stream.Collectors;

import com.revature.exceptions.NegativeValueException;
import com.revature.exceptions.OverdraftException;
import com.revature.models.Account;
import com.revature.models.MoneyTransfer;
import com.revature.models.Transaction;
import com.revature.services.BankMoneyTransferService;
import com.revature.services.BankTransactionService;
import com.revature.services.JHBankMoneyTransferService;
import com.revature.services.JHBankTransactionService;

public class CreateMTMenu extends AbstractMenu {
	
	private static BankMoneyTransferService bmts = new JHBankMoneyTransferService();
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
		List<Account> aList = bmts.getAllCurrentUsersAccounts(MenuSelector.getMenuSelector().getCurrentUser().getUser_id());
		
		List<MenuLine> lines = aList.stream()
				.map(ele -> { 
					return new MenuLine(aList.indexOf(ele),
										()->aList.indexOf(ele)+1 + ": " + ele.display(), 
										()->{ 
											System.out.println("Enter Account ID your transfering money to: ");
											String input = this.getInputReader().nextLine();
											try {
												int acc_id = Integer.parseInt(input);
												System.out.println("Amount sending from " + ele.getAcc_name() + 
														",\n(MUST BE GREATER THEN $0.00): ");
												input = this.getInputReader().nextLine();
												double amount = Double.parseDouble(input);
												MoneyTransfer mt = bmts.createMoneyTransfer(ele, acc_id, amount);
												mt = bmts.createMoneyTransfer(mt);
												System.out.println("Money Transfer Created\n"+mt.display());
												Transaction newTrans = bts.writeTransaction(MenuSelector.getMenuSelector().getCurrentUser(),
														ele, acc_id,
														ele.getAcc_name() + " Created Money Transfer ");
												newTrans = bts.createNewTransaction(newTrans);
												MenuSelector.getMenuSelector().updateAllChildren(true);
											}catch (NumberFormatException e) {
												System.out.println("Please Make a Valid Account Id/Sending Amount");
											}catch (NegativeValueException e) {
												System.out.println("Amounts must be greater then $0.00!");
											}catch (OverdraftException e) {
												System.out.println("Account "+ ele.getAcc_name() + 
														" doesn't have enough money for money transfer");
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
