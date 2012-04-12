import java.util.ArrayList;


public class InduceC45 {
	
	public static void main(String[] args) {
		new InduceC45();
	}
	
	public InduceC45() {
		DecisionTreeNode tree = generateFakeTree();
		tree.printXML(new XML("data/domainHouses.xml"));
	}
	
	private DecisionTreeNode generateFakeTree() {
		DecisionTreeNode tree = new DecisionTreeNode(1);
		ArrayList<DecisionTreeNode> children = new ArrayList<DecisionTreeNode>();
		children.add(new DecisionTreeNode(2));
		children.add(new DecisionTreeNode(1));
		tree.setChildren(children);
		for (DecisionTreeNode child : tree.getChildren()) {
			ArrayList<DecisionTreeNode> innerChildren = new ArrayList<DecisionTreeNode>();
			innerChildren.add(new DecisionTreeNode(Math.round((float)Math.random() * 4)));
			innerChildren.add(new DecisionTreeNode(Math.round((float)Math.random() * 4)));
			child.setChildren(innerChildren);
		}
		return tree;
	}
}
