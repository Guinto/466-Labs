
public class Valdiation {
   
   public void overallMatrix(CSV trainer, CSV restrict, int folds, DecisionTreeNode tree) {
      double precision = 0, recall = 0, pF = 0, fMeasure = 0;
      matrix confusion = new matrix();
      for(int i = 0; i < folds; i++) {
         matrix temp = confuseMatrix(trainer, tree);
         confusion.tP += temp.tP;
         confusion.fP += temp.fP;
         confusion.fN += temp.fN;
         confusion.tN += temp.tN;
      }
      
      
      precision = confusion.tP/(confusion.tP+confusion.fP);
      recall = confusion.tP/(confusion.tP+confusion.fN);
      pF = confusion.fP/(confusion.fP+confusion.tN);
      fMeasure = (2 * precision * recall) /  (precision+recall);
      
      System.out.println(confusion.tP + "     " + confusion.fN);
      System.out.println(confusion.fP + "     " + confusion.tN);
   }
   public matrix confuseMatrix(CSV trainer, DecisionTreeNode tree) {
      int records[] = new int[trainer.vectors.size()];
      matrix confmatr = new matrix();
      for(int i = 0; i < trainer.vectors.size(); i++) {
         records[i] = checkTree(trainer.vectors.get(i), trainer, tree);
      }
      for(int j = 0; j < trainer.vectors.size(); j++) {
         if(records[j] == 0)
            confmatr.tP++;
         else if(records[j] == 1)
            confmatr.fN++;
         else if(records[j] == 2)
            confmatr.fP++;
         else if(records[j] == 3)
            confmatr.tN++;
      }
      return confmatr;
   }
   
   public int checkTree(Vector v, CSV trainer, DecisionTreeNode tree) {
      int value = v.get(tree.value);
      int correct = 0;
      if(tree.children.size() != 0) {
         correct = checkTree(v, trainer, tree.children.get(value-1));
      }
      else {
         if(tree.value == v.get(trainer.findCat()) && tree.value == 1) {
            return 0;
         }
         else if(tree.value == v.get(trainer.findCat()) && tree.value == 0){
            return 3;
         }
         else if(tree.value != v.get(trainer.findCat()) && tree.value == 1) {
            return 2;
         }
         else {
            return 1;
         }
      }
      return correct;
   }
   private class matrix {
      int tP, fN, fP, tN;
      public matrix() {
         this.tP = 0;
         this.fN = 0;
         this.fP = 0;
         this.tN = 0;
      }
   }
}
