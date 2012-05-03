
public class Node {
   public double value;
   public Vector max;
   public Vector min;
   public Vector points;
   public Node left;
   public Node right;

   public Node(double value, Vector min, Vector max, Node left, Node right) {
      this.value = value;
      this.left = left;
      this.right = right;
      this.min = min;
      this.max = max;
   }

   public Node(Vector points, Vector min, Vector max) {
      this.value = 0;
      this.points = points;
      this.min = min;
      this.max = max;
   }

   public Node merge(Node other, DecisionTreeNode tree) {
      Node answer = new Node(this.findDist(other), this.findMin(other), this.findMax(other), this, other);
      tree.remove(this);
      tree.remove(other);

      return answer;
   }

   public double findDist(Node other) {      
      double first = this.max.getEucledianDistance(other.min);
      double second = other.max.getEucledianDistance(this.min);
      double third = this.max.getEucledianDistance(this.min);
      double fourth = other.max.getEucledianDistance(other.min);

      return Math.max(first, Math.max(second, Math.max(third, fourth)));
   }
   
   public Vector findMin(Node other) {
      double first = this.max.getEucledianDistance(other.min);
      double fourth = other.max.getEucledianDistance(other.min);
      double answer = this.findDist(other);
      
      if(first == answer || fourth == answer)
         return other.min;
      else
         return this.min;
   }
   
   public Vector findMax(Node other) {
      double first = this.max.getEucledianDistance(other.min);
      double third = this.max.getEucledianDistance(this.min);
      double answer = this.findDist(other);
      
      if(first == answer || third == answer)
         return this.max;
      else
         return other.max;
   }
}
