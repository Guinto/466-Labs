
public class HClustering {


   public static void main(String[] args) {

		CSV data = null;
		if (args.length == 2) { //TODO CHANGE BACK AFTER
			data = new CSV(args[0]);
			new HClustering(data, Integer.parseInt(args[1]));
		} else if (args.length == 1) {
			data = new CSV(args[0]);
			new HClustering(data, -1);
		} else {
			System.err.println("Usage: HClustering <fileName> [<threshold>]");
			System.exit(1);
		}
   }
   
   public HClustering(CSV data, double threshold) {
	   DecisionTreeNode dtn = createTree(data);
	   System.out.println(dtn.toXMLString(-1));
	   if (threshold == -1) {
		   System.out.println("\nNo threshold was used, so it will not be printed again");
	   } else {
		   System.out.println("\nTree using threshold: " + threshold);
		   System.out.println(dtn.toXMLString(threshold));
	   }
   }

   public DecisionTreeNode createTree(CSV data) {
      DecisionTreeNode tree = new DecisionTreeNode();

      for(Vector v: data.vectors) {
         tree.children.add(new ClusterNode(v,v,v));
      }
      
      while(tree.children.size() > 1) {
         findShortest(tree);
      }
      
      return tree;
   }

   public void findShortest(DecisionTreeNode tree) {
      ClusterNode[] answer = new ClusterNode[2];
      double value = tree.children.get(0).findDist(tree.children.get(1));
      answer[0] = tree.children.get(0);
      answer[1] = tree.children.get(1);

      for(int i = 0; i < tree.children.size(); i++) {
         for(int j = i+1; j < tree.children.size(); j++) {
            if(value > tree.children.get(i).findDist(tree.children.get(j))) {
               value = tree.children.get(i).findDist(tree.children.get(j));
               answer[0] = tree.children.get(i);
               answer[1] = tree.children.get(j);
            }
         }
      }
      tree.children.add(answer[0].merge(answer[1], tree));
   }
}
