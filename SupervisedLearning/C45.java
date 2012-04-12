import java.util.ArrayList;

/**
 * 
 * @author Trent Ellingsen
 *
 */
public class C45 {

   public DecisionTreeNode C45(Domain trainer, CSV restrict) {
      int base1 = 0;
      Vector check = trainer.getVectors().get(0);
      for(Vector v: trainer.getVectors()) {
         if(check.last() != v.last()) {
            base1 = 1;
         }
         if(base1 == 1) {
            break;
         }
      }
      if(base1 == 0) {
         return new DecisionTreeNode(check.last());
      }
      int base2 = 0;
      for(int i = 0; i < restrict.vectors.get(0).length(); i++) {
         if(restrict.vectors.get(0).get(i) != 0) {
            base2 = 1;
            break;
         }
      }
      if(base2 == 0)
         return new DecisionTreeNode(findmostfreq(trainer));


      int split = SelectSplittingAttribute(trainer, restrict);
      if(split == -1) {
         return new DecisionTreeNode(findmostfreq(trainer));
      }
      else {
         DecisionTreeNode tree = new DecisionTreeNode(split);
         ArrayList<Domain> splitTrain = trainer.split();
         for(int i = 0; i < splitTrain.size(); i++) {
            tree.children.add(C45(splitTrain.get(i), restrict));
         }
         restrict.vectors.get(0).set(split, 0);
         return tree;
      }
   }

   private int SelectSplittingAttribute(Domain trainer, CSV restrict) {
	   double enthropy = getEnthropy(trainer);
	   ArrayList<Double> enthropies = new ArrayList<Double>();
	   for(int i = 0; i < restrict.vectors.size(); i++) {
	      ArrayList<Domain> split = trainer.split(i, restrict.vectors.get(0).get(i));
	      int attrenthrop = 0;
	      for(int j = 0; j < split.size(); j++) {
	         attrenthrop += split.get(j).size()/trainer.size() * getEnthropy(split.get(j));
	      }
	   }
	   int max = 0;
	   for(int i = 1; i < restrict.vectors.size(); i++) {
	      if(enthropies.get(max) < enthropies.get(i))
	         max = i;
	   }
	   return max;
   }

	private static int findmostfreq(Domain trainer) {
	   int[] count = new int[trainer.getNumCategories()];
	   for(int i = 0; i < count.length; i++)
	      count[i] = 0;
	   for(Vector v: trainer.getVectors()) {
	      int index = (int) v.last();
	      count[index]++;
	   }
	   
	   int index = 0;
	   for(int i = 1; i < count.length; i++) {
	      if(count[i] > count[index])
	         index = i;
	   }
	   
	   return index;
	}


	private double getEnthropy(Domain d) {
	   ArrayList<Domain> domains = d.split();
	   return  getEnthropy(domains.get(0).getYesRatio(), (1-domains.get(1).getYesRatio()));
	}
	
	private double getEnthropy(double a, double b) {
	   return -a * Math.log(a)/Math.log(2) - -b * Math.log(b)/Math.log(2);
	}
}
