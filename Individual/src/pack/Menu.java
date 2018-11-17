package pack;

import java.io.Console;
import java.util.ArrayList;

public class Menu {
	
	private User user = User.instance();
	private Database db = new Database();
	private Login login = new Login();
	private FilesWriter filesWriter= new FilesWriter();

	public Menu() {
		// TODO Auto-generated constructor stub
	}

	public void loginMenu() {

		String ch ="@#" ;
		Console console = System.console();
		while (!(ch.equals("e"))) {
			System.out.println("\t\tPRESS 1 - FOR LOGIN");
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
						db.changeStatus(username,"online");
						user = login.getUserInfo(username);	
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
		Role userR = login.createUserRole(user);
		ArrayList<Message> messageList = new ArrayList<>();
		String ch = "@#";
		Console console = System.console();
		while (!(ch.equals("e"))) {	
			//Load all the messages of the user.
			int credits = db.getCredits(user.getUserName());
			messageList = db.getAllMessages(user.getId());	
			// get unread messages
			int unread = 0 ;
			for (int index =0 ; index <messageList.size(); index++ ) {	
				if (messageList.get(index).getStatus().equals("unread")) {
					unread ++;
				}
			}
			// get read messages
			int read = (messageList.size() - unread);

			System.out.println("\n\t\tUser Messages and Credits info.");
			System.out.println("\t_______________________________________________________");		
			System.out.printf("%-20s %1s %5s %5s %5s %n", "\t|Username" , "| Unread" , "| Read" , "| Total" , "| Credits |");
			System.out.println("\t|-------------------|--------|------|-------|---------|");
			System.out.printf("%-20s %1s %4s %3s %3s %2s %3s %3s %9s %n","\t|" + user.getUserName() 
			,"|" ,  unread 
			,"|" ,  read
			,"|" ,  messageList.size() 
			,"|" ,  credits + "    |");
			System.out.println("\t|___________________|________|______|_______|_________|");

			/////////////////////////////////////////////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////////////////////////////////////////////

			System.out.println("\n\nTell us what you want to do by pressing the right keys...");
			System.out.println("\t\tPRESS 1 - FOR SEE THE QUESTIONS");
			System.out.println("\t\tPRESS 2 - TO READ A SPECIFIC MESSAGE");
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
			case "1":
				//View the questions.
				db.getAllQuestions();
				break;
			case "2":
				//message read base on id of msg.
				System.out.println("Give id for message you want to read.");
				String idMsg = console.readLine();
				try {
					userR.viewMessageById(Integer.parseInt(idMsg),user.getId());
				} catch (NumberFormatException e) {
					if(idMsg.equals("") || idMsg == null) {
						System.out.println("You have entered empty input."); 
					}  else {
						System.out.println("You've entered non-intereger number.");
					}
				}
				break;
			case"3":
				//send message.
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
			case "4":
				//edit a question.
				if ("EditRole".equalsIgnoreCase(user.getUserRole()) || "DeleteRole".equalsIgnoreCase(user.getUserRole()) || "Admin".equalsIgnoreCase(user.getUserRole())) {
					//check if the pass time is higher than 24 hours from the last edit question.
					if (db.checkTime()>=24) {
						//System.out.println(db.checkTime()+" hours.");
						System.out.println("\nWrite the new question.");
						String newQ = console.readLine();
						String newQ2 = login.getCorrectInputForMsg(10, 250, newQ);
						userR.editQuestion(db.getIdqts(), user.getUserName() , newQ2);
					}else {
						System.out.println("You cannot edit a question.");
						System.out.println("The remaining time for change a question is " + (24-db.checkTime()) +" hours.");
					}
				}else {
					System.out.println("You provided wrong input. Hit e to exit");
				}
				break;
			case "5":
				//delete message base on id msg
				if ("DeleteRole".equalsIgnoreCase(user.getUserRole()) || "Admin".equalsIgnoreCase(user.getUserRole())) {
					System.out.println("Plz provide an id of message you want to delete.");
					String idMsgD = console.readLine();	
					try {
						Integer.parseInt(idMsgD);
						userR.deleteMessage(Integer.parseInt(idMsgD) , user.getId());
					} catch (NumberFormatException e) {
						if(idMsgD.equals("") || idMsgD == null) {
							System.out.println("You have entered empty input."); 
						}  else {
							System.out.println("You've entered non-intereger number.");
						}
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
				System.out.println("Each user have a rank acording to their credits."
						+ "\nEvrey time you send a message to an exist user of the system you gain 1 credit."
						+ "\nAll users start with 0 credits."
						+ "\nGain ranks to unlock more options in menu.");
				System.out.println("User ranks are: 1)normal = can view and transacte data between users."
						+ "\n\t\t2)edit = all the above and also edit a question.(10+ credits)"
						+ "\n\t\t3)delete = all the above and also delete the transacte data.(15+ credits)");
				break;
			case "e":
				db.changeStatus(user.getUserName(),"offline");
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
		Role userR = login.createUserRole(user);
		String ch = "@#";
		Console console = System.console();
		while (!(ch.equals("e"))) {	
			System.out.println("\n\t----- WELCOME TO SUPER ADMIN MENU -----");
			System.out.println("\nTell us what you want to do by pressing the right keys...");
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
				System.out.println("Write a username for the user.");
				String username = console.readLine();
				// get correct inputs for username & password
				String name = login.getCorrectInput(4,12,username);
				System.out.println("Write a password for the user.");
				String password = console.readLine();
				String pass = login.getCorrectInput(4,12,password);
				userR.createUser(name , pass);
				break;			
			case "3"://delete user
				System.out.println("Write the username of the user who want to delete.");
				String usernameDelete = console.readLine();				
				if("offline".equalsIgnoreCase(db.getUserStatus(usernameDelete))) {
					userR.deleteUser(usernameDelete);
				}else {
					System.out.println("Cannot delete a user who is online.");
				}	
				break;		
			case "4"://update user
				System.out.println("Write the username of the user who want to update.");
				String usernameToUpdate = console.readLine();
				System.out.println("");
				System.out.println("Press 1 for change username Or "
						+ "2 for change password Or 3 for change both.\n\t Or press e to Exit");
				String h = console.readLine();
				while (!h.equals("1") && !h.equals("2") && (!h.equals("3")) && !h.equals("e")) {
					System.out.println("please provide VALID  argument. 1 for username Or "
							+ "2 for password Or 3 for both.\n\t Or e to Exit");
					h = console.readLine();
				}
				if (h.equals("1")) {
					System.out.println("Write the new username for the user.");
					String newUsername = console.readLine();
					String newUsername2 = login.getCorrectInput(4, 12, newUsername);
					userR.updateUser(1 ,newUsername2,"", usernameToUpdate);
					break;
				}
				if (h.equals("2")) {
					System.out.println("Write the new password for the user.");
					String newPassword = console.readLine();
					String newPassword2 = login.getCorrectInput(4, 12, newPassword);
					userR.updateUser(2 ,"", newPassword2 ,usernameToUpdate);
					break;
				}
				if (h.equals("3")) {
					System.out.println("Write the new username for the user.");
					String newUsername = console.readLine();
					String newUsername2 = login.getCorrectInput(4, 12, newUsername);
					System.out.println("Write the new password for the user.");
					String newPassword = console.readLine();
					String newPassword2 = login.getCorrectInput(4, 12, newPassword);				
					userR.updateUser(3 , newUsername2 , newPassword2 , usernameToUpdate);
					break;					
				}
			case "5"://assignRole
				System.out.println("Write the username of the user who want to change a Role.");
				String usernameToAssign = console.readLine();
				System.out.println("Press 1 for assign to Edit Role "
						+ "Or 2 for assign to Delete Role Or 3 for assign to No Role.\n\t Or press e to Exit");
				String h2 = console.readLine();
				while (!h2.equals("1") && !h2.equals("2") && (!h2.equals("3")) && !h2.equals("e")) {
					System.out.println("please provide VALID  argument. 1 for assign to Edit Role "
							+ "Or 2 for assign to Delete Role Or 3 for assign to No Role.\\n\\t Or press e to Exit");
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
				db.changeStatus(user.getUserName(),"offline");
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



