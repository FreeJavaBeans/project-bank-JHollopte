package com.revature.menus;

import java.util.ArrayList;
import java.util.List;

public class MoneyTransferMenu extends AbstractMenu {

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
		MenuLine l1 = new MenuLine(0, ()->"1: Create New Money Transfer", ()->{
			MenuSelector.getMenuSelector().traverse(0);
		});
	MenuLine l2 = new MenuLine(1, ()->"2: View Pending Transfers", ()->{
			MenuSelector.getMenuSelector().traverse(1);
		});
	MenuLine l3 = new MenuLine(2, ()->"3: Accept Money Transfers", ()->{
			MenuSelector.getMenuSelector().traverse(2);
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
