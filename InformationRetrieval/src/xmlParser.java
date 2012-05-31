import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class xmlParser {

	public static void main(String[] args) {
		new xmlParser(new File("data/Jokes.xml"));
	}
	
	public xmlParser(File file) {
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
		int jokeNum = 0;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.contains("<jokes>") || line.contains("</joke")) {
				continue;
			} else if (line.contains("<joke>")) {
				jokeNum++;
				System.out.println("\n********* JOKE#" + jokeNum + " ************\n");
			} else {
				System.out.println(line);
			}
		}
		
		sc.close();
	}
	

}
