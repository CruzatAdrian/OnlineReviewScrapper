package Utilities;

import java.util.HashMap;

public class MainStatistics {
	private HashMap<String, Integer> yelpCount = new HashMap<String, Integer>();
	private HashMap<String, Integer> tripCount = new HashMap<String, Integer>();
	
	public MainStatistics(){
		
	}
	
	public String yelpStatistics(){
		String summary = "";
		int totalReviewCounter = 0;
		for (String Place : yelpCount.keySet()){
			summary += "There is a total of " + yelpCount.get(Place) + " reviews for " + Place + " on Yelp.\n";
			totalReviewCounter += yelpCount.get(Place);
		}
		summary += "There is a total of " + totalReviewCounter + " reviews on Yelp.\n";
		yelpCount = new HashMap<String, Integer>();
		
		
		return summary;
	}
	
	
	public String tripStatistics(){
		String summary = "";
		int totalReviewCounter = 0;
		for (String Place : tripCount.keySet()){
			summary += "There is a total of " + tripCount.get(Place) + " reviews for " + Place + " on TripAdvisor\n";
			totalReviewCounter += tripCount.get(Place);
		}
		summary += "There is a total of " + totalReviewCounter + " reviews on TripAdvisor.\n";
		tripCount = new HashMap<String, Integer>();
		return summary;
	}
	
	public void putTrip(String Location, int count){
		tripCount.put(Location,count);
	}
	
	public void putYelp(String Location, int count){
		yelpCount.put(Location,count);
	}
}
