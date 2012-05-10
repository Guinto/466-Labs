
public class EvaluateCFList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			new Filter(new CSV("jester-data-1.csv"));
		} else {
			System.err.println("Usage: EvaluateCFList Method FileName");
			System.exit(1);
		}
	}

}
