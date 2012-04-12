
public class Classify {
   public void Classify(DecisionTreeNode tree, CSV trainer) {
      int[] records = new int[trainer.vectors.size()];
      int correct = 0, wrong = 0;
      for(int i = 0; i < trainer.vectors.size(); i++) {
         records[i] = checkTree(trainer.vectors.get(i), trainer, tree);
      }
      
      for(int j = 0; j < trainer.vectors.size(); j++) {
         if(records[j] == 1)
            correct++;
         else
            wrong++;
      }
      System.out.println("total number " + trainer.vectors.size());
      System.out.println("correct " + correct);
      System.out.println("wrong " + wrong);
      System.out.println("accuracy " + correct/trainer.vectors.size());
      System.out.println("error " + wrong/trainer.vectors.size());
   }
   
   public int checkTree(Vector v, CSV trainer, DecisionTreeNode tree) {
      int value = v.get(tree.value);
      if(tree.children.size() != 0) {
         checkTree(v, trainer, tree.children.get(value-1));
      }
      else {
         if(tree.value == v.last()) {
            return 1;
         }
         else {
            return 0;
         }
      }
      return 0;
   }
}
