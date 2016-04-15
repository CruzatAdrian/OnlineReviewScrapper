package Main;

import java.io.File;
import Exceptions.FileNotFound;
import Exceptions.InsufficientArgumentsException;
import Utilities.ContentMatch;

public class ContentMatchMain {

	public static void main(String[] args) {

		File KeywordFile;
		File ReviewsFile;
		String Reviews;
		String KeywordsFileName;
		final String defaultKeyWordList = "KeyWords.txt";
		final String defaultReviewInput = "Reviews.txt";


		/*
		 * Description: This "if" block takes the command line arguments and instantiates the file objects to be used later. If the file is not found or 
		 * not enough arguments are provided the system will throw a corresponding exception.
		 * Preconditions: There are 0 or 2 arguments.
		 * Postconditions: KeywordFile, ReviewsFile, Reviews and KeyWordsFileName are all instantiated.
		 * Author: Adrian Cruzat
		 */

		try {
			

			if (args.length <= 0){
				KeywordFile = new File(defaultKeyWordList);
				ReviewsFile = new File(defaultReviewInput);
				KeywordsFileName = defaultKeyWordList;
				Reviews = defaultReviewInput;
				if((!KeywordFile.exists()) | (!ReviewsFile.exists())){
					throw new FileNotFound();
				}
			} else if (args.length == 1) {
				throw new InsufficientArgumentsException();
			} else {
				Reviews = args[0];
				KeywordsFileName = args[1];
				KeywordFile = new File(KeywordsFileName);
				ReviewsFile = new File(Reviews);
				if((!KeywordFile.exists()) | (!ReviewsFile.exists())) {
					throw new FileNotFound();
				}
			}


			ContentMatch.contentMatch(KeywordsFileName, Reviews, "Keywords_Output");

		} catch (FileNotFound var16) {
			System.out.println("File not found.");
		} catch (InsufficientArgumentsException e){
			System.out.println("Provide enough arguments or leave blank for default implementation!");
		}

	}



}
