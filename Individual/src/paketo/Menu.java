package paketo;

import java.io.Console;

public class Menu {
	
	private User user = new User();
	private Database db = new Database();
	private Login login = new Login();
	
	

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
				//read the password, without echoing the output 
				String password = new String(console.readPassword("Password: "));
				//verify user name and password using some mechanism 
				if (login.validateLogin(username , password)) {
					//verify username & password by server  side 	
					if(	login.validateServerLogin(username , password)) {
						console.printf("Welcome, %1$s.",username);
						db.changeStatus(username,"online");
						user = login.getUserInfo(username);
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
		User userR=login.createUserRole(user);
		String ch = "@#";
		Console console = System.console();
		while (!(ch.equals("e"))) {	
			System.out.println("\n\nTell us what you want to do by pressing the right keys...");
			System.out.println("\t\tPRESS 1 - FOR SEE YOUR MESSAGES\t\t\t");
			System.out.println("\t\tPRESS 2 - FOR SEE YOUR MESSAGES\t\t\t");
			System.out.println("\t\tPRESS 3 - TO SEND A MESSAGE\t\t\t");	
			if (user.getRole().equals("Admin")) {
				System.out.println("\tPRESS 4 - TO PRINT");
			}
			System.out.println("\t\tPRESS e - TO EXIT PROGRAM");
			ch  = console.readLine();
			switch (ch) {
			case "1":
				System.out.println("Bring your vehicle in the parking lot");	
				System.out.println(user);
				userR.edit();
				userR.view(user.getUserName());
				break;
			case "2":
				System.out.println("Give id for message you want to read.");
				String idMessage = console.readLine();
				 try {
					 userR.viewMessage(user.getUserName(),Integer.parseInt(idMessage));
			        } catch (NumberFormatException e) {
			            if(idMessage.equals("") || idMessage == null) {
			                System.out.println("You have entered empty input."); 
			            }  else {
			                System.out.println("You've entered non-intereger number.");
			            }
			        }
				
				break;
			case"3":
				System.out.println("Write the text you want to send.");
				String message=console.readLine();
				System.out.println("Write the name of the receiver.");
				String receiver=console.readLine();
				userR.send(receiver, user.getUserName(), message);
				
			case "4":if (user.getRole().equals("Admin")) {
				System.out.println("Kryfo menou");
			}
				
				break;
			case "e":
				db.changeStatus(user.getUserName(),"offline");
				System.out.println("EXIT PROGRAM");
				System.exit(0);
				break;
			default:
				System.out.println("You provided wrong input. Hit e to exit");
				break;
			}
		}
	}


	public void adminMenu() {}

}



