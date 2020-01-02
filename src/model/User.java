package model;


import java.util.Date;


import enums.Role;

public class User {
	
	private String username;
	private String password;
	private Date registrationDate;
	private Role role;
	private boolean isActive;
	
	public User() {}
	
	public User(String username, String password, Date registrationDate, Role role, boolean isActive) {
		super();
		this.username = username;
		this.password = password;
		this.registrationDate = registrationDate;
		this.role = role;
		this.isActive = isActive;
	}
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	

}
