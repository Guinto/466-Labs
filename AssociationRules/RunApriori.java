
public class RunApriori {
	
	public static void main(String[] args) {
		CSV data = null;
		if (args.length >= 0) {
			data = new CSV("data/1000/1000i.csv");
		} else {
			System.err.println("Usage: RunApriori fileName minSup minConf");
			System.exit(1);
		}
		
		Apriori apriori = new Apriori();
		apriori.run(data, 0.050, 0);
	}
}
