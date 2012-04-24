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

   private ArrayList<Vector> vectors;
   private Hashtable<Integer, ArrayList<Integer>> sets;
   
   public ArrayList<Vector> getVectors() {
	   return vectors;
   }
   
   public Hashtable<Integer, ArrayList<Integer>> getSets() {
	   return sets;
   }
   
   public CSV(String fileName) {
      this.vectors = new ArrayList<Vector>();
      this.sets = new Hashtable<Integer, ArrayList<Integer>>();
      File file = new File(fileName);
      readVectorsFromFile(file);
      getHash();
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

   public int size() {
	   return vectors.size();
   }
   

   public void printVectors() {
      for(int i = 0; i < vectors.size(); i++) {
         System.out.println(vectors.get(i));
      }
   }

   public void getHash() {
      for(Vector v: vectors) {
         for(int i = 1; i < v.size(); i++) {
            if(sets.containsKey(v.get(i))) {
            	((ArrayList<Integer>) sets.get(v.get(i))).add(v.get(0));
            }
            else {
            	ArrayList<Integer> firstElement = new ArrayList<Integer>();
            	firstElement.add(v.get(0));
            	sets.put(v.get(i), firstElement);
            }
         }
      }
   }
}