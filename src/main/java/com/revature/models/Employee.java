package com.revature.models;

public class Employee extends User{
	private String job_title;

	public Employee(String job_title) {
		super();
		this.job_title = job_title;
	}

	public Employee() {
		super();
		this.job_title = "Bank Employee";
	}

	public Employee(int user_id, String username, String password, String last_name, String first_name, String address,
			String phone_number, boolean employed_by_bank,String job_title) {
		super(user_id, username, password, last_name, first_name, address, phone_number, employed_by_bank);
		this.job_title = job_title;
	}

	@Override
	public String toString() {
		return "Employee [job_title=" + job_title + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((job_title == null) ? 0 : job_title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (job_title == null) {
			if (other.job_title != null)
				return false;
		} else if (!job_title.equals(other.job_title))
			return false;
		return true;
	}

	public String getJob_title() {
		return job_title;
	}

	public void setJob_title(String job_title) {
		this.job_title = job_title;
	}
	
	

}
