package de.rwth.i9.palm.analytics.algorithm.clustering;

import weka.clusterers.XMeans;
import weka.core.Instances;

public class WekaXMeans
{
	private static XMeans xmeans = new XMeans();

	static public XMeans run( String seedVal, String noOfClustersVal, String iterationsVal, Instances data ) throws Exception
	{
		xmeans.setMinNumClusters( Integer.parseInt( noOfClustersVal ) );
		xmeans.setMaxNumClusters( 20 );
		xmeans.setSeed( Integer.parseInt( seedVal ) );
		xmeans.setMaxIterations( Integer.parseInt( iterationsVal ) );
		xmeans.buildClusterer( data );
		return xmeans;
	}
}
