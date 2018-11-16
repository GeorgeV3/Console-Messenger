package paketo;


import java.sql.ResultSet;
import java.sql.Statement;

public class AdminRole extends DeleteRole {

	Database db = new Database();
	FilesWriter filesWriter = new FilesWriter();

	//public void view(){}
	public void createUser(String username , String password) {
		if (db.createUser(username, password) == true) {
			System.out.println("User create successfull.");
		}else {
			System.out.println("Something has gone wrong.");
		}
	}

	public void viewUsers() {
		try {
			db.connect();
			Statement stm =db.connect().createStatement();
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
		if (db.deleteUser(usernameInput) > 0) {
			System.out.println("User delete successfull.");
		}else {
			System.out.println("User did not delete , propably you give wrong username:" + usernameInput + ".");
		}
	}
	public void updateUser(int number , String newUsername , String newPassword , String usernameInput){
		if (db.updateUser(number, newUsername, newPassword, usernameInput) > 0){
			System.out.println("Update successfull.");
		}else {
			System.out.println("Update fail , propably you give wrong username: "+ usernameInput +".");
		}	
	}

	public void assignRole(Integer number, String usernameInput){
		if (db.assignRole(number , usernameInput) > 0) {
			System.out.println("Assign Role succefull.");
		}else {
			System.out.println("Assign role fail , propably you give wrong username: "+ usernameInput +".");
		}
	}

	public AdminRole() {
		// TODO Auto-generated constructor stub
	}
}
