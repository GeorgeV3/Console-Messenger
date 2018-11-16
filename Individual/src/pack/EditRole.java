package pack;

public class EditRole extends User {
	

	public EditRole() {
		// TODO Auto-generated constructor stub
	}

	public void editQuestion(int idQts, String username, String newQ){
		if (database.editQuestion(idQts, username, newQ) > 0) {
			System.out.printf("Question with id: %1$s successfully update with new question.", idQts);
			filesWriter.keepActions(user.getUserName(),"Edit_Question");
		}else {
			System.out.println("Something gone wrong, question did not upgrade.");
		}
	}
	

}