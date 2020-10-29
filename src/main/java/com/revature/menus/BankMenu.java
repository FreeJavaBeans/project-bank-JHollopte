package com.revature.menus;

import java.util.ArrayList;
import java.util.List;


import com.revature.repositories.BankAccountRepository;
import com.revature.repositories.JHBankAccountDAO;

//menu relating to food operations
public class BankMenu {
	
	private List<BankMenuLine> menu;
	
	//make one object for all lines to use
	private BankAccountRepository ros = new JHBankAccountDAO();
	
	
	public String display() {
		//returns complete menu display 
		StringBuilder sb = new StringBuilder();
		for(BankMenuLine fl : menu) {
			sb.append(fl.display());
			sb.append('\n');
		}
		return sb.toString();
	}
	
	public void chooseALine(int i) {
		menu.get(i).doAction();
	}
	
	
	public BankMenu() {
		super();
		
	}
	
	

	public List<BankMenuLine> getMenu() {
		return menu;
	}

	public void setMenu(List<BankMenuLine> menu) {
		this.menu = menu;
	}

	@Override
	public String toString() {
		return "BankMenu [menu=" + menu + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((menu == null) ? 0 : menu.hashCode());
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
		BankMenu other = (BankMenu) obj;
		if (menu == null) {
			if (other.menu != null)
				return false;
		} else if (!menu.equals(other.menu))
			return false;
		return true;
	}
	
	

}
