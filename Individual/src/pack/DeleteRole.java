package pack;

public class DeleteRole extends EditRole {
	
	public void deleteMessage(int idmsg , int iduser){
		if(database.deleteMessage(idmsg , iduser) > 0) {
			System.out.printf("Message with id:%1$s deleted successfully",idmsg);
			filesWriter.keepActions(Menu.user.getUserName(),"Delete_Message");
		}else {
			System.out.println("Message did not delete , propably you put wrong message: " + idmsg + " number.");
		}
	}

	public DeleteRole() {
		// TODO Auto-generated constructor stub
	}

}
