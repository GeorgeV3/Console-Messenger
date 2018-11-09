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
	private String username = "root";
	private String password = "1234";

	private Connection connection;
	private Statement stm;
	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
	private DateFormat dformat = new SimpleDateFormat("MM/dd/yyyy");	

	public Connection connect() {
		try {
			connection = DriverManager.getConnection(url, username, password);
			return connection;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Problem with conection on server/database.");
			return null;
		}
	}

	public int executeUpdateStatement(String sql) {
		try {
			connect();
			stm = connection.createStatement();
			stm.executeUpdate(sql);
			connection.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("wrong execute statment.");
			return -22;
		}
		return 0;
	}

	public ResultSet executeQueryStatement(String sql) {
		ResultSet rs = null;
		try {
			connect();
			stm = connection.createStatement();
			rs = stm.executeQuery(sql);

			connect().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("wrong execute statment.");
		}
		return rs;
	}

	public ArrayList<Message> getAllMessages(String username)  {
		ArrayList<Message> messageList = new ArrayList<>();
		try {
			connect();
			PreparedStatement ps = connection.prepareStatement("Select * from inbox where receiver = ?");
			ps.setString(1,username);
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {
				Message message = new Message();
				message.setId(rst.getInt("idinbox"));
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

	public ArrayList<Message> getMessage(String username , int id) {
		ArrayList<Message> messageList = new ArrayList<>();
		try {
			connect();
			PreparedStatement ps = connection.prepareStatement("Select * from inbox where idinbox = ? and receiver = ?;");
			ps.setInt(1,id);
			ps.setString(2,username);
			ResultSet rst = ps.executeQuery();
			while (rst.next()) {
				Message message = new Message();
				message.setId(rst.getInt("idinbox"));
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

	public int countStatusMessages(String username , String status) {
		int countStatus = 0;
		try {
			connect();
			PreparedStatement ps;
			ps = connection.prepareStatement("Select count(status) from inbox where receiver = ? and status = 'unread';");
			ps.setString(1,username);
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
			String sql = "select * from questions";
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next())  {
				System.out.println("\t"+rs.getString("question")+"\t\t\t"+rs.getString("senderQ")); 

			}connect().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("wrong execute statment.");
		}
	}

	public boolean updateCredits(String username) {	
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
		return true;
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
			ps = connection.prepareStatement("INSERT INTO `individualdb`.`inbox` (`receiver`, `sender`, `message`) VALUES (?, ?, ?);");
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





}







