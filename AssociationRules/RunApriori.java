
public class RunApriori {
	
	public static void main(String[] args) {
		CSV data = null;
		if (args.length >= 0) {
			data = new CSV("data/1000/1000-out1.csv");
		} else {
			System.err.println("Usage: RunApriori fileName minSup minConf");
			System.exit(1);
		}
		
		Apriori apriori = new Apriori();
<<<<<<< HEAD
		apriori.run(data, 0.000000000000000001, 0);
=======
		apriori.run(data, 0.03, 0);
>>>>>>> changed the name of a method
	}
}
