package Utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class YelpReview extends Element {

   private int FriendCount;
   private int ReviewCount;
   private String ReviwerLocation;
   private Calendar Date = Calendar.getInstance();
   private String URL;
   private int UserCoolCount = 0;
   private int UserFunnyCount = 0;
   private int UserUsefulCount = 0;
   private int UserComplimentsCount = 0;
   private String Joined = "Unknown";

   public static String Fields = "WEBSITE,USERNAME,LOCATION,ADDRESS,BIG_3_LOCATION,SCORE,DATE,USER_URL,USER_LOCATION,USER_REVIEW_COUNT,USER_FRIEND_COUNT,USER_USEFUL_COUNT,USER_FUNNY_COUNT,USER_COOL_COUNT,USER_COMPLIMENT_COUNT,JOINED,CONTET\n";

   public YelpReview(String Site, String Name, String Content, float Score, int FriendCount, int ReviewCount, String ReviwerLocation, String Date, String URL, String Address) {
      super("YELP", Name, Content, Site, Score,Address);
      this.FriendCount = FriendCount;
      this.ReviewCount = ReviewCount;
      this.ReviwerLocation = ReviwerLocation;
      String[] DateFields = Date.split("-");
      int Year = Integer.parseInt(DateFields[0]);
      int Month = Integer.parseInt(DateFields[1]);
      int Day = Integer.parseInt(DateFields[2]);
      this.Date.set(Year, Month, Day);
      this.URL = URL;
   }

   /*
    * Second Constructor method. Fields need to be reorganized to match current representation
    * Priority: LOW
    */
   
//   public YelpReview(String[] CSVFields) {
//      super("YELP", CSVFields[1], CSVFields[2], CSVFields[3], Float.parseFloat(CSVFields[4]));
//      String tempDate = CSVFields[5];
//      String[] DateFields = tempDate.split("-");
//      int Year = Integer.parseInt(DateFields[0]);
//      int Month = Integer.parseInt(DateFields[1]);
//      int Day = Integer.parseInt(DateFields[2]);
//      this.Date.set(Year, Month, Day);
//      this.URL = CSVFields[6];
//      this.ReviwerLocation = CSVFields[7];
//      this.ReviewCount = Integer.parseInt(CSVFields[8]);
//      this.FriendCount = Integer.parseInt(CSVFields[9]);
//      this.UserUsefulCount = Integer.parseInt(CSVFields[10]);
//      this.UserFunnyCount = Integer.parseInt(CSVFields[11]);
//      this.UserCoolCount = Integer.parseInt(CSVFields[12]);
//      this.UserComplimentsCount = Integer.parseInt(CSVFields[13]);
//      this.Joined = CSVFields[14].substring(0, CSVFields[14].length() - 1);
//   }

   public int getFriendCount() {
      return this.FriendCount;
   }

   public int getReviewCount() {
      return this.ReviewCount;
   }

   public String getReviwerLocation() {
      return this.ReviwerLocation;
   }

   public String getDate() {
      SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
      String formatted = format1.format(this.Date.getTime());
      return formatted;
   }

   public String getURL() {
      return this.URL;
   }

   public int getUsefulCount() {
      return this.UserUsefulCount;
   }

   public void setUserFriendCount(int friendCount) {
      this.FriendCount = friendCount;
   }

   public void setUserCoolCount(int coolCount) {
      this.UserCoolCount = coolCount;
   }

   public void setUserUsefulCount(int usefulCount) {
      this.UserUsefulCount = usefulCount;
   }

   public void setUserComplimentsCount(int complimentsCount) {
      this.UserComplimentsCount = complimentsCount;
   }

   public void setUserFunnyCount(int FunnyCount) {
      this.UserFunnyCount = FunnyCount;
   }

   public void setUserJoined(String Joined) {
      this.Joined = Joined;
   }

   public String toString() {
      return this.getCSVLine();
   }

   public String getCSVLine() {
      String CSV = super.toString() + "," + "\"" + this.getDate() + "\"," + "\"" + this.URL + "\"," + "\"" + this.ReviwerLocation + "\"," + "\"" + this.ReviewCount + "\"," + "\"" + this.FriendCount + "\"," + "\"" + this.UserUsefulCount + "\"," + "\"" + this.UserFunnyCount + "\"," + "\"" + this.UserCoolCount + "\"," + "\"" + this.UserComplimentsCount + "\"," + "\"" + this.Joined + "\"," + "\"" + super.getContent() + "\"";
      return CSV;
   }
}
