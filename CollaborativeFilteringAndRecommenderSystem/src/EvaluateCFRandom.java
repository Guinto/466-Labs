
public class EvaluateCFRandom {

	
	public static void main(String[] args) {
		if (args.length == 0) {
			new Filter(new CSV("jester-data-1.csv"));
		} else {
			System.err.println("Usage: EvaluateCFRandom Method Size");
			System.exit(1);
		}
	}

}
