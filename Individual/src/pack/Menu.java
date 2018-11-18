package pack;

import java.io.Console;
import java.util.ArrayList;
import java.util.Comparator;

public class Menu {

	static User user = User.instance();
	private Database db = new Database();
	private Login login = new Login();
	private FilesWriter filesWriter= new FilesWriter();

	public Menu() {}

	public void loginMenu() {

		String ch =" @#" ;
		Console console = System.console();
		while (!(ch.equals("e"))) {
			System.out.println("\t\tPRESS 1 - TO LOGIN");
			System.out.println("\t\tPRESS e - TO EXIT PROGRAM");
			ch  = console.readLine();
			switch (ch) {
			case "1":		
				System.out.println("\t\t\tFILL THE TEXT FIELDS");
				String username = console.readLine("Username: ");
				String password = new String(console.readPassword("Password: "));
				//check that user has enter username & password 
				if (login.validateLogin(username , password)) {
					//validate username & password by server  side
					if(	login.validateServerLogin(username.trim() , password.trim())) {
						console.printf("Welcome, %1$s.",username);
						db.changeUserStatus(username,"online");
						user = db.getUserInfo(username);	
						filesWriter.keepActions(user.getUserName(),"login");
						userMenu();			
					}
				}break;	
			case "e":
				System.out.println("EXIT PROGRAM");
				break;
			default:
				System.out.println("You provided wrong input. Hit e to exit");
				break;
			}
		}
	}


	public void userMenu() {
		Role userR = user.createUserRole(user);
		ArrayList<Message> messageList = new ArrayList<>();
		String ch = "@#";
		Console console = System.console();
		while (!(ch.equals("e"))) {	
			//Load credits of the user.
			int credits = db.getCredits(user.getUserName());
			//Load all the messages of the user.
			messageList = db.getAllMessages(user.getId());
			messageList.sort(Comparator.comparing(Message::getDate));
			//Create a list of unread messages.
			ArrayList<Message> unreadList = new ArrayList<>();
			//Create a list of read messages.
			ArrayList<Message> readList = new ArrayList<>();
			for ( Message printList : messageList ) {
				if (printList.getStatus().equals("unread")) {
					unreadList.add(printList);	
				}else {
					readList.add(printList);
				}
			}

			System.out.println("\n\n\t\t\t Messages and Credits info.");
			System.out.println("\t_______________________________________________________");		
			System.out.printf("%-20s %1s %5s %5s %5s %n", "\t|Username" , "| Unread" , "| Read" , "| Total" , "| Credits |");
			System.out.println("\t|-------------------|--------|------|-------|---------|");
			System.out.printf("%-20s %1s %4s %3s %3s %2s %3s %3s %9s %n","\t|" + user.getUserName() 
			,"|" ,  unreadList.size() 
			,"|" ,  readList.size()
			,"|" ,  messageList.size() 
			,"|" ,  credits + "    |");
			System.out.println("\t|___________________|________|______|_______|_________|");

			/////////////////////////////////////////////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////////////////////////////////////////////

			System.out.println("\n\nTell us what you want to do by pressing the correct keys.");
			System.out.println("\t\tPRESS 1 - TO SEE THE QUESTIONS");
			System.out.println("\t\tPRESS 2 - TO READ YOUR MESSAGES");
			System.out.println("\t\tPRESS 3 - TO SEND A MESSAGE");	
			if ("EditRole".equalsIgnoreCase(user.getUserRole()) || "DeleteRole".equalsIgnoreCase(user.getUserRole()) || "Admin".equalsIgnoreCase(user.getUserRole())) {
				System.out.println("\t\tPRESS 4 - TO EDIT A QUESTION");
			}
			if ("DeleteRole".equalsIgnoreCase(user.getUserRole()) || "Admin".equalsIgnoreCase(user.getUserRole())) {
				System.out.println("\t\tPRESS 5 - TO DELETE A MESSAGE");
			}
			if ("Admin".equalsIgnoreCase(user.getUserRole())) {
				System.out.println("\t\tPRESS a - TO CHANGE TO SUPER ADMIN MENU");
			}
			System.out.println("\t\tPRESS h - FOR HELP SECTION");
			System.out.println("\t\tPRESS e - TO EXIT PROGRAM");

			ch  = console.readLine();
			switch (ch) {
			case "1"://View the questions.
				db.getAllQuestions();
				break;
			case "2"://messages read.
				System.out.println("Press 1 to read your unread messages. "
						+ "Or 2 to read all your old messages.\n\t\t\tOr press e to Exit");
				String h = console.readLine();
				while (!h.equals("1") && !h.equals("2") && !h.equals("e")) {
					System.out.println("Press 1 to read your Unread messages. "
							+ "Or 2 to read all your old messages.\n\t\t\tOr press e to Exit");
					h = console.readLine();

				}//view unread message 1 by 1	
				if (h.equals("1")) {
					int index = 0;
					if (index < unreadList.size()) {
						System.out.println("Message: "  + (index+1) + "\nText: "  + unreadList.get(index).getMessageData() +
								"\nFrom: "+ unreadList.get(index).getSender() + " Date : " +unreadList.get(index).getDate());
						db.changeMsgStatus(unreadList.get(index).getId());
						System.out.println("-------------------------------------------------------------------------------");
						System.out.println("Press r to see your next message Or e to exit.");
					}else {
						System.out.println("You do not have unread messages.");
						break;
					}
					String h2 = " @#" ;
					while (!h2.equals("e")) {
						h2 = console.readLine();
						if (h2.equals("r")) {
							if (index < unreadList.size()-1) {
								index++;
								System.out.println("Message: "  + (index+1) + "\nText: "  + unreadList.get(index).getMessageData() +
										"\nFrom: "+ unreadList.get(index).getSender() + " Date: " +unreadList.get(index).getDate());
								db.changeMsgStatus(unreadList.get(index).getId());
								System.out.println("-------------------------------------------------------------------------------");
								System.out.println("Press r to see your next message Or e to exit.");
							}
							else {
								System.out.println("You have read all your new messages.");
								break;
							}
						}
					}
				}//view all read messages	
				if (h.equals("2")) {
					for ( Message printList : readList ) {
						System.out.println("Message: "+ (readList.indexOf(printList)+1)+"\nText: "  + printList.getMessageData() + 
								"\nFrom: "+ printList.getSender() + " Date: " +printList.getDate() + "\n");	
					}
				}
				break;
			case"3"://send message.
				System.out.println("Write the text you want to send.");
				String message=console.readLine();
				//get a correct input
				String message2 = login.getCorrectInputForMsg(10, 250, message);
				System.out.println("Write the name of the receiver.");
				String receiver=console.readLine();
				if (!user.getUserName().equals(receiver)) {
					userR.send(receiver , user.getUserName(), message2);
				}else {
					System.out.println("You cannot send a message to yourself.");
				}
				break;
			case "4"://edit a question.
				if ("EditRole".equalsIgnoreCase(user.getUserRole()) || "DeleteRole".equalsIgnoreCase(user.getUserRole()) || "Admin".equalsIgnoreCase(user.getUserRole())) {
					//check if the pass time is higher than 24 hours from the last edit question.
					if (db.checkTime()>=24) {
						//System.out.println(db.checkTime()+" hours.");
						System.out.println("\nWrite the new question.");
						String newQ = console.readLine();
						String newQ2 = login.getCorrectInputForMsg(10, 250, newQ);
						String newQ3 = newQ2.substring(0, 1).toUpperCase() + newQ2.substring(1);	
						userR.editQuestion(db.getIdqts(), user.getUserName() , newQ3);
					}else {
						System.out.println("You cannot edit a question.");
						System.out.println("The remaining time to change a question is " + (24-db.checkTime()) +" hours.");
					}
				}else {
					System.out.println("You provided wrong input. Hit e to exit");
				}
				break;
			case "5"://You can only delete messages that you have read , 
					//based on the number of the message which had link on the id of the message.
				if ("DeleteRole".equalsIgnoreCase(user.getUserRole()) || "Admin".equalsIgnoreCase(user.getUserRole())) {
					if (readList.size() > 0) {	
						System.out.println("Provide the number of the message you want to delete.");
						String number = console.readLine();		
						try {
							if (Integer.parseInt(number)>0 && Integer.parseInt(number) -1 < readList.size()) {
								int idmsg = readList.get(Integer.parseInt(number)-1).getId();
								userR.deleteMessage(idmsg , user.getId());
							}else {
								System.out.println("You provide wrong message number.");
							}		
						} catch (NumberFormatException e) {
							if(number.equals("") || number == null) {
								System.out.println("You have entered empty input."); 
							}  else {
								System.out.println("You have entered non-intereger number.");
							}
						}
					}else {
						System.out.println("Nothing to delete. Your read message list is emtpy."
								+ "\nPlz read your messages first.");
					}
				}else {
					System.out.println("You provided wrong input. Hit e to exit");
				}
				break;
			case "a"://change to admin menu.
				if ("Admin".equalsIgnoreCase(user.getUserRole())) {
					adminMenu();
				}else {
					System.out.println("You provided wrong input. Hit e to exit");
				}
				break;
			case"h":
				System.out.println("\t\tWelcome to help session.");
				System.out.println("Each user is ranked according to his credits."
						+ "\nEvery time you send a message to an existing user of the system you gain 1 credits."
						+ "\nAll users start with 0 credits."
						+ "\nMove to the next rank to unlock more options in menu.");
				System.out.println("User ranks are: 1)normal = View the transacted data between the users."
						+ "\n\t\t2)edit = All the above and also edit a question.(10+ credits)"
						+ "\n\t\t3)delete = All the above and also delete the transacted data.(20+ credits)"
						+"\nThe passwords of the users of the systems are all 1234");
				break;
			case "e":
				db.changeUserStatus(user.getUserName(),"offline");
				filesWriter.keepActions(user.getUserName(),"logout");
				System.out.println("EXIT PROGRAM");
				System.exit(0);
				break;
			default:
				System.out.println("You provided wrong input. Hit e to exit");
				break;
			}
		}
	}

