package com.revature.repositories;

import org.junit.jupiter.api.Test;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.models.Account;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class JHBankAccountDAOTest {
	
	private JHBankAccountDAO jhbad;
	
	public JHBankAccountDAOTest() {
		super();
		this.jhbad = new JHBankAccountDAO();
	}
	
	@Test
	public void getAccountFromId() {
		Account a = new Account(1,100.00,"A","My Savings");
		List<Account> aList = new ArrayList<Account>();
		try {
			assertEquals(a,jhbad.getAccountById(1));
		} catch (AccountNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void failgetAccountFromBadId() {
		assertThrows(AccountNotFoundException.class, ()->{
			jhbad.getAccountById(99999);
		});
	}
	
	
	@Test
	public void getAccountsFromUserId() {
		Account a1 = new Account(2,18900.00,"A","College Fund");
		Account a2 = new Account(9,750.00,"A","Wedding Fund");
		Account a3 = new Account(4,374.50,"A","Checking Account");
		List<Account> aList = new ArrayList<Account>();
		aList.add(a1);
		aList.add(a2);
		aList.add(a3);
		
		assertEquals(aList,jhbad.getAccountsByUserId(1));
	}

}
