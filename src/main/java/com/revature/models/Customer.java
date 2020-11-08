package com.revature.models;

public class Customer extends User{

	public Customer() {
		super();
	}

	public Customer(int user_id, String username, String password, String last_name, String first_name, String address,
			String phone_number) {
		super(user_id, username, password, last_name, first_name, address, phone_number, false);
	}
	
}
