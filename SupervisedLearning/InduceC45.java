
public class InduceC45 {
	
	public static void main(String[] args) {
		if (args.length >= 2) {
			new InduceC45(args[0], args[1]);
		} else if (args.length >= 1) {
			new InduceC45(args[0], null);
		} else {
			System.err.println("Usage: InduceC45 trainerFileName restrictFileName");
			System.exit(1);
		}
	}
	
	public InduceC45(String trainerFileName, String restrictFileName) {
	    CSV trainer = new CSV(trainerFileName);
	    CSV restrict;
	    if (restrictFileName == null) {
	    	restrict = new CSV(trainer.vectors.get(0).size());
	    } else {
	    	restrict = new CSV(restrictFileName, 0);
	    }
	    Domain test = new Domain(trainer.vectors, trainer.dataCounts.get(0).last());
	    C45 run = new C45(test, restrict, trainer);
	    
		DecisionTreeNode tree = run.getDecisionTree();
		tree.outputTree("tree.xml", new XML("data/domain.xml"));
		tree.print(new XML("data/domain.xml"));
		tree.print();
	}
}
