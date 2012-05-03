import java.util.ArrayList;


public class KMeans {

   public static void main(String[] args) {
      CSV data = null;
      if (args.length != 2) {
         data = new CSV("lab4data/birth_death_rate.csv");
         System.out.println(data);

         new KMeans(data, 4);
      } else {
         System.err.println("Usage: KMeans <fileName> <k>");
         System.exit(1);
      }
   }

   public KMeans(CSV data, int numClusters) {
      ArrayList<Vector> centroids = selectInitialCentroids(data.vectors.size(), numClusters, data);
      ArrayList<ArrayList<Vector>> clusters = new ArrayList<ArrayList<Vector>>();
      int[] numPointsInCluster = new int[numClusters];
      boolean end = false;
      int clustChange = 0;
      int pointChange = 0;
      int sse = 0;
      int prevsse = 0;
      int close;
      Vector avg;

      for (int i = 0; i < numClusters; i++) {
         numPointsInCluster[i] = 0;
         clusters.add(new ArrayList<Vector>());
      }

      for(Vector v: data.vectors) {
         clusters.get(findClosest(v, centroids)).add(v);
      }

      for(int i = 0; i < numClusters; i++) {
         centroids.set(i, findAvg(clusters.get(i), data.vectors.get(0).size()));
      }


      for(int i = 0; i < numClusters; i++) {
         prevsse += findSSE(clusters.get(i));
      }
      
      while(end == false) {
         clustChange = 0;
         pointChange = 0;
         for(int i = 0; i < clusters.size(); i++) {
            for(int j = 0; j < clusters.get(i).size(); j++) {
               close = findClosest(clusters.get(i).get(j), centroids);
               if(close != i) {
                  clusters.get(close).add(clusters.get(i).remove(j));
                  pointChange++;
               }
            }
         }

         for(int i = 0; i < numClusters; i++) {
            avg = findAvg(clusters.get(i), data.vectors.get(0).size());
            if(!centroids.get(i).equals(avg)) {
               centroids.set(i, avg);
               clustChange++;
            }
         }
         
         for(int i = 0; i < numClusters; i++) {
            sse += findSSE(clusters.get(i));
         }

         if(clustChange == 0 || pointChange == 0)
            end = true;
         if(Math.abs(prevsse - sse) <= 100)
            end = true;
         prevsse = sse;
      }

      for(int i = 0; i < numClusters; i++) {
         System.out.println("Number of points in Cluster: " + clusters.get(i).size());
         System.out.println("Centroid: " + centroids.get(i));
         System.out.println("Min Distance " + findMinDist(clusters.get(i), centroids.get(i)));
         System.out.println("Max Distance " + findMaxDist(clusters.get(i), centroids.get(i)));
         System.out.println("Avg Distance " + findAvgDist(clusters.get(i), centroids.get(i)));
         for(int j = 0; j < clusters.get(i).size(); j++) {
            System.out.println(clusters.get(i).get(j));
         }

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
         if(distances[max] > distances[i])
            max = i;
      }

      return max;
   }
   
   private double findSSE(ArrayList<Vector> cluster) {
	   double sse = 0;
	   Vector avg = findAvg(cluster, cluster.get(0).size());
	   for (int i = 0; i < cluster.size(); i++) {
		  sse += Math.pow(cluster.get(i).getEucledianDistance(avg), 2); 
	   }
	   return sse;
   }

   private Vector findAvg(ArrayList<Vector> cluster, int size) {
      float[] vector = new float[size];
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

   private double findAvgDist(ArrayList<Vector> cluster, Vector centroid) {
      double answer = 0.0;
      for(int i = 0; i < cluster.size(); i++) {
         answer += cluster.get(i).getEucledianDistance(centroid);
      }

      return answer/cluster.size();
   }

   private double findMaxDist(ArrayList<Vector> cluster, Vector centroid) {
      double answer = cluster.get(0).getEucledianDistance(centroid);
      for(int i = 1; i < cluster.size(); i++) {
         if(answer < cluster.get(i).getEucledianDistance(centroid))
            answer = cluster.get(i).getEucledianDistance(centroid);
      }

      return answer;
   }
   private double findMinDist(ArrayList<Vector> cluster, Vector centroid) {
      double answer = cluster.get(0).getEucledianDistance(centroid);
      for(int i = 1; i < cluster.size(); i++) {
         if(answer > cluster.get(i).getEucledianDistance(centroid))
            answer = cluster.get(i).getEucledianDistance(centroid);
      }

      return answer;
   }
}
