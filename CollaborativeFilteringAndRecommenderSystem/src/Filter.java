import java.util.ArrayList;


public class Filter {
	
	private ArrayList<UserRating> userRatings;
	private CSV data;
	
	public Filter(CSV data) {
		this.data = data;
		userRatings = new ArrayList<UserRating>();
		System.out.println("mean utility prediction for 0, 10: " + meanUtility(0, 10));
		System.out.println("weighted sum prediction for 0, 10: " + weightedSum(0, 10));
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
	
	public float weightedSum(int user, int joke) {
		float total = 0;
		UserRating a = new UserRating(user, data.getVectors().get(user));
		
		for (int i = 0; i < data.getVectors().size(); i++) {
			if (i != user && data.getVectors().get(i).get(joke) != 99) {
				UserRating b = new UserRating(i, data.getVectors().get(i));
				total += similarity(a, b) * data.getVectors().get(i).get(joke);
			}
		}
		return normalization(user, joke) + total;
	}
	
	public float weightedSum2(int user, int joke) {
      float total = 0;
      UserRating a = new UserRating(user, data.getVectors().get(user));
      
      for (int i = 0; i < data.getVectors().size(); i++) {
         if (i != user && data.getVectors().get(i).get(joke) != 99) {
            UserRating b = new UserRating(i, data.getVectors().get(i));
            total += similarity(a, b) * meanUtility(i, joke);
         }
      }
      return normalization(user, joke) * total;
   }
	
	private float normalization(int user, int joke) {
		float total = 0;
		UserRating a = new UserRating(user, data.getVectors().get(user));
		
		for (int i = 0; i < data.getVectors().size(); i++) {
			if (i != user && data.getVectors().get(i).get(joke) != 99) {
				UserRating b = new UserRating(i, data.getVectors().get(i));
				total += similarity(a, b);
			}
		}
		return 1 / Math.abs(total);
	}
		
	private float similarity(UserRating a, UserRating b) {
		float top = 0, bottom = 0;
		float meanForA = avgRatingForUser(a.id);
		float meanForB = avgRatingForUser(b.id);
		
		for (int i = 0; i < a.ratings.size(); i++) {
			if (a.ratings.get(i) == 99 && b.ratings.get(i) == 99) continue;
			top += ((a.ratings.get(i) - meanForA) * (b.ratings.get(i) - meanForB));
		}
		for (int i = 0; i < a.ratings.size(); i++) {
			if (a.ratings.get(i) == 99 && b.ratings.get(i) == 99) continue;
			bottom += Math.sqrt((Math.pow((a.ratings.get(i) - meanForA), 2) * Math.pow((b.ratings.get(i) - meanForB), 2)));
		}
		
		return top / bottom;
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
		public Vector ratings;
		
		public UserRating(int id, Vector ratings) {
			this.id = id;
			this.ratings = ratings;
		}
	}
}
