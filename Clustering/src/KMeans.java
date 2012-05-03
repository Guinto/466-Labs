import java.util.ArrayList;


public class KMeans {

   public static void main(String[] args) {
      CSV data = null;
      if (args.length != 2) {
         data = new CSV("lab4data/birth_death_rate.csv");
         System.out.println(data);

         new KMeans(data, Integer.parseInt(args[0]));
      } else {
         System.err.println("Usage: KMeans <fileName> <k>");
         System.exit(1);
      }
   }

   public KMeans(CSV data, int numClusters) {
      ArrayList<Vector> centroids = selectInitialCentroids(data.vectors.size(), numClusters, data);
      ArrayList<ArrayList<Vector>> clusters = new ArrayList<ArrayList<Vector>>();
      int[] numPointsInCluster = new int[numClusters];


      for (int i = 0; i < numClusters; i++) {
         numPointsInCluster[i] = 0;
         clusters.add(new ArrayList<Vector>());
      }

      for(Vector v: data.vectors) {
         clusters.get(findClosest(v, centroids)).add(v);
      }
      
      for(int i = 0; i < numClusters; i++) {
         centroids.set(i, findAvg(clusters.get(i)));
      }


   }

   private ArrayList<Vector> selectInitialCentroids(int dataSize, int numClusters, CSV data) {
      ArrayList<Integer> points = new ArrayList<Integer>();
      ArrayList<Vector> centroids = new ArrayList<Vector>();
      for (int i = 0; i < numClusters; i++) {
         points.add((int)Math.floor(Math.random() * dataSize));
      }

      for(int i = 0; i < numClusters; i++) {
         centroids.add(data.vectors.get(points.get(i)));
      }

      return centroids;
   }

   private int findClosest(Vector v, ArrayList<Vector> centroids) {
      double[] distances = new double[centroids.size()];
      int max = 0;

      for(int i = 0; i < centroids.size(); i++) {
         distances[i] = v.getEucledianDistance(centroids.get(i));
      }

      for(int i = 1; i < centroids.size(); i++) {
         if(distances[max] < distances[i])
            max = i;
      }

      return max;
   }

   private Vector findAvg(ArrayList<Vector> cluster) {
      float[] vector = new float[cluster.size()];
      Vector answer = new Vector();

      for(int i = 0; i < cluster.size(); i++) {
         for(int j = 0; j < cluster.get(i).size(); j++) {
            vector[j] += cluster.get(i).get(j);
         }
      }

      for(int i = 0; i < vector.length; i++) {
         vector[i] = vector[i]/cluster.size();
         answer.add(vector[i]);
      }

      return answer;
   }
}
