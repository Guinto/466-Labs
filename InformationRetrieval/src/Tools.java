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
   private Hashtable<String, KeyWord> words;
   private ArrayList<DocumentList> docs;

   public Tools() {
      this.words = new Hashtable<String, KeyWord>();
      this.docs = new ArrayList<DocumentList>();
   }
   public double termFrequency(String word, PlainTextReader words) {
      return words.getWords().get(word);
   }
   
   public Hashtable<String, KeyWord> getWords() {
      return words;
   }
   
   public void setWords(Hashtable<String, KeyWord> words) {
      this.words = words;
   }
   
   public ArrayList<DocumentList> getDocs() {
      return docs;
   }

   public double idf(String word) {
      return Math.log((double) (docs.size()/words.get(word).getdocs().size()));
   }

   public double weight(String word, String id) {
      return words.get(word).getid(id).getTF() * words.get(word).getIDF();
   }

   public void readTextFromFile(File file) {
      Scanner scanner;
      try {
         scanner = new Scanner(file);
         int flag = 0;
         while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.split("[,]");

            if(flag == 0 && tokens.length == 2) {
               docs.add(new DocumentList(tokens[0], tokens[1]));
            }
            else if(tokens.length == 0) {
               flag = 1;
            }
            else if(flag == 1){
               for(int i = 2; i < tokens.length; i = i + 3) {
                  if(i == 2)
                     words.put(tokens[0], new KeyWord(new ArrayList<Document>(), Double.parseDouble(tokens[1])));
                  words.get(tokens[0]).getdocs().add(new Document(tokens[i], Double.parseDouble(tokens[i+1]), Double.parseDouble(tokens[i+2])));
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
      writer.write("\r\n");
      Enumeration<String> keys = words.keys();
      while(keys.hasMoreElements()) {
         String obj = keys.nextElement();
         writer.write(keys + "," + words.get(obj).getIDF());
         for(int i = 0; i < words.get(obj).getdocs().size(); i++) {
            writer.write("," + words.get(obj).getdocs().get(i).getid() + "," + words.get(obj).getdocs().get(i).getTF() + "," + words.get(obj).getdocs().get(i).getWeight());
         }
         writer.write("\r\n");
      }
   }

   public double cosineSim(String firstWord, String secWord, String firstID, String secID) {
      double sim = words.get(firstWord).getid(firstID).getWeight() * words.get(secWord).getid(secID).getWeight();
      sim = sim / Math.sqrt(Math.pow(words.get(firstWord).getid(firstID).getWeight(), 2.0) * Math.pow(words.get(secWord).getid(secID).getWeight(), 2.0));
      return sim;
   }
}
