import java.util.ArrayList;
	
public class DecisionTreeNode {
	public int value;
	public ArrayList<DecisionTreeNode> children;
   
	public DecisionTreeNode(int value) {
	   this.value = value;
	   this.children = new ArrayList<DecisionTreeNode>();
	}
}