package pack;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminRole extends DeleteRole {

	//public void view(){}
	public void createUser(String username , String password) {
		if (dbCreateUser(username, password) > 0) {
			System.out.println("User create successfull.");
			filesWriter.keepActions(Menu.user.getUserName(),"Create_A_User");
		}else {
			System.out.println("Something has gone wrong.\nPropably a user with this username has already exist.");
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
				System.out.printf("%-30s | %-20s | %-12s  | %-19s|%n"
						,"|Username: "+rst.getString("username")
						,"Role: " + rst.getString("role")
						,"Credits:" + rst.getString("credits") 
						,"Status: "+ rst.getString("status") );
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void deleteUser(String usernameInput){
		if (dbDeleteUser(usernameInput) > 0) {
			System.out.println("User delete successfull.");
			filesWriter.keepActions(Menu.user.getUserName(),"Delete_A_User");
		}else {
			System.out.println("User did not delete , propably you give wrong username:" + usernameInput + ".");
		}
	}

	public void updateUser(int number , String newUsername , String newPassword , String usernameInput){
		if (dbUpdateUser(number, newUsername, newPassword, usernameInput) > 0){
			System.out.println("Update successfull.");
			filesWriter.keepActions(Menu.user.getUserName(),"Update_A_User");
		}else {
			System.out.println("Update fail , propably you give wrong username: "+ usernameInput +".");
		}	
	}

	public void assignRole(Integer number, String usernameInput){
		if (dbAssignRole(number , usernameInput) > 0) {
			System.out.println("Role assignment succefull.");
			filesWriter.keepActions(Menu.user.getUserName(),"Assign_A_role");
		}else {
			System.out.println("Assignment fail , probably you gave wrong username: "+ usernameInput +".");
		}
	}

	public AdminRole() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	///////////////////////
	//Database methods////

	public int dbCreateUser(String username , String password) {
		int rows = 0 ;
		try {
			database.connect();
			PreparedStatement ps;
			ps = database.connect().prepareStatement("INSERT INTO users (username, password) VALUES (?, AES_ENCRYPT(? , 'secret'));");
			ps.setString(1,username);
			ps.setString(2, password);
			rows = ps.executeUpdate();
			database.connect().close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block			
		}
		return rows ;
	}

	public int dbDeleteUser(String usernameInput) {
		int rows = 0 ;
		try {
			database.connect();
			PreparedStatement ps;
			ps = database.connect().prepareStatement("DELETE FROM users WHERE username = ? ;");
			ps.setString(1,usernameInput);		
			rows = ps.executeUpdate();
			database.connect().close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");
		}
		return rows;
	}

	public int dbUpdateUser(int number , String newUsername , String newPassword , String usernameInput) {
		int rows = 0 ;
		try {
			database.connect();
			if (number == 1) {
				PreparedStatement ps1;
				ps1 =database.connect().prepareStatement("UPDATE users SET username = ? WHERE username = ? ;");
				ps1.setString(1, newUsername);
				ps1.setString(2, usernameInput);
				rows = ps1.executeUpdate();
			}		
			if (number == 2) {
				PreparedStatement ps2;
				ps2 =database.connect().prepareStatement("UPDATE users SET password =  AES_ENCRYPT(? , 'secret') WHERE username = ? ;");
				ps2.setString(1,newPassword);	
				ps2.setString(2, usernameInput);
				rows = ps2.executeUpdate();
			}	
			if (number == 3) {
				PreparedStatement ps3;
				ps3 =database.connect().prepareStatement("UPDATE users SET username = ? , password = AES_ENCRYPT(? , 'secret')  WHERE username = ? ; ");
				ps3.setString(1,newUsername);	
				ps3.setString(2,newPassword);	
				ps3.setString(3,usernameInput);	
				rows = ps3.executeUpdate();
			}
			database.connect().close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");
		}
		return rows;
	}

	public int dbAssignRole(int number , String usernameInput) {
		int rows = 0 ;
		try {
			database.connect();
			
			if (number == 1) {
				PreparedStatement ps3;
				ps3 =database.connect().prepareStatement("UPDATE users SET role ='NoRole' where username = ? ;");
				ps3.setString(1,usernameInput);		
				rows = ps3.executeUpdate();
			}
			
			if (number == 2) {
				PreparedStatement ps1;
				ps1 =database.connect().prepareStatement("UPDATE users SET role ='EditRole' where username = ? ;");
				ps1.setString(1,usernameInput);		
				rows = ps1.executeUpdate();
			}		
			if (number == 3) {
				PreparedStatement ps2;
				ps2 =database.connect().prepareStatement("UPDATE users SET role ='DeleteRole' where username = ? ;");
				ps2.setString(1,usernameInput);		
				rows = ps2.executeUpdate();
			}	
			
			database.connect().close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");
		}
		return rows;
	}
	
	
	
}

