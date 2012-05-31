import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;


public class ir {
	
   boolean programIsRunning;
   Scanner sc;
   Tools data;
   public static void main(String[] args) throws IOException {
      new ir();
   }

   public ir() throws IOException {
	  data = new Tools();
  	  File f = new File("config.txt");
	  if (f.exists()) {
    	  data.readTextFromFile(f);
	  } else {
    	  FileWriter fw = new FileWriter("config.txt");
    	  fw.append("");
    	  fw.close();
	  }
      displayStartup();
      startProgram();
      runProgram();
   }

   private void displayStartup() {
      System.out.println("IR System Version 1.0\n");
   }

   private void startProgram() {
      sc = new Scanner(System.in);
      programIsRunning = true;
   }

   private void runProgram() throws IOException {
      while (programIsRunning) {
         System.out.print("IR>");
         String[] fullCommand = sc.nextLine().split(" ");
         runCommand(fullCommand);
      }
   }

   private void runCommand(String[] fullCommand) throws IOException {
      String command = fullCommand[0];
      String[] params = getParams(fullCommand);
      if (command.toUpperCase().equals("READ")) {
         if (params.length > 0 && params[0].toUpperCase().equals("LIST")) {
            readList(params);
         } else {
            read(params);
         }
      } else if (command.toUpperCase().equals("LIST")) {
         list();
      } else if (command.toUpperCase().equals("CLEAR")) {
         clear();
      } else if (command.toUpperCase().equals("PRINT")) {
         print(params);
      } else if (command.toUpperCase().equals("SHOW")) {
         show(params);
      } else if (command.toUpperCase().equals("SIM")) {
         sim(params);
      } else if (command.toUpperCase().equals("SEARCH")) {
         if (params.length > 0 && params[0].toUpperCase().equals("DOC")) {
            searchDoc(params);
         } else {
            search(params);
         }
      } else if (command.toUpperCase().equals("QUIT")) {
         quit();
      } else {
         System.err.println("Command does not exist");
      }
   }

   private String[] getParams(String[] fullCommand) {
      String[] params = new String[fullCommand.length - 1];
      for (int i = 1; i < fullCommand.length; i++) {
         params[i - 1] = fullCommand[i];
      }

      return params;
   }

   private void read(String[] params) {
	   readFile(params[0]);
   }
   
   private int readFile(String docId) {
	   File f = new File(docId);
	   if (!f.exists()) {
		   System.err.println("File " + docId + " not found");
		   return 1;
	   }
      PlainTextReader file = new PlainTextReader(docId);
      data.getDocs().add(new DocumentList(docId, docId));
      Enumeration<String> keys = file.getWords().keys();
      
      Hashtable<String, KeyWord> words = data.getWords();
      
      while(keys.hasMoreElements()) {
         String name = keys.nextElement();
         if(data.getWords().containsKey(name)) {
            data.getWords().get(name).addID(docId, data.termFrequency(name, file), 60);
            data.getWords().get(name).setidf(data.idf(name));
            for(Document d: data.getWords().get(name).getdocs()) {
               d.setweight(data.weight(name, d.getid()));
            }
         }
         else {
            words.put(name, new KeyWord(new ArrayList<Document>(), 0));
            words.get(name).addID(docId, data.termFrequency(name, file), 50);
            words.get(name).setidf(data.idf(name));
            words.get(name).getid(docId).setweight(data.weight(name, docId));
         }
      }
      return 0;
   }
   private void readFileList(String docId, int count) {
      PlainTextReader file = new PlainTextReader(docId);
      data.getDocs().add(new DocumentList(docId, docId + count));
      Enumeration<String> keys = file.getWords().keys();
      
      Hashtable<String, KeyWord> words = data.getWords();
      
      while(keys.hasMoreElements()) {
         String name = keys.nextElement();
         if(data.getWords().containsKey(name)) {
            data.getWords().get(name).addID(docId + count, data.termFrequency(name, file), 60);
            data.getWords().get(name).setidf(data.idf(name));
            for(Document d: data.getWords().get(name).getdocs()) {
               d.setweight(data.weight(name, d.getid()));
            }
         }
         else {
            words.put(name, new KeyWord(new ArrayList<Document>(), 0));
            words.get(name).addID(docId + count, data.termFrequency(name, file), 50);
            words.get(name).setidf(data.idf(name));
            words.get(name).getid(docId + count).setweight(data.weight(name, docId + count));
         }
      }
   }
  private void readString(String query) {
	  String docId = "query";
     PlainTextReader file = new PlainTextReader(query, true);
     data.getDocs().add(new DocumentList(docId, docId));
     Enumeration<String> keys = file.getWords().keys();
     
     Hashtable<String, KeyWord> words = data.getWords();
     
     while(keys.hasMoreElements()) {
        String name = keys.nextElement();
        if(data.getWords().containsKey(name)) {
           data.getWords().get(name).addID(docId, data.termFrequency(name, file), 60);
           data.getWords().get(name).setidf(data.idf(name));
           for(Document d: data.getWords().get(name).getdocs()) {
              d.setweight(data.weight(name, d.getid()));
           }
        }
        else {
           words.put(name, new KeyWord(new ArrayList<Document>(), 0));
           words.get(name).addID(docId, data.termFrequency(name, file), 50);
           words.get(name).setidf(data.idf(name));
           words.get(name).getid(docId).setweight(data.weight(name, docId));
        }
     }
  }

