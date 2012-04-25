
public class RunApriori {

   public static void main(String[] args) {
      CSV data = null;
      if (args.length >= 0 && args.length < 4) {
         //data = new CSV(args[0]);
         data = new CSV("data/75000/75000-out1.csv");
      } 
      else if(args.length >= 4) {
         data = new CSV(args[0], 0);
      }
      else {
         System.err.println("Usage: RunApriori fileName minSup minConf");
         System.exit(1);
      }
      Apriori apriori = new Apriori();
      //apriori.run(data, Double.parseDouble(args[1]), Double.parseDouble(args[2]));
      apriori.run(data, 3, 90);
   }
}
