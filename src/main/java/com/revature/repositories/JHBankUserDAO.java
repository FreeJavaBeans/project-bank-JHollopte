package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.revature.exceptions.InternalErrorException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.UsernameTakenException;
import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class JHBankUserDAO implements BankUserDAO {
	
	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

	@Override
	public User findUserByUsernameAndPassword(String username, String password)
			throws UserNotFoundException, InternalErrorException {
		Connection conn = cu.getConnection();
		try {
			
			String sql = "Select * from users where username = ? and \"password\" = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, username);
			ps.setString(2, password);
			
			ResultSet res = ps.executeQuery();
			if(res.next()) {
				User u = null;
				if(res.getBoolean("employed_by_bank")) {
					u = new Employee();
				} else {
					u = new Customer();
				}
				u.setUser_id(res.getInt("user_id"));
				u.setFirst_name(res.getString("first_name"));
				u.setLast_name(res.getString("last_name"));
				u.setPhone_number(res.getString("phone_number"));
				u.setPassword("");
				u.setUsername(res.getString("username"));
				u.setAddress(res.getString("address"));
				
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
	public List<User> findAllusers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findUserByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User createNewUser(User u) throws InternalErrorException, UsernameTakenException{
		Connection conn = cu.getConnection();
		try {
			
			String sql = "Select * from users where username = ?;";
			
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, u.getUsername());
			ResultSet res = ps.executeQuery();
			if(res.next()) {
				throw new UsernameTakenException();
			}
			
			sql = "insert into users (username,\"password\",last_name,first_name,address,phone_number,employed_by_bank)"
					+ "	values 	(?, ?, ?, ?, ?, ?,false) returning users.user_id;";
			ps = conn.prepareStatement(sql);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getLast_name());
			ps.setString(4, u.getFirst_name());
			ps.setString(5, u.getAddress());
			ps.setString(6, u.getPhone_number());
			res = ps.executeQuery();
			
			if(res.next()) {
				u.setUser_id(res.getInt("user_id"));
				u.setPassword("");
				sql = "insert into customers (user_id_fk) values (?) returning customers.user_id_fk;";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, u.getUser_id());
				ps.executeQuery();
				return u;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			throw new InternalErrorException();
		}
		System.out.println("Failed to create new account");
		throw new InternalErrorException();
	}

}