   private void readList(String[] params) {
      int count = 0;
      Scanner scanner = null;
      try {
		scanner = new Scanner(new File(params[1]));
	} catch (FileNotFoundException e) {
		System.err.println("File " + params[1] + " not found");
		return;
	}
      while (scanner.hasNextLine()) {
         String line = scanner.nextLine();
         readFileList(line, count++);
      }
   }

   private void list() {
      for(DocumentList d : data.getDocs()) {
         System.out.println(d.getID());
      }
   }

   private void clear() {
      data = new Tools();
   }

   private void print(String[] params) {
      if (params[0].contains(".xml")) {
         xmlParser xml = new xmlParser(new File(params[0]));
         System.out.println(xml.getParsedFile());
      } else {
         try {
            Scanner s = new Scanner(new File(params[0]));
            while (s.hasNextLine()) {
               System.out.println(s.nextLine());
            }
         } catch (FileNotFoundException e) {
            System.err.println("File " + params[0] + " not found.");
            e.printStackTrace();
         }
      }
   }

   private void show(String[] params) {
	   String docId = params[0];
	   if (!doesDocIdExist(docId)) {
		   System.err.println("docId " + docId + " not found");
		   return;
	   }
	   Hashtable<String, KeyWord> words = data.getWords();
	   
	   Enumeration<String> keys = words.keys();
	   while(keys.hasMoreElements()) {
		   String name = keys.nextElement();
		   if(words.get(name).getid(docId) != null) {
			   System.out.println("word: " + name);
			   System.out.print("idf: " + words.get(name).getIDF());
			   System.out.print(", tf: " + words.get(name).getid(docId).getTF());
			   System.out.println(", weight: " + words.get(name).getid(docId).getWeight());
		   }
	   }
   }
   
   private boolean doesDocIdExist(String docId) {
	   int i;
	   for (i = 0; i < data.getDocs().size(); i++) {
		   if (data.getDocs().get(i).getID().equals(docId)) {
			   i--;
			   break;
		   }
	   }
	   if (i == data.getDocs().size()) {
		   return false;
	   }
	   return true;
   }

   private void sim(String[] params) {
	   if (params.length != 2) {
		   System.err.println("Usage: sim <docId> <docId>");
		   return;
	   }

	   String docId = params[0];
	   if (!doesDocIdExist(docId)) {
		   System.err.println("docId " + docId + " not found");
		   System.err.println("reading docId " + docId + "...");
		   if (readFile(docId) != 0) return;
	   }
	   String docId2 = params[1];
	   if (!doesDocIdExist(docId2)) {
		   System.err.println("docId " + docId2 + " not found");
		   System.err.println("reading docId " + docId2 + "...");
		   if (readFile(docId2) != 0) return;
	   }
	   System.out.println("Similarity Rank: " + simScore(docId, docId2));
   }
   
   private float simScore(String docId, String docId2) {
	   Hashtable<String, KeyWord> words = data.getWords();
	   
	   int sim = 0;
	   Enumeration<String> keys = words.keys();
	   while(keys.hasMoreElements()) {
		   String name = keys.nextElement();
		   if (words.get(name).getid(docId) != null && words.get(name).getid(docId2) != null) {
			   sim++;
		   } else if (words.get(name).getid(docId) == null && words.get(name).getid(docId2) == null) {
			   sim++;
		   }
	   }
	   
	   return (float)sim / words.keySet().size();
   }

   private void searchDoc(String[] params) {
	   String docId = params[1];
	   if (!doesDocIdExist(docId)) {
		   System.err.println("docId " + docId + " not found");
		   System.err.println("reading docId " + docId + "...");
		   if (readFile(docId) != 0) return;
	   }
	   
	   ArrayList<Float> simScores = new ArrayList<Float>();
	   
	   int dontPrint = -1;
	   for (int i = 0; i < data.getDocs().size(); i++) {
		   simScores.add(simScore(docId, data.getDocs().get(i).getID()));
		   if (docId.equals(data.getDocs().get(i).getID())) {
			   dontPrint = i;
		   }
	   }
	   
	   System.out.println("Similarity Scores for " + docId);
	   for (int i = 0; i < simScores.size(); i++) {
		   if (i == dontPrint) continue;
		   System.out.println(simScores.get(i) + " : " + data.getDocs().get(i).getID());
	   }
	   
   }

   private void search(String[] params) {
	   String query = "";
	   for (int i = 0; i < params.length; i++) {
		   query += params[i] + " ";
	   }
	   query = query.substring(1, query.length() - 1);
	   readString(query);
	   searchDoc(new String[] {"doc", "query"});
   }
   
   private void quit() throws IOException {
      File config = new File("config.txt");
      data.writeTextFromFile(config);
      programIsRunning = false;
   }
}
