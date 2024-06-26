
public class User {
	private int userID;
	private String userName;

	public User(int userID) {
		this.userID = userID;
	}

	public User(int userID, String userName) {
		this.userID = userID;
		this.userName = userName;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "{" + userID + "," + userName + "}";
	}
}
