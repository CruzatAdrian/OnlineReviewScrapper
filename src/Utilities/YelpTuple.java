package Utilities;

import java.util.ArrayList;

public class YelpTuple {

   private ArrayList<YelpReview> x;
   private boolean y;


   public YelpTuple(ArrayList<YelpReview> x, boolean y) {
      this.x = x;
      this.y = y;
   }

   public ArrayList<YelpReview> getX() {
      return this.x;
   }

   public boolean isY() {
      return this.y;
   }
}
