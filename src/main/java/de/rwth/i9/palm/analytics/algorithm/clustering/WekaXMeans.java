package de.rwth.i9.palm.analytics.algorithm.clustering;

import weka.clusterers.XMeans;
import weka.core.EuclideanDistance;
import weka.core.Instances;

public class WekaXMeans
{
	private static XMeans xmeans = new XMeans();

	static public XMeans run( int k, Instances data ) throws Exception
	{
		xmeans.setMinNumClusters( 5 );
		xmeans.setMaxNumClusters( 20 );
		xmeans.setSeed( k );
		xmeans.setUseKDTree( true );
		xmeans.setMaxKMeans( 100 );
		xmeans.setDistanceF( new EuclideanDistance() );
		xmeans.buildClusterer( data );
		xmeans.postExecution();
		return xmeans;
	}
}
