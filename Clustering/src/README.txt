Trent Ellingsen & Thomas Wang
Input File Type: CSV Files

K-means Clustering

Usage: KMeans <Filename> <k>

Note: <k> is the number of clusters the program has to produce

We picked random initial cluster centroids. To recompute we found the average point of the cluster to find new centroids. We used no point reassignment or no centroid change for our stopping points. The SSE threshold we used was anything under 100 change. The program was implemented using Eucledean distance without dealing with outliers.

Hierarchical Clustering

Usage: hclustering <Filename> [threshold]

Note: threshold is optional and is used to cut the cluster hierarchy into smaller clusters.

We used eucledean distance as the measure for the distance between two points. We use complete-link as the method for distance measures for clusters.
