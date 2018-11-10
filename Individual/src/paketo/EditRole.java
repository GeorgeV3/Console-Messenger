package paketo;

public class EditRole extends User {
	
	

	public EditRole() {
		// TODO Auto-generated constructor stub
	}


	public void editQuestion(int idQts, String username, String newQ){
		Database database = new Database();
		if (database.editQuestion(idQts, username, newQ) == true) {
			System.out.printf("Question with id:%1$s successfully update with new question.", idQts);
		}else {
			System.out.println("Something gone wrong, question did not upgrade.");
		}
	}
	

}
