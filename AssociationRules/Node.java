import java.util.ArrayList;

public class Node {
   ArrayList<Integer> name;
   ArrayList<Node> children;
   Boolean skyline;
   public Node(ArrayList<Integer> name, Node child1, Node child2) {
      this.name = name;
      this.children = new ArrayList<Node>();
      this.children.add(child1);
      this.children.add(child2);
      this.skyline = false;
   }

   public Node(ArrayList<Integer> name) {
      this.name = name;
      this.children = null;
      this.skyline = true;
   }
   
   public boolean equals (Object x) {
      int count = 0;
      for(Integer check: this.name) {
         if(!((Node) x).children.contains(check)) {
            return false;
         }
      }
      if(((Node) x).children.size() != count)
         return false;
      return true;
   }
   
   public String toString() {
	   return this.name.toString();
   }
}