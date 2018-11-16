package paketo;

import java.io.Console;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

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
			ps = db.connect().prepareStatement("Select username ,CAST(AES_DECRYPT(password, 'secret') AS CHAR(30)) pwrd from users where username = ? ;");
			ps.setString(1,username);
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {
				user = rst.getString("username");
				pass = rst.getString("pwrd");
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
	// General methods ,, i create them in login class so i don't  create a new helper one , because in menu class i will call ///
	// the login methods , so for don't make a new Helper helper = new Helper(); to use the already new login.               ///
	public User getUserInfo(String username) {
		Database db = new Database();
		User user = new User();
		String role = null;
		try {
			db.connect();	
			PreparedStatement ps = db.connect().prepareStatement("Select iduser ,username , role , status , credits "
					+ "from users where username = ? ;");
			ps.setString(1,username);
			ResultSet rst = ps.executeQuery();	
			while (rst.next()) {			
				user.setId(rst.getInt("iduser"));
				user.setUserName(rst.getString("username"));
				role =(rst.getString("role"));
				user.setRole(role);
				user.setStatus(rst.getString("status"));
				user.setCredits(rst.getInt("credits"));		
				//System.out.println("\n"+role);		
			} db.connect().close();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statment.");
		} 	  
		return user;	 
	}

	public User createUserRole(User user) { 
		if (user.getRole() != null && "Admin".equalsIgnoreCase(user.getRole())) {
			//System.out.print("mesa stin if tou create admin");
			user = new AdminRole();
		}
		if (user.getRole() != null && "EditRole".equalsIgnoreCase(user.getRole())) {
			//System.out.print("mesa stin if tou create editorle");
			user = new EditRole();
		}
		if (user.getRole() != null && "DeleteRole".equalsIgnoreCase(user.getRole())) {
			//System.out.print("mesa stin if tou create deleterole");
			user = new DeleteRole();
		}
		return user;
	}

	//!input.matches("[a-zA-Z0-9]+") ||
	public String getCorrectInput(int min , int max , String input) {
		Console console2 = System.console();	
		while(input.length() < min || input.length() > max || input.contains(" ")){
			System.out.println("Please enter a valid input!" +
					"\nAny char between rage "+ min + "-" + max + " .!");
			input = console2.readLine();		
		}	
		return input;
	}







}
