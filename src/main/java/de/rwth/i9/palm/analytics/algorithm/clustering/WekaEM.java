package de.rwth.i9.palm.analytics.algorithm.clustering;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import weka.clusterers.EM;
import weka.core.Instances;

@Component
@Transactional
public class WekaEM
{
	private static EM clusterer = new EM();

	// String[] options = new String[4];

	public EM run( String seedVal, String foldsVal, String iterationsVal, Instances data ) throws Exception
	{
		System.out.println( "folds: " + foldsVal );
		clusterer.setMaximumNumberOfClusters( 20 );
		clusterer.setSeed( Integer.parseInt( seedVal ) );
		clusterer.setMaxIterations( Integer.parseInt( iterationsVal ) );
		clusterer.setNumFolds( Integer.parseInt( foldsVal ) );
		clusterer.buildClusterer( data );
		return clusterer;
	}

}
