/**
 * @author Trent Ellingsen & Thomas Wong
 * @file PlainTextReader.java
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PlainTextReader {

   boolean isEndOfParagraph, isEndOfSentence;
   private List<String> splitString;
   private Hashtable<String, Integer> words;
   int currentParagraph, currentSentence;
   private List<Paragraph> document;

   private Scanner scanner;

   public PlainTextReader(String fileName) {
      isEndOfParagraph = false;
      isEndOfSentence = false;
      splitString = new ArrayList<String>(); // used if symbols used in words
      words = new Hashtable<String, Integer>();
      currentParagraph = currentSentence = 0;
      document = new ArrayList<Paragraph>();
      document.add(new Paragraph());

      try {
         scanner = new Scanner(new File(fileName));
         scanner.useDelimiter("[ \t\u000B\f\r\u001C\u001D\u001E\u001F]"); // Delimits all white space except \n
         parseFile();
      } catch (FileNotFoundException e) {
         System.err.println("FILE " + fileName + " NOT FOUND");
         e.printStackTrace();
      }
   }

   public int getNumParagraphs() {
      int count = 0;
      for(Paragraph p : document)
         if(p.getSentences().size() != 0)
            count++;
      return count - 1;
   }

   public int getNumSentences() {
      int count = 0;
      for(int i = 0; i < document.size(); i++) {
         count += document.get(i).getSentences().size();
      }
      return count;
   }

   public int getNumWords() {
      int count = 0;
      for (int i = 0; i < document.size(); i++) {
         for(int j = 0; j < document.get(i).getSentences().size(); j++)
            count += document.get(i).getSentences().get(j).getWords().size();
      }
      return count;
   }

   public int getNumUniqueWords() {
      return words.size();
   }

   public ArrayList<String> mostFrequency() {
      ArrayList<String> answer = new ArrayList<String>();
      int max = Collections.max(words.values());
      for(Map.Entry<String, Integer> entry : words.entrySet())
         if(entry.getValue().equals(max))
            answer.add(entry.getKey());
      return answer;
   }
   
   public ArrayList<String> getFrequency(int max) {
      ArrayList<String> answer = new ArrayList<String>();
      for(Map.Entry<String, Integer> entry : words.entrySet())
         if(entry.getValue().equals(max))
            answer.add(entry.getKey());
      return answer;
   }
   
   public ArrayList<String> leastFrequency(int max) {
      ArrayList<String> answer = new ArrayList<String>();
      for(Map.Entry<String, Integer> entry : words.entrySet())
         if(entry.getValue() > max)
            answer.add(entry.getKey());
      return answer;
   }
   
   public boolean doesWordExist(String word) {
      if(words.containsKey(word))
         return true;
      return false;
   }

   public void parseFile() {
      while (hasNextWord()) {
         String word = nextWord();
         addWordToTable(word);
         addWordToDocument(word);
      }
      removeLastSentence();
   }

   private void removeLastSentence() {
      document.get(currentParagraph).getSentences().remove(currentSentence);
   }

   private void addWordToTable(String word) {
      if (words.containsKey(word)) {
         words.put(word, words.get(word) + 1);
      } else {
         words.put(word, 0);
      }
   }

   private void addWordToDocument(String word) {
      Paragraph p = document.get(currentParagraph);
      p.getSentences().get(currentSentence).addWord(word);
      if (isEndOfParagraph) {
         document.add(new Paragraph());
         currentParagraph = document.size() - 1;
         isEndOfParagraph = false;
         currentSentence = 0;
      }
      if (isEndOfSentence) {
         p.addSentence(new Sentence());
         currentSentence = p.getSentences().size() - 1;
         isEndOfSentence = false;
      }
      document.set(currentParagraph, p);
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
            || word.contains("...")) {
            word = word.replace("...", ",");
            String[] split = word.split("[,:;\\(\\)]");
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
         word = word.substring(0, word.length() - 1);
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
		if (!Character.isLetter(word.charAt(0))) {
			word = word.substring(1);
			return stripWord(word);
		} else if (!Character.isLetter(word.charAt(word.length() - 1))) {
			word = word.substring(0, word.length() - 1);
			return stripWord(word);
		}
		return word;
	}

   public static void main(String[] args) {
      Scanner in = new Scanner(System.in);
      String answer = "";
      String word = "";
      int num;
      if (args.length != 0) {
         PlainTextReader data = new PlainTextReader(args[0]);
         System.out.println("Number of paragraphs: " + data.getNumParagraphs()); 

         System.out.println("Number of sentences: " + data.getNumSentences());

         System.out.println("Number of words: " + data.getNumWords());

         System.out.println("Number of unique words: " + data.getNumUniqueWords()); 
         
         System.out.print("Highest frequency word/s:");
         for(String s: data.mostFrequency())
            System.out.print(" " + s);
         System.out.println();
         
         System.out.println("Check to see if words with frequency? y/n");
         answer = in.next();
         while(!answer.equals("n")) {
            if(answer.equals("y")) {
               System.out.println("Enter Frequency: ");
               num = Integer.parseInt(in.next());
               System.out.print("Words with Frequency " + num);
               for(String s: data.getFrequency(num))
                  System.out.print(" " + s);
               System.out.println();
            }
            System.out.println("Check Another?");
            answer = in.next();
         }
         answer = "";
         
         System.out.println("Check to see if words greater than a frequency? y/n");
         answer = in.next();
         while(!answer.equals("n")) {
            if(answer.equals("y")) {
               System.out.println("Enter Frequency: ");
               num = Integer.parseInt(in.next());
               System.out.print("Words with Frequency " + num);
               for(String s: data.leastFrequency(num))
                  System.out.print(" " + s);
               System.out.println();
            }
            System.out.println("Check Another?");
            answer = in.next();
         }
         answer = "";
         
         System.out.println("Check to see if a word exists? y/n");
         answer = in.next();
         while(!answer.equals("n")) {
            if(answer.equals("y")) {
               System.out.println("Enter Word: ");
               word = in.next();
               if(data.doesWordExist(word))
                  System.out.println("word exists");
               else
                  System.out.println("word does not exist");
            }
            System.out.println("Keep checking?");
            answer = in.next();
            word = "";
         }
      } else {
         System.err.println("Usage: PlainTextReader fileName");
         System.exit(1);
      }
   }

   private class Sentence {
      private List<String> words;

      public List<String> getWords() {
         return words;
      }

      public Sentence() {
         words = new ArrayList<String>();
      }

      public void addWord(String word) {
         words.add(word);
      }
   }

   private class Paragraph {
      private List<Sentence> sentences;

      public Paragraph() {
         sentences = new ArrayList<Sentence>();
         addSentence(new Sentence());
      }

      public List<Sentence> getSentences() {
         return sentences;
      }

      public void addSentence(Sentence sentence) {
         sentences.add(sentence);
      }
   }
}  
