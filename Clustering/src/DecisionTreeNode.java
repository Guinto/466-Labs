import java.util.ArrayList;

public class DecisionTreeNode {
	public ArrayList<ClusterNode> children;
   
	public DecisionTreeNode() {
	   children = new ArrayList<ClusterNode>();
	}
	
	public ArrayList<ClusterNode> getChildren() {
		return children;
	}
	
	public void setChildren(ArrayList<ClusterNode> children) {
		this.children = children;
	}
	
	public void remove(ClusterNode child) {
	   children.remove(child);
	}
	
	public String toString() {
		return children.get(0).toString();
	}
	
	public String toXMLString(double threshold) {
		return children.get(0).toXMLString(threshold);
	}
}
