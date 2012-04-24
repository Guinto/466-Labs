import java.util.ArrayList;


public class Levels {
   private ArrayList<ArrayList<Node>> levels;

   public Levels() {
      levels = new ArrayList<ArrayList<Node>>();
   }
   
   public ArrayList<Node> get(int level) {
	   return levels.get(level);
   }
   
   public void addLevel() {
	   levels.add(new ArrayList<Node>());
   }
   
   public void addNodeToLevel(Node node, int level) {
	   levels.get(level).add(node);
   }
   
   public void removeNodesFromLevel(ArrayList<Node> nodes, int level) {
	   levels.get(level).removeAll(nodes);
   }
   
   public void setLevel(int i, ArrayList<Node> level) {
	   levels.set(i, level);
   }
   
   public String toString() {
	   String string = "";
	   for (ArrayList<Node> level : levels) {
		   string += level + "\n";
	   }
	   return string;
   }

   public ArrayList<Node> findUnions(ArrayList<Node> prev) {
      ArrayList<Node> next = new ArrayList<Node>();
      Node temp;
      for(int i = 0; i < prev.size(); i++) {
         for(int j = i + 1; j < prev.size(); j++) {
            if(almostEqual(prev.get(i), prev.get(j))) {
               temp = new Node(unionName(prev.get(i), prev.get(j)), prev.get(i), prev.get(j));
               if(next.contains(temp)) {
                  if(!next.get(next.indexOf(temp)).getChildren().contains(prev.get(j)))
                  next.get(next.indexOf(temp)).getChildren().add(prev.get(j));
               }
               else {
                  next.add(new Node(unionName(prev.get(i), prev.get(j)), prev.get(i), prev.get(j)));
               }
            }
         }
      }
      return next;
   }
   
   public void markSkyline() {
      for(int i = 1; i < levels.size(); i++) {
         for(int j = 0; j < levels.get(i).size(); j++) {
            levels.get(i).get(j).setSkyline(true);
            for(int k = 0; k < levels.get(i).get(j).getChildren().size(); k++) {
               levels.get(i-1).get(levels.get(i-1).indexOf(levels.get(i).get(j).getChildren().get(k))).setSkyline(false);
            }
         }
      }
   }
   
   public void showSkyline() {
      int i = 0;
	   for (ArrayList<Node> level : levels) {
		   for (Node node : level) {
			   if (node.getSkyline()) {
				   for(int j = 0; j < node.getChildren().size(); j++) {
				      System.out.print("Rule " + ++i + ":     ");
				      System.out.print(node.getChildren().get(j) + " ---> ");
				      System.out.print(node.getName().get(node.getChildren().size()-j-1));
				     // System.out.printf("   [sup=%f   conf=", node.getSupport());
				      System.out.print("   [sup=" + node.getSupport() + "   conf=");
				      System.out.printf("%.8f]\n", node.getConfidence().get(j));
				   }
			   }
		   }
	   }
   }
   
   public void showLevels() {
      for (ArrayList<Node> level : levels) {
         for (Node node : level) {
               System.out.println("Skyline node: " + node.getName());
               System.out.println("Support: " + node.getSupport());
               System.out.println("Confidence: " + node.getConfidence());
               System.out.println();
         }
      }
   }

   public static boolean almostEqual(Node first, Node second) {
      int count = 0;
      for(Integer base: first.getName()) {
         if(second.getName().contains(base)) {
            count++;
         }
      }
      if(count == first.getName().size()-1)
         return true;
      return false;
   }

   public static ArrayList<Integer> unionName(Node first, Node second) {
      ArrayList<Integer> ans = new ArrayList<Integer>();
      for(Integer base: first.getName()) {
         ans.add(base);
      }
      for(Integer base: second.getName()) {
         if(!ans.contains(base))
            ans.add(base);
      }
      return ans;
   }

	public int size() {
		return levels.size();
	}

	public void removeLastLevel() {
		levels.remove(levels.size() - 1);
	}
}
