package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.NegativeValueException;
import com.revature.exceptions.OverdraftException;
import com.revature.models.Account;
import com.revature.models.MoneyTransfer;
import com.revature.util.ConnectionUtil;

public class JHBankMoneyTransferDAO implements BankMoneyTransferDAO {
	
	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

	@Override
	public MoneyTransfer createMoneyTransfer(MoneyTransfer mt) throws OverdraftException, NegativeValueException {
		if(mt.getAmount()<=0) {
			throw new NegativeValueException();
		}
		Connection conn = cu.getConnection();
		try {
			PreparedStatement prepStatement = conn.prepareStatement(
					"select balance from accounts where acc_id = ?;");
			
			prepStatement.setInt(1, mt.getTransferFromAccId());
			
			ResultSet results = prepStatement.executeQuery();
			
			results.next();
			if(results.getDouble("balance")<mt.getAmount()) {
				throw new OverdraftException();
			}
			
			prepStatement = conn.prepareStatement(
					"update accounts set balance = (balance - ?) " +
					"where acc_id = ? returning accounts.balance;");
			
			prepStatement.setDouble(1, mt.getAmount());
			prepStatement.setInt(2, mt.getTransferFromAccId());
			
			results = prepStatement.executeQuery();
			
			prepStatement = conn.prepareStatement("insert into money_transfers (acc_from,acc_to,amount) "
																	+ " values (?, ?, ?) returning money_transfers.moneyt_id;");
			
			prepStatement.setInt(1, mt.getTransferFromAccId());
			prepStatement.setInt(2, mt.getTransferToAccId());
			prepStatement.setDouble(3, mt.getAmount());
						

			results = prepStatement.executeQuery();

			results.next();
			mt.setTransferId(results.getInt("moneyt_id"));
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Save Account Failed");
		}
		
		//return the original money transfer object with its new id from the database
		return mt;
	}

	@Override
	public List<Account> getAllCurrentUsersAccounts(int currentUserId) {
		Connection conn = cu.getConnection();
		List<Account> allAccounts = new ArrayList<Account>();

		try {
			PreparedStatement ps = 
					conn.prepareStatement("select * from accounts where acc_id in"
							+ "(select acc_id from accountholdings where user_id = ?);");
			
			ps.setInt(1, currentUserId);
			
			ResultSet rs = ps.executeQuery();
			
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
		
		return allAccounts;
	}

	@Override
	public String[] getAccountHolderName(int accId) throws AccountNotFoundException {
		
		Connection conn = cu.getConnection();
		String[] full_name = new String[2];
		
		try {
			PreparedStatement ps = 
					conn.prepareStatement("select first_name,last_name from users where user_id in"
							+ "(select user_id from accountholdings where acc_id = ?);");
			
			ps.setInt(1, accId);
			
			ResultSet rs = ps.executeQuery();
			if(rs.equals(null)) {
				throw new AccountNotFoundException();
			}
			rs.next();
			full_name[0] = rs.getString("first_name");
			full_name[1] = rs.getString("last_name");
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("failed to find by id");
		}
		
		return full_name;
	}

	@Override
	public List<MoneyTransfer> viewAllPendingTransfers(int currentUserId) {
		Connection conn = cu.getConnection();
		List<MoneyTransfer> allPendingMTs = new ArrayList<MoneyTransfer>();

		try {
			PreparedStatement ps = 
					conn.prepareStatement("select * from money_transfers where acc_from in"
							+ "(select acc_id from accountholdings where user_id = ?);");
			
			ps.setInt(1, currentUserId);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				MoneyTransfer mt = new MoneyTransfer();
				
				mt.setTransferId(rs.getInt("moneyt_id"));
				mt.setTransferFromAccId(rs.getInt("acc_from"));
				mt.setTransferToAccId(rs.getInt("acc_to"));
				mt.setAmount(rs.getDouble("amount"));

				allPendingMTs.add(mt);
			}
			return allPendingMTs;
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("failed to find by id");
		}
		
		return allPendingMTs;
	}

	@Override
	public List<MoneyTransfer> viewAllYetToAcceptTransfers(int currentUserId) {
		Connection conn = cu.getConnection();
		List<MoneyTransfer> allPendingMTs = new ArrayList<MoneyTransfer>();

		try {
			PreparedStatement ps = 
					conn.prepareStatement("select * from money_transfers where acc_to in"
							+ "(select acc_id from accountholdings where user_id = ?);");
			
			ps.setInt(1, currentUserId);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				MoneyTransfer mt = new MoneyTransfer();
				
				mt.setTransferId(rs.getInt("moneyt_id"));
				mt.setTransferFromAccId(rs.getInt("acc_from"));
				mt.setTransferToAccId(rs.getInt("acc_to"));
				mt.setAmount(rs.getDouble("amount"));

				allPendingMTs.add(mt);
			}
			return allPendingMTs;
			
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("failed to find by id");
		}
		
		return allPendingMTs;
	}

	@Override
	public Account acceptTransfer(MoneyTransfer mt) {
		
		Connection conn = cu.getConnection();
		Account a = new Account();
		
		try {
			PreparedStatement prepStatement = conn.prepareStatement(
					"update accounts set balance = (balance + ?) " +
					"where acc_id = ? returning *;");
			
			prepStatement.setDouble(1, mt.getAmount());
			prepStatement.setInt(2, mt.getTransferToAccId());
			
			ResultSet results = prepStatement.executeQuery();
			
			results.next();
			a.setAcc_name(results.getString("acc_name"));
			a.setAcc_id(results.getInt("acc_id"));
			a.setBalance(results.getDouble("balance"));
			a.setAcc_status(results.getString("acc_status"));
			
			prepStatement = conn.prepareStatement(
					"delete from money_transfers where " +
					"moneyt_id = ? returning *;");
			
			prepStatement.setInt(1, mt.getTransferId());
			
			results = prepStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Save Account Failed");
		}
		
		//return the account object with its updates from the database
		return a;
	}

	@Override
	public Account cancelTransfer(MoneyTransfer mt) {
		Connection conn = cu.getConnection();
		Account a = new Account();
		
		try {
			PreparedStatement prepStatement = conn.prepareStatement(
					"update accounts set balance = (balance + ?) " +
					"where acc_id = ? returning *;");
			
			prepStatement.setDouble(1, mt.getAmount());
			prepStatement.setInt(2, mt.getTransferFromAccId());
			
			ResultSet results = prepStatement.executeQuery();
			
			results.next();
			a.setAcc_name(results.getString("acc_name"));
			a.setAcc_id(results.getInt("acc_id"));
			a.setBalance(results.getDouble("balance"));
			a.setAcc_status(results.getString("acc_status"));
			
			prepStatement = conn.prepareStatement(
					"delete from money_transfers where " +
					"moneyt_id = ? returning *;");
			
			prepStatement.setInt(1, mt.getTransferId());
			
			results = prepStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Save Account Failed");
		}
		
		//return the account object with its updates from the database
		return a;
	}

}
