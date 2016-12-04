package de.rwth.i9.palm.analytics.algorithm.clustering;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

@Component
@Transactional
public class WekaKMeans
{
	private static SimpleKMeans clusterer = new SimpleKMeans();

	// String[] options = new String[4];

	public static SimpleKMeans run( String seedVal, String noOfClustersVal, Instances data ) throws Exception
	{
		System.out.println();
		clusterer.setSeed( Integer.parseInt( seedVal ) );
		clusterer.setNumClusters( Integer.parseInt( noOfClustersVal ) );
		clusterer.buildClusterer( data );
		return clusterer;
	}
}
