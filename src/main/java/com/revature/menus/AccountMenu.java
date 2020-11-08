package com.revature.menus;

import java.util.List;
import java.util.stream.Collectors;

import com.revature.models.Account;
import com.revature.services.BankAccountService;
import com.revature.services.JHBankAccountService;

public class AccountMenu extends AbstractMenu {
	
	private static BankAccountService bas = new JHBankAccountService();

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
		System.out.println(MenuSelector.getMenuSelector().getCurrentUser().toString());
		List<Account> aList = bas.findAllAccountsForUser(
				MenuSelector.getMenuSelector().getCurrentUser().getUser_id());
			
		List<MenuLine> lines = aList.stream()
				.map(ele -> { 
					return new MenuLine(aList.indexOf(ele),
										()->aList.indexOf(ele)+1 + ": " + ele.display(), 
										()->{ 
												if(ele.getAcc_status().equals("P")) {
													System.out.println("Account still waiting for approval");
												}else if(ele.getAcc_status().equals("D")) {
													System.out.println("Account was Denied, contact "+
															"JHBank for more details");
												}else {
													System.out.println(ele.toString());
													MenuSelector.getMenuSelector().updateNodeAfterAccount(ele);
													MenuSelector.getMenuSelector().traverse(0);
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
