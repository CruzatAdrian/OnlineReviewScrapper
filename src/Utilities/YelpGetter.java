package Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class YelpGetter {

   private String URL;
   private String Site;
   private String Address ="";
   private ArrayList<YelpReview> ReviewArray = new ArrayList<YelpReview>();


   public YelpGetter(String URL, String Site) {
      this.URL = URL;
      this.Site = Site;
   }

   public synchronized YelpTuple getData() {
      String tempContent = "";
      String tempLocation = "";
      String tempDate = "";
      String tempURL = "";
      String tempName = "";
      float tempScore = 0.0F;
      int tempFriendCount = 0;
      int tempReviewCount = 0;
      boolean working = true;

      try {
         URL e = new URL(this.URL);
         URLConnection con = e.openConnection();
         InputStream is = con.getInputStream();
         BufferedReader br = new BufferedReader(new InputStreamReader(is));

         this.Address = getAddress(br);
         
         for(int a = 0; a < 10; ++a) {
            tempName = this.cleanName(this.getUsername(br));
            tempURL = this.cleanURL(this.getURL(br));
            tempLocation = this.cleanLocation(this.getLocation(br));
            tempFriendCount = this.cleanFriendCount(this.getUserFriendCount(br));
            tempReviewCount = this.cleanReviewCount(this.getUserReviewCount(br));
            tempScore = this.cleanScore(this.getScore(br));
            tempDate = this.cleanDate(this.getDate(br));
            tempContent = this.cleanContent(this.getContent(br));
            YelpReview tempReview = new YelpReview(this.Site, tempName, tempContent, tempScore, tempFriendCount, tempReviewCount, tempLocation, tempDate, tempURL, Address);
            this.ReviewArray.add(tempReview);
            tempContent = "";
            tempLocation = "";
            tempDate = "";
            tempURL = "";
            tempName = "";
            tempScore = 0.0F;
         }

         br.close();
      } catch (IOException var28) {
         System.out.println("URL was not reachable");
      } catch (IndexOutOfBoundsException var29) {
         working = false;
         System.out.println("All Reviews for " + this.Site + " Stored!");
      } finally {
    	  
    	 for(YelpReview r : ReviewArray){
    		 setExtraData(r);
    		 try {
                 Thread.sleep(500L);
              } catch (InterruptedException var27) {
                 Thread.currentThread().interrupt();
              }
    	 }

      }

      return new YelpTuple(this.ReviewArray, working);
   }

   private synchronized void setExtraData(YelpReview Review) {
      try {
         URL ioe = new URL(Review.getURL());
         URLConnection con = ioe.openConnection();
         InputStream is = con.getInputStream();
         BufferedReader br = new BufferedReader(new InputStreamReader(is));
         Review.setUserUsefulCount(this.cleanUserUsefulCount(this.getUserUsefulCount(br)));
         Review.setUserFunnyCount(this.cleanUserFunnyCount(this.getUserFunnyCount(br)));
         Review.setUserCoolCount(this.cleanUserCoolCount(this.getUserCoolCount(br)));
         Review.setUserComplimentsCount(this.cleanUserComplimentCount(this.getUserComplimentCount(br)));
         Review.setUserJoined(this.cleanUserJoined(this.getUserJoined(br)));
         br.close();
      } catch (IOException var6) {
         System.out.println("URL was not reachable");
      }

   }

   private String getAddress(BufferedReader br) throws IOException {
	   String matchAddress = "<address>$";
	   String Line = "";
	   
	   while((Line = br.readLine()) != null) {
	         Line = Line.trim();
	         if(Line.matches(matchAddress)) {
	        	Line = br.readLine();
	            return Line.trim();
	         }
	      }
	   return "";
   }
   
   private synchronized String getUsername(BufferedReader br) throws IOException {
      String Line = "";

      while((Line = br.readLine()) != null) {
         Line = Line.trim();
         if(Line.matches("<meta itemprop=\"author\" content=\".*\">$")) {
            return Line;
         }
      }

      return "";
   }

   private synchronized String getURL(BufferedReader br) throws IOException {
      String Line = "";

      while((Line = br.readLine()) != null) {
         Line = Line.trim();
         if(Line.matches("<a class=\"user-display-name\" href=\"/.*\" data-hovercard-id=\".*\">.*</a>$")) {
            return Line;
         }
      }

      return "";
   }

   private synchronized String getLocation(BufferedReader br) throws IOException {
      String Line = "";

      while((Line = br.readLine()) != null) {
         Line = Line.trim();
         if(Line.matches("<li class=\"user-location\">$")) {
            return br.readLine();
         }
      }

      return "";
   }

   private synchronized String getUserFriendCount(BufferedReader br) throws IOException {
      String Line = "";

      while((Line = br.readLine()) != null) {
         Line = Line.trim();
         if(Line.matches("<li class=\"friend-count.*$")) {
            Line = br.readLine();
            return Line;
         }
      }

      return "";
   }

   private synchronized String getUserReviewCount(BufferedReader br) throws IOException {
      String Line = "";

      while((Line = br.readLine()) != null) {
         Line = Line.trim();
         if(Line.matches("<li class=\"review-count.*$")) {
            Line = br.readLine();
            return Line;
         }
      }

      return "";
   }

   private synchronized String getScore(BufferedReader br) throws IOException {
      String Line = "";

      while((Line = br.readLine()) != null) {
         Line = Line.trim();
         if(Line.matches("<meta itemprop=\"ratingValue\" content=\".*$")) {
            return Line;
         }
      }

      return "";
   }

   private synchronized String getDate(BufferedReader br) throws IOException {
      String Line = "";

      while((Line = br.readLine()) != null) {
         Line = Line.trim();
         if(Line.matches("<meta itemprop=\"datePublished\" content=\".*$")) {
            return Line;
         }
      }

      return "";
   }

   private synchronized String getContent(BufferedReader br) throws IOException {
      String Line = "";

      while((Line = br.readLine()) != null) {
         Line = Line.trim();
         if(Line.matches("<p itemprop=\"description\" lang=\"en\">.*$")) {
            return Line;
         }
      }

      return "";
   }

   private synchronized String getUserUsefulCount(BufferedReader br) throws IOException {
      String Line = "";

      while((Line = br.readLine()) != null) {
         Line = Line.trim();
         if(Line.matches("<span.*></i> Useful</span>$")) {
            Line = br.readLine();
            return Line;
         }
      }

      return "";
   }

   private synchronized String getUserFunnyCount(BufferedReader br) throws IOException {
      String Line = "";

      while((Line = br.readLine()) != null) {
         Line = Line.trim();
         if(Line.matches("<span.*></i> Funny</span>$")) {
            Line = br.readLine();
            return Line;
         }
      }

      return "";
   }

   private synchronized String getUserCoolCount(BufferedReader br) throws IOException {
      String Line = "";

      while((Line = br.readLine()) != null) {
         Line = Line.trim();
         if(Line.matches("<span.*></i> Cool</span>$")) {
            Line = br.readLine();
            return Line;
         }
      }

      return "";
   }

   private synchronized String getUserComplimentCount(BufferedReader br) throws IOException {
      String Line = "";

      while((Line = br.readLine()) != null) {
         Line = Line.trim();
         if(Line.matches("<h4>.* Compliments</h4>$")) {
            return Line;
         }
      }

      return "";
   }

   private synchronized String getUserJoined(BufferedReader br) throws IOException {
      String Line = "";

      while((Line = br.readLine()) != null) {
         Line = Line.trim();
         if(Line.matches("<h4>Yelping Since</h4>$")) {
            Line = br.readLine();
            return Line;
         }
      }

      return "";
   }

   private synchronized int cleanUserUsefulCount(String UserUsefulCount) {
      try {
         UserUsefulCount = UserUsefulCount.trim();
         int e = UserUsefulCount.length();
         if(UserUsefulCount.equals("")) {
            return 0;
         } else {
            String cleanUserUsefulCount = UserUsefulCount.substring(8, e - 9);
            return Integer.parseInt(cleanUserUsefulCount);
         }
      } catch (Exception var4) {
         return 0;
      }
   }

   private synchronized int cleanUserFunnyCount(String UserFunnyCount) {
      try {
         UserFunnyCount = UserFunnyCount.trim();
         int e = UserFunnyCount.length();
         if(UserFunnyCount.equals("")) {
            return 0;
         } else {
            String cleanUserFunnyCount = UserFunnyCount.substring(8, e - 9);
            return Integer.parseInt(cleanUserFunnyCount);
         }
      } catch (Exception var4) {
         return 0;
      }
   }

   private synchronized int cleanUserCoolCount(String UserCoolCount) {
      try {
         UserCoolCount = UserCoolCount.trim();
         int e = UserCoolCount.length();
         if(UserCoolCount.equals("")) {
            return 0;
         } else {
            String cleanUserCoolCount = UserCoolCount.substring(8, e - 9);
            return Integer.parseInt(cleanUserCoolCount);
         }
      } catch (Exception var4) {
         return 0;
      }
   }

   private synchronized int cleanFriendCount(String FriendCount) {
      try {
         FriendCount = FriendCount.trim();
         String[] e = FriendCount.split(" ");
         String cleanFriendCount = e[9];
         int StrLen = cleanFriendCount.length();
         cleanFriendCount = cleanFriendCount.substring(3, StrLen - 4);
         return cleanFriendCount.equals("")?0:Integer.parseInt(cleanFriendCount);
      } catch (Exception var5) {
         return 0;
      }
   }

   private synchronized int cleanReviewCount(String ReviewCount) {
      try {
         ReviewCount = ReviewCount.trim();
         String[] e = ReviewCount.split(" ");
         String cleanReviewCount = e[9];
         int StrLen = cleanReviewCount.length();
         cleanReviewCount = cleanReviewCount.substring(3, StrLen - 4);
         return cleanReviewCount.equals("")?0:Integer.parseInt(cleanReviewCount);
      } catch (Exception var5) {
         return 0;
      }
   }

   private synchronized float cleanScore(String Score) {
      try {
         Score = Score.trim();
         String[] e = Score.split(" ");
         String cleanScore = e[2];
         int StrLen = cleanScore.length();
         cleanScore = cleanScore.substring(9, StrLen - 2);
         return cleanScore.equals("")?0.0F:Float.parseFloat(cleanScore);
      } catch (Exception var5) {
         return 0.0F;
      }
   }

   private synchronized String cleanContent(String Content) {
      try {
         Content = Content.trim();
         int e = Content.length();
         String cleanContent = Content.substring(36, e - 4);
         cleanContent = cleanContent.replaceAll("<br><br>", " ");
         return cleanContent;
      } catch (Exception var4) {
         return "Unknown";
      }
   }

   private synchronized String cleanLocation(String Location) {
      try {
         Location = Location.trim();
         int e = Location.length();
         String cleanLocation = Location.substring(3, e - 4);
         return cleanLocation;
      } catch (Exception var4) {
         return "Unknown";
      }
   }

   private synchronized String cleanDate(String Date) {
      try {
         Date = Date.trim();
         String[] e = Date.split(" ");
         String cleanDate = e[2];
         int StrLen = cleanDate.length();
         cleanDate = cleanDate.substring(9, StrLen - 2);
         return cleanDate;
      } catch (Exception var5) {
         return "2015-16-10";
      }
   }

   private synchronized String cleanURL(String URL) {
      String[] Words = URL.split(" ");
      String cleanURL = Words[2];
      int StrLen = cleanURL.length();
      cleanURL = cleanURL.substring(6, StrLen - 1);
      cleanURL = "http://www.yelp.com" + cleanURL;
      return cleanURL;
   }

   private synchronized String cleanName(String Name) {
      try {
         int e = Name.length();
         if(Name.matches(".*<div class=\"review-sidebar\">")) {
            Name = Name.substring(0, e - 28);
            e = Name.length();
         }

         String cleanName = Name.substring(33, e - 2);
         return cleanName;
      } catch (Exception var4) {
         return "Unknown";
      }
   }

   private synchronized int cleanUserComplimentCount(String UserComplimentCount) {
      try {
         UserComplimentCount = UserComplimentCount.trim();
         int e = UserComplimentCount.length();
         if(UserComplimentCount.equals("")) {
            return 0;
         } else {
            String cleanUserComplimentCount = UserComplimentCount.substring(4, e - 17);
            return Integer.parseInt(cleanUserComplimentCount);
         }
      } catch (Exception var4) {
         return 0;
      }
   }

   private synchronized String cleanUserJoined(String Joined) {
      try {
         if(Joined.equals("")) {
            return "Unknown";
         } else {
            Joined = Joined.trim();
            int e = Joined.length();
            String cleanJoined = Joined.substring(3, e - 4);
            return cleanJoined;
         }
      } catch (Exception var4) {
         return "Unknown";
      }
   }
}
