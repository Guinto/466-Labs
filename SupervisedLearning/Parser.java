
public class Parser {
   public static void main(String args[]) {
      if (args.length > 1) {
         CSV trainer = new CSV(args[1]);
         if(args.length > 2) {
            CSV restrict = new CSV(args[2], 0);
         }
         else {
            CSV restrict = new CSV(trainer.vectors.get(0));
         }
      }
      else {
         System.err.println("Usage: CSV fileName");
         System.exit(1);
      }
   }
}