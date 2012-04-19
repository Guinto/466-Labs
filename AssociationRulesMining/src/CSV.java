/**
* @author Trent Ellingsen & Thomas Wong
* @file CSV.java
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Scanner;


public class CSV {

   public ArrayList<Vector> vectors;
   public ArrayList<ArrayList<String>> data;
   public ArrayList<Vector> dataCounts;
   public Hashtable sets;
   public int category;
   public int id;

   public static void main(String[] args) {
      if (args.length > 0) {
         new CSV("tree03-20-numbers.csv");
      }
      else {
         System.err.println("Usage: CSV fileName");
         System.exit(1);
      }
   }

   public CSV(String fileName) {
      this.vectors = new ArrayList<Vector>();
      this.sets = new Hashtable();
      File file = new File(fileName);
      readVectorsFromFile(file);
   }

   private Vector getListFromColumn(int column) {
      Vector columnList = new Vector();

      for (int i = 0; i < vectors.size(); i++) {
         if (vectors.get(i) == null) {
            // TODO Should throw better exception?
            throw new NullPointerException("Not all vectors conform to same length");
         }
         columnList.add(vectors.get(i).get(column));
      }

      return columnList;
   }

   private void readVectorsFromFile(File file) {
      Scanner scanner;
      try {
         scanner = new Scanner(file);
         while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            line = line.replaceAll("[ ]", "");
            String[] tokens = line.split("[,]");
            vectors.add(new Vector(Arrays.asList(tokens)));
         }
      }
      catch (FileNotFoundException e) {
         System.err.println("FILE: " + file.getName() + " NOT FOUND");
         e.printStackTrace();
      }
   }
   

   public void printVectors() {
      for(int i = 0; i < vectors.size(); i++) {
         System.out.println(vectors.get(i));
      }
   }
   
   public void getHash() {
      for(Vector v: vectors) {
         if(sets.containsKey(v.get(0))) {
            for(int i = 1; i < v.size(); i++) {
               ((ArrayList<Integer>) sets.get(v.get(0))).add(v.get(i));
            }
         }
         else {
            sets.put(v.get(0), new ArrayList());
            for(int i = 1; i < v.size(); i++) {
               ((ArrayList<Integer>) sets.get(v.get(0))).add(v.get(i));
            }
         }
      }
   }
}