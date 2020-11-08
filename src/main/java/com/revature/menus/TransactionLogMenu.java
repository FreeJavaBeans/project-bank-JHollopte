package com.revature.menus;

import java.util.List;
import java.util.stream.Collectors;

import com.revature.models.Transaction;
import com.revature.services.BankTransactionService;
import com.revature.services.JHBankTransactionService;

public class TransactionLogMenu extends AbstractMenu {
	
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
		List<Transaction> tList = bts.getAllTransactions();
		List<MenuLine> lines = tList.stream()
				.map(ele -> { 
					return new MenuLine(tList.indexOf(ele),
										()->tList.indexOf(ele)+1 + ": " + ele.display(), 
										()->{ 
												System.out.println(ele.toString());
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
