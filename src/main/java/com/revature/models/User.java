package com.revature.models;

public class User {
	
	private int user_id;
	
	private String username;
	
	private String password;

	private String last_name;
	
	private String first_name;
	
	private String address;
	
	private String phone_number;
	
	private boolean employed_by_bank;

	public User() {
		super();
		this.user_id = 0;
		this.username = null;
		this.password = null;
		this.last_name = null;
		this.first_name = null;
		this.first_name = null;
		this.address = null;
		this.phone_number = null;
		this.employed_by_bank = false;
	}

	public User(int user_id, String username, String password, String last_name, String first_name, String address,
			String phone_number, boolean employed_by_bank) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.last_name = last_name;
		this.first_name = first_name;
		this.address = address;
		this.phone_number = phone_number;
		this.employed_by_bank = employed_by_bank;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ","
				+ " username=" + username + 
				", password=" + password + 
				", last_name=" + last_name + 
				", first_name=" + first_name + 
				", address=" + address + 
				", phone_number=" + phone_number + 
				", employed_by_bank=" + employed_by_bank + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + (employed_by_bank ? 1231 : 1237);
		result = prime * result + ((first_name == null) ? 0 : first_name.hashCode());
		result = prime * result + ((last_name == null) ? 0 : last_name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone_number == null) ? 0 : phone_number.hashCode());
		result = prime * result + user_id;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (employed_by_bank != other.employed_by_bank)
			return false;
		if (first_name == null) {
			if (other.first_name != null)
				return false;
		} else if (!first_name.equals(other.first_name))
			return false;
		if (last_name == null) {
			if (other.last_name != null)
				return false;
		} else if (!last_name.equals(other.last_name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phone_number == null) {
			if (other.phone_number != null)
				return false;
		} else if (!phone_number.equals(other.phone_number))
			return false;
		if (user_id != other.user_id)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	//~~~~~~~~~~~~~Getters and Setters~~~~~~~~~~~~~~~~~~~~~~~
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public boolean isEmployed_by_bank() {
		return employed_by_bank;
	}

	public void setEmployed_by_bank(boolean employed_by_bank) {
		this.employed_by_bank = employed_by_bank;
	}
}
