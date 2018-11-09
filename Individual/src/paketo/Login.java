package paketo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

	public Login() {
		// TODO Auto-generated constructor stub
	}

	public boolean validateLogin(String username , String password){
		if (username == null || username.trim().length()==0){
			System.out.println("Username required");
			return false;
		}
		if (password == null || password.trim().length()==0){
			System.out.println("Password required");
			return false;
		}
		return true;
	}

	public boolean validateServerLogin(String username , String password) {
		Database db = new Database();
		String user = null;
		String pass = null;	
		try {
			db.connect();
			PreparedStatement ps;
			ps = db.connect().prepareStatement("Select username , password from users where username = ? and password = ? ;");
			ps.setString(1,username);
			ps.setString(2, password);
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {
				user = rst.getString("username");
				pass = rst.getString("password");
			}db.connect().close();	 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statment.");
		}
		if (username.trim().equals(user) && password.trim().equals(pass)) {
			System.out.println("pass");
			return true;
		}else {
			System.out.println("No user with this username or password not exist.");
			return false;
		}
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// General methods i create them in login class for don't do create a new helper one , because in menu class i will call ///
	// the login methods , so for don't make a new Helper helper = new Helper(); to use the already new login.               ///
	public User getUserInfo(String username) {
		Database db = new Database();
		User user = new User();
		String role = null;
		String userName = null;
		int iduser;
		int credits;
		try {
			db.connect();	
			PreparedStatement ps = db.connect().prepareStatement("Select iduser ,username , role , credits from users where username = ? ;");
			ps.setString(1,username);
			ResultSet rst = ps.executeQuery();	
			while (rst.next()) {			
				iduser = rst.getInt("iduser");
				userName = rst.getString("username");
				role = rst.getString("role");
				credits = rst.getInt("credits");
				user.setUserName(userName);
				user.setId(iduser);
				user.setRole(role);
				user.setCredits(credits);		
				System.out.println("\n"+role);		
			} db.connect().close();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statment.");
		} 	  
		return user;	 
	}

	public User createUserRole(User user) { 
		if (user.getRole() != null && user.getRole().equals("Admin")) {
			System.out.print("mesa stin if tou create admin");
			user = new AdminRole();
		}
		if (user.getRole() != null && user.getRole().equals("EditRole")) {
			user = new EditRole();
		}
		if (user.getRole() != null && user.getRole().equals("DeleteRole")) {
			user = new DeleteRole();
		}
		return user;
	}







}
