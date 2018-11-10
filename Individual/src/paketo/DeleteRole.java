package paketo;

public class DeleteRole extends EditRole {
	
	public void deleteMessage(int idmsg , int iduser){
		Database database = new Database();
		if(database.deleteMessage(idmsg, iduser) == true) {
			System.out.printf("Message with id:%1$s deleted successfully",idmsg);
		}else {
			System.out.println("Something gone wrong, message did not delete.");
		}
	}

	public DeleteRole() {
		// TODO Auto-generated constructor stub
	}

}
