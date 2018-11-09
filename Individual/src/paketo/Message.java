package paketo;

public class Message {
	
	private int id;
	private String messageData;
	private String reciever;
	private String sender;
	private String status;
	private String date;
	

	public Message() {
		// TODO Auto-generated constructor stub
		
	}
	public Message(int id, String messageData, String reciever, String sender, String status, String date) {
		super();
		this.id = id;
		this.messageData = messageData;
		this.reciever = reciever;
		this.sender = sender;
		this.status = status;
		this.date = date;
	}


	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getMessageData() {
		return messageData;
	}


	public void setMessageData(String messageData) {
		this.messageData = messageData;
	}


	public String getReciever() {
		return reciever;
	}


	public void setReciever(String reciever) {
		this.reciever = reciever;
	}


	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String string) {
		this.date = string;
	}


	@Override
	public String toString() {
		return "Message [id=" + id + ", messageData=" + messageData + ", reciever=" + reciever + ", sender=" + sender
				+ ", status=" + status + ", date=" + date + "]";
	}

	
	
}
