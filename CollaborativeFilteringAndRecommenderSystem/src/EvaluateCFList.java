import java.util.ArrayList;


public class EvaluateCFList {

   /**
    * @param args
    */
   public static void main(String[] args) {
      if (args.length == 2) {
         ArrayList<Joke> userInput = makeUserInput(args[1]);
         if(args[0].equals("MU")) {
            new Filter(new CSV("jester-data-1.csv"), 0, userInput.size(), userInput);
         }
         if(args[0].equals("WS")) {
            new Filter(new CSV("jester-data-1.csv"), 1, userInput.size(), userInput);
         }
         if(args[0].equals("AS")) {
            new Filter(new CSV("jester-data-1.csv"), 2, userInput.size(), userInput);
         }
      } else {
         System.err.println("Usage: EvaluateCFList Method FileName");
         System.exit(1);
      }
   }

   public static ArrayList<Joke> makeUserInput(String fileName) {
      ArrayList<Joke> jokes = new ArrayList<Joke>();
      CSV data = new CSV(fileName);
      for (int i = 0; i < data.getVectors().size(); i++) {
         int joke = (int) data.getVectors().get(i).get(1);
         int user = (int) data.getVectors().get(i).get(0);
         jokes.add(new Joke(joke, user, 99));
      }

      return jokes;
   }

}
