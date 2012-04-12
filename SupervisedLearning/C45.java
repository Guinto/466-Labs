import java.util.ArrayList;

/**
 * 
 * @author Trent Ellingsen
 *
 */
public class C45 {
   public DecisionTreeNode decisionTree;
   
   public C45(Domain trainer, CSV restrict, CSV original) {
      decisionTree = runC45(trainer, restrict, original);
   }
   
   public DecisionTreeNode getDecisionTree() {
	   return decisionTree;
   }

   private DecisionTreeNode runC45(Domain trainer, CSV restrict, CSV original) {
      int base1 = 0;
      Vector check = trainer.getVectors().get(0);
      for(Vector v: trainer.getVectors()) {
         if(check.findCat(restrict) != v.findCat(restrict)) {
            base1 = 1;
         }
         if(base1 == 1) {
            break;
         }
      }
      if(base1 == 0) {
         return new DecisionTreeNode(check.findCat(restrict));
      }
      int base2 = 0;
      for(int i = 0; i < restrict.vectors.get(0).length(); i++) {
         if(restrict.vectors.get(0).get(i) != 0) {
            base2 = 1;
            break;
         }
      }
      if(base2 == 0) {
         return new DecisionTreeNode(findmostfreq(trainer, restrict)+1);
      }


      int split = SelectSplittingAttribute(trainer, restrict, original);
      if(split == -1) {
         return new DecisionTreeNode(findmostfreq(trainer, restrict)+1);
      }
      else {
         DecisionTreeNode tree = new DecisionTreeNode(split);
         Domain[] splitTrain = trainer.split(split, original.dataCounts.get(0).get(split));
         for(int i = 0; i < splitTrain.length; i++) {
            tree.children.add(runC45(splitTrain[i], restrict, original));
         }
         restrict.vectors.get(0).set(split, 0);
         return tree;
      }
   }

   private int SelectSplittingAttribute(Domain trainer, CSV restrict, CSV original) {
      double enthropy = getEnthropy(trainer);
      ArrayList<Double> enthropies = new ArrayList<Double>();
      for(int i = 0; i < restrict.vectors.get(0).size(); i++) {
         if(restrict.vectors.get(0).get(i) == 1) {
            Domain[] split = trainer.split(i, original.dataCounts.get(0).get(i));
            double attrenthrop = 0;
            for(int j = 0; j < split.length; j++) {
               if(split[j].size() > 0)
               attrenthrop += split[j].size()/trainer.size() * getEnthropy(split[j]);
            }
            enthropies.add(new Double(getEnthropy(enthropy, attrenthrop)));
         }
         else {
            enthropies.add(new Double(0));
         }
      }
      int max = 0;
      for(int i = 1; i < restrict.vectors.get(0).size(); i++) {
         if(enthropies.get(i) > 0 && enthropies.get(max) < enthropies.get(i))
            max = i;
      }
      if(max == 0)
         return -1;
      return max;
   }

   private static int findmostfreq(Domain trainer, CSV restrict) {
      int[] count = new int[trainer.getNumCategories()];
      for(int i = 0; i < count.length; i++)
         count[i] = 0;
      for(Vector v: trainer.getVectors()) {
         int index = (int) v.findCat(restrict);
         count[index-1]++;
      }

      int index = 0;
      for(int i = 1; i < count.length; i++) {
         if(count[i] > count[index])
            index = i;
      }

      return index;
   }

   public void printTree(DecisionTreeNode node) {
      System.out.println("a node " + node.value + " " + node.children.size());
      for(int i = 0; i < node.children.size(); i++) {
          System.out.println(i);
    	  printTree(node.children.get(i));
      }
   }

   private double getEnthropy(Domain d) {
      Domain[] domains = d.split();
      return  getEnthropy(domains[0].size()/d.size(), domains[1].size()/d.size());
   }

   private double getEnthropy(double a, double b) {
      if(a != 0.0 && b != 0.0)
         return -a * Math.log(a)/Math.log(2) - b * Math.log(b)/Math.log(2);
      return 0.0;
   }
}
