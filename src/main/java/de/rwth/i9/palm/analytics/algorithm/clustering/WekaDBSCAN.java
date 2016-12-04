package de.rwth.i9.palm.analytics.algorithm.clustering;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import weka.clusterers.DBSCAN;
import weka.core.Instances;

@Component
@Transactional
public class WekaDBSCAN
{
	private static DBSCAN clusterer = new DBSCAN();

	String[] options = new String[4];

	public DBSCAN run( Instances data ) throws Exception
	{
		// clusterer.setEpsilon( 8 );
		// clusterer.setMinPoints( 0 );
		// clusterer.setDatabase_Type(
		// "weka.clusterers.forOPTICSAndDBScan.Databases.SequentialDatabase" );
		// clusterer.setDatabase_distanceType(
		// "weka.clusterers.forOPTICSAndDBScan.DataObjects.ManhattanDataObject"
		// );
		clusterer.setEpsilon( 8.0 );
		clusterer.setMinPoints( 0 );
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
