import java.util.ArrayList;

public class Node {
   private ArrayList<Integer> name;
   private ArrayList<Node> children;
   private Boolean skyline;
   private Double support;
   private ArrayList<Double> confidence;
   
   public Node(ArrayList<Integer> name, Node child1, Node child2) {
      this.setName(name);
      this.children = new ArrayList<Node>();
      this.confidence = new ArrayList<Double>();
      this.children.add(child1);
      this.children.add(child2);
      this.setSkyline(false);
   }
   
   public Node(Integer name) {
	   ArrayList<Integer> singleNameList = new ArrayList<Integer>();
	   singleNameList.add(name);
	   this.setName(singleNameList);
	   this.children = new ArrayList<Node>();
      this.confidence = new ArrayList<Double>();
   }
   
   public Node(ArrayList<Integer> name) {
      this.setName(name);
      this.children = new ArrayList<Node>();
      this.confidence = new ArrayList<Double>();
      this.setSkyline(true);
   }
   
   public ArrayList<Node> getChildren() {
	   return children;
   }
   
   public void addChild(Node node) {
	   children.add(node);
   }
   
   public boolean equals(Object x) {
      for(Integer check: this.getName()) {
         if(!((Node) x).getName().contains(check)) {
            return false;
         }
      }
      return true;
   }
   
   public String toString() {
	   return this.getName().toString();
   }

	public Boolean getSkyline() {
		return skyline;
	}
	
	public void setSkyline(Boolean skyline) {
		this.skyline = skyline;
	}
	
	public ArrayList<Integer> getName() {
		return name;
	}
	
	public void setName(ArrayList<Integer> name) {
		this.name = name;
	}
	
	public Double getSupport() {
		return support;
	}
	
	public void setSupport(Double support) {
		this.support = support;
	}
	
	public ArrayList<Double> getConfidence() {
		return confidence;
	}
	
	public void setConfidence(ArrayList<Double> confidence) {
		this.confidence = confidence;
	}
}