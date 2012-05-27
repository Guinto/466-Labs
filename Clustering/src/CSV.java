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
   public Vector restrictions;
   public int category;
   public int id;

   public CSV(String fileName) {
      this.vectors = new ArrayList<Vector>();
      this.restrictions = new Vector();
      File file = new File(fileName);
      readTextFromFile(file, false);
   }

   public CSV(String fileName, boolean isAccidentReportFull) {
      this.vectors = new ArrayList<Vector>();
      this.restrictions = new Vector();
      File file = new File(fileName);
      readTextFromFile(file, isAccidentReportFull);
   }
   
   private void readTextFromFile(File file, boolean isAccidentReportFull) {
      Scanner scanner;
      try {
         scanner = new Scanner(file);
         int count = 0;
         while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.split("[,]");
            
            if(count == 0 && isAccidentReportFull == false) {
               restrictions = new Vector(Arrays.asList(tokens));
            } else if (count == 0) {
            	ArrayList<String> fullAccess = new ArrayList<String>();
            	for (int i = 0; i < tokens.length; i++) {
            		fullAccess.add("1");
            	}
            	restrictions = new Vector(fullAccess);
            	count++;
            	continue;
            } else {
            	Vector v = new Vector();
            	for (int i = 0; i < tokens.length; i++) {
            		if (i < restrictions.size() && restrictions.get(i) == 1) {
            			v.add(Float.parseFloat(tokens[i]));
            		}
            	}
               vectors.add(v);
            }
            count++;
         }
      }
      catch (FileNotFoundException e) {
         System.err.println("FILE: " + file.getName() + " NOT FOUND");
         e.printStackTrace();
      }
   }
   
   public String toString() {
	   String str = restrictions.toString() + "\n";
	   
	   for(int i = 0; i < vectors.size(); i++) {
		   str += vectors.get(i) + "\n";
	   }
      
      return str;
   }
}