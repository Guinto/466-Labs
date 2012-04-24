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
		
		levels.addLevel();
		levels.setLevel(1, levels.findUnions(levels.get(0)));
		for (int i = 0; i < levels.get(1).size(); i++) {
			double support = support(levels.get(1), data);
		}
		
		System.out.println(levels);
	}

   public double support(ArrayList<Node> items, CSV data) {
      ArrayList<Integer> names = new ArrayList<Integer>();
      ArrayList<Integer> check = new ArrayList<Integer>();
      
      for(int i = 0; i < items.size(); i++) {
         for(int j = 0; j < items.get(i).name.size(); j++) {
            if(!names.contains(items.get(i).name.get(j))) {
               names.add(items.get(i).name.get(j));
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
