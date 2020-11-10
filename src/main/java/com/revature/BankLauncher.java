package com.revature;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.menus.MenuSelector;

public class BankLauncher {
	
	public static Logger logger = LogManager.getLogger("com.revature.jhbank");

	public static void main(String[] args) {
		
		MenuSelector ms = MenuSelector.getMenuSelector();
		while(true) {
			System.out.println(ms.display());
			ms.handleInput();
		}
	}

}