	public void adminMenu() {
		Role userR = user.createUserRole(user);
		String ch = "@#";
		Console console = System.console();
		while (!(ch.equals("e"))) {	
			System.out.println("\n\t----- WELCOME TO SUPER ADMIN MENU -----");
			System.out.println("\nTell us what you want to do by pressing the right keys.");
			System.out.println("\t\tPRESS 1 - TO SEE ALL USERS");
			System.out.println("\t\tPRESS 2 - TO CREATE A USER");
			System.out.println("\t\tPRESS 3 - TO DELETE A USER");
			System.out.println("\t\tPRESS 4 - TO UPDATE A USER");
			System.out.println("\t\tPRESS 5 - TO ASSIGN ROLE TO A USER");
			System.out.println("\t\tPRESS r - TO RETURN TO USER MENU");
			System.out.println("\t\tPRESS e - TO EXIT PROGRAM");
			ch  = console.readLine();
			switch (ch) {
			case "1":// view users
				System.out.println("___________________________________________________________________________________________");
				userR.viewUsers();
				System.out.println("|______________________________|______________________|_______________|____________________|");
				break;	
			case "2"://create user
				System.out.println("Write a username.");
				String username = console.readLine();
				// get correct inputs for username & password
				String name = login.getCorrectInput(4,12,username);
				System.out.println("Write a password.");
				String password = console.readLine();
				String pass = login.getCorrectInput(4,12,password);
				userR.createUser(name , pass);
				break;			
			case "3"://delete user
				System.out.println("Write the username of the user who wants to be deleted.");
				String usernameDelete = console.readLine();				
				if("offline".equalsIgnoreCase(db.getUserStatus(usernameDelete))) {
					userR.deleteUser(usernameDelete);
				}else {
					System.out.println("Cannot delete a user who is online.");
				}	
				break;		
			case "4"://update user
				System.out.println("Write the username of the user who wants to be updated.");
				String usernameToUpdate = console.readLine();
				System.out.println("");
				System.out.println("Press 1 to change username Or "
						+ "2 to change password Or 3 to change both.\n\t Or press e to Exit");
				String h = console.readLine();
				while (!h.equals("1") && !h.equals("2") && (!h.equals("3")) && !h.equals("e")) {
					System.out.println("please provide correct  argument. 1 to change username Or "
							+ "2 to change password Or 3 to change both.\n\t Or e to Exit");
					h = console.readLine();
				}
				if (h.equals("1")) {
					System.out.println("Write the new username.");
					String newUsername = console.readLine();
					String newUsername2 = login.getCorrectInput(4, 12, newUsername);
					userR.updateUser(1 ,newUsername2,"", usernameToUpdate);
					break;
				}
				if (h.equals("2")) {
					System.out.println("Write the new password.");
					String newPassword = console.readLine();
					String newPassword2 = login.getCorrectInput(4, 12, newPassword);
					userR.updateUser(2 ,"", newPassword2 ,usernameToUpdate);
					break;
				}
				if (h.equals("3")) {
					System.out.println("Write the new username.");
					String newUsername = console.readLine();
					String newUsername2 = login.getCorrectInput(4, 12, newUsername);
					System.out.println("Write the new password.");
					String newPassword = console.readLine();
					String newPassword2 = login.getCorrectInput(4, 12, newPassword);				
					userR.updateUser(3 , newUsername2 , newPassword2 , usernameToUpdate);
					break;					
				}
			case "5"://assignRole
				System.out.println("Write the username of the user who wants to change a Role.");
				String usernameToAssign = console.readLine();
				System.out.println("Press 1 to assign to Edit Role "
						+ "Or 2 to assign to Delete Role Or 3 to assign to Normal Role.\n\t Or press e to Exit");
				String h2 = console.readLine();
				while (!h2.equals("1") && !h2.equals("2") && (!h2.equals("3")) && !h2.equals("e")) {
					System.out.println("please provide VALID  argument. 1 to assign to Edit Role "
							+ "Or 2 to assign to Delete Role Or 3 to assign to No Role.\\n\\t Or press e to Exit");
					h2 = console.readLine();
				}if (h2.equals("1")) {	
					userR.assignRole(1 , usernameToAssign);
				}
				if (h2.equals("2")) {				
					userR.assignRole(2 , usernameToAssign);		
				}
				if (h2.equals("3")) {
					userR.assignRole(3 , usernameToAssign);		
				}
				break;		
			case "r":
				userMenu();
				break;		
			case "e":
				db.changeUserStatus(user.getUserName(),"offline");
				filesWriter.keepActions(user.getUserName(),"logout");
				System.out.println("EXIT PROGRAM");
				System.exit(0);
				break;
			default:
				System.out.println("You provided wrong input. Hit e to exit");
				break;
			}
		}
	}

}



