package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Transaction;
import com.revature.util.ConnectionUtil;

public class JHBankTransactionDAO implements BankTransactionDAO {
	
	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	
	@Override
	public Transaction createNewTransaction(Transaction t) {
		Connection conn = cu.getConnection();
		try {
			PreparedStatement prepStatement = 
					conn.prepareStatement("insert into transactions (user_id,acc_id_1,trans_type) "
										+ "values (?, ?, ?) "
										+ "returning transactions.trans_timedate;");
			if(t.getAccountId2()==0){
				prepStatement.setString(3,t.getTransactionType());
			}else {
				prepStatement = 
						conn.prepareStatement("insert into transactions "
								+ "(user_id,acc_id_1,acc_id_2,trans_type) "
								+ "values (?, ?, ?, ?) "
								+ "returning transactions.trans_timedate;");
				prepStatement.setInt(3, t.getAccountId2());
				prepStatement.setString(4,t.getTransactionType());
			}
			prepStatement.setInt(1, t.getUserId());
			prepStatement.setInt(2, t.getAccountId1());
			

			ResultSet results = prepStatement.executeQuery();

			results.next();
			t.setDateTime(results.getString("trans_timedate"));
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Transaction Failed to insert");
		}
		
		//return the original transaction with time created for proof success
		return t;
	}

	@Override
	public List<Transaction> getAllTransactions() {
		//get a connection
				Connection conn = cu.getConnection();
				List<Transaction> allTransactions = new ArrayList<Transaction>();
				
				try {
					//prepare a sql statement get all pending approval accounts
					Statement statementObject = conn.createStatement();
					
					String queryString = "select * from transactions order by trans_timedate asc;";
					//execute the statement
					ResultSet rs = statementObject.executeQuery(queryString);
					
					

					//loop through result set building transaction objects
					// next() will take us to the first row
					while(rs.next()) {
						Transaction t = new Transaction();
						
						t.setUserId(rs.getInt("user_id"));
						t.setAccountId1(rs.getInt("acc_id_1"));
						t.setAccountId2(rs.getInt("acc_id_2"));
						t.setTransactionType(rs.getString("trans_type"));
						t.setDateTime(rs.getString("trans_timedate"));

						allTransactions.add(t);
					}
					
					
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Get ALL Accounts Failed");
				}
				
				
				//return that list
				return allTransactions;
	}

}
