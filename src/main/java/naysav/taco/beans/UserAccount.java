package naysav.taco.beans;

public class UserAccount {
	private String userName;
	private String gender;
	private String password;

	public UserAccount() {

	}

	public UserAccount(String userName, String gender, String password) {
		this.userName = userName;
		this.gender = gender;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}