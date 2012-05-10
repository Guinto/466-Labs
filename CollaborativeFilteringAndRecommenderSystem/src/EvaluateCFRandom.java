
public class EvaluateCFRandom {

	
	public static void main(String[] args) {
		CSV data = null;
		if (args.length == 0) {
			data = new CSV("jester-data-1.csv");
			for (Vector v : data.getVectors()) {
				System.out.println("size: " + v.size());
			}
		} else {
			System.err.println("Usage: HClustering <fileName> [<threshold>]");
			System.exit(1);
		}
	}

}
