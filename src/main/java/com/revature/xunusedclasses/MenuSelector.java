package com.revature.xunusedclasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuSelector {
	
	
	//an array of all menus?
	
	//currentMenu
	private BankMenu currentMenu;
	//manage use input
	private Scanner input;
	
	/**
	 * Show the current menu and prompt the use for an input
	 */
	public void display() {
		System.out.println(currentMenu.display() +
				(currentMenu.getMenu().size()+1) + ". Exit System.");
		getUserInput();
	}
	
	
	private void getUserInput() {
		
		System.out.println("Please choose a number between " 
		                    + "1"
		                    + "-" 
		                    + (currentMenu.getMenu().size()+1));
		
		if(input.hasNextInt()) {
			int choice = input.nextInt();
			if(0< choice && choice <= currentMenu.getMenu().size()) {
				currentMenu.chooseALine(choice-1);
			}else if(choice == currentMenu.getMenu().size()+1) {
				System.out.println("Exiting...");
				System.exit(0);
			}else {
				System.out.println("Please input a valid Choice");
			}
		} else {
			System.out.println("Please input a valid Choice");
			input.next();
		}
	}
	
	public MenuSelector() {
		super();
		currentMenu = new BankMenu();
		this.input = new Scanner(System.in);
	}


	public BankMenu getCurrentMenu() {
		return currentMenu;
	}


	public void setCurrentMenu(BankMenu currentMenu) {
		this.currentMenu = currentMenu;
	}


	public Scanner getInput() {
		return input;
	}


	public void setInput(Scanner input) {
		this.input = input;
	}


	@Override
	public String toString() {
		return "MenuSelector [currentMenu=" + currentMenu + ", input=" + input + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currentMenu == null) ? 0 : currentMenu.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuSelector other = (MenuSelector) obj;
		if (currentMenu == null) {
			if (other.currentMenu != null)
				return false;
		} else if (!currentMenu.equals(other.currentMenu))
			return false;
		return true;
	}
	
}
