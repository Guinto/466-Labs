import java.util.ArrayList;

/**
 * 
 * @author Trent Ellingsen
 *
 */
public class C45 {

   public Node C45(Domain trainer, CSV restrict) {
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
         return new Node(check.last());
      }
      int base2 = 0;
      for(int i = 0; i < restrict.vectors.get(0).length(); i++) {
         if(restrict.vectors.get(0).get(i) != 0) {
            base2 = 1;
            break;
         }
      }
      if(base2 == 0)
         return new Node(findmostfreq(trainer));


      int split = SelectSplittingAttribute();
      if(split == -1) {
         return new Node(findmostfreq(trainer));
      }
      else {
         Node tree = new Node(split);
         restrict.vectors.get(0).set(split, 0);
         ArrayList<Domain> splitTrain = trainer.split();
         for(int i = 0; i < splitTrain.size(); i++) {
            tree.children.add(C45(splitTrain.get(i), restrict));
         }
         return tree;
      }
   }

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
public ArrayList<Domain> split() {
   ArrayList<Domain> splitDomain = new ArrayList<Domain>(getNumCategories());
   
   for (Vector v : attributesAndCategory) {
      int index = (int) v.last();
      Domain d = splitDomain.get(index);
      d.addVector(v);
   }
   
   return splitDomain;
}


private double getEnthropy(Domain d) {
   ArrayList<Domain> domains = d.split();
   return  getEnthropy(domains.get(0).size(), domains.get(1).size());
}

private double getEnthropy(double a, double b) {
   return -a * Math.log(a)/Math.log(2) - -b * Math.log(b)/Math.log(2);
}

private class Node {
   public int value;
   public ArrayList<Node> children;
   
   public Node(int value) {
      this.value = value;
   }
}
}
