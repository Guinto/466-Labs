import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class xmlParser {

	private String parsedFile;
		
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
	
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.contains("<") && line.contains(">")) {
				continue;
			} else {
				parsedFile += line + "\n";
			}
		}
		
		sc.close();
	}	

}
