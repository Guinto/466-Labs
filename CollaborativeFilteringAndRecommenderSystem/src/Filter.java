import java.util.ArrayList;


public class Filter {

   private ArrayList<Joke> userRatings;
   private CSV data;
   private ArrayList<Float> avgRating;

   public Filter(CSV data, int flag, int size) {
      this.data = data;
      userRatings = new ArrayList<Joke>();
      avgRating = new ArrayList<Float>();
      int user;
      int joke;
      float delta = 0;
      for(int i = 0; i < data.getVectors().size(); i++) {
         avgRating.add(avgRatingForUser(i));
      }
      for(int i = 0; i < size; i++) {
         user = (int) (Math.random() * data.getVectors().size());
         joke = (int) (Math.random() * 100);
         while(userRatings.contains(new Joke(joke, user, 99)) || data.getVectors().get(user).get(joke) == 99) {
            user = (int) (Math.random() * data.getVectors().size());
            joke = (int) (Math.random() * 100);
         }
         if(flag == 0) {
            userRatings.add(new Joke(joke, user, meanUtility(user, joke)));
         }
         if(flag == 1) {
            userRatings.add(new Joke(joke, user, weightedSum(user, joke)));
         }
         if(flag == 2) {
            userRatings.add(new Joke(joke, user, adjustedWeightedSum(user, joke)));
         }
         System.out.println(user + ", " +  joke + ", " +  data.getVectors().get(user).get(joke) + ", " +  userRatings.get(userRatings.size()-1).value + ", " + (data.getVectors().get(user).get(joke) - userRatings.get(userRatings.size()-1).value));
         delta += Math.abs(data.getVectors().get(user).get(joke) - userRatings.get(userRatings.size()-1).value);
      }
      System.out.println("Mean Absolute Error: " + meanAbsoluteError(userRatings) + " average delta: " + delta/userRatings.size());
   }

   public Filter(CSV data, int flag, int size, ArrayList<Joke> users) {
      this.data = data;
      userRatings = new ArrayList<Joke>();
      avgRating = new ArrayList<Float>();
      int user;
      int joke;
      float delta = 0;
      for(int i = 0; i < data.getVectors().size(); i++) {
         avgRating.add(avgRatingForUser(i));
      }
      for(int i = 0; i < users.size(); i++) {
         user = users.get(i).userNum;
         joke = users.get(i).jokeNum;
         if(data.getVectors().get(user).get(joke) != 99) {
            if(flag == 0) {
               userRatings.add(new Joke(joke, user, meanUtility(user, joke)));
            }
            if(flag == 1) {
               userRatings.add(new Joke(joke, user, weightedSum(user, joke)));
            }
            if(flag == 2) {
               userRatings.add(new Joke(joke, user, adjustedWeightedSum(user, joke)));
            }
            delta += Math.abs(data.getVectors().get(user).get(joke) - userRatings.get(userRatings.size()-1).value);
            System.out.println(user + ", " +  joke + ", " +  data.getVectors().get(user).get(joke) + ", " +  userRatings.get(userRatings.size()-1).value + ", " + (data.getVectors().get(user).get(joke) - userRatings.get(userRatings.size()-1).value));
         }
      }
      System.out.println("Mean Absolute Error: " + meanAbsoluteError(userRatings) + " average delta: " + delta/userRatings.size());
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
      return normalization(user, joke) * total;
   }

   public float adjustedWeightedSum(int user, int joke) {
      float total = 0;
      UserRating a = new UserRating(user, data.getVectors().get(user));

      for (int i = 0; i < data.getVectors().size(); i++) {
         if (i != user && data.getVectors().get(i).get(joke) != 99) {
            UserRating b = new UserRating(i, data.getVectors().get(i));
            total += similarity(a, b) * (data.getVectors().get(i).get(joke) - avgRating.get(i));
         }
      }
      return avgRating.get(user) + normalization(user, joke) * total;
   }

   private float normalization(int user, int joke) {
      float total = 0;
      UserRating a = new UserRating(user, data.getVectors().get(user));

      for (int i = 0; i < data.getVectors().size(); i++) {
         if (i != user && data.getVectors().get(i).get(joke) != 99) {
            UserRating b = new UserRating(i, data.getVectors().get(i));
            total += Math.abs(similarity(a, b));
         }
      }
      return 1 / total;
   }

   private float similarity(UserRating a, UserRating b) {
      float top = 0, bottom = 0;
      float meanForA = avgRating.get(a.id);
      float meanForB = avgRating.get(b.id);

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

   public float meanAbsoluteError(ArrayList<Joke> users) {
      float total = 0;
      for(int i = 0; i < users.size(); i++) {
         total += Math.abs(userRatings.get(i).value - data.getVectors().get(userRatings.get(i).userNum).get(userRatings.get(i).jokeNum));
      }
      return total/users.size();
   }

   class UserRating {
      public int id;
      public Vector ratings;

      public UserRating(int id, Vector ratings) {
         this.id = id;
         this.ratings = ratings;
      }
   }

   class Joke{
      public int jokeNum;
      public int userNum;
      public float value;

      public Joke(int jokeNum, int userNum, float value) {
         this.jokeNum = jokeNum;
         this.userNum = userNum;
         this.value = value;
      }

      public boolean equals(Object x) {
         if(this.jokeNum == ((Joke) x).jokeNum && this.userNum == ((Joke) x).userNum) {
            return true;
         }
         return false;
      }
   }
}
