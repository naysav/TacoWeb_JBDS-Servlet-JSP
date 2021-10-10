package naysav.taco.repository;

// класс UserAccount хранит информацию об аккаунте пользователя
// для чтения и записи в БД USER_ACCOUNT
public class UserAccount {
	// userNAme хранит имя аккаунта
	private String userName;

	// gender хранит пол владельца аккаунта
	private String gender;

	// userName хранит имя аккаунта
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