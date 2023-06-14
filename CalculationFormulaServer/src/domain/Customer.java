package domain;

import java.io.Serializable;

public class Customer implements Serializable {
	private String id;
	private String password;
	private String name;
	private String email;
	private long phoneNumber;
	private String address;
	private boolean hasHome;
	private boolean hasWorkplace;

	public Customer(String id, String password, String name, String email, long phoneNumber, String address, boolean hasHome, boolean hasWorkplace) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.hasHome = hasHome;
		this.hasWorkplace = hasWorkplace;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isHasHome() {
		return hasHome;
	}

	public void setHasHome(boolean hasHome) {
		this.hasHome = hasHome;
	}

	public boolean isHasWorkplace() {
		return hasWorkplace;
	}

	public void setHasWorkplace(boolean hasWorkplace) {
		this.hasWorkplace = hasWorkplace;
	}

}