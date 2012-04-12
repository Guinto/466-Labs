
public class Classify {
	
	public static void main(String args[]) {
		if (args.length >= 2) {
			String csvFileName = args[0];
			String xmlFileName = args[1];
			XML xml = new XML(xmlFileName, new CSV(csvFileName));
			new Classify(xml.getDecisionTree(), new CSV(csvFileName));
		} else {
			System.err.println("Usage: Classify CSVFile XMLFile");
			System.exit(1);
		}
	}
	
   public Classify(DecisionTreeNode tree, CSV trainer) {
      int[] records = new int[trainer.vectors.size()];
      int correct = 0, wrong = 0;
      for(int i = 0; i < trainer.vectors.size(); i++) {
         records[i] = checkTree(trainer.vectors.get(i), trainer, tree);
      }
      for(int j = 0; j < trainer.vectors.size(); j++) {
         if(records[j] == 1)
            correct++;
         else
            wrong++;
      }
      System.out.println("total number " + trainer.vectors.size());
      System.out.println("correct " + correct);
      System.out.println("wrong " + wrong);
      System.out.println("accuracy " + (double)correct/trainer.vectors.size());
      System.out.println("error " + (double)wrong/trainer.vectors.size());
   }
   
   public int checkTree(Vector v, CSV trainer, DecisionTreeNode tree) {
      int value = v.get(tree.value);
      int correct = 0;
      if(tree.children.size() != 0) {
         correct = checkTree(v, trainer, tree.children.get(value-1));
      }
      else {
         if (tree.value == v.get(trainer.findCat())) {
            return 1;
         }
         else {
            return 0;
         }
      }
      return correct;
   }
}
