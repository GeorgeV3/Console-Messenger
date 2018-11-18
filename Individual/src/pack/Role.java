package pack;


public class Role {

	Database database = new Database();
	FilesWriter filesWriter = new FilesWriter();

	public void send(String receiver, String sender , String message ){
		if(database.sendMessage(receiver, sender, message) > 0) {
			System.out.println(sender +" : Your message send.");
			filesWriter.keepActions(Menu.user.getUserName(), "Message_Send");
			filesWriter.keepMessages(sender, receiver, message);
			if (!"admin".equalsIgnoreCase(sender))
				database.updateCredits(sender);
			int currentCredits = database.getCredits(sender);
			if ( currentCredits == 10) {
				database.autoMsgToAdmin(receiver, sender, currentCredits);
			} else if (currentCredits == 20) {
				database.autoMsgToAdmin(receiver, sender, currentCredits);
			}
		}else {
			System.out.printf(sender +" : Your message fail to send it.\nNo user with name %1$s exist.", receiver);
		}
	}

	public void editQuestion(int idQts, String username, String newQ){}
	public void deleteMessage(int idmsg , int iduser){}
	public void createUser(String username , String password){}
	public void viewUsers(){}
	public void deleteUser(String usernameInput){}
	public void updateUser(int number , String newUsername , String newPassword , String usernameInput){}
	public void assignRole(Integer number, String usernameInput){}

	public Role() {}

}
