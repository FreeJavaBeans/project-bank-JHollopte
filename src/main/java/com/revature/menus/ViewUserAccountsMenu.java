package com.revature.menus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.revature.exceptions.InternalErrorException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.services.BankEmployeeService;
import com.revature.services.JHBankEmployeeService;

public class ViewUserAccountsMenu extends AbstractMenu {
	
	private static BankEmployeeService bes = new JHBankEmployeeService();
	
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
		List<MenuLine> lines = new ArrayList<MenuLine>();
		System.out.println("Please Enter Customer ID: ");
		String input = this.getInputReader().nextLine();
		try {
			int userId = Integer.parseInt(input);
			User currentCustomer = bes.getUserbyUserId(userId);
			
			System.out.println("Is this the correct customer info? (Y/N)");
			System.out.println(currentCustomer.toString());
			input = this.getInputReader().nextLine();
			input = input.toUpperCase();
			if(input.equals("Y")||input.equals("YES")) {
				List<Account> aList = bes.getAllAccountsByUser(currentCustomer);
				lines = aList.stream()
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
														}
													});
						})
						.collect(Collectors.toList());
			}else {
				MenuLine start = new MenuLine(0,()->"No Accounts Found",()->System.out.println("No Action"));
				lines.add(start);
				System.out.println("Check User Id, and try again...");
			}
		}catch (NumberFormatException e) {
			System.out.println("Please Make a Valid Choice");
		}catch(InternalErrorException e) {
			System.out.println("An Error has occured in EMainMenu, reseting to login");
			MenuSelector.getMenuSelector().reset();
		}catch(UserNotFoundException e) {
			System.out.println("Invaild User Id, please try again...");
		}	
		final int size = lines.size()+1;
		MenuLine l4 = new MenuLine(3, ()->size+": Go Back", ()->{
			MenuSelector.getMenuSelector().traverse(-1); 
		});
		lines.add(l4);
		MenuLine exit = new MenuLine(lines.size(), ()->(size+1) +
			  ": Exit Bank App", ()->{ System.out.println("Exiting..."); System.exit(0);
			  }); 
		lines.add(exit);
		return lines;
	}

	

}
