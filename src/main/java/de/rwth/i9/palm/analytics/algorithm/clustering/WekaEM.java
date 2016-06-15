package de.rwth.i9.palm.analytics.algorithm.clustering;

import weka.clusterers.EM;
import weka.core.Instances;

public class WekaEM
{
	private static EM eM = new EM();

	static public EM run( Instances data ) throws Exception
	{
		eM.buildClusterer( data );
		return eM;
	}
}
