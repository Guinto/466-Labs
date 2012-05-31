import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;


public class pageRank {
   private Hashtable<String, ArrayList<Node>> nodes;
   private ArrayList<Node> ranks;
   public static int count = 0;
   public static void main(String[] args) {
      if (args.length == 0) {
         pageRank page = new pageRank();
         page.readTextFromFile(new File("data/dolphins.csv"));
         System.out.println(page.getNodes().size());

         Enumeration<String> e = page.getNodes().keys();
         while(e.hasMoreElements()) {
            String n = e.nextElement();
            page.ranks.add(new Node(n, page.findPageRank(new Node(n, 0.0))));
            //         System.out.println(page.findPageRank(new Node(n, 0)) + " " + page.nodes.get(n).size() + " " + n);
            count = 0;
         }
         Collections.sort(page.ranks);
         for(Node n : page.ranks) {
            System.out.println(n.getName() + " " + n.getValue());
         }
      } else {
         System.err.println("Usage: HClustering <fileName> [<threshold>]");
         System.exit(1);
      }

   }

   public pageRank() {
      this.nodes = new Hashtable<String, ArrayList<Node>>();
      this.ranks = new ArrayList<Node>();
   }

   public double findPageRank(Node name) {
      double rank = 0.0;
      count++;
      if(count > 1000) {
         return 0.0;
      }
      for(int i = 0; i < nodes.get(name.getName()).size(); i++) { 
         rank += 1.0/nodes.get(name.getName()).size() * findPageRank(nodes.get(name.getName()).get(i));
      }
      rank = rank * .803;
      rank += (1 - .803) * 1/nodes.size();
      return rank;
   }

   public Hashtable<String, ArrayList<Node>> getNodes() {
      return nodes;
   }
   private void readTextFromFile(File file) {
      Scanner scanner;
      Node a, b;
      try {
         scanner = new Scanner(file);
         while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            line = line.replaceAll("[ ]", "");
            String[] tokens = line.split("[,]");
            a = new Node(tokens[0], Integer.parseInt(tokens[1]));
            b = new Node(tokens[2], Integer.parseInt(tokens[3]));
            if(!nodes.containsKey(a.getName())) {
               nodes.put(a.getName(), new ArrayList<Node>());
            }
            if(!nodes.get(a.getName()).contains(b))
               nodes.get(a.getName()).add(b);
            if(!nodes.containsKey(b.getName())) {
               nodes.put(b.getName(), new ArrayList<Node>());
            }
            if(!nodes.get(b.getName()).contains(a))
               nodes.get(b.getName()).add(a);
         }
      }
      catch (FileNotFoundException e) {
         System.err.println("FILE: " + file.getName() + " NOT FOUND");
         e.printStackTrace();
      }
   }

   private void readDirectedTextFromFile(File file) {
      Scanner scanner;
      Node a, b;
      try {
         scanner = new Scanner(file);
         while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            line = line.replaceAll("[ ]", "");
            String[] tokens = line.split("[,]");
            a = new Node(tokens[0], Integer.parseInt(tokens[1]));
            b = new Node(tokens[2], Integer.parseInt(tokens[3]));

            if(!nodes.containsKey(a.getName())) {
               nodes.put(a.getName(), new ArrayList<Node>());
            }
            if(!nodes.get(a.getName()).contains(b))
               nodes.get(a.getName()).add(b);
         }
      }
      catch (FileNotFoundException e) {
         System.err.println("FILE: " + file.getName() + " NOT FOUND");
         e.printStackTrace();
      }
   }
}
