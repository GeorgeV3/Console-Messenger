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

	private String url = "jdbc:mysql://localhost:3306/individualdb?useSSL=false";
	private String usernameDb = "user";
	private String passwordDb = "test";

	private Connection connection;
	private Statement stm;
	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DateFormat dformat = new SimpleDateFormat(" 'at' HH:mm:ss z dd/MM/yyyy");	

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
	
	
	public User getUserInfo(String username) {
		User user = new User();
		try {
			connect();	
			PreparedStatement ps = connect().prepareStatement("Select iduser ,username , role , status , credits "
					+ "from users where username = ? ;");
			ps.setString(1,username);
			ResultSet rst = ps.executeQuery();	
			while (rst.next()) {			
				user.setId(rst.getInt("iduser"));
				user.setUserName(rst.getString("username"));
				user.setUserRole(rst.getString("role"));
				user.setStatus(rst.getString("status"));
				user.setCredits(rst.getInt("credits"));			
			} connect().close();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statment.");
		} 	  
		return user;	 
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
			} connect().close();
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");
		} return messageList;
	}


	public void getAllQuestions() {
		try {
			connect();
			stm = connect().createStatement();
			String sql = "select * from questions order by datetime DESC;";
			ResultSet rst = stm.executeQuery(sql);
			while(rst.next())  {
				int id = rst.getInt("idqts");
				String senderQ = rst.getString("senderQ");
				String question = rst.getString("question");
				Date date = format.parse(rst.getString("datetime"));
				String datetime = dformat.format(date);
				System.out.println("\n\nQuestion: " + id +"\nText: "+ question +"\nFrom: "+ senderQ + 
						" " + datetime );
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
		}
		return rows;	
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

	public int changeMsgStatus(int idmsg) {
		int rows = 0;
		try {
			connect();
			PreparedStatement ps;
			ps = connect().prepareStatement("UPDATE inbox SET status = 'read'  WHERE  idmsg = ? ; ");
			ps.setInt(1 , idmsg);
			rows = ps.executeUpdate();
			connect().close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statement.");	
		}
		return rows;
	}


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

	public int changeUserStatus(String username , String status) {
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

	public int autoMsgToAdmin( String sender , int currentCredits) {
		int rows = 0 ;
		try {
			connect();
			PreparedStatement ps;
			String message = "The user " + sender + " reach " + currentCredits + " credits and request to be promote." ;
			ps = connect().prepareStatement("INSERT INTO inbox (receiver, sender , message) "
					+ "VALUES ((SELECT iduser FROM users WHERE username = 'admin' )"
					+ ",?,?);");
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

	////////////////////////////////////////////////////////////////
	//Time methods.

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
		LocalDateTime toDateTime = convertToLocalDateTimeViaSqlTimestamp(toDate);
		LocalDateTime fromDateTime = convertToLocalDateTimeViaSqlTimestamp(fromDate);	
		long hours = ChronoUnit.HOURS.between(fromDateTime, toDateTime);	
		return hours;
	}




}







