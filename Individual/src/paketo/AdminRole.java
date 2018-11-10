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
			String sql = "SELECT username , role , status , credits\r\n" + 
					"FROM users \r\n" + 
					"WHERE username <>\"admin\";";
			ResultSet rst = stm.executeQuery(sql);
			while(rst.next())  {
				System.out.println(rst.getString("username" + " " + "role" + "" + "credits"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void deleteUser(String username){
		if (db.deleteUser(username) == true) {
			System.out.println("User delete successfull.");
		}else {
			System.out.println("Something has gone wrong.");
		}
	}
	public boolean updateUser(String query){
		boolean userUpdate = true ;
		try {
			db.connect();
			Statement stm = db.connect().createStatement();
			stm.executeUpdate(query);
			db.connect().close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statment.");
			userUpdate = false;
		}
		return userUpdate;
	}
	
	public void assignRole(){}

	public AdminRole() {
		// TODO Auto-generated constructor stub
	}
}
