package pack;

import java.io.Console;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
	private Database db = new Database();

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
			System.out.println("No user with this username or password exists.");
			return false;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// General methods for correct input


	public String getCorrectInput(int min , int max , String input) {
		Console console = System.console();	
		while(input.length() < min || input.length() > max || input.contains(" ")){
			System.out.println("Please enter a valid input!" +
					"\nAny char between the following range "+ min + "-" + max + " without space between them.");
			input = console.readLine();		
		}	
		return input;
	}

	public String getCorrectInputForMsg(int min , int max , String input) {
		Console console = System.console();
		while(input.length() < min || input.length() > max){
			System.out.println("Please enter a valid input!" +
					"\nAny char between the following range "+ min + "-" + max + " .");
			input = console.readLine();		
		}return input;
	}


}
