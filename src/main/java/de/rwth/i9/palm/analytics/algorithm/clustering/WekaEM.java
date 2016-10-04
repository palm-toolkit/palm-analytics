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

	String[] options = new String[4];

	public EM run( Instances data ) throws Exception
	{
		// need to check combinations
		clusterer.setMaximumNumberOfClusters( 10 );
		clusterer.setMinStdDev( 0.8 );
		// clusterer.setSeed( 4 );
		// clusterer.setMinLogLikelihoodImprovementCV( 1.5 );
		clusterer.setMaxIterations( 10 );
		clusterer.buildClusterer( data );
		return clusterer;
	}


}
