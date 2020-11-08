package com.revature;

import java.util.Set;

import com.revature.menus.MenuSelector;
import com.revature.models.Account;
import com.revature.repositories.BankAccountRepository;
import com.revature.repositories.JHBankAccountDAO;

public class BankLauncher {

	public static void main(String[] args) {
		
		MenuSelector ms = MenuSelector.getMenuSelector();
		while(true) {
			System.out.println(ms.display());
			ms.handleInput();
		}
	}

}
