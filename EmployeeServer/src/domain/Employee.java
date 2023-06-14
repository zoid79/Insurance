package domain;

import enumeration.employee.Department;
import enumeration.employee.Rank;

import java.io.Serializable;

public class Employee implements Serializable {

	private String id;
	private String password;
	private Department department;
	private String name;
	private String email;
	private long phoneNumber;
	private Rank rank;
//	public Insurance m_Insurance;
//	public Policy m_Policy;

	public Employee(String id, String password, Department department, String name, String email, long phoneNumber, Rank rank) {
		this.id = id;
		this.password = password;
		this.department = department;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.rank = rank;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

}