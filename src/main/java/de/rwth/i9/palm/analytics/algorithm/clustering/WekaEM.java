package de.rwth.i9.palm.analytics.algorithm.clustering;

import weka.clusterers.EM;
import weka.core.Instances;

public class WekaEM
{
	private static EM em = new EM();

	static public EM run( int k, Instances data ) throws Exception
	{
		em.setNumClusters( k );
		em.buildClusterer( data );
		em.setMaximumNumberOfClusters( 10 );
		em.setSeed( 10 );
		em.setMinStdDev( 10 );
		em.setNumKMeansRuns( 10 );
		return em;
	}
}
