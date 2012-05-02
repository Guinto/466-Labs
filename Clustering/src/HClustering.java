import java.util.ArrayList;


public class HClustering {


   public static void main(String[] args) {

   }

   public void createTree(CSV data) {
      DecisionTreeNode tree = new DecisionTreeNode();

      for(Vector v: data.vectors) {
         tree.children.add(new Node(v,v,v));
      }

      while(tree.children.size() > 1) {
         findShortest(tree);
      }
   }

   public void findShortest(DecisionTreeNode tree) {
      Node[] answer = new Node[2];
      double value = tree.children.get(0).findDist(tree.children.get(1));
      answer[0] = tree.children.get(0);
      answer[1] = tree.children.get(1);

      for(int i = 0; i < tree.children.size(); i++) {
         for(int j = i+1; j < tree.children.size(); j++) {
            if(value < tree.children.get(i).findDist(tree.children.get(j))) {
               value = tree.children.get(i).findDist(tree.children.get(j));
               answer[0] = tree.children.get(i);
               answer[1] = tree.children.get(j);
            }
         }
      }
      tree.children.add(answer[0].merge(answer[1], tree));
   }
}
