import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vector {
   private ArrayList<Integer> elements;

   public Vector(List<String> list) {
      setElements(new ArrayList<Integer>());
      for (String e : list) {
         if (e.isEmpty()) {
            elements.add( 0);
         } else {
            elements.add(Integer.parseInt(e));
         }
      }
   }

   public void add(int element) {
      elements.add((int) element);
   }

   public Vector() {
      setElements(new ArrayList<Integer>());
   }

   public void setElements(ArrayList<Integer> elements) {
      this.elements = elements;
   }

   public int get(int index) {
      return elements.get(index);
   }

   public int size() {
      return elements.size();
   }

   public String toString() {
      return elements.toString();
   }


   public int getDotProduct(Vector v) {
      if (size() != v.size()) {
         System.err.println("Vector lengths not equal: " + size() + " vs " + v.size());
         return -1; //TODO Should throw exception
      }

      int total = 0;
      for (int i = 0; i < size(); i++) {
         total += get(i) * v.get(i);
      }

      return total;
   }

   public int getEucledianDistance(Vector v) {
      if (size() != v.size()) {
         System.err.println("Vector lengths not equal: " + size() + " vs " + v.size());
         return -1; //TODO Should throw exception
      }

      int total = 0;
      for (int i = 0; i < size(); i++) {
         total += Math.pow(get(i) - v.get(i), 2);
      }

      return total;
   }

   public int getManhattanDistance(Vector v) {
      if (size() != v.size()) {
         System.err.println("Vector lengths not equal: " + size() + " vs " + v.size());
         return -1; //TODO Should throw exception
      }

      int total = 0;
      for (int i = 0; i < size(); i++) {
         total += Math.abs(get(i) - v.get(i));
      }

      return total;
   }

   public int getPearsonCorrelation(Vector v) {
      if (size() != v.size()) {
         System.err.println("Vector lengths not equal: " + size() + " vs " + v.size());
         return -1; //TODO Should throw exception
      }

      int total = 0;
      for (int i = 0; i < size(); i++) {
         total += get(i) * v.get(i);
      }

      return total;
   }

   public int getLargestElement() {
      if (size() == 0) {
         System.err.println("Vector size is 0");
         return -1; //TODO Should throw exception
      }
      int largest = elements.get(0);

      for(int d : elements) {
         if (d > largest) {
            largest = d;
         }
      }
      return largest;
   }

   public int getSmallestElement() {
      if (size() == 0) {
         System.err.println("Vector size is 0");
         return -1; //TODO Should throw exception
      }
      int smallest = elements.get(0);

      for(int d : elements) {
         if (d < smallest) {
            smallest = d;
         }
      }
      return smallest;
   }

   public int getMedian() {
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

   public int getMean() {
      if (size() == 0) {
         System.err.println("Vector size is 0");
         return -1; //TODO Should throw exception
      }

      int total = 0;

      for(int d : elements) {
         total += d;
      }
      return total / size();

   }

   private void sortElements() {
      Collections.sort(elements);
   }

   public int last() {
      return elements.get(elements.size() - 1);
   }

   public void set(int i, int j) {
      elements.set(i, j);

   }

   public int length() {
      return elements.size();
   }

      public int findCat(CSV restrictions) {
      for(int i = 0; i < restrictions.vectors.get(0).size(); i++) {
         if(restrictions.vectors.get(0).get(i) == -1)
            return i;
      }
      return restrictions.vectors.get(0).size()-1;
   }
}