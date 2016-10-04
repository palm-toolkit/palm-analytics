package de.rwth.i9.palm.analytics.algorithm.clustering;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import weka.clusterers.HierarchicalClusterer;
import weka.core.Instances;
import weka.core.ManhattanDistance;

@Component
@Transactional
public class WekaHierarchichal
{
	private static HierarchicalClusterer clusterer = new HierarchicalClusterer();

	String[] options = new String[4];

	public HierarchicalClusterer run( Instances data ) throws Exception
	{
		// clusterer.setDistanceFunction( new ChebyshevDistance() );
		// clusterer.setDistanceFunction( new MinkowskiDistance() );
		clusterer.setNumClusters( 3 );
		//clusterer.setDistanceFunction( new FilteredDistance() );
		clusterer.setDistanceFunction( new ManhattanDistance() );
		clusterer.buildClusterer( data );
		return clusterer;
	}


}
