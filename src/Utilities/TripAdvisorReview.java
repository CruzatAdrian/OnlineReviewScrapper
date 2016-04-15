package Utilities;


public class TripAdvisorReview extends Element {

   private String Title;
   private String Date;
   private int UserReviewCount;
   private int UserHelpfulCount;

   public static String Fields = "WEBSITE,USERNAME,LOCATION,ADDRESS,BIG_3_LOCATION,SCORE,DATE,TITLE,USER_HELPFUL_COUNT,USER_REVIEW_COUNT,CONTENT\n"; 

   public TripAdvisorReview(String Site, String Name, String Content, String Title, String Date, float Score, int HelpfulCount, int UserReviewCount, String Address) {
      super("TRIPADVISOR", Name, Content, Site, Score, Address);
      this.Title = Title;
      Date = Date.replaceAll(",", "");
      String[] dateField = Date.split(" ");
      this.Date = dateField[0].substring(0, 3) + "-" + dateField[1] + "-" + dateField[2];
      this.UserHelpfulCount = HelpfulCount;
      this.UserReviewCount = UserReviewCount;
   }

/*
 * Second Constructor method. Fields need to be reorganized to match current representation
 * Priority: LOW.   
 */
   
   
//   public TripAdvisorReview(String[] CSVFields) {
//      super("TRIPADVISOR", CSVFields[1], CSVFields[8], CSVFields[3], Float.parseFloat(CSVFields[4]));
//      String tempDate = CSVFields[4];
//      String[] DateFields = tempDate.split("-");
//      int Year = Integer.parseInt(DateFields[0]);
//      int Month = Integer.parseInt(DateFields[1]);
//      int Day = Integer.parseInt(DateFields[2]);
//      this.Date = Month + "-" + Day + "-" + Year;
//      this.Title = CSVFields[5];
//      this.UserHelpfulCount = Integer.parseInt(CSVFields[6]);
//      this.UserReviewCount = Integer.parseInt(CSVFields[7]);
//   }

   public String getTitle() {
      return this.Title;
   }

   public String getDate() {
      return this.Date;
   }

   public int getUserReviewCount() {
      return this.UserReviewCount;
   }

   public int getUserHelpfulCount() {
      return this.UserHelpfulCount;
   }

   public String getCSVLine() {
      String CSV = super.toString() + "," + "\"" + this.getDate() + "\"," + "\"" + this.Title + "\"," + "\"" + this.UserHelpfulCount + "\"," + "\"" + this.UserReviewCount + "\",\"" + super.getContent() + "\"";
      return CSV;
   }

   public String toString() {
      return this.getCSVLine();
   }
   
   public void setFullContent(String content){
	   this.setContent(content);
   }
}
