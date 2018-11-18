package pack;


import java.sql.ResultSet;
import java.sql.Statement;

public class AdminRole extends DeleteRole {

	//public void view(){}
	public void createUser(String username , String password) {
		if (database.createUser(username, password) > 0) {
			System.out.println("User create successfull.");
		}else {
			System.out.println("Something has gone wrong.");
		}
	}

	public void viewUsers() {
		try {
			database.connect();
			Statement stm =database.connect().createStatement();
			String sql = "SELECT username ,  role , status , credits , status\r\n" + 
					"FROM users \r\n" + 
					"WHERE username !=\"admin\";";
			ResultSet rst = stm.executeQuery(sql);
			while(rst.next())  {
				System.out.printf("%-30s | %-20s | %12s  | %-19s|%n"
						,"|Username: "+rst.getString("username")
						,"Role: " + rst.getString("role")
						,"Credits: " + rst.getString("credits") 
						,"Status: "+ rst.getString("status") );
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void deleteUser(String usernameInput){
		if (database.deleteUser(usernameInput) > 0) {
			System.out.println("User delete successfull.");
			filesWriter.keepActions(Menu.user.getUserName(),"Delete_A_User");
		}else {
			System.out.println("User did not delete , propably you give wrong username:" + usernameInput + ".");
		}
	}

	public void updateUser(int number , String newUsername , String newPassword , String usernameInput){
		if (database.updateUser(number, newUsername, newPassword, usernameInput) > 0){
			System.out.println("Update successfull.");
			filesWriter.keepActions(Menu.user.getUserName(),"Update_A_User");
		}else {
			System.out.println("Update fail , propably you give wrong username: "+ usernameInput +".");
		}	
	}

	public void assignRole(Integer number, String usernameInput){
		if (database.assignRole(number , usernameInput) > 0) {
			System.out.println("Assign Role succefull.");
			filesWriter.keepActions(Menu.user.getUserName(),"Assign_A_role");
		}else {
			System.out.println("Assign role fail , propably you give wrong username: "+ usernameInput +".");
		}
	}

	public AdminRole() {
		// TODO Auto-generated constructor stub
	}
}
