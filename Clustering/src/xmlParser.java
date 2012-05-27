import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class xmlParser {

	public static void main(String[] args) {
		new xmlParser(new File("AccidentDataReport.xml"));
	}
	
	public xmlParser(File file) {
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
		int nodesDeep = 0;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.contains("<node")) {
				if (nodesDeep == 0) {
					System.out.println(line);
				}
				nodesDeep++;
			} else if (line.contains("</node")) {
				nodesDeep--;
			} else if (line.contains("<leaf")) {
				line = line.substring(13, line.length() - 17);
				System.out.println(line);
			}
		}
		
		sc.close();
	}
	

}
