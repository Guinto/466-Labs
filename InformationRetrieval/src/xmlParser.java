import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class xmlParser {

	private String parsedFile;
	
	public static void main(String[] args) {
		xmlParser xml = new xmlParser(new File("data/Jokes.xml"));
		xml.runPlainTextReader();
	}
	
	public String getParsedFile() {
		return parsedFile;
	}
	
	public xmlParser(File file) {
		parsedFile = "";
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
				//System.out.println("\n********* JOKE#" + jokeNum + " ************\n");
			} else {
				//System.out.println(line);
				parsedFile += line + "\n";
			}
		}
		
		sc.close();
	}
	
	public void runPlainTextReader() {
		PlainTextReader ptr = new PlainTextReader(parsedFile, true);
		System.out.println("num Uniques " + ptr.getNumUniqueWords());
		System.out.println("num Uniques " + ptr.getNumWords());
	}
	

}
