package de.rwth.i9.palm.analytics.algorithm.clustering;

import java.util.Map;

import de.rwth.i9.palm.persistence.PersistenceStrategy;

public interface Clustering
{
	public Map<String, Integer> clusterPublications( PersistenceStrategy persistenceStrategy, String algorithm, String relatedObjectId, String relatedObjectType ) throws Exception;
}
