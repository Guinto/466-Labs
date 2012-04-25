
public class RunApriori {

   public static void main(String[] args) {
      CSV data = null;
      if (args.length >= 0 && args.length < 4) {
         data = new CSV("data/factors/factor_baskets_sparse.csv");
      } 
      else if(args.length >= 4) {
         data = new CSV("data/factors/factor_baskets_sparse.csv", 0);
      }
      else {
         System.err.println("Usage: RunApriori fileName minSup minConf");
         System.exit(1);
      }
      Apriori apriori = new Apriori();
      apriori.run(data, 92, 0);
   }
}
