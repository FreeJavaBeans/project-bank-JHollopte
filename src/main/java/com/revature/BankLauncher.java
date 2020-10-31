package com.revature;

import java.util.Set;

import com.revature.menus.MenuSelector;
import com.revature.models.Account;
import com.revature.repositories.BankAccountRepository;
import com.revature.repositories.JHBankAccountDAO;

public class BankLauncher {

	public static void main(String[] args) {
		
		MenuSelector ms = new MenuSelector();
		while(true) {
			ms.display();
			//login logout functionality
		}
	}

}
