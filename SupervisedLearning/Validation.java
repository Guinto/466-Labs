import java.util.ArrayList;


public class Validation {

   public static void main(String args[]) {
      Evaluate eval = null;
      if (args.length >= 4) {
         eval = new Evaluate(args[0], args[1], Integer.parseInt(args[2]), args[3]);
      } else if (args.length >= 3) {
         eval = new Evaluate(args[0], args[1], Integer.parseInt(args[2]), null);
      } else {
         System.err.println("Usage: Evaluate domainFileName trainerFileName numFolds restrictFileName");
         System.exit(1);
      }

      Validation validation = new Validation();

      validation.overallMatrix(eval.getDomains(), new CSV(args[1]), Integer.parseInt(args[2]), eval.getTrees());

   }


   public void overallMatrix(ArrayList<Domain> domains, CSV trainer, int folds, ArrayList<DecisionTreeNode> tree) {
      double precision = 0, recall = 0, pF = 0, fMeasure = 0;
      matrix confusion = new matrix();
      if(folds > 0)
         for(int i = 0; i < folds; i++) {
            matrix temp = confuseMatrix(domains.get(i), trainer, tree.get(i));
            confusion.tP += temp.tP;
            confusion.fP += temp.fP;
            confusion.fN += temp.fN;
            confusion.tN += temp.tN;
         }
      else if(folds == 0) {
         matrix temp = confuseMatrix(domains.get(0), trainer, tree.get(0));
         confusion.tP += temp.tP;
         confusion.fP += temp.fP;
         confusion.fN += temp.fN;
         confusion.tN += temp.tN;
      }
      else if(folds == -1)
         for(int i = 0; i < domains.size(); i++) {
            matrix temp = confuseMatrix(domains.get(i), trainer, tree.get(i));
            confusion.tP += temp.tP;
            confusion.fP += temp.fP;
            confusion.fN += temp.fN;
            confusion.tN += temp.tN;
         }


      precision = (double)confusion.tP/(confusion.tP+confusion.fP);
      recall = (double)confusion.tP/(confusion.tP+confusion.fN);
      pF = (double)confusion.fP/(confusion.fP+confusion.tN);
      fMeasure = (double)(2 * precision * recall) /  (precision+recall);

      System.out.println(confusion.tP + "     " + confusion.fN);
      System.out.println(confusion.fP + "     " + confusion.tN);

      System.out.println("precision " + precision);
      System.out.println("recall " + recall);
      System.out.println("pF " + pF);
      System.out.println("f-measure " + fMeasure);
      System.out.println("overall Accuracy " + (double)(confusion.tP+confusion.tN)/trainer.vectors.size());
      if(folds != -1 && folds != 0)
         System.out.println("average Accuracy " + (double)(confusion.tP+confusion.tN)/trainer.vectors.size()/folds);
      else if(folds == -1)
         System.out.println("average Accuracy " + (double)(confusion.tP+confusion.tN)/trainer.vectors.size()/(trainer.vectors.size()-1));
      else if(folds == 0)
         System.out.println("average Accuracy " + (double)(confusion.tP+confusion.tN)/trainer.vectors.size());
      System.out.println("overall Error Rate " + (double)(confusion.fP+confusion.fN)/trainer.vectors.size());
      if(folds != -1 && folds != 0)
         System.out.println("average Error Rate " + (double)(confusion.fP+confusion.fN)/trainer.vectors.size()/folds);
      else if(folds == -1)
         System.out.println("average Error Rate " + (double)(confusion.fP+confusion.fN)/trainer.vectors.size()/(trainer.vectors.size()-1));
      else if(folds == 0)
         System.out.println("average Error Rate " + (double)(confusion.fP+confusion.fN)/trainer.vectors.size());
   }
   public matrix confuseMatrix(Domain domains, CSV trainer, DecisionTreeNode tree) {
      int records[] = new int[(int)domains.size()];
      matrix confmatr = new matrix();
      for(int i = 0; i < (int)domains.size(); i++) {
         records[i] = checkTree(domains.getVectors().get(i), trainer, tree);
      }
      for(int j = 0; j < (int)domains.size(); j++) {
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
         else if(tree.value == v.get(trainer.findCat()) && tree.value == 2) {
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
