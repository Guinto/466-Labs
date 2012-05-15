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
	
	public ArrayList<Vector> getVectors() {
		return vectors;
	}
   
   	public CSV(String fileName) {
       this.vectors = new ArrayList<Vector>();
       File file = new File(fileName);
       
       readVectorsFromFile(file);
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
}