
public class KMeans {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CSV data = null;
		if (args.length != 2) {
			data = new CSV("lab4data/birth_death_rate.csv");
		} else {
			System.err.println("Usage: KMeans <fileName> <k>");
			System.exit(1);
		}
		
		System.out.println(data);
	}
}
