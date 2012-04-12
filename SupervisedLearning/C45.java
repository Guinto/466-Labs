import java.util.ArrayList;

/**
 * 
 * @author Trent Ellingsen
 *
 */
public class C45 {
   DecisionTreeNode decisionTree;
   public C45(Domain trainer, CSV restrict, CSV original) {
      decisionTree = C45(trainer, restrict, original);
   }

   private DecisionTreeNode C45(Domain trainer, CSV restrict, CSV original) {
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


      int split = SelectSplittingAttribute(trainer, restrict, original);
      if(split == -1) {
         return new DecisionTreeNode(findmostfreq(trainer));
      }
      else {
         DecisionTreeNode tree = new DecisionTreeNode(split);
         Domain[] splitTrain = trainer.split();
         for(int i = 0; i < splitTrain.length; i++) {
            tree.children.add(C45(splitTrain[i], restrict, original));
         }
         restrict.vectors.get(0).set(split, 0);
         return tree;
      }
   }

   private int SelectSplittingAttribute(Domain trainer, CSV restrict, CSV original) {
      double enthropy = getEnthropy(trainer);
      ArrayList<Double> enthropies = new ArrayList<Double>();
      for(int i = 0; i < restrict.vectors.size(); i++) {
         if(restrict.vectors.get(0).get(i) == 1) {
            Domain[] split = trainer.split(i, original.dataCounts.get(0).get(i));
            int attrenthrop = 0;
            for(int j = 0; j < split.length; j++) {
               attrenthrop += split[j].size()/trainer.size() * getEnthropy(split[j]);
            }
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
      Domain[] domains = d.split();
      return  getEnthropy(domains[0].getYesRatio(), (1-domains[1].getYesRatio()));
   }

   private double getEnthropy(double a, double b) {
      return -a * Math.log(a)/Math.log(2) - -b * Math.log(b)/Math.log(2);
   }
}
