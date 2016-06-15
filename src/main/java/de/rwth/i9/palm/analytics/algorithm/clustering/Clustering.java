package de.rwth.i9.palm.analytics.algorithm.clustering;

import java.util.Map;

import de.rwth.i9.palm.model.DataMiningPublication;
import de.rwth.i9.palm.persistence.PersistenceStrategy;

public interface Clustering
{
	public Map<DataMiningPublication, Integer> clusterPublications( PersistenceStrategy persistenceStrategy, String algorithm, String relatedObjectId, String relatedObjectType ) throws Exception;
}
