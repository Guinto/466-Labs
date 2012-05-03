import java.util.ArrayList;


public class KMeans {

	public static void main(String[] args) {
		CSV data = null;
		if (args.length == 2) {
			data = new CSV("lab4data/birth_death_rate.csv");
			System.out.println(data);
			
			new KMeans(data, Integer.parseInt(args[1]));
		} else {
			System.err.println("Usage: KMeans <fileName> <k>");
			System.exit(1);
		}
	}
	
	public KMeans(CSV data, int numClusters) {
		ArrayList<Integer> centroids = selectInitialCentroids(data.vectors.size(), numClusters);
		ArrayList<Vector> clusters = new ArrayList<Vector>();
		int[] numPointsInCluster = new int[numClusters];
		
		for (int i = 0; i < numClusters; i++) {
			numPointsInCluster[i] = 0;
		}
		
		
	}
	
	private ArrayList<Integer> selectInitialCentroids(int dataSize, int numClusters) {
		ArrayList<Integer> centroids = new ArrayList<Integer>();
		for (int i = 0; i < numClusters; i++) {
			centroids.add((int)Math.floor(Math.random() * dataSize));
		}
		
		return centroids;
	}
}
