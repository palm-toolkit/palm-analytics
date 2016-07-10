package de.rwth.i9.palm.analytics.algorithm.clustering;

import weka.clusterers.SimpleKMeans;
import weka.core.EuclideanDistance;
import weka.core.Instances;

public class WekaSimpleKMeans
{
	private static SimpleKMeans kmeans = new SimpleKMeans();

	static public SimpleKMeans run( int k, Instances data ) throws Exception
	{
		// kmeans.setSeed( 10 );
		kmeans.setPreserveInstancesOrder( true );
		kmeans.setNumClusters( k );
		// kmeans.setMaxIterations( 100 );
		kmeans.buildClusterer( data );
		kmeans.setPreserveInstancesOrder( true );
		kmeans.setDistanceFunction( new EuclideanDistance() );
		kmeans.setFastDistanceCalc( true );
		return kmeans;
	}
}
