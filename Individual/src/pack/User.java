package pack;

public class User {
	
	private int id;
	private String userName;
	private String status;
	private String userRole;
	private int credits;
	private Role role;

	static User user = new User();
	
	public static User instance() {
		return user;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(int id, String userName, String status, String userRole, int credits, Role role) {
		super();
		this.id = id;
		this.userName = userName;
	
		this.status = status;
		this.userRole = userRole;
		this.credits = credits;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", status=" + status + ", userRole=" + userRole
				+ ", credits=" + credits + ", role=" + role + "]";
	}

	

}
