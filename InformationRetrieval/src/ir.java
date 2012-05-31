import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
      data = new Tools();
      data.readTextFromFile(new File("config.txt"));
      if (command.toUpperCase().equals("READ")) {
         if (params.length > 0 && params[0].toUpperCase().equals("List")) {
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
      PlainTextReader file = new PlainTextReader(params[0]);
      data.getDocs().add(new DocumentList(params[0], params[0]));
      Enumeration<String> keys = file.getWords().keys();
      
      Hashtable<String, KeyWord> words = data.getWords();
      
      while(keys.hasMoreElements()) {
         String name = keys.nextElement();
         if(data.getWords().containsKey(name)) {
            data.getWords().get(name).addID(params[0], data.termFrequency(name, file), 0);
            data.getWords().get(name).setidf(data.idf(name));
            for(Document d: data.getWords().get(name).getdocs()) {
               d.setweight(data.weight(name, d.getid()));
            }
         }
         else {
            words.put(name, new KeyWord(new ArrayList<Document>(), 0));
            words.get(name).addID(params[0], data.termFrequency(name, file), 0);
            words.get(name).setidf(data.idf(name));;
            words.get(name).getid(params[0]).setweight(data.weight(name, params[0]));
         }
      }
      System.out.println("READ " + printParams(params));
   }

   private void readList(String[] params) throws FileNotFoundException {
      Scanner scanner;
      int count = 0;
      scanner = new Scanner(new File(params[0]));
      while (scanner.hasNextLine()) {
         String line = scanner.nextLine();
         PlainTextReader file = new PlainTextReader(line);
         data.getDocs().add(new DocumentList(line, line + count));
         Enumeration<String> keys = file.getWords().keys();
         while(keys.hasMoreElements()) {
            String name = keys.nextElement();
            if(data.getWords().containsKey(name)) {
               data.getWords().get(name).addID(line + count, data.termFrequency(name, file), 0);
               data.getWords().get(name).setidf(data.idf(name));
               for(Document d: data.getWords().get(name).getdocs()) {
                  d.setweight(data.weight(name, d.getid()));
               }
            }
            else {
               data.getWords().put(name, new KeyWord(new ArrayList<Document>(), 0));
               data.getWords().get(name).addID(line + count, data.termFrequency(name, file), 0);
               data.getWords().get(name).setidf(data.idf(name));;
               data.getWords().get(name).getid(line + count).setweight(data.weight(name, line + count));
            }
         }
         System.out.println("READ " + printParams(params));
         count++;
      }
   }

   private void list() {
      for(DocumentList d : data.getDocs()) {
         System.out.println(d.getID());
      }
      System.out.println("LIST");
   }

   private void clear() {
      data = new Tools();
      System.out.println("CLEAR");
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
	   Hashtable<String, KeyWord> words = data.getWords();
	   System.out.println("Num words " + data.getWords().keySet().size());
	   Enumeration<String> keys = words.keys();
	   while(keys.hasMoreElements()) {
		   String name = keys.nextElement();
		   System.out.println(name);
		   if(words.containsKey(name)) {
			   System.out.println("\nword: " + name);
			   System.out.print("tf: " + words.get(name).getid(docId).getTF());
			   System.out.println(", weight: " + words.get(name).getid(docId).getWeight());
		   }
	   }
   }

   private void sim(String[] params) {
      //TODO stub method
      System.out.println("SIM " + printParams(params));
   }

   private void searchDoc(String[] params) {
      //TODO stub method
      System.out.println("SEARCH DOC" + printParams(params));
   }

   private void search(String[] params) {
      //TODO stub method
      System.out.println("SEARCH " + printParams(params));
   }

   private String printParams(String[] params) {
      String str = "";

      for (int i = 0; i < params.length; i++) {
         str += params[i] + " ";
      }

      return str;
   }

   private void quit() throws IOException {
      File config = new File("config.txt");
      data.writeTextFromFile(config);
      programIsRunning = false;
   }
}
