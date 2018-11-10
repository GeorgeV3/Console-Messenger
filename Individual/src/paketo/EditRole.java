package paketo;

public class EditRole extends User {
	
	

	public EditRole() {
		// TODO Auto-generated constructor stub
	}


	public void edit(int idqts , String username , String newQ){
		Database database = new Database();
		if (database.editQuestion(idqts, username, newQ)== true) {
			System.out.printf("Question with id:%1$s successfully update with new question.", idqts);
		}else {
			System.out.println("Something gone wrong, question did not upgrade.");
		}
	}
	

}
