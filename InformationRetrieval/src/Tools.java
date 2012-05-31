import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;



public class Tools {
   private Hashtable<String, Document> words;
   private ArrayList<docList> docs;

   public Tools() {
      this.words = new Hashtable<String, Document>();
      this.docs = new ArrayList<docList>();
   }
   public int termFrequency(String word, PlainTextReader words) {
      return words.getWords().get(word);
   }

   public double idf(String word) {
      return Math.log((double) (docs.size()/words.get(word).getIDs().size()));
   }

   public double weight(String word) {
      return words.get(word).getTF() * words.get(word).getIDF();
   }

   public void readTextFromFile(File file) {
      Scanner scanner;
      try {
         scanner = new Scanner(file);
         int count = 0;
         while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.split("[,]");

            if(tokens.length == 2) {
               docs.add(new docList(tokens[0], Integer.parseInt(tokens[1])));
            }
            else {
               for(int i = 4; i < tokens.length; i++) {
                  if(i == 4)
                     words.put(tokens[0], new Document(new ArrayList<Integer>(), Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]), Double.parseDouble(tokens[3])));
                  words.get(tokens[0]).getIDs().add(Integer.parseInt(tokens[i]));
               }
            }
         }
      }
      catch (FileNotFoundException e) {
         System.err.println("FILE: " + file.getName() + " NOT FOUND");
         e.printStackTrace();
      }
   }

   public void writeTextFromFile(File file) throws IOException {
      FileWriter writer = new FileWriter(file);
      for(int i = 0; i < docs.size(); i++) {
         writer.write(docs.get(i).getName() + "," + docs.get(i).getID() + "\r\n");
      }      
      Enumeration<String> keys = words.keys();
      while(keys.hasMoreElements()) {
         String obj = keys.nextElement();
         writer.write(keys + "," + words.get(obj).getTF() + "," + words.get(obj).getIDF() + "," + words.get(obj).getWeight());
         for(int i = 0; i < words.get(obj).getIDs().size(); i++) {
            writer.write("," + words.get(obj).getIDs().get(i));
         }
         writer.write("\r\n");
      }
   }

   public double cosineSim(String firstWord, String secWord) {
      double sim = words.get(firstWord).getWeight() * words.get(secWord).getWeight();
      sim = sim / Math.sqrt(Math.pow(words.get(firstWord).getWeight(), 2.0) * Math.pow(words.get(secWord).getWeight(), 2.0));
      return sim;
   }
}
