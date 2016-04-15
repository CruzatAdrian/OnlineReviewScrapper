package Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


import Exceptions.EndOfFileException;

public class TripGetter {

	private String URL;
	private String Site;
	private String Address;
	private ArrayList<TripAdvisorReview> ReviewArray = new ArrayList<TripAdvisorReview>();


	public TripGetter(String URL, String Site) {
		this.URL = URL;
		this.Site = Site;
	}

	public synchronized ArrayList<TripAdvisorReview> getData() {
		String TempUsername = "";
		String TempContent = "";
		String TempTitle = "";
		String TempDate = "";
		String TempURL = "";
		String TempFullContent = "";
		float TempScore = 0.0F;
		int TempUserHelpfulCount = 0;
		int TempUserReviewCount = 0;
		

		try {
			URL SIOE = new URL(this.URL);
			URLConnection con = SIOE.openConnection();
			InputStream is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			this.Address = cleanAddress(getAddress(br));


			for(int a = 0; a < 10; ++a) {
				
				TempUsername = this.cleanUsername(this.getUsername(br));
				TempUserReviewCount = this.cleanUserReviewCount(this.getUserReviewCount(br));
				TempUserHelpfulCount = this.cleanUserHelpfulCount(this.getUserHelpfulCount(br));
				String rawTitle =  this.getTitle(br);
				TempURL = this.cleanURL(rawTitle);
				TempTitle = this.cleanTitle(rawTitle);
				TempScore = this.cleanScore(this.getScore(br));
				TempDate = this.cleanDate(this.getDate(br));
				TempContent = this.cleanContent(this.getContent(br));
				
				TripAdvisorReview TempReview = new TripAdvisorReview(this.Site, TempUsername, TempContent, TempTitle, TempDate, TempScore, TempUserHelpfulCount, TempUserReviewCount, this.Address);
				
				/*
				 * Get the full content if it is not completely displayed initially
				 */
				if(TempContent.length() >= 285){
					TempFullContent = getExtraContent(TempURL);
					TempReview.setFullContent(TempFullContent);
				}
				
				
				this.ReviewArray.add(TempReview);
				
				TempUsername = "";
				TempContent = "";
				TempTitle = "";
				TempDate = "";
				TempScore = 0.0F;
				TempUserHelpfulCount = 0;
				TempUserReviewCount = 0;
				TempURL = "";
				TempFullContent = "";
			}
		} catch (IOException var14) {
			System.out.println("URL was not reachable");
		} catch (EndOfFileException var15) {
			System.out.println("All Reviews for " + this.Site + " Stored!");
			return this.ReviewArray;
		} 

		return this.ReviewArray;
	}
	
	private String getExtraContent(String URL){
		try {
			URL SIOE = new URL(URL);
			URLConnection con = SIOE.openConnection();
			InputStream is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			String fullContent = getFullContent(br);
			return fullContent;
		} catch (IOException e){
			System.out.println("URL was not reachable");
		} finally{
		}
		return "";
	}


	private String getAddress(BufferedReader br) throws IOException{

		String AddressMatch = ".*property=\"streetAddress\">.*</span>.*$";
		String Line = "";
		while((Line = br.readLine()) != null) {
			Line = Line.trim();
			if(Line.matches(AddressMatch)) {
				return Line;
			}
		}
		return "";
	}

	private String getUsername(BufferedReader br) throws IOException, EndOfFileException {
		String UsernameMatch = "<div class=\"username mo\">$";
		String Line = "";

		while((Line = br.readLine()) != null) {
			Line = Line.trim();
			if(Line.matches(UsernameMatch)) {
				Line = br.readLine();
				return Line;
			} else if (Line.matches("<h3 class=\"tabs_header\">Questions &amp; Answers</h3>")){
				throw new EndOfFileException();
			}
		}

		throw new EndOfFileException();
		
	}

	private String getContent(BufferedReader br) throws IOException {
		String ContentMatch = "<p class=\"partial_entry\">$";
		String Line = "";

		while((Line = br.readLine()) != null) {
			Line = Line.trim();
			if(Line.matches(ContentMatch)) {
				return br.readLine();
			}
		}

		return "";
	}

