package Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.regex.Pattern;

import Exceptions.FileNotFound;

public class ContentMatch {

	public static void contentMatch(String keywordsFilename, String reviewsFilename, String outputFilename){

		ArrayList<String> keyWordsList = new ArrayList<String>();
		ArrayList<String> matchesList = new ArrayList<String>();
//		ArrayList<String> matchContext = new ArrayList<String>();
		HashMap<String, Integer> matchCount = new HashMap<String, Integer>();

		/*
		 * Description: This "if" block takes the command line arguments and instantiates the file objects to be used later. If the file is not found or 
		 * not enough arguments are provided the system will throw a corresponding exception.
		 * Preconditions: There are 0 or 2 arguments.
		 * Postconditions: KeywordFile, ReviewsFile, Reviews and KeyWordsFileName are all instantiated.
		 * Author: Adrian Cruzat
		 */

		try {
			File KeywordFile;
			File ReviewsFile;
			
			KeywordFile = new File(keywordsFilename);
			ReviewsFile = new File(reviewsFilename);
			if((!KeywordFile.exists()) | (!ReviewsFile.exists())) {
				throw new FileNotFound();
			}
			

			/*
			 * Description: This section of the code creates a new buffered reader from the KeywordFile instance. It then adds all the keywords to an array.
			 * Preconditions: KeywordFile exists and is not = null, each keyword is listed on a single line in the file;
			 * Postconditions: An array containing all the keywords is instantiated.
			 * Author: Adrian Cruzat
			 * Possible Changes: This section could be created as a function of its own instead of being hard coded in the middle of the program
			 */

			BufferedReader br = new BufferedReader(new FileReader(KeywordFile));

			String KeywordLine;
			while((KeywordLine = br.readLine()) != null) {
				if(!KeywordLine.equals("")) {
					keyWordsList.add(KeywordLine.trim());
				}
			}

			/*
			 * Description: This section of the code creates a buffered reader from the reviews file and then adds all the reviews that match any of the given keywords to an array.
			 * Preconditions: The array with keywords != null, ReviewsFile != null, each review is in a single line.
			 * Postconditions: An array containing all reviews that matched a keyword is created
			 * Author: Adrian Cruzat
			 */

			BufferedReader br2 = new BufferedReader(new FileReader(ReviewsFile));
			for(String word : keyWordsList){
				word = word.toLowerCase();
				matchCount.put(word,0);
			}

			String ReviewLine;
			while((ReviewLine = br2.readLine()) != null) {
				if(!ReviewLine.equals("")) {
					String[] tempFields = ReviewLine.split("\",\"");
					int Size = tempFields.length;
					String content = tempFields[Size - 1];
//					String[] contentSentences = content.split(Pattern.quote("."));

					for(String word : keyWordsList){
						content = content.toLowerCase();
						word = word.toLowerCase();
						String wordRegEx = ".*\\b" + word + "\\b.*";


						if(content.matches(wordRegEx)) {

							matchesList.add(ReviewLine);

//							String contextLine = "";
//							int len = contentSentences.length;
							matchCount.put(word, matchCount.get(word) + 1);

							/*
							 * This method still needs more work as it doesn't match all instances
							 */
//							for(int a = 0; a < len; a++){
//
//								String sentence = contentSentences[a];
//								if(sentence.matches(wordRegEx)) {
//									if(a > 0){
//										contextLine += (contentSentences[a-1] + ".");
//									}
//									contextLine += contentSentences[a] + ".";
//									if (a < len - 1){
//										contextLine += (contentSentences[a+1] + ".");
//									}
//									matchContext.add(contextLine);
//									break;
//								}
//							}
							break;
						}
					}

				}
			}

			br2.close();
			br.close();

			WriteFile(outputFilename, matchesList);
			WriteStatistics(outputFilename, matchCount);
//			WriteContext(outputFilename, matchContext);

		} catch (ArrayIndexOutOfBoundsException var15) {
			System.out.println("Please specify command line arguments!");
			return;
		} catch (FileNotFound var16) {
			System.out.println("File not found.");
		} catch (IOException var17) {
			System.out.println("Could not read file.");
		} 

	}
	
	private static void WriteFile(String TopKeyWord, ArrayList<String> ReviewArray) {
		try {
			File file = new File("Matches_File_" + TopKeyWord + ".txt");
			if(!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(TripAdvisorReview.Fields);
			bw.write(YelpReview.Fields);

			for(String r : ReviewArray){
				bw.write(r + "\n");
			}

			bw.close();
		} catch (Exception var7) {
			;
		}

	}

	private static void WriteStatistics(String TopKeyWord, HashMap<String, Integer> matchCount){

		try{
			File file = new File("Statistics_File_" + TopKeyWord + ".txt");
			if(!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for(String keyword : matchCount.keySet()){
				bw.write(keyword + " was matched " + matchCount.get(keyword) + " time(s)\n");
			}
			bw.close();
			
		} catch (Exception var7) {
			;
		}
	}
	
	/*
	 * This Method needs more work
	 */
//	
//	private static void WriteContext(String TopKeyWord, ArrayList<String> matchContext){
//		try{
//			File file = new File("Context_File_" + TopKeyWord + ".txt");
//			if(!file.exists()) {
//				file.createNewFile();
//			}
//			FileWriter fw = new FileWriter(file.getAbsoluteFile());
//			BufferedWriter bw = new BufferedWriter(fw);
//			for(String Line : matchContext){
//				bw.write(Line + "\n");
//			}
//			bw.close();
//		} catch (Exception var7) {
//			;
//		}
//	}
}
