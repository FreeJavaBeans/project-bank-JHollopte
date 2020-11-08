package com.revature.menus;

import java.util.ArrayList;
import java.util.List;
import com.revature.exceptions.InternalErrorException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.UsernameTakenException;
import com.revature.models.Customer;
import com.revature.models.User;
import com.revature.services.BankUserService;
import com.revature.services.JHBankUserServices;

public class LoginMenu extends AbstractMenu{
	
	private BankUserService bus = new JHBankUserServices();

	@Override
	//specifically for taking in which line gets chosen
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
		//a menu line has a display, and an action to execute if it gets chosen
		MenuLine l1 = new MenuLine(0, ()->"1: Login, Existing User", ()->{
			String username;
			String password;
			System.out.print("Please Enter your Username: ");
			username = this.getInputReader().nextLine();
			System.out.print("Please Enter your Password: ");
			password = this.getInputReader().nextLine();
			try {
				MenuSelector.getMenuSelector().setCurrentUser(bus.login(username, password));
				MenuSelector.getMenuSelector().updateNodesAfterLogin();
				MenuSelector.getMenuSelector().traverse(0);
			} catch (UserNotFoundException e) {// expected possibility
				System.out.println("Your Username or Password was Incorrect. Please Try Again");
			} catch (InternalErrorException e) {
				// TODO Auto-generated catch block
				System.out.println("OOps something went wrong taking you back to login");
				MenuSelector.getMenuSelector().reset();
			}
		});
		MenuLine l2 = new MenuLine(1, ()->"2: Create Account, New to JHBank", ()->{
			User customer = new Customer();
			System.out.print("Enter your desired username: ");
			customer.setUsername(this.getInputReader().nextLine());
			System.out.print("Enter your new password: ");
			customer.setPassword(this.getInputReader().nextLine());
			System.out.print("Enter Last Name: ");
			customer.setLast_name(this.getInputReader().nextLine());
			System.out.print("Enter First Name: ");
			customer.setFirst_name(this.getInputReader().nextLine());
			System.out.print("Enter Address: ");
			customer.setAddress(this.getInputReader().nextLine());
			System.out.print("Enter phone number: ");
			customer.setPhone_number(this.getInputReader().nextLine());
			try {
				MenuSelector.getMenuSelector().setCurrentUser(bus.createNewUser(customer));
				MenuSelector.getMenuSelector().updateNodesAfterLogin();
				MenuSelector.getMenuSelector().traverse(0);
			} catch (UsernameTakenException e) {// expected possibility
				System.out.println("This username is taken. Please Try Again");
			} catch (InternalErrorException e) {
				// TODO Auto-generated catch block
				System.out.println("OOps something went wrong taking you back to login");
				MenuSelector.getMenuSelector().reset();
			}
		});
		MenuLine l3 = new MenuLine(2, ()->"3: Exit Banking App", ()->{
			System.out.println("Exiting...");
			System.exit(0);
		});
		
		
		List<MenuLine> ret = new ArrayList<MenuLine>();
		ret.add(l1);
		ret.add(l2);
		ret.add(l3);
		
		return ret;
	}

	
	
	
	
	

		
	

}
