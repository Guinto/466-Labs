import java.util.ArrayList;
	
public class DecisionTreeNode {
	public int value;
	public ArrayList<DecisionTreeNode> children;
   
	public DecisionTreeNode(int value) {
	   this.value = value;
	   children = new ArrayList<DecisionTreeNode>();
	}
	
	public int getValue() {
		return value;
	}
	
	public ArrayList<DecisionTreeNode> getChildren() {
		return children;
	}
	
	public void setChildren(ArrayList<DecisionTreeNode> children) {
		this.children = children;
	}
}