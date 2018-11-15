package paketo;


import java.sql.ResultSet;
import java.sql.Statement;

public class AdminRole extends DeleteRole {

	Database db = new Database();

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

	public void deleteUser(String username){
		if (db.executeUpdate(username) > 0) {
			System.out.println("User delete successfull.");
		}else {
			System.out.println("User did not delete , propably you give wrong username.");
		}
	}
	public void updateUser(String query , String usernameInput){
		if (db.executeUpdate(query) > 0){
			System.out.println("Update successfull.");
		}else {
			System.out.println("Update fail something had gone wrong.");
			System.out.println("Check your inputs if you write the currect "
					+ "username : "+ usernameInput +" that you want to change.");
		}	
	}

	public void assignRole(String query , String usernameInput){
		if (db.executeUpdate(query) > 0) {
			System.out.println("Assign Role succefull.");
		}else {
			System.out.println("Assign role fail , something had gone wrong.");
			System.out.println("Assign role fail , propably you give wrong username: "+ usernameInput +".");
		}
	}

	public AdminRole() {
		// TODO Auto-generated constructor stub
	}
}
