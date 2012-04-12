/**
 * 
 * @author Trent Ellingsen
 *
 */
public class C45 {

   public C45(CSV trainer, CSV restrict) {
      int base1 = 0;
      Vector check = trainer.vectors.get(0);
      for(Vector v: trainer.vectors) {
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
         return new Node(check.last());


      int split = SelectSplittingAttribute();
      if(split == -1) {
         return new Node(value = findmostfreq());
      }
      else {
         Node tree = new Node(split);
         restrict.vectors.get(0).set(split, 0);

      }
   }

}

private double getEnthropy(Domain d) {
   return 0;
}

private double getEnthropy(double a, double b) {
   return -a * Math.log(a)/Math.log(2) - -b * Math.log(b)/Math.log(2);
}

private class Node {
   public int value;
   public Node left, right;
   public Node() {
      this.value = 0;
      this.left = null;
      this.right = null;
   }
   public Node(int value) {
      this.value = value;
      this.left = null;
      this.right = null;
   }
}
}
