import java.util.ArrayList;


public class Apriori {

   public void run(CSV data, double minSup, double minConf) {
      Levels levels = new Levels();
      levels.addLevel(); // Set up base level
      Levels inAll = new Levels();
   	  inAll.addLevel();
   	  for (Integer i : data.getSets().keySet()) {
         double firstLevelSupport = 100.0 * data.getSets().get(i).size() / data.size();
         if (firstLevelSupport == 100.0) {
             ArrayList<Integer> firstLevelNode = new ArrayList<Integer>();
             firstLevelNode.add(i);
             Node node = new Node(firstLevelNode);
             node.setSupport(firstLevelSupport);
             node.setConfidence(new ArrayList<Double>());
             inAll.addNodeToLevel(node, 0);
         } else if (firstLevelSupport >= minSup) {
            ArrayList<Integer> firstLevelNode = new ArrayList<Integer>();
            firstLevelNode.add(i);
            Node node = new Node(firstLevelNode);
            node.setSupport(firstLevelSupport);
            node.setConfidence(new ArrayList<Double>());
            levels.addNodeToLevel(node, 0);
         }
      }
      for (int i = 0; levels.get(i).size() > 0; i++) {
         levels.addLevel();
         levels.setLevel(i + 1, levels.findUnions(levels.get(i)));
         prune(levels, minSup, minConf, data);
         System.out.println(levels.get(i));
      }

      levels.removeLastLevel(); // it is empty

      levels.markSkyline();
      if (inAll.size() == 0) {
    	  levels.showSkyline(minConf);
      } else {
    	  levels.showFactorsSkyline(inAll, minConf);
      }
   }

   private void prune(Levels levels, double minSup, double minConf, CSV data) {
      ArrayList<Node> nodesToRemove = new ArrayList<Node>();
      for (int i = 0; i < levels.get(levels.size() - 1).size(); i++) {
         Node node = levels.get(levels.size() - 1).get(i);
         double support = support(node, data);
         ArrayList<Double> confidence = confidence(node, data);
         node.setSupport(support);
         node.setConfidence(confidence);
         if (support < minSup || node.getChildren().size() < levels.size()-1) {
            nodesToRemove.add(node);
         }
      }

      levels.removeNodesFromLevel(nodesToRemove, levels.size() - 1);
   }

   public double support(Node node, CSV data) {
      ArrayList<Integer> matchedNumbers = new ArrayList<Integer>(data.getSets().get(node.getName().get(0)));
      ArrayList<Integer> removeNumbers = new ArrayList<Integer>();

      for (int i = 1; i < node.getName().size(); i++) {
         ArrayList<Integer> matchedAgainst = data.getSets().get(node.getName().get(i));
         for (Integer val : matchedNumbers) {
            if (!matchedAgainst.contains(val)) {
               removeNumbers.add(val);
            }
         }
         matchedNumbers.removeAll(removeNumbers);
         removeNumbers = new ArrayList<Integer>();
      }
      return 100.0 * matchedNumbers.size() / data.size();
   }

   public ArrayList<Double> confidence(Node node, CSV data) {
      ArrayList<Double> confidences = new ArrayList<Double>();
      for(Node n: node.getChildren()) {
         double s1 = support(node, data), s2 = support(n, data);
         if(s1 > 0.0 || s2 > 0.0)
            confidences.add(100.0 * s1 / s2);
         else
            confidences.add(100.0);
      }
      return confidences;
   }
}
