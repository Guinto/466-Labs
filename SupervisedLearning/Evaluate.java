
public class Evaluate {

	public static void main(String args[]) {
		if (args.length >= 4) {
			new Evaluate(args[0], args[1], Integer.parseInt(args[2]), args[3]);
		} else if (args.length >= 3) {
			new Evaluate(args[0], args[1], Integer.parseInt(args[2]), null);
		} else {
			System.err.println("Usage: Evaluate domainFileName trainerFileName numFolds restrictFileName");
			System.exit(1);
		}
	}

	public Evaluate(String domainFileName, String trainerFileName, int numFolds, String restrictFileName) {
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
	    Domain[] splitDomain = test.split(numFolds);
	    for (int i = 0; i < splitDomain.length; i++) {
		    if (restrictFileName == null) {
		    	restrict = new CSV(trainer.vectors.get(0).size());
		    } else {
		    	restrict = new CSV(restrictFileName, 0);
		    }
		    trainer.editCat(restrict);
		    trainer.findID(restrict);
		    
	    	Domain temp = new Domain();
		    for (int j = 0; j < splitDomain.length; j++) {
		    	if (j != i) {
			    	temp.getVectors().addAll(splitDomain[j].getVectors());
		    	}
		    }

	    	C45 run = new C45(test, restrict, trainer);
	    
			DecisionTreeNode tree = run.getDecisionTree();
			tree.print(new XML(domainFileName));
	    }
	}
}
