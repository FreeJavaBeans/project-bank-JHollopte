package com.revature.menus;

import java.util.List;
import java.util.stream.Collectors;

import com.revature.models.MoneyTransfer;
import com.revature.models.Transaction;
import com.revature.services.BankMoneyTransferService;
import com.revature.services.BankTransactionService;
import com.revature.services.JHBankMoneyTransferService;
import com.revature.services.JHBankTransactionService;

public class AcceptMTMenu extends AbstractMenu {
	
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
		List<MoneyTransfer> mtList = bmts.viewAllYetToAcceptTransfers(MenuSelector.getMenuSelector().getCurrentUser().getUser_id());
		
		List<MenuLine> lines = mtList.stream()
				.map(ele -> { 
					return new MenuLine(mtList.indexOf(ele),
										()->mtList.indexOf(ele)+1 + ": " + ele.display(), 
										()->{ 
											System.out.println(
													"Do you wish to accept money transfer?\n" + 
													ele.display() + "\n (Y/N):");
											String input = this.getInputReader().nextLine();
											input = input.toUpperCase();
											if(input.equals("Y")||input.equals("YES")) {
												bmts.acceptTransfer(ele);
												Transaction newTrans = bts.writeTransaction(MenuSelector.getMenuSelector().getCurrentUser(),
														ele.getTransferFromAccId(), ele.getTransferToAccId(),
														"Accepted Money Transfer");
												newTrans = bts.createNewTransaction(newTrans);
												MenuSelector.getMenuSelector().updateNode();
												MenuSelector.getMenuSelector().updateParentsParentChildren();
											}else {
												System.out.println("Heading back to last menu...");
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
