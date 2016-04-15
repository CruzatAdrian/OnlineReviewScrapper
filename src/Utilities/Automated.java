package Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Exceptions.FileNotFound;


public class Automated {

	public static void getReviews(String URLFile){
		ArrayList<String> YelpURL = new ArrayList<String>();
		ArrayList<String> TripURL = new ArrayList<String>();
		ArrayList<YelpReview> YelpReviews = new ArrayList<YelpReview>();
		ArrayList<TripAdvisorReview> TripReviews = new ArrayList<TripAdvisorReview>();
		MainStatistics Stats = new MainStatistics();

		try {
			File file;
			file = new File(URLFile);
			if(!file.exists()){
				throw new FileNotFound();
			}
			BufferedReader br = new BufferedReader(new FileReader(file));

			String Line;
			while((Line = br.readLine()) != null) {
				if(!Line.equals("")) {
					String[] urlFields = Line.split("/");
					String url = urlFields[2];

					
					if(url.equals("www.yelp.com")){
						YelpURL.add(Line);
					} else if(url.equals("www.tripadvisor.com")){
						TripURL.add(Line);
					} else {
						System.out.println(url + " is a bad Website!\n");
					}        
				}
			}

			
			br.close();

			for(String lineURL : TripURL){
				
				TripReviews.addAll(CreateTrip(lineURL,Stats));
			}

			WriteTripFile(TripReviews);

			for(String lineURL : YelpURL){
				YelpReviews.addAll(CreateYelp(lineURL, Stats));
			}

			WriteYelpFile(YelpReviews);

			WriteStatistics(Stats);


		} catch (FileNotFound var12) {
			System.out.println("File not found");
		} catch (IOException var13) {
			System.out.println("Could not read file");
		}

	}

	private static ArrayList<TripAdvisorReview> CreateTrip(String URL, MainStatistics Stats) {
		
		ArrayList<TripAdvisorReview> ReviewArray = new ArrayList<TripAdvisorReview>();
		
		int counter = 0;
		boolean bool = true;
		String[] tempFields = URL.split("-");
		String Location = tempFields[4];

		while(bool) {
			String[] Fields = URL.split("-");
			String tempURL = Fields[0] + "-" + Fields[1] + "-" + Fields[2] + "-" + Fields[3] + "-or" + counter + "-" + Fields[4] + "-" + Fields[5];
			counter += 10;
			TripGetter Tripper = new TripGetter(tempURL, Location);
			ArrayList<TripAdvisorReview> tempArray = Tripper.getData();
			ReviewArray.addAll(tempArray);
			if(tempArray.size() < 10) {
				bool = false;
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException var11) {
				Thread.currentThread().interrupt();
			}
		}

		Stats.putTrip(Location,ReviewArray.size());
		
		return ReviewArray;
	}

	private static ArrayList<YelpReview> CreateYelp(String URL, MainStatistics Stats) {
		int counter = 0;
		boolean bool = true;
		
		ArrayList<YelpReview> ReviewArray = new ArrayList<YelpReview>();
		String[] tempFields = URL.split("/");
		String Location = tempFields[4];
		

		while(bool) {
			String tempURL = URL + "?start=" + counter;
			YelpGetter Yelper = new YelpGetter(tempURL, Location);
			YelpTuple tuple = Yelper.getData();
			ReviewArray.addAll(tuple.getX());
			bool = tuple.isY();
			counter += 10;
			System.gc();
		}

		Stats.putYelp(Location, ReviewArray.size());

		return ReviewArray;
	}

	private static void WriteYelpFile(ArrayList<YelpReview> ReviewArray) {
		try {
			File file = new File("Reviews/YelpReviews.txt");
			if(!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(YelpReview.Fields);

			for(YelpReview r : ReviewArray){
				bw.write(r.toString() + "\n");
			}


			bw.close();
		} catch (Exception var6) {
			;
		}

	}

	private static void WriteTripFile(ArrayList<TripAdvisorReview> ReviewArray) {
		try {
			File file = new File("Reviews/TripReviews.txt");
			if(!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(TripAdvisorReview.Fields);

			for(TripAdvisorReview r : ReviewArray){
				bw.write(r.toString() + "\n");
			}

			bw.close();
		} catch (Exception var6) {
			;
		}

	}
	
	private static void WriteStatistics(MainStatistics Stats){
		try {
			File file = new File("Reviews/Statistics.txt");
			if(!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(Stats.yelpStatistics());
			bw.write(Stats.tripStatistics());
			bw.close();
		} catch (Exception var6) {
			;
		}
	}
}
