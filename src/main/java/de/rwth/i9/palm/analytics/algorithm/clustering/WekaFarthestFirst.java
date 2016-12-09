package de.rwth.i9.palm.analytics.algorithm.clustering;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import weka.clusterers.FarthestFirst;
import weka.core.Instances;

@Component
@Transactional
public class WekaFarthestFirst
{
	private static FarthestFirst clusterer = new FarthestFirst();

	String[] options = new String[4];

	public FarthestFirst run( String seedVal, String noOfClustersVal, Instances data ) throws Exception
	{
		// clusterer.setEpsilon( 8 );
		// clusterer.setMinPoints( 0 );
		// clusterer.setDatabase_Type(
		// "weka.clusterers.forOPTICSAndDBScan.Databases.SequentialDatabase" );
		// clusterer.setDatabase_distanceType(
		// "weka.clusterers.forOPTICSAndDBScan.DataObjects.ManhattanDataObject"
		// );
		// clusterer.setEpsilon( 4.0 );
		// clusterer.setMinPoints( 0 );
		clusterer.setSeed( Integer.parseInt( seedVal ) );
		clusterer.setNumClusters( Integer.parseInt( noOfClustersVal ) );
		clusterer.buildClusterer( data );
		// System.out.println( data );
		//
		// ClusterEvaluation clev = new ClusterEvaluation();
		// clev.setClusterer( clusterer );
		// clev.evaluateClusterer( data );
		// System.out.println( "\n\nno of clev clusters: " +
		// clev.getNumClusters() );
		return clusterer;
	}


}
