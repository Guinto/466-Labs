import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;


public class pageRank {
   private Hashtable<Node, ArrayList<Node>> nodes;
   public static void main(String[] args) {
      if (args.length == 0) {
         pageRank page = new pageRank();
         page.readTextFromFile(new File("data/dolphins.csv"));
         System.out.println(page.getNodes().size());
        /* Enumeration<Node> e = page.getNodes().keys();
         while(e.hasMoreElements()) {
            Node n = e.nextElement();
            System.out.println(n.getName() + " " +  n.getValue());
         }*/
      } else {
         System.err.println("Usage: HClustering <fileName> [<threshold>]");
         System.exit(1);
      }

   }

   public pageRank() {
      this.nodes = new Hashtable<Node, ArrayList<Node>>();
   }

   public Hashtable<Node, ArrayList<Node>> getNodes() {
      return nodes;
   }
   private void readTextFromFile(File file) {
      Scanner scanner;
      Node a, b;
      try {
         scanner = new Scanner(file);
         while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.split("[,]");
            a = new Node(tokens[0], Integer.parseInt(tokens[1]));
            b = new Node(tokens[2], Integer.parseInt(tokens[3]));
            if(!nodes.containsKey(a)) {
               nodes.put(a, new ArrayList<Node>());
            }
            if(!nodes.get(a).contains(b))
               nodes.get(a).add(b);
            if(!nodes.containsKey(b)) {
               nodes.put(b, new ArrayList<Node>());
            }
            if(!nodes.get(b).contains(a))
               nodes.get(b).add(a);
         }
      }
      catch (FileNotFoundException e) {
         System.err.println("FILE: " + file.getName() + " NOT FOUND");
         e.printStackTrace();
      }
   }
}
