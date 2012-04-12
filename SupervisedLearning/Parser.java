
public class Parser {
   public static void main(String args[]) {
      CSV trainer = null;
      CSV restrict = null;
      if (args.length > 0) {
         trainer = new CSV(args[0]);
         if(args.length > 1) {
            restrict = new CSV(args[1], 0);
         }
         else {
            restrict = new CSV(trainer.vectors.get(0).size());
         }
      }
      else {
         System.err.println("Usage: CSV fileName");
         System.exit(1);
      }
      Domain test = new Domain(trainer.vectors, trainer.dataCounts.get(0).last());
      C45 tree = new C45(test, restrict, trainer);
      //tree.getDecisionTree().print();
      tree.printTree(tree.decisionTree);
   }
}