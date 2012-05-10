import java.util.ArrayList;


public class Filter {
	
	private ArrayList<UserRating> userRatings;
	private CSV data;
	
	public Filter(CSV data) {
		this.data = data;
		userRatings = new ArrayList<UserRating>();
		System.out.println("prediction for 0, 10: " + meanUtility(0, 10));
	}
	
	public float meanUtility(int user, int joke) {
		int notNullPeople = 0;
		float total = 0;
		for (int i = 0; i < data.getVectors().size(); i++) {
			if (i != user && data.getVectors().get(i).get(joke) != 99) {
				total += data.getVectors().get(i).get(joke);
				notNullPeople++;
			}
		}
		return total / notNullPeople;
	}
	
	public float avgRatingForJoke(int joke) {
		int notNullPeople = 0;
		float total = 0;
		for (int i = 0; i < data.getVectors().size(); i++) {
			if (data.getVectors().get(i).get(joke) != 99) {
				total += data.getVectors().get(i).get(joke);
				notNullPeople++;
			}
		}
		return total / notNullPeople;
	}
	
	public float avgRatingForUser(int user) {
		float total = 0;
		for (int i = 0; i < data.getVectors().get(user).size(); i++) {
			if (data.getVectors().get(user).get(i) != 99) {
				total += data.getVectors().get(user).get(i);
			}
		}
		return total / data.getVectors().get(user).size();
	}
	
	class UserRating {
		public int id;
		public ArrayList<Float> ratings = new ArrayList<Float>();
	}
}
