package paketo;
import java.util.ArrayList;

public class User {

	private Database database = new Database();
	FilesWriter filesWriter = new FilesWriter();

	private int id;
	private String userName;
	private String userPassword;
	private String status;
	private String role;
	private int credits;

	public void view (int iduser ){
		ArrayList<Message> messageList = database.getAllMessages(iduser);
		for ( Message printList : messageList ) {	
			System.out.println(printList.getId()+ " " + printList.getSender() + " " + printList.getStatus()
			+ " " + printList.getDate());	
		}	
	}

	public void viewMessageById(int idmsg , int iduser)  {
		ArrayList<Message> messageList = database.getMessage(idmsg , iduser);
		if (messageList.size()>0) {
			for ( Message printList : messageList ) {
				System.out.println(printList.getId()+ " " + printList.getSender() + " " + printList.getMessageData()
				+ " " + printList.getDate());	
			}	
		}else {
			System.out.println("You put wrong id number.");
		}
	}	

	public void send(String receiver, String sender , String message ){
		if(database.sendMessage(receiver, sender, message) == true) {
			System.out.println("Message send.");
			filesWriter.keepMessages(sender, receiver, message);
			if (!"admin".equalsIgnoreCase(sender))
			database.updateCredits(sender);
			int currentCredits = database.getCredits(sender);
			//System.out.println(currentCredits);
			if ( currentCredits == 10) {
				database.autoMsgToAdmin(receiver, sender, currentCredits);
			} else if (currentCredits == 20) {
				database.autoMsgToAdmin(receiver, sender, currentCredits);
			}
			
		}else {
			System.out.printf("Message fail to send it.\nNo user with name %1$s exist.", receiver);
		}
	}
	
	public void editQuestion(int idQts, String username, String newQ){}
	public void deleteMessage(int idmsg , int iduser){}
	public void createUser(String username , String password){}
	public void viewUsers(){}
	public void deleteUser(String usernameInput){}
	public void updateUser(int number , String newUsername , String newPassword , String usernameInput){}
	public void assignRole(Integer number, String usernameInput){}

	public User() {}
	
	public User(int id, String userName, String userPassword, String status, String role, int credits) {
		super();
		this.id = id;
		this.userName = userName;
		this.userPassword = userPassword;
		this.status = status;
		this.role = role;
		this.credits = credits;
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
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getStatusCheck() {
		return status;
	}
	public void setStatusCheck(String status) {
		this.status = status;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", userPassword=" + userPassword + ", status="
				+ status + ", role=" + role + ", credits=" + credits + "]";
	}


}
