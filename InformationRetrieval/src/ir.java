import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;


public class ir {
	
	boolean programIsRunning;
	Scanner sc;
	Tools data;
	
	public static void main(String[] args) {
		new ir();
	}
	
	public ir() {
		data = new Tools();
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
	
	private void runProgram() {
		while (programIsRunning) {
			System.out.print("IR>");
			String[] fullCommand = sc.nextLine().split(" ");
			runCommand(fullCommand);
		}
	}
	
	private void runCommand(String[] fullCommand) {
		String command = fullCommand[0];
		String[] params = getParams(fullCommand);
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
         if(words.containsKey(name)) {
            words.get(name).addID(params[0], data.termFrequency(name, file), 0);
            words.get(name).setidf(data.idf(name));
            words.get(name).getid(params[0]).setweight(data.weight(name, params[0]));
         }
         else {
            words.put(name, new KeyWord(new ArrayList<Document>(), 0));
            words.get(name).addID(params[0], data.termFrequency(name, file), 0);
            words.get(name).setidf(data.idf(name));;
            words.get(name).getid(params[0]).setweight(data.weight(name, params[0]));
         }
      }
      data.setWords(words);
		System.out.println("Num words " + data.getWords().keySet().size());
		System.out.println("READ " + printParams(params));
	}
	
	private void readList(String[] params) {
		//TODO stub method
		System.out.println("READ LIST" + printParams(params));
	}
	
	private void list() {
		//TODO stub method
		System.out.println("LIST");
	}
	
	private void clear() {
		//TODO stub method
		System.out.println("CLEAR");
	}
	
	private void print(String[] params) {
		//TODO stub method
		System.out.println("PRINT " + printParams(params));
	}
	
	private void show(String[] params) {
		String docId = params[0];
		
		//System.out.println("For docId: " + docId);
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
	
	private void quit() {
		programIsRunning = false;
	}
}
