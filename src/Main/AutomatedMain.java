package Main;

import java.io.File;
import Exceptions.FileNotFound;
import Utilities.Automated;


public class AutomatedMain {

	//This is a test comment
	public static void main(String[] args) {
		final String defaultURLFile = "URLList.txt";
		String fileName;
		File file;
		

		

		try {
			if (args.length <= 0){
				fileName = defaultURLFile;
				file = new File(fileName);
				if(!file.exists()){
					throw new FileNotFound();
				}
			} else {
				fileName = args[0];
				file = new File(fileName);
				if(!file.exists()) {
					throw new FileNotFound();
				}
			}
			
			Automated.getReviews(fileName);
		} catch (FileNotFound e){
			System.out.println("File not found");
		}

	}
}