	private String getScore(BufferedReader br) throws IOException {
		String ScoreMatch = "<span class=\"rate sprite-rating_s rating_s\"> <img .*>$";
		String Line = "";

		while((Line = br.readLine()) != null) {
			Line = Line.trim();
			if(Line.matches(ScoreMatch)) {
				return Line;
			}
		}

		return "";
	}

	private String getTitle(BufferedReader br) throws IOException {
		String TitleMatch = "<div class=\"quote.*\">$";
		String Line = "";
		
		while((Line = br.readLine()) != null) {
			Line = Line.trim();
			
			if(Line.matches(TitleMatch)) {
				Line = br.readLine();
				return Line;
			}
		}

		return "";
	}

	private String getUserReviewCount(BufferedReader br) throws IOException {
		String UserReviewCountMatch = "<div class=\"reviewerBadge badge\">$";
		String Line = "";

		while((Line = br.readLine()) != null) {
			Line = Line.trim();
			if(Line.matches(UserReviewCountMatch)) {
				br.readLine();
				return br.readLine();
			}
		}

		return "";
	}

	private String getUserHelpfulCount(BufferedReader br) throws IOException {
		String UserHelpfulCountMatch = "<span class=\"badgeText\">$";
		String noCountMatch = "<div class=\"innerBubble\">$";
		String Line = "";

		while((Line = br.readLine()) != null) {
			Line = Line.trim();
			if(Line.matches(UserHelpfulCountMatch)) {
				return br.readLine();
			}

			if(Line.matches(noCountMatch)) {
				return "";
			}
		}

		return "";
	}

	private String getDate(BufferedReader br) throws IOException {
		String DateMatch = "</span>$";
		String Line = "";

		while((Line = br.readLine()) != null) {
			Line = Line.trim();
			if(Line.matches(DateMatch)) {
				return br.readLine();
			}
		}

		return "";
	}
	
	private String getFullContent(BufferedReader br) throws IOException{
		String fullContentMatch = "<p property=\"reviewBody\".*$";
		String Line = "";
		
		while((Line = br.readLine()) != null) {
			Line = Line.trim();
			
			if(Line.matches(fullContentMatch)) {
				Line = br.readLine();
				return Line;
			}
		}

		return "";
	}
	

	private String cleanAddress(String Address){
		try{
			String[] AddressFields = Address.split("property=\"streetAddress\">");
			AddressFields = AddressFields[1].split("</span>");
			return AddressFields[0];
		} catch(StringIndexOutOfBoundsException e){
			return "";
		}
	}

	private String cleanUsername(String Username) {
		int StrLen = Username.length();
		Username = Username.substring(171, StrLen - 7);
		return Username;
	}

	private String cleanContent(String Content) {
		Content = Content.trim();
		return Content;
	}

	private float cleanScore(String Score) {
		int StrLen = Score.length();
		Score = Score.substring(StrLen - 14, StrLen - 13);
		return Score.equals("")?1.0F:Float.parseFloat(Score);
	}

	private String cleanURL(String URL){
		String cleanURL = "http://www.tripadvisor.com" + URL.split("\"")[1];
		return cleanURL;
	}
	
	private String cleanTitle(String Title) {
		Title = Title.replaceAll("<a .*\'noQuotes\'>", "");
		Title = Title.replaceAll("</span>.*</a>", "");
		return Title;
	}

	private int cleanUserReviewCount(String UserReviewCount) {

		
		int StrLen = UserReviewCount.length();
		UserReviewCount = UserReviewCount.substring(24, StrLen - 22);
		UserReviewCount = UserReviewCount.replaceAll(",", "");
		return UserReviewCount.equals("")?1:Integer.parseInt(UserReviewCount);
	}

	private int cleanUserHelpfulCount(String UserHelpfulCount) {
		if(UserHelpfulCount.equals("")) {
			return 0;
		} else {
			int StrLen = UserHelpfulCount.length();
			UserHelpfulCount = UserHelpfulCount.substring(0, StrLen - 22);
			return UserHelpfulCount.equals("")?1:Integer.parseInt(UserHelpfulCount);
		}
	}

	private String cleanDate(String Date) {
		Date = Date.substring(34);
		return Date;
	}
	
	
}
