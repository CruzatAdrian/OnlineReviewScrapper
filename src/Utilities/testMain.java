package Utilities;


public class testMain {

   public static void main(String[] args) {
	   
	   TripGetter Tripper = new TripGetter("http://www.tripadvisor.com/Restaurant_Review-g48503-d6847171-Reviews-or10-The_Soup_Spoon-Rochester_Finger_Lakes_New_York.html", "DeezNuts");
	   Tripper.getData();
   }
}
