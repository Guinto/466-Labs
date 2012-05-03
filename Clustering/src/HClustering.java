
public class HClustering {


   public static void main(String[] args) {

		CSV data = null;
		if (args.length != 2) { //TODO CHANGE BACK AFTER
			data = new CSV("lab4data/birth_death_rate.csv");
			new HClustering(data);
		} else {
			System.err.println("Usage: KMeans <fileName> <k>");
			System.exit(1);
		}
   }
   
   public HClustering(CSV data) {
	   DecisionTreeNode dtn = createTree(data);
	   System.out.println(dtn.toXMLString());
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
            if(value < tree.children.get(i).findDist(tree.children.get(j))) {
               value = tree.children.get(i).findDist(tree.children.get(j));
               answer[0] = tree.children.get(i);
               answer[1] = tree.children.get(j);
            }
         }
      }
      tree.children.add(answer[0].merge(answer[1], tree));
   }
}
