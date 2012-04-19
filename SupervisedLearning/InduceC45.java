
public class InduceC45 {
	
	public static void main(String[] args) {
		if (args.length >= 3) {
			new InduceC45(args[0], args[1], args[2]);
		} else if (args.length >= 2) {
			new InduceC45(args[0], args[1], null);
		} else {
			System.err.println("Usage: InduceC45 domainFileName trainerFileName [restrictFileName]");
			System.exit(1);
		}
		
		System.out.println("check tree.xml file for output");
	}
	
	public InduceC45(String domainFileName, String trainerFileName, String restrictFileName) {
	    CSV trainer = new CSV(trainerFileName);
	    CSV restrict;
	    if (restrictFileName == null) {
	    	restrict = new CSV(trainer.vectors.get(0).size());
	    } else {
	    	restrict = new CSV(restrictFileName, 0);
	    }
	    trainer.editCat(restrict);
	    trainer.findID(restrict);
	    Domain test = new Domain(trainer.vectors, trainer.dataCounts.get(0).last());
	    C45 run = new C45(test, restrict, trainer);
	    
		DecisionTreeNode tree = run.getDecisionTree();
		tree.outputTree("tree.xml", new XML(domainFileName));
	}
}
