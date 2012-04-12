import java.util.ArrayList;


public class Evaluate {
	
	private ArrayList<DecisionTreeNode> trees;
	private ArrayList<Domain> splitDomain;

	public Evaluate(String domainFileName, String trainerFileName, int numFolds, String restrictFileName) {
		splitDomain = new ArrayList<Domain>();
		trees = new ArrayList<DecisionTreeNode>();
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
		   
			    	temp.getVectors().addAll(splitDomain[i].getVectors());
		    	
		    
		    
		    this.splitDomain.add(temp);

	    	C45 run = new C45(temp, restrict, trainer);
	    
			DecisionTreeNode tree = run.getDecisionTree();
			trees.add(tree);
	    }
	}
	
	public ArrayList<DecisionTreeNode> getTrees() {
		return trees;
	}
	
	public ArrayList<Domain> getDomains() {
		return this.splitDomain;
	}
}
