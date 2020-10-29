package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class JHBankAccountDAO implements BankAccountRepository {
	
	
	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();


	public Set<Account> getAllAccounts() {
		//get a connection
				Connection conn = cu.getConnection();
				Set<Account> allAccounts = new HashSet<Account>();
				
				try {
					//prepare a sql statement to get all food "select * from food;"
					Statement statementObject = conn.createStatement();
					
					String queryString = "select * from Account;";
					//execute the statement
					ResultSet rs = statementObject.executeQuery(queryString);
					
					

					//loop through result set building food objects
					//calling first() or next() will take us to the first row
					while(rs.next()) {
						Account a = new Account();
						
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

	public Account getAccountById(int id) throws AccountNotFoundException {

				Connection conn = cu.getConnection();

				try {
					PreparedStatement ps = conn.prepareStatement("select * from account where acc_id = ?");
					
					ps.setInt(1, id);
					//execute query
					ResultSet rs = ps.executeQuery();
					//process result set to a food object
					if(rs.next()) {
						Account a = new Account();
						
						a.setAcc_id(rs.getInt("acc_id"));
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

	public Account createNewAccountforUser(Account a) {
		//get a connection
				Connection conn = cu.getConnection();
				try {
					PreparedStatement prepStatement = conn.prepareStatement("insert into account (balance,acc_status) "
																			+ " values (?, ?) returning account.acc_id;");
					
					prepStatement.setDouble(1, a.getBalance());
					prepStatement.setString(2, a.getAcc_status());
								

					ResultSet results = prepStatement.executeQuery();

					results.next();
					a.setAcc_id(results.getInt("acc_id"));
					
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Save Food Failed");
				}
				
				//return the original food object with its new id from the database
				return a;
	}

}
