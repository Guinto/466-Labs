import java.util.ArrayList;


public class Apriori {

   public double minSup(ArrayList<Node> items, CSV data) {
      ArrayList<Integer> names = new ArrayList<Integer>();
      ArrayList<Integer> check = new ArrayList<Integer>();
      
      for(int i = 0; i < items.size(); i++) {
         for(int j = 0; j < items.get(i).name.size(); j++) {
            if(!names.contains(items.get(i).name.get(j))) {
               names.add(items.get(i).name.get(j));
            }
         }
      }
      
      check.addAll(data.sets.get(names.get(0)));
      
      for(int i = 1; i < names.size(); i++) {
         for(int j = 0; j < check.size(); j++) {
            if(!data.sets.get(names.get(i)).contains(check.get(j))) {
               check.remove(check.get(j));
            }
         }
      }
      
      return (double)check.size()/data.sets.size();
   }
   
   public double confidence(ArrayList<Node> items, ArrayList<Node> items2, CSV data) {
      return minSup(items, data)/minSup(items2, data);
   }
}
