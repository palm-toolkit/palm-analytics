package de.rwth.i9.palm.analytics.algorithm.clustering;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

public class WekaSimpleKMeans
{
	private static SimpleKMeans kmeans = new SimpleKMeans();

	static public SimpleKMeans run( int k, Instances data ) throws Exception
	{
		kmeans.setSeed( 10 );
		kmeans.setPreserveInstancesOrder( true );
		kmeans.setNumClusters( k );
		kmeans.buildClusterer( data );
		return kmeans;
	}
}
