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
       printStats();
   	}
   	
   	public double getLargestElementFromColumn(int column) {
		if (vectors.size() == 0) {
			System.err.println("Vector size is 0");
			return -1; //TODO Should throw exception
		}
		
		return getListFromColumn(column).getLargestElement();
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
   	
   	public double getSmallestElementFromColumn(int column) {
		if (vectors.size() == 0) {
			System.err.println("Vector list size is 0");
			return -1; //TODO Should throw exception
		}

		return getListFromColumn(column).getSmallestElement();
   	}
   	
   	public double getMedianFromColumn(int column) {
		if (vectors.size() == 0) {
			System.err.println("Vector list size is 0");
			return -1; //TODO Should throw exception
		}

		return getListFromColumn(column).getMedian();
   	}
   	
   	public double getMeanFromColumn(int column) {
		if (vectors.size() == 0) {
			System.err.println("Vector list size is 0");
			return -1; //TODO Should throw exception
		}
		
		return getListFromColumn(column).getMean();
	}
   	
   	public double getStandardDeviationFromColumn(int column) {
		if (vectors.size() == 0) {
			System.err.println("Vector list size is 0");
			return -1; //TODO Should throw exception
		}
		
		return getListFromColumn(column).getStandardDeviation();
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
   
   	private void printVectors() {
	   for(int i = 0; i < vectors.size(); i++) {
       	 System.out.println(vectors.get(i));
	   }
   	}
   	
   	private void printStats() {
   		System.out.println("Printout based on first 2 rows & columns");
   		
   		System.out.println("\nRows:");
   		System.out.println("Vector Length: " + vectors.get(0).getLength());
   		System.out.println("Vector Dot Product: " + vectors.get(0).getDotProduct(vectors.get(1)));
   		System.out.println("Vector Eucledian Distance: " + vectors.get(0).getEucledianDistance(vectors.get(1)));
   		System.out.println("Vector Manhattan Distance: " + vectors.get(0).getManhattanDistance(vectors.get(1)));
   		System.out.println("Vector Pearson Correlation: " + vectors.get(0).getPearsonCorrelation(vectors.get(1)));
   		System.out.println("Largest Element in Vector: " + vectors.get(0).getLargestElement());
   		System.out.println("Smallest Element in Vector: " + vectors.get(0).getSmallestElement());
   		System.out.println("Median in Vector: " + vectors.get(0).getMedian());
   		System.out.println("Mean in Vector: " + vectors.get(0).getMean());
   		System.out.println("Standard Deviation in Vector: " + vectors.get(0).getStandardDeviation());
   		
   		System.out.println("\nColumn:");
   		System.out.println("Largest Element in Column: " + getLargestElementFromColumn(0));
   		System.out.println("Smallest Element in Column: " + getSmallestElementFromColumn(0));
   		System.out.println("Median in Column: " + getMedianFromColumn(0));
   		System.out.println("Mean in Column: " + getMeanFromColumn(0));
   		System.out.println("Standard Deviation in Column: " + getStandardDeviationFromColumn(0));
   	}
}
