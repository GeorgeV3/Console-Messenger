package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;


public class Database {

	public Database() {
		// TODO Auto-generated constructor stub
	}
	
	//// user - test
	private String url = "jdbc:mysql://localhost:3306/individualdb?useSSL=false";
	private String usernameDb = "root";
	private String passwordDb = "1234";

	private Connection connection;
	private Statement stm;
	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DateFormat dformat = new SimpleDateFormat("MM/dd/yyyy G 'at' HH:mm:ss z");	

	public Connection connect() {
		try {
			connection = DriverManager.getConnection(url, usernameDb, passwordDb);
			return connection;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Problem with conection on server/database.");
			return null;
		}
	}

	public ArrayList<Message> getAllMessages(int iduser)  {
		ArrayList<Message> messageList = new ArrayList<>();
		try {
			connect();
			PreparedStatement ps = connect().prepareStatement("Select * from inbox where receiver = ?");
			ps.setInt(1,iduser);
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {
				Message message = new Message();
				message.setId(rst.getInt("idmsg"));
				message.setReciever(rst.getString("receiver"));
				message.setSender(rst.getString("sender"));
				message.setMessageData(rst.getString("message"));
				message.setStatus(rst.getString("status"));
				Date date = format.parse(rst.getString("date"));
				message.setDate(dformat.format(date));				
				messageList.add(message);
				// System.out.println(messageList);
			} connect().close();
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");
		} return messageList;
	}

	public ArrayList<Message> getMessage(int idmsg , int iduser  ) {
		ArrayList<Message> messageList = new ArrayList<>();
		try {
			connect();
			PreparedStatement ps = connect().prepareStatement("Select * from inbox where idmsg = ? and receiver = ?;");
			ps.setInt(1,idmsg);
			ps.setInt(2,iduser);
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {
				Message message = new Message();
				message.setId(rst.getInt("idmsg"));
				message.setReciever(rst.getString("receiver"));
				message.setSender(rst.getString("sender"));
				message.setMessageData(rst.getString("message"));
				message.setStatus(rst.getString("status"));
				Date date = format.parse(rst.getString("date"));
				message.setDate(dformat.format(date));				
				messageList.add(message);
				// System.out.println(messageList);
			} connect().close();
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");
		} return messageList;
	}

	//	public int countStatusMessages(int iduser , String status) {
	//		int countStatus = 0;
	//		try {
	//			connect();
	//			PreparedStatement ps;
	//			ps = connection.prepareStatement("Select count(status) from inbox where receiver = ? and status = ? ;");
	//			ps.setInt(1,iduser);
	//			ps.setString(2, status);
	//			ResultSet rst = ps.executeQuery();
	//			while (rst.next()) {
	//				countStatus = rst.getInt("count(status)");
	//			}connect().close();
	//		} catch (SQLException e) {
	//			// TODO Auto-generated catch block
	//			System.out.println("wrong execute statment.");
	//		}
	//		return countStatus;
	//	}

	//	public boolean checkIfExistUser(String username) {
	//		try {
	//			connect();
	//			PreparedStatement ps;
	//			ps = connection.prepareStatement("Select username from users where username = ?;");
	//			ps.setString(1,username);
	//			ResultSet rst = ps.executeQuery();
	//			//while (rst.next()) {
	//			String userName = rst.getString("username");
	//			//}	
	//			connect().close();
	//			if (username.equals(userName)) {
	//				System.out.println("Message send it.");
	//				return true;
	//			}else {
	//				System.out.printf("Message fail to send it.\nNo user with name %1$s exist in database.",username);
	//			}
	//		} catch (SQLException e) {
	//			// TODO Auto-generated catch block
	//			System.out.println("wrong execute statment.");
	//		}
	//		return false;		
	//	}

	public void getAllQuestions() {
		try {
			connect();
			stm = connect().createStatement();
			String sql = "select * from questions order by datetime;";
			ResultSet rst = stm.executeQuery(sql);
			while(rst.next())  {
				int id = rst.getInt("idqts");
				String senderQ = rst.getString("senderQ");
				String question = rst.getString("question");
				Date date = format.parse(rst.getString("datetime"));
				String datetime = dformat.format(date);
				System.out.println("\n\nID: " + id +"  Question: "+ question +"\nFrom: "+ senderQ + 
						" Date: " + datetime );
			}connect().close();
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");
		}
	}



	public int sendMessage (String receiver , String sender , String message ) {
		int rows = 0 ;
		try {
			connect();
			PreparedStatement ps;
			ps = connect().prepareStatement("INSERT INTO inbox (receiver, sender , message) "
					+ "VALUES ((SELECT iduser FROM users WHERE username = ?)"
					+ ",?,?);");
			ps.setString(1,receiver);
			ps.setString(2,sender);
			ps.setString(3,message);
			rows = ps.executeUpdate();
			connect().close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}return rows;	
	}

	public int deleteMessage(int idmsg , int iduser) {
		int rows = 0 ;
		try {
			connect();
			PreparedStatement ps;
			ps = connect().prepareStatement("DELETE FROM inbox WHERE idmsg = ? and receiver = ? ;");
			ps.setInt(1,idmsg);
			ps.setInt(2,iduser);
			rows = ps.executeUpdate();
			connect().close();		
		} catch (SQLException e) {		
			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");		
		}
		return rows;	
	}

	public int editQuestion(int idQts , String username , String newQ) {
		int rows = 0;
		try {
			connect();
			PreparedStatement ps;
			ps = connect().prepareStatement("UPDATE questions\r\n" + 
					"SET senderQ = ?, question = ? , datetime = CURRENT_TIMESTAMP \r\n" + 
					"WHERE idqts = ? ;");
			ps.setString(1,username);
			ps.setString(2, newQ);
			ps.setInt(3, idQts);
			rows = ps.executeUpdate();
			connect().close();		
		} catch (SQLException e) {
		
			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");		
		}
		return rows;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////     Admin methods

	public int createUser(String username , String password) {
		int rows = 0 ;
		try {
			connect();
			PreparedStatement ps;
			ps = connect().prepareStatement("INSERT INTO users (username, password) VALUES (?, AES_ENCRYPT(? , 'secret'));");
			ps.setString(1,username);
			ps.setString(2, password);
			rows = ps.executeUpdate();
			connect().close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");
			
		}
		return rows ;
	}

	public int deleteUser(String usernameInput) {
		int rows = 0 ;
		try {
			connect();
			PreparedStatement ps;
			ps = connect().prepareStatement("DELETE FROM users WHERE username = ? ;");
			ps.setString(1,usernameInput);		
			rows = ps.executeUpdate();
			connect().close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");
		}
		return rows;
	}

	public int updateUser(int number , String newUsername , String newPassword , String usernameInput) {
		int rows = 0 ;
		try {
			connect();
			if (number == 1) {
				PreparedStatement ps1;
				ps1 =connect().prepareStatement("UPDATE users SET username = ? WHERE username = ? ;");
				ps1.setString(1, newUsername);
				ps1.setString(2, usernameInput);
				rows = ps1.executeUpdate();
			}		
			if (number == 2) {
				PreparedStatement ps2;
				ps2 =connect().prepareStatement("UPDATE users SET password = ? WHERE username = ? ;");
				ps2.setString(1,newPassword);	
				ps2.setString(2, usernameInput);
				rows = ps2.executeUpdate();
			}	
			if (number == 3) {
				PreparedStatement ps3;
				ps3 =connect().prepareStatement("UPDATE users SET username = ? , password = ?  WHERE username = ? ; ");
				ps3.setString(1,newUsername);	
				ps3.setString(2,newPassword);	
				ps3.setString(3,usernameInput);	
				rows = ps3.executeUpdate();
			}
			connect().close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");
		}
		return rows;
	}

	public int assignRole(int number , String usernameInput) {
		int rows = 0 ;
		try {
			connect();
			if (number == 1) {
				PreparedStatement ps1;
				ps1 =connect().prepareStatement("UPDATE users SET role ='EditRole' where username = ? ;");
				ps1.setString(1,usernameInput);		
				rows = ps1.executeUpdate();
			}		
			if (number == 2) {
				PreparedStatement ps2;
				ps2 =connect().prepareStatement("UPDATE users SET role ='DeleteRole' where username = ? ;");
				ps2.setString(1,usernameInput);		
				rows = ps2.executeUpdate();
			}	
			if (number == 3) {
				PreparedStatement ps3;
				ps3 =connect().prepareStatement("UPDATE users SET role ='NoRole' where username = ? ;");
				ps3.setString(1,usernameInput);		
				rows = ps3.executeUpdate();
			}
			connect().close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");
		}
		return rows;
	}

	////////////////////////////////////////////////
	///////////////////////////////////////////////
	//////////////////////////////////////////////
	//General methods

	public void updateCredits(String username) {	
		try {
			connect();
			PreparedStatement ps;
			ps = connect().prepareStatement("UPDATE users SET credits = credits + 1  WHERE  username = ? ; ");
			ps.setString(1 , username);
			ps.executeUpdate();
			connect().close();		
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");	
		}
	}

	public int changeStatus(String username , String status) {
		int rows = 0;
		try {
			connect();
			PreparedStatement ps;
			ps = connect().prepareStatement("UPDATE users SET status = ?  WHERE  username = ? ; ");
			ps.setString(1 , status);
			ps.setString(2, username);
			rows = ps.executeUpdate();
			connect().close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");	
		}
		return rows;
	}


	public int getCredits(String username) {
		int getCredits = 0;
		try {
			PreparedStatement ps = connect().prepareStatement("Select credits from users where username = ? ; ");
			ps.setString(1,username);
			ResultSet rst = ps.executeQuery();	
			while (rst.next()) {			
				getCredits = rst.getInt("credits");
			} connect().close();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");
		} 	  
		return getCredits;
	}

	public Date getDateTimeQuestion() {
		Date date = null;
		String queryDatetime = "Select datetime from questions order by datetime limit 1 ;";
		try {
			connect();
			stm = connect().createStatement();
			ResultSet rst = stm.executeQuery(queryDatetime);
			while (rst.next()){
				date = format.parse(rst.getString("datetime"));	
			}		
			connect().close();		
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");		
		}
		return date;
	}

	public static LocalDateTime convertToLocalDateTimeViaSqlTimestamp(Date dateToConvert) {
		return new java.sql.Timestamp(
				dateToConvert.getTime()).toLocalDateTime();
	}

	public long checkTime() {
		Date toDate = new Date();
		Date fromDate = getDateTimeQuestion();
		LocalDateTime fromDateTime = convertToLocalDateTimeViaSqlTimestamp(toDate);
		LocalDateTime toDateTime = convertToLocalDateTimeViaSqlTimestamp(fromDate);
		long hours = ChronoUnit.HOURS.between(toDateTime, fromDateTime);	
		return hours;
	}

	public int getIdqts() {
		int getIdqts = 0;
		String queryIdqts = "Select idqts from questions order by datetime limit 1 ;";
		try {
			connect();
			stm = connect().createStatement();
			ResultSet rst = stm.executeQuery(queryIdqts);
			while (rst.next()){
				getIdqts = rst.getInt("idqts");	
			}		
			connect().close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");		
		}
		return getIdqts;
	}

	public int autoMsgToAdmin(String receiver , String sender , int currentCredits) {
		int rows = 0 ;
		try {
			connect();
			PreparedStatement ps;
			String message = "The user " + sender + " reach " + currentCredits + " credits and request to be promote." ;
			ps = connect().prepareStatement("INSERT INTO inbox (receiver, sender , message) "
					+ "VALUES ((SELECT iduser FROM users WHERE username = ?)"
					+ ",?,?);");
			ps.setString(1,receiver);
			ps.setString(2,sender);
			ps.setString(3,message);
			rows = ps.executeUpdate();
			connect().close();		
		} catch (SQLException e) {

			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");		
		}return rows;	
	}
	
	public String getUserStatus(String username) {
		String status = null;
		try {
			connect();
			PreparedStatement ps;
			ps = connect().prepareStatement("Select status from users where username = ? ;");
			ps.setString(1, username);
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {			
				status = rst.getString("status");
			}
			connect().close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");		
		} return status;
	}

}







