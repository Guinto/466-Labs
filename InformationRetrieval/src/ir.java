import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ir {
	
	boolean programIsRunning;
	Scanner sc;

	public static void main(String[] args) {
		new ir();
	}
	
	public ir() {
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
		//TODO stub method
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
		//TODO stub method
		System.out.println("SHOW " + printParams(params));
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
