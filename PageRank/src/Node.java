
public class Node implements Comparable{
   private String name;
   private double value;
   
   public Node(String name, double value) {
      this.name = name;
      this.value = value;
   }

   public String getName() {
      return name;
   }

   public double getValue() {
      return value;
   }

   
   public int compareTo(Object arg0) {
      if(this.getValue() > ((Node) arg0).getValue()) {
         return -1;
      }
      else if(this.getValue() < ((Node) arg0).getValue()) {
         return 1;
      }
      return 0;
   }
   
   public boolean equals(Object x) {
      if(this.getName().equals(((Node)x).getName())) {
         return true;
      }
      return false;
   }

   
}
