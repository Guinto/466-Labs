import java.util.ArrayList;

public class Node {
   private ArrayList<Integer> name;
   private ArrayList<Node> children;
   private Boolean skyline;
   
   public Node(ArrayList<Integer> name, Node child1, Node child2) {
      this.setName(name);
      this.children = new ArrayList<Node>();
      this.children.add(child1);
      this.children.add(child2);
      this.setSkyline(false);
   }
   
   public Node(Integer name) {
	   ArrayList<Integer> singleNameList = new ArrayList<Integer>();
	   singleNameList.add(name);
	   this.setName(singleNameList);
   }

   public Node(ArrayList<Integer> name) {
      this.setName(name);
      this.children = null;
      this.setSkyline(true);
   }
   
   public ArrayList<Node> getChildren() {
	   return children;
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
}