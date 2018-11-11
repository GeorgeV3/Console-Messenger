package paketo;

import java.io.Console;

public class Menu {

	private User user = new User();
	private Database db = new Database();
	private Login login = new Login();
	FilesWriter filesWriter= new FilesWriter();

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
				//check that user has enter a username & password 
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
		User userR = login.createUserRole(user);
		String ch = "@#";
		Console console = System.console();
		while (!(ch.equals("e"))) {	
			System.out.println("\n\nTell us what you want to do by pressing the right keys...");
			System.out.println("\t\tPRESS 1 - FOR SEE THE QUESTIONS");
			System.out.println("\t\tPRESS 2 - TO READ A SPECIFIC MESSAGE");
			System.out.println("\t\tPRESS 3 - TO SEND A MESSAGE\t\t\t");	
			if (user.getRole().equals("EditRole") || user.getRole().equals("DeleteRole") || user.getRole().equals("Admin")) {
				System.out.println("\t\tPRESS 4 - TO EDIT A QUESTION");
			}
			if (user.getRole().equals("DeleteRole") || user.getRole().equals("Admin")) {
				System.out.println("\t\tPRESS 5 - TO DELETE A MESSAGE");
			}
			if (user.getRole().equals("Admin")) {
				System.out.println("\t\tPRESS a - TO CHANGE TO SUPER ADMIN MENU");
			}
			System.out.println("\t\tPRESS h - FOR HELP SECTION");
			System.out.println("\t\tPRESS e - TO EXIT PROGRAM");

			ch  = console.readLine();
			switch (ch) {
			case "1":
				System.out.println(user);
				userR.view(user.getId());
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
				System.out.println("Write the name of the receiver.");
				String receiver=console.readLine();
				System.out.println(user.getUserName());
				if (!user.getUserName().equals(receiver)) {
					userR.send(receiver , user.getUserName(), message);
				}else {
					System.out.println("You cannot send a message to your self.");
				}
				break;
			case "4":
				//edit a question.
				if (user.getRole().equals("EditRole") || user.getRole().equals("DeleteRole") || user.getRole().equals("Admin")) {
					System.out.println("Plz give the id of the question you want to edit.");
					String idQts = console.readLine();
					try {
						System.out.println("Write the new question.");
						String newQ = console.readLine();
						userR.editQuestion(Integer.parseInt(idQts), user.getUserName() , newQ);
						filesWriter.keepActions(user.getUserName(),"Edit_Question");
					} catch (NumberFormatException e) {
						if(idQts.equals("") || idQts == null) {
							System.out.println("You have entered empty input."); 
						}  else {
							System.out.println("You've entered non-intereger number.");
						}
					}

				}else {
					System.out.println("You provided wrong input. Hit e to exit");
				}
				break;
			case "5":
				//delete message base on id msg
				if (user.getRole().equals("DeleteRole") || user.getRole().equals("Admin")) {
					System.out.println("Plz provide an id of message you want to delete.");
					String idMsgD = console.readLine();
					Integer.parseInt(idMsgD);
					try {
						userR.deleteMessage(Integer.parseInt(idMsgD) , user.getId());
						filesWriter.keepActions(user.getUserName(),"Delete_Message");
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
			case "a"://join to admin menu.
				if (user.getRole().equals("Admin")) {
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
		User userR = login.createUserRole(user);
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
			case "1":
				System.out.println("__________________________________________________________________________________");
				userR.viewUsers();
				System.out.println("|_______________________|_______________________|_______________|_________________|");
				break;	
			case "2"://create user
				System.out.println("Write a username for the user.");
				String username = console.readLine();
				System.out.println("Write a password for the user.");
				String password = console.readLine();
				userR.createUser(username , password);
				break;			
			case "3"://delete user
				System.out.println("Write the username of the user who want to delete.");
				String usernameDelete = console.readLine();
				userR.deleteUser(usernameDelete);
				break;		
			case "4"://update user
				System.out.println("Write the username of the user who want to update.");
				String usernameUpdate = console.readLine();
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
					String query ="UPDATE users SET username = '" + newUsername +"' WHERE username = '" + usernameUpdate + "';";
					if(userR.updateUser(query)== true) {
						System.out.println("Update successfull.");
					}else {
						System.out.println("Update fail something had gone wrong.");
					}
					break;
				}
				if (h.equals("2")) {
					System.out.println("Write the new password for the user.");
					String newPassword = console.readLine();
					String query ="UPDATE users SET password = '" + newPassword +"' WHERE username = '" + usernameUpdate + "';";
					if(userR.updateUser(query)== true) {
						System.out.println("Update successfull.");
					}else {
						System.out.println("Update fail something had gone wrong.");
					}
					break;
				}
				if (h.equals("3")) {
					System.out.println("Write the new username for the user.");
					String newUsername = console.readLine();
					System.out.println("Write the new password for the user.");
					String newPassword = console.readLine();
					String query ="UPDATE users SET username = '" + newUsername +"' , password = '" + newPassword + 
							" WHERE username = '" + usernameUpdate + "';";
					if(userR.updateUser(query)== true) {
						System.out.println("Update successfull.");
					}else {
						System.out.println("Update fail something had gone wrong.");
					}
					break;					
				}
			case "5"://assignRole
				System.out.println("Write the name of the user who want to change a Role.");
				String usernameAssign = console.readLine();
				System.out.println("Press 1 for assign to EditRole "
						+ "Or 2 for assign to DeleteRole Or 3 for assign to NoRole.\n\t Or press e to Exit");
				String h2 = console.readLine();
				while (!h2.equals("1") && !h2.equals("2") && (!h2.equals("3")) && !h2.equals("e")) {
					System.out.println("please provide VALID  argument. 1 for assign to EditRole "
							+ "Or 2 for assign to DeleteRole Or 3 for assign to NoRole.\\n\\t Or press e to Exit");
					h2 = console.readLine();
				}if (h2.equals("1")) {
					String query ="UPDATE users SET role ='EditRole' where username = '" + usernameAssign +"';";
					userR.assignRole(query);
				}
				if (h2.equals("2")) {
					String query ="UPDATE users SET role ='DeleteRole' where username = '" + usernameAssign +"';";
					userR.assignRole(query);
				}
				if (h2.equals("3")) {
					String query ="UPDATE users SET role ='NoRole' where username = '" + usernameAssign +"';";
					userR.assignRole(query);
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



