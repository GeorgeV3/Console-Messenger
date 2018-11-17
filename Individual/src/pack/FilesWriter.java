package pack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FilesWriter {

	
	public void keepActions(String username , String action) {
		BufferedWriter bw = null;
//		PrintWriter printWriter = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy G 'at' HH:mm:ss z");
		String dateFormat = sdf.format(date);
		String headLine = "------DATETIME-----------------USERNAME------ACTION----";
		try {
			//Specify the file name and path here
			//Important change the path where you want to save.
			File file = new File("C:\\Users\\Gv\\Desktop\\Keep_Actions");
			if (!file.exists()) {
				file.createNewFile();
				FileWriter fw2 = new FileWriter(file);
				BufferedWriter bw2 = new BufferedWriter(fw2);
				bw2.write(headLine);
				bw2.close();
			}
			FileWriter fw = new FileWriter(file , true);
//			printWriter = new PrintWriter(fw);
//			printWriter.printf("%2s | %8s | %5s %n", dateFormat , username , action);
			bw = new BufferedWriter(fw);
			bw.newLine();
			bw.write(dateFormat +" | " + username + "     | " + action );
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("\nChange the path of file to the currect one to fit on your local machine.");
		}
		finally
		{ 
			try{
				if(bw!=null)
					bw.close();

			}catch(Exception ex){
				System.out.println("Error in closing the BufferedWriter"+ex);
			}
		}
	}	

	public void keepMessages(String sender , String receiver , String  messageData) {
		BufferedWriter bw = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy G 'at' HH:mm:ss z");
		String dateFormat = sdf.format(date);
		String headLine = "------DATETIME-----------------SENDER------RECEIVER----MESSAGE---";
		try {
			//Specify the file name and path here
			//Important change the path where you want to save.
			File file = new File("C:\\Users\\Gv\\Desktop\\Keep_Messages");
			if (!file.exists()) {
				file.createNewFile();
				FileWriter fw2 = new FileWriter(file);
				BufferedWriter bw2 = new BufferedWriter(fw2);
				bw2.write(headLine);
				bw2.close();
			}
			FileWriter fw = new FileWriter(file , true);
			bw = new BufferedWriter(fw);
			bw.newLine();
			bw.write(dateFormat + " | " + sender + "  | " + receiver + "  | " + messageData );
		} catch (IOException ioe) {
			ioe.printStackTrace();			
			System.out.println("\nChange the path of file to the currect one to fit on your local machine.");	
		}
		finally
		{ 
			try{
				if(bw!=null)
					bw.close();
			}catch(Exception ex){
				System.out.println("Error in closing the BufferedWriter"+ex);
			}
		}
	}




}
