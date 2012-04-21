import java.util.ArrayList;


public class levels {
   ArrayList<ArrayList<Node>> levels;

   public levels() {
      this.levels = new ArrayList<ArrayList<Node>>();
   }

   public ArrayList<Node> findUnions(ArrayList<Node> prev) {
      ArrayList<Node> next = new ArrayList<Node>();
      Node temp;
      for(int i = 0; i < prev.size(); i++) {
         for(int j = i + 1; j < prev.size(); j++) {
            if(almostEqual(prev.get(i), prev.get(j))) {
               temp = new Node(unionName(prev.get(i), prev.get(j)), prev.get(i), prev.get(j));
               if(next.contains(temp)) {
                  next.get(next.indexOf(temp)).children.add(prev.get(j));
               }
               else {
                  next.add(new Node(unionName(prev.get(i), prev.get(j)), prev.get(i), prev.get(j)));
               }
            }
         }
      }
      return next;
   }
   
   public void findSkyline() {
      for(int i = 1; i < levels.size(); i++) {
         for(int j = 0; j < levels.get(i).size(); j++) {
            levels.get(i).get(j).skyline = true;
            for(int k = 0; k < levels.get(i).get(j).children.size(); k++) {
               levels.get(i-1).get(levels.get(i-1).indexOf(levels.get(i).get(j).children.get(k))).skyline = false;
            }
         }
      }
   }

   public static boolean almostEqual(Node first, Node second) {
      int count = 0;
      for(Integer base: first.name) {
         if(second.name.contains(base)) {
            count++;
         }
      }
      if(count == first.name.size()-1)
         return true;
      return false;
   }

   public static ArrayList<Integer> unionName(Node first, Node second) {
      ArrayList<Integer> ans = new ArrayList<Integer>();
      for(Integer base: first.name) {
         ans.add(base);
      }
      for(Integer base: second.name) {
         if(!ans.contains(base))
            ans.add(base);
      }
      return ans;
   }

   private class Node {
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
   }
}
