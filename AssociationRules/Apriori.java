import java.util.ArrayList;


public class Apriori {
	
	public void run(CSV data, double minSup, double minConf) {
		Levels levels = new Levels();
		levels.addLevel(); // Set up base level
		for (int i = 0; i < data.getSets().size(); i++) {
			double firstLevelSupport = data.getSets().get(i).size() / (double) data.size();
			if (firstLevelSupport >= minSup) {
				ArrayList<Integer> firstLevelNode = new ArrayList<Integer>();
				firstLevelNode.add(i);
				levels.addNodeToLevel(new Node(firstLevelNode), 0);
			}
		}
		
		for (int i = 0; levels.get(i).size() > 0; i++) {
			levels.addLevel();
			levels.setLevel(i + 1, levels.findUnions(levels.get(i)));
			prune(levels, minSup, data);
		} 
		
		System.out.println(levels);
	}
	
	private void prune(Levels levels, double minSup, CSV data) {
		ArrayList<Node> nodesToRemove = new ArrayList<Node>();
		for (int i = 0; i < levels.get(levels.size() - 1).size(); i++) {
			Node node = levels.get(levels.size() - 1).get(i);
			double support = support(node, data);
			double conf = confidence(node, data);
			System.out.println("sup " + support + " conf: " + conf);
			if (support < minSup) {
				nodesToRemove.add(node);
			}
		}
		
		levels.removeAllNodesFromLevel(nodesToRemove, levels.size() - 1);
	}
	
	public double support(Node node, CSV data) {
		ArrayList<Integer> matchedNumbers = data.getSets().get(node.getName().get(0));
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
		
		return matchedNumbers.size() / (double) data.size();
	}
	
	public double confidence(Node node, CSV data) {
		Node n = new Node(node.getName().get(0));
		double s1 = support(node, data), s2 = support(n, data);
		System.out.println(node.getName() + " + " + n.getName());
		return support(node, data) / support(new Node(node.getName().get(0)), data);
	}

   public double support(ArrayList<Node> items, CSV data) {
      ArrayList<Integer> names = new ArrayList<Integer>();
      ArrayList<Integer> check = new ArrayList<Integer>();
      
      for(int i = 0; i < items.size(); i++) {
         for(int j = 0; j < items.get(i).getName().size(); j++) {
            if(!names.contains(items.get(i).getName().get(j))) {
               names.add(items.get(i).getName().get(j));
            }
         }
      }
      
      check.addAll(data.getSets().get(names.get(0)));
      
      for(int i = 1; i < names.size(); i++) {
         for(int j = 0; j < check.size(); j++) {
            if(!data.getSets().get(names.get(i)).contains(check.get(j))) {
               check.remove(check.get(j));
            }
         }
      }
      
      return (double)check.size()/data.getSets().size();
   }
   
   public double confidence(ArrayList<Node> items, ArrayList<Node> items2, CSV data) {
      return support(items, data)/support(items2, data);
   }
}
