/**
 * @author Trent Ellingsen & Thomas Wong
 * @file PlainTextReader.java
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class PlainTextReader {

   boolean isEndOfParagraph, isEndOfSentence;
   private List<String> splitString;
   private Hashtable<String, Integer> words;
   int currentParagraph, currentSentence;

   private Scanner scanner;
      
   public PlainTextReader(String fileAsString, boolean isString) {
	   initStuff();
       scanner = new Scanner(fileAsString);
       scanner.useDelimiter("[ \t\u000B\f\r\u001C\u001D\u001E\u001F]"); // Delimits all white space except \n
       parseFile();
   }
   
   public PlainTextReader(String fileName) {
	   initStuff();
      try {
         scanner = new Scanner(new File(fileName));
         scanner.useDelimiter("[ \t\u000B\f\r\u001C\u001D\u001E\u001F]"); // Delimits all white space except \n
         parseFile();
         
      } catch (FileNotFoundException e) {
         System.err.println("FILE " + fileName + " NOT FOUND");
         e.printStackTrace();
      }
   }
   
   public void initStuff() {
	      isEndOfParagraph = false;
	      isEndOfSentence = false;
	      splitString = new ArrayList<String>(); // used if symbols used in words
	      words = new Hashtable<String, Integer>();
	      currentParagraph = currentSentence = 0;
   }
   
   public Hashtable<String, Integer> getWords() {
      return words;
   }
   
   public int getNumUniqueWords() {
      return words.size();
   }

   public void parseFile() {
      while (hasNextWord()) {
         String word = nextWord();
         if (word == null) break;
         Stemmer stemmed = new Stemmer();
         stemmed.add(word.toCharArray(), word.length());
         stemmed.stem();
         addWordToTable(stemmed.toString());
      }
   }
   
   private void addWordToTable(String word) {
	   if (word == null) return;
      if (words.containsKey(word)) {
         words.put(word, words.get(word) + 1);
      } else {
         words.put(word, 0);
      }
   }
   
   private boolean hasNextWord() {
      return scanner.hasNext();
   }

   private String nextWord() {
      String word = "";
      if (splitString.size() != 0) {
         word = splitString.get(0);
         splitString.remove(0);
         if (word.equals("-")) {
            return nextWord();
         } 
      } else {
    	  if (!scanner.hasNext()) return null;
    		  word = scanner.next();
         if (word.isEmpty()) {
        	 return nextWord();
         }
         if (word.charAt(word.length() - 1) == '\n' || countNewlines(word) > 1) {
   			isEndOfParagraph = true;
        	isEndOfSentence = true;
         }
         if (word.contains(",") || word.contains("-") || word.contains(":") 
            || word.contains(";") || word.contains("(") || word.contains(")") 
            || word.contains("...") || word.contains("\n") 
            || (word.contains(".") && word.charAt(word.length() - 1) != '.')) {
            word = word.replace("...", ",");
            String[] split = word.split("[,:;.\\(\\)\n]");
            for (int i = 0; i < split.length; i++) {
               splitString.add(split[i]);
            }
            return nextWord();
         }
      }
      
      if (word.length() == 0) {
         return nextWord();
      }
      if (word.charAt(word.length() - 1) == '.' || word.charAt(word.length() - 1) == '!' || word.charAt(word.length() - 1) == '?') {
         isEndOfSentence = true;
         try {
        	 word = word.substring(0, word.length() - 2);
         } catch (StringIndexOutOfBoundsException e) {
        	 return nextWord();
         }
      }
      
      word = stripWord(word);

      return word;
   }
   
   private int countNewlines(String word) {
	   int total = 0;
	   for (int i = 0; i < word.length(); i++) {
		   if (word.charAt(i) == '\n') {
			   total++;
		   }
	   }
	   return total;
   }
   
	private String stripWord(String word) {
		if (word.isEmpty()) {
			return nextWord();
		}
		char c = word.charAt(0);
		char c2 = word.charAt(word.length() - 1);
		if (!isChar(c)) {
			return stripWord(word.substring(1));
		} else if (!isChar(c2)) {
			return stripWord(word.substring(0, word.length() - 1));
		}
		return word;
	}

	private boolean isChar(char c) {
		return (c <= 122 && c >= 65 && !(c > 90 && c < 97));
	}}  
