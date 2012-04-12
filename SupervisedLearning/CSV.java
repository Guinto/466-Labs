/**
 * @author Trent Ellingsen & Thomas Wong
 * @file CSV.java
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class CSV {

   public ArrayList<Vector> vectors;
   public ArrayList<ArrayList<String>> data;
   public ArrayList<Vector> dataCounts;

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
      this.data = new ArrayList<ArrayList<String>>();
      this.dataCounts = new ArrayList<Vector>();
      File file = new File(fileName);
      readTextFromFile(file);
   }

   public CSV(String fileName, int flag) {
      this.vectors = new ArrayList<Vector>();
      this.data = new ArrayList<ArrayList<String>>();
      File file = new File(fileName);
      readVectorsFromFile(file);
   }
   
   public CSV(Vector vectors) {
      this.vectors = new ArrayList<Vector>();
      this.vectors.add(vectors);
      for(int i = 0; i < this.vectors.get(0).size();i++) {
         if(i == 0)
            this.vectors.get(0).set(i,0);
         else
            this.vectors.get(0).set(i, 1);
      }
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
   private void readTextFromFile(File file) {
      Scanner scanner;
      try {
         scanner = new Scanner(file);
         int count = 0;
         while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.split("[,]");
            if(count == 0 || count == 2)
               data.add(new ArrayList<String>(Arrays.asList(tokens)));
            else if(count == 1)
               dataCounts.add(new Vector(Arrays.asList(tokens)));
            else
               vectors.add(new Vector(Arrays.asList(tokens)));
            count++;
         }
      }
      catch (FileNotFoundException e) {
         System.err.println("FILE: " + file.getName() + " NOT FOUND");
         e.printStackTrace();
      }
   }
   private void readOneVector(File file) {
      Scanner scanner;
      try {
         scanner = new Scanner(file);
         if (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.split("[,]");
            vectors.add(new Vector(Arrays.asList(tokens)));
         }
      }
      catch (FileNotFoundException e) {
         System.err.println("FILE: " + file.getName() + " NOT FOUND");
         e.printStackTrace();
      }
   }
   private void readVectorsFromFile(File file) {
      Scanner scanner;
      try {
         scanner = new Scanner(file);
         while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
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
}