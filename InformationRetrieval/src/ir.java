
public class ir {

	public static void main(String[] args) {
		new ir();
	}
	
	public ir() {
		displayStartup();
	}

	public void displayStartup() {
		System.out.println("IR System Version 1.0\n");
	}
	
	public void runCommand(String command, String[] params) {
		if (command.toUpperCase().equals("READ")) {
			read(params);
		} else if (command.toUpperCase().equals("READ LIST")) {
			readList(params);
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
		} else if (command.toUpperCase().equals("SEARCH DOC")) {
			searchDoc(params);
		} else if (command.toUpperCase().equals("SEARCH")) {
			search(params);
		} else if (command.toUpperCase().equals("QUIT")) {
			quit();
		} else {
			System.err.println("Command does not exist");
		}
	}
	
	public void read(String[] params) {
		//TODO stub method
	}
	
	public void readList(String[] params) {
		//TODO stub method
	}
	
	public void list() {
		//TODO stub method
	}
	
	public void clear() {
		//TODO stub method
	}
	
	public void print(String[] params) {
		//TODO stub method
	}
	
	public void show(String[] params) {
		//TODO stub method
	}
	
	public void sim(String[] params) {
		//TODO stub method
	}
	
	public void searchDoc(String[] params) {
		//TODO stub method
	}
	
	public void search(String[] params) {
		//TODO stub method
	}
	
	public void quit() {
		//TODO stub method
	}
}
