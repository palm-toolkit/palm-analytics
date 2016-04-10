package de.rwth.i9.palm.analytics.algorithm.topicModelingClustering;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class kMeansClustering
{
	public int numClusters; // number of centroids is the same as this one
	public int numDimenstions; // equivalent to the number of topics
	public double[][] dataset; // holds all the data points (N. docs x N.
								// topics)
	public double[][] centroids; // holds informations regarding centroids
									// (N.clusters x N. topics)
	public int[][] clusters; // holds values 0/1 to show the cluster assignment
								// for each doc (N. clusters x N. docs)

	public void addInstances( HashMap<String, List<Double>> documentsTopicDistribution, int numDocuments, int numTopics )
	{
		dataset = new double[numDocuments][numTopics];
		int i = 0;
		for (Entry<String, List<Double>> document : documentsTopicDistribution.entrySet()){
			int j = 0;
			for ( double distribution : document.getValue() )
			{
				dataset[i][j++] = distribution;
			}
			i++;
		}
	}

}
