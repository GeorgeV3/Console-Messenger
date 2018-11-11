package paketo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Database {

	public Database() {
		// TODO Auto-generated constructor stub
	}
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
			PreparedStatement ps = connection.prepareStatement("Select * from inbox where receiver = ?");
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
			System.out.println("wrong execute statment.");
		} return messageList;
	}

	public ArrayList<Message> getMessage(int idmsg , int iduser  ) {
		ArrayList<Message> messageList = new ArrayList<>();
		try {
			connect();
			PreparedStatement ps = connection.prepareStatement("Select * from inbox where idmsg = ? and receiver = ?;");
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
			System.out.println("wrong execute statment.");
		} return messageList;
	}

	public int countStatusMessages(int iduser , String status) {
		int countStatus = 0;
		try {
			connect();
			PreparedStatement ps;
			ps = connection.prepareStatement("Select count(status) from inbox where receiver = ? and status = 'unread';");
			ps.setInt(1,iduser);
			ps.setString(2, status);
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {
				countStatus = rst.getInt("count(status)");
			}connect().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statment.");
		}
		return countStatus;
	}

	public boolean checkIfExistUser(String username) {
		try {
			connect();
			PreparedStatement ps;
			ps = connection.prepareStatement("Select username from users where username = ?;");
			ps.setString(1,username);
			ResultSet rst = ps.executeQuery();
			//while (rst.next()) {
			String userName = rst.getString("username");
			//}	
			connect().close();
			if (username.equals(userName)) {
				System.out.println("Message send it.");
				return true;
			}else {
				System.out.printf("Message fail to send it.\nNo user with name %1$s exist in database.",username);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statment.");
		}
		return false;		
	}

	public void getAllQuestions() {
		try {
			connect();
			stm = connection.createStatement();
			String sql = "select * from questions;";
			ResultSet rst = stm.executeQuery(sql);
			while(rst.next())  {
				int id = rst.getInt("idqts");
				String senderQ = rst.getString("senderQ");
				String question = rst.getString("question");
				Date date = format.parse(rst.getString("datetime"));
				String datetime = dformat.format(date);
				System.out.println("ID: " + id +"  Question: "+ question +"\nFrom: "+ senderQ + 
						" Date: " + datetime );
			}connect().close();
		} catch (SQLException | ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("wrong execute statment.");
		}
	}

	public void updateCredits(String username) {	
		try {
			connect();
			PreparedStatement ps;
			ps = connection.prepareStatement("UPDATE users SET credits = credits + 1  WHERE  username = ? ; ");
			ps.setString(1 , username);
			ps.executeUpdate();
			connect().close();		
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statment.");	
		}
	}

	public boolean changeStatus(String username , String status) {
		try {
			connect();
			PreparedStatement ps;
			ps = connection.prepareStatement("UPDATE users SET status = ?  WHERE  username = ? ; ");
			ps.setString(1 , status);
			ps.setString(2, username);
			ps.executeUpdate();
			connect().close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statment.");	
		}
		return true;
	}

	public boolean sendMessage (String receiver , String sender , String message ) {
		boolean messageSend = true;
		try {
			connect();
			PreparedStatement ps;
			ps = connection.prepareStatement("INSERT INTO inbox (receiver, sender , message) "
					+ "VALUES ((SELECT iduser FROM users WHERE username = ?)"
					+ ",?,?);");
			ps.setString(1,receiver);
			ps.setString(2,sender);
			ps.setString(3,message);
			ps.executeUpdate();
			connect().close();		
		} catch (SQLException e) {
			messageSend =false;
			// TODO Auto-generated catch block
			System.out.println("wrong execute statment.");		
		}return messageSend;	
	}

	public boolean deleteMessage(int idmsg , int iduser) {
		boolean messageDelete = true ;
		try {
			connect();
			System.out.println("idmsg:" + idmsg);
			System.out.println("iduser:" + iduser);
			PreparedStatement ps;
			ps = connection.prepareStatement("DELETE FROM inbox WHERE idmsg = ? and receiver = ? ;");
			ps.setInt(1,idmsg);
			ps.setInt(2,iduser);
			ps.executeUpdate();
			System.out.println("mesa stin delete message");
			connect().close();		
		} catch (SQLException e) {
			messageDelete = false ;
			// TODO Auto-generated catch block
			System.out.println("wrong execute statment.");		
		}
		return messageDelete;	
	}

	public boolean editQuestion(int idQts , String username , String newQ) {
		boolean question = true ;
		try {
			connect();
			PreparedStatement ps;
			ps = connection.prepareStatement("UPDATE questions\r\n" + 
					"SET senderQ = ?, question = ?\r\n" + 
					"WHERE idqts = ? ;");
			ps.setString(1,username);
			ps.setString(2, newQ);
			ps.setInt(3, idQts);
			ps.executeUpdate();
			connect().close();		
		} catch (SQLException e) {
			question = false ;
			// TODO Auto-generated catch block
			System.out.println("wrong execute statment.");		
		}
		return question;
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////     Admin methods
	
	public boolean createUser(String username , String password) {
		try {
			connect();
			PreparedStatement ps;
			ps = connection.prepareStatement("INSERT INTO users (username , password)\r\n" + 
					"VALUES (?,?);");
			ps.setString(1,username);
			ps.setString(2, password);
			ps.executeUpdate();
			connect().close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statment.");
			return false;
		}
		return true ;
	}
	
	public boolean deleteUser(String username){
		boolean userDelete = true ;
		try {
			connect();
			PreparedStatement ps;
			ps = connection.prepareStatement("DELETE FROM users WHERE username = ? ;");
			ps.setString(1,username);
			ps.executeUpdate();
			connect().close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statment.");
			userDelete = false;
		}
		return userDelete;
	}
	
	public boolean executeQuery(String query) {
		boolean userUpdate = true ;
		try {
			connect();
			stm = connection.createStatement();
			ResultSet rst = stm.executeQuery(query);
			connect().close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statment.");
			userUpdate = false;
		}
		return userUpdate;
	}
	
	public boolean executeUpdate(String query){
		boolean execute = true ;
		try {
			connect();
			Statement stm = connect().createStatement();
			stm.executeUpdate(query);
			connect().close();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("wrong execute statment.");
			execute = false;
		}
			return execute;
	}
	






}







