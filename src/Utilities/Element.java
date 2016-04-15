package Utilities;

import java.util.HashSet;

public abstract class Element {

   private String Name;
   private String Content;
   private String Site;
   private float Score;
   private String Website;
   private String big3;
   private String Address;
   


   public Element(String Website, String Name, String Content, String Site, float Score, String Address) {
      this.Website = Website;
      this.Name = Name;
      this.Content = Content;
      this.Site = Site;
      this.Score = Score;
      this.Address = Address;
      CollegeTownDict a = new CollegeTownDict();
      HashSet<String> b = a.getDict();
      if(!this.Site.equals("rochester-public-market-rochester") && !this.Site.equals("Rochester_Public_Market")) {
         if(b.contains(this.Site)) {
            this.big3 = "College Town";
         } else {
            this.big3 = "East End";
         }
      } else {
         this.big3 = "Rochester Public Market";
      }

   }

   public String getName() {
      return this.Name;
   }

   public String getContent() {
      return this.Content;
   }

   public float getScore() {
      return this.Score;
   }

   public String getSite() {
      return this.Site;
   }

   public String toString() {
      String CSV = "\"" + this.Website + "\"," + "\"" + this.Name  + "\"," + "\"" + this.Site + "\"," + "\"" + this.Address + "\"," + "\"" + this.big3 + "\"," + "\"" + this.Score + "\"";
      return CSV;
   }

   public boolean inContent(String match) {
      match = match.toLowerCase();
      return this.Content.toLowerCase().matches(match);
   }

   public boolean scoreIsGreaterThanOrEqual(int checkedScore) {
      return this.Score >= (float)checkedScore;
   }

   public boolean scoreIsLessThanOrEqual(int checkedScore) {
      return this.Score <= (float)checkedScore;
   }

   public int contentLength() {
      return this.Content.length();
   }
   
   public void setContent(String content){
	   this.Content = content;
   }
}
