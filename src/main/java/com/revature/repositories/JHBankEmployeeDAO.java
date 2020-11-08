package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.InternalErrorException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class JHBankEmployeeDAO implements BankEmployeeDAO {
	
	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	
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

	@Override
	public User findUserByUserId(int userId) throws UserNotFoundException, InternalErrorException {
		Connection conn = cu.getConnection();
		try {
			
			String sql = "Select * from users where user_id = ?;";
			
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, userId);
			ResultSet res = ps.executeQuery();
			
			if(res.next()) {
				User u = new Customer();
				u.setUser_id(res.getInt("user_id"));
				u.setUsername(res.getString("username"));
				u.setFirst_name(res.getString("first_name"));
				u.setLast_name(res.getString("last_name"));
				u.setAddress(res.getString("address"));
				u.setPhone_number(res.getString("phone_number"));
				u.setPassword("");
				
				return u;
			}else {
				throw new UserNotFoundException();
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw new InternalErrorException();
		}
	}

	@Override
	public List<Account> getAccountsByUser(User u) {
		return getAccountsByUserId(u.getUser_id());
	}

}
