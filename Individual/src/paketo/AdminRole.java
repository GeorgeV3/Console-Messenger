package paketo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminRole extends User {

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
				System.out.printf("%-30s | %-20s | %12s  | %19s %n"
				,"|Username: "+rst.getString("username")
				,"Role: " + rst.getString("role")
				,"Credits: " + rst.getString("credits") 
				,"Status: "+ rst.getString("status") + " |");
//				System.out.println("|Username: "+rst.getString("username") + "       \t| Role: " + rst.getString("role") 
//				+ "    \t| Credits: " + rst.getString("credits") +"  \t| Status: "+ rst.getString("status") + " |");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void deleteUser(String username){
		if (db.executeUpdate(username) == true) {
			System.out.println("User delete successfull.");
		}else {
			System.out.println("Something has gone wrong.");
		}
	}
	public void updateUser(String query){
		if (db.executeUpdate(query) == true){
			System.out.println("Update successfull.");
		}else {
			System.out.println("Update fail something had gone wrong.");
		}	
	}

	public void assignRole(String query){
		if (db.executeUpdate(query)==true) {
			System.out.println("Assign Role succefull.");
		}else {
			System.out.println("Assign role fail , something had gone wrong.");
		}
	}

	public AdminRole() {
		// TODO Auto-generated constructor stub
	}
}
