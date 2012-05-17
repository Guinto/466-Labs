
public class EvaluateCFRandom {


   public static void main(String[] args) {
      if (args.length == 2) {
         if(args[0].equals("MU")) {
            new Filter(new CSV("jester-data-1.csv"), 0, Integer.parseInt(args[1]));
         }
         if(args[0].equals("WS")) {
            new Filter(new CSV("jester-data-1.csv"), 1, Integer.parseInt(args[1]));
         }
         if(args[0].equals("AS")) {
            new Filter(new CSV("jester-data-1.csv"), 2, Integer.parseInt(args[1]));
         }
      } else {
         System.err.println("Usage: EvaluateCFRandom Method Size");
         System.exit(1);
      }
   }

}
