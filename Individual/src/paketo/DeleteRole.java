package paketo;

public class DeleteRole extends EditRole {
	
	public void deleteMessage(int idmsg , int iduser){
		Database database = new Database();
		if(database.deleteMessage(idmsg , iduser) > 0) {
			System.out.printf("Message with id:%1$s deleted successfully",idmsg);
		}else {
			System.out.println("Message did not delete , propably you put wrong id: " + idmsg + " number.");
		}
	}

	public DeleteRole() {
		// TODO Auto-generated constructor stub
	}

}
