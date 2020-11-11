package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.NegativeValueException;
import com.revature.exceptions.OverdraftException;
import com.revature.models.Account;
import com.revature.util.ConnectionUtil;

public class JHBankAccountDAO implements BankAccountRepository {
	
	
	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();


	// mostly for testing, don't think any user should have access to all accounts
	// maybe employees
	public List<Account> getAllAccounts() {
		//get a connection
				Connection conn = cu.getConnection();
				List<Account> allAccounts = new ArrayList<Account>();
				
				try {
					//prepare a sql statement to get all food "select * from food;"
					Statement statementObject = conn.createStatement();
					
					String queryString = "select * from accounts;";
					//execute the statement
					ResultSet rs = statementObject.executeQuery(queryString);
					
					

					//loop through result set building food objects
					//calling first() or next() will take us to the first row
					while(rs.next()) {
						Account a = new Account();
						
						a.setAcc_name(rs.getString("acc_name"));
						a.setAcc_id(rs.getInt("acc_id"));
						a.setBalance(rs.getDouble("balance"));
						a.setAcc_status(rs.getString("acc_status"));

						allAccounts.add(a);
					}
					
					
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Get ALL Accounts Failed");
				}
				
				
				//return that set
				return allAccounts;
	}
	
	public List<Account> getAccountsByUserId(int id) {

		Connection conn = cu.getConnection();
		List<Account> allAccounts = new ArrayList<Account>();

		try {
			PreparedStatement ps = 
					conn.prepareStatement("select * from accounts where acc_id in"
							+ "(select acc_id from accountholdings where user_id = ?);");
			
			ps.setInt(1, id);
			//execute query
			ResultSet rs = ps.executeQuery();
			//process result set to a food object
			while(rs.next()) {
				Account a = new Account();
				
				a.setAcc_name(rs.getString("acc_name"));
				a.setAcc_id(rs.getInt("acc_id"));
				a.setBalance(rs.getDouble("balance"));
				a.setAcc_status(rs.getString("acc_status"));

				allAccounts.add(a);
			}
			return allAccounts;
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("failed to find by id");
		}
		
		return null;
}

	public Account getAccountById(int id) throws AccountNotFoundException {

				Connection conn = cu.getConnection();

				try {
					PreparedStatement ps = conn.prepareStatement("select * from accounts where acc_id = ?");
					
					ps.setInt(1, id);
					//execute query
					ResultSet rs = ps.executeQuery();
					//process result set to a food object
					if(rs.next()) {
						Account a = new Account();
						
						a.setAcc_id(rs.getInt("acc_id"));
						a.setAcc_name(rs.getString("acc_name"));
						a.setBalance(rs.getDouble("balance"));
						a.setAcc_status(rs.getString("acc_status"));

						return a;
					}
					
				}catch(SQLException e) {
					e.printStackTrace();
					System.out.println("failed to find by id");
				}
				
				throw new AccountNotFoundException();
	}

	// newly created accounts should ONLY have acc_name w/ (P)ending status
	public Account createNewAccountforUser(int userId, Account a) {
		//get a connection
				Connection conn = cu.getConnection();
				try {
					PreparedStatement prepStatement = conn.prepareStatement("insert into accounts (acc_name,balance,acc_status) "
																			+ " values (?, ?, ?) returning accounts.acc_id;");
					
					prepStatement.setString(1, a.getAcc_name());
					prepStatement.setDouble(2, a.getBalance());
					prepStatement.setString(3, a.getAcc_status());
								

					ResultSet results = prepStatement.executeQuery();

					results.next();
					a.setAcc_id(results.getInt("acc_id"));
					
					prepStatement = conn.prepareStatement("insert into accountholdings (user_id,acc_id) "
							+ " values (?, ?) returning *;");
					
					prepStatement.setInt(1, userId);
					prepStatement.setInt(2, a.getAcc_id());
					
					results = prepStatement.executeQuery();
					
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Save Account Failed");
				}
				
				//return the original Account object with its new id from the database
				return a;
	}

	public Account updateBalance(Account a, double transaction_amount, boolean withdraw) throws OverdraftException,NegativeValueException {
		if(transaction_amount<0) {
			throw new NegativeValueException();
		}
		Connection conn = cu.getConnection();
		try {
			PreparedStatement prepStatement = 
					conn.prepareStatement("update accounts set balance = (balance-?) "
										+ "where acc_id = ? "
										+ "returning accounts.balance;");
			
			// if withdraw, check if not overdraft
			if(withdraw) {
				if(a.getBalance()-transaction_amount < 0) {
					throw new OverdraftException();
				}else {
					prepStatement.setDouble(1, transaction_amount);
				}
			}else {
				prepStatement.setDouble(1, (-transaction_amount));
			}

			prepStatement.setInt(2, a.getAcc_id());

			ResultSet results = prepStatement.executeQuery();

			results.next();
			a.setBalance(results.getDouble("balance"));
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Updated Account Balance Failed");
		}
		
		//return the original account with updated balance
		return a;
	}

	public List<Account> getAllPendingAccounts() {
		//get a connection
		Connection conn = cu.getConnection();
		List<Account> allAccounts = new ArrayList<Account>();
		
		try {
			//prepare a sql statement get all pending approval accounts
			Statement statementObject = conn.createStatement();
			
			String queryString = "select * from accounts where acc_status = 'P';";
			//execute the statement
			ResultSet rs = statementObject.executeQuery(queryString);
			
			

			//loop through result set building account objects
			//calling first() or next() will take us to the first row
			while(rs.next()) {
				Account a = new Account();
				
				a.setAcc_name(rs.getString("acc_name"));
				a.setAcc_id(rs.getInt("acc_id"));
				a.setBalance(rs.getDouble("balance"));
				a.setAcc_status(rs.getString("acc_status"));

				allAccounts.add(a);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Get ALL Accounts Failed");
		}
		
		
		//return that set
		return allAccounts;
	}

	public Account approveOrDenyAccount(Account a, boolean approved) throws AccountNotFoundException {
		Connection conn = cu.getConnection();
		try {
			PreparedStatement prepStatement = 
					conn.prepareStatement("update accounts set acc_status = ? "
										+ "where acc_id = ? "
										+ "returning accounts.acc_status;");
			
			// if withdraw, check if not overdraft
			if(approved) {
					prepStatement.setString(1, "A");
			}else {
				prepStatement.setString(1,"D");
			}

			prepStatement.setInt(2, a.getAcc_id());

			ResultSet results = prepStatement.executeQuery();

			results.next();
			a.setAcc_status(results.getString("acc_status"));
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Updated Account Balance Failed");
		}
		
		//return the original account with updated status
		return a;
	}

}
