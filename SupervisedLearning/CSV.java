/**
 * @author Trent Ellingsen & Thomas Wong
 * @file CSV.java
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class CSV {

   private ArrayList<Vector> vectors;


   public static void main(String[] args) {
      if (args.length > 0) {
         new CSV("test02.csv");
      }
      else {
         System.err.println("Usage: CSV fileName");
         System.exit(1);
      }
   }

   public CSV(String fileName) {
      this.vectors = new ArrayList<Vector>();
      File file = new File(fileName);

      readVectorsFromFile(file);
      printVectors();
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
            //line = line.replaceAll("[ ]", "");
            String[] tokens = line.split("[,]");
            vectors.add(new Vector(Arrays.asList(tokens)));
         }
      }
      catch (FileNotFoundException e) {
         System.err.println("FILE: " + file.getName() + " NOT FOUND");
         e.printStackTrace();
      }
   }

   private void printVectors() {
      for(int i = 0; i < vectors.size(); i++) {
         System.out.println(vectors.get(i));
      }
   }

   private class Vector {
      private ArrayList<Double> elements;

      public Vector(List<String> list) {
         setElements(new ArrayList<Double>());
         for (String e : list) {
            if (e.isEmpty()) {
               elements.add(0.0);
            } else {
               elements.add(Double.parseDouble(e));
            }
         }
      }

      public void add(double element) {
         elements.add(element);
      }

      public Vector() {
         setElements(new ArrayList<Double>());
      }

      public void setElements(ArrayList<Double> elements) {
         this.elements = elements;
      }

      public double get(int index) {
         return elements.get(index);
      }

      public int size() {
         return elements.size();
      }

      public String toString() {
         return elements.toString();
      }

      public double getLength() {
         double total = 0;
         for (Double d : elements) {
            total += d * d;
         }
         return Math.sqrt(total);
      }

      public double getDotProduct(Vector v) {
         if (size() != v.size()) {
            System.err.println("Vector lengths not equal: " + size() + " vs " + v.size());
            return -1; //TODO Should throw exception
         }

         double total = 0;
         for (int i = 0; i < size(); i++) {
            total += get(i) * v.get(i);
         }

         return total;
      }

      public double getEucledianDistance(Vector v) {
         if (size() != v.size()) {
            System.err.println("Vector lengths not equal: " + size() + " vs " + v.size());
            return -1; //TODO Should throw exception
         }

         double total = 0;
         for (int i = 0; i < size(); i++) {
            total += Math.pow(get(i) - v.get(i), 2);
         }

         return total;
      }

      public double getManhattanDistance(Vector v) {
         if (size() != v.size()) {
            System.err.println("Vector lengths not equal: " + size() + " vs " + v.size());
            return -1; //TODO Should throw exception
         }

         double total = 0;
         for (int i = 0; i < size(); i++) {
            total += Math.abs(get(i) - v.get(i));
         }

         return total;
      }

      public double getPearsonCorrelation(Vector v) {
         if (size() != v.size()) {
            System.err.println("Vector lengths not equal: " + size() + " vs " + v.size());
            return -1; //TODO Should throw exception
         }

         double total = 0;
         for (int i = 0; i < size(); i++) {
            total += get(i) * v.get(i);
         }

         return total;
      }

      public double getLargestElement() {
         if (size() == 0) {
            System.err.println("Vector size is 0");
            return -1; //TODO Should throw exception
         }
         double largest = elements.get(0);

         for(double d : elements) {
            if (d > largest) {
               largest = d;
            }
         }
         return largest;
      }

      public double getSmallestElement() {
         if (size() == 0) {
            System.err.println("Vector size is 0");
            return -1; //TODO Should throw exception
         }
         double smallest = elements.get(0);

         for(double d : elements) {
            if (d < smallest) {
               smallest = d;
            }
         }
         return smallest;
      }

      public double getMedian() {
         if (size() == 0) {
            System.err.println("Vector size is 0");
            return -1; //TODO Should throw exception
         }
         sortElements();

         if (size() % 2 == 0) {
            return (elements.get(size() / 2) + elements.get(size() / 2 - 1)) / 2;  // takes average
         }
         return elements.get(size() / 2);
      }

      public double getMean() {
         if (size() == 0) {
            System.err.println("Vector size is 0");
            return -1; //TODO Should throw exception
         }

         double total = 0;

         for(double d : elements) {
            total += d;
         }
         return total / size();

      }

      public double getStandardDeviation() {
         if (size() == 0) {
            System.err.println("Vector size is 0");
            return -1; //TODO Should throw exception
         }

         double mean = getMean();
         ArrayList<Double> deviationList = new ArrayList<Double>();

         for (int i = 0; i < size(); i++) {
            deviationList.add(Math.pow(get(i) - mean, 2));
         }

         double total = 0;
         for (Double d : deviationList) {
            total += d;
         }

         return Math.sqrt(total / (size() - 1));
      }

      private void sortElements() {
         Collections.sort(elements);
      }
   }
}