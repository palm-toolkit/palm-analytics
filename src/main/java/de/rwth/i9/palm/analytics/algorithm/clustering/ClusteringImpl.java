package de.rwth.i9.palm.analytics.algorithm.clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.rwth.i9.palm.model.DataMiningPublication;
import de.rwth.i9.palm.model.PublicationTopic;
import de.rwth.i9.palm.persistence.PersistenceStrategy;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;

public class ClusteringImpl implements Clustering
{

	public Map<DataMiningPublication, Integer> clusterPublications( PersistenceStrategy persistenceStrategy, String algorithm, String relatedObjectId, String relatedObjectType ) throws Exception
	{
		Map<DataMiningPublication, Integer> resultMap = new HashMap<DataMiningPublication, Integer>();

		List<DataMiningPublication> publications;
		if ( relatedObjectId != null && relatedObjectType != null )
		{
			switch ( relatedObjectType ) {
			case "event":
				publications = persistenceStrategy.getPublicationDAO().getDataMiningObjects();
				// publications =
				// persistenceStrategy.getPublicationDAO().getPublicationByEventId(
				// relatedObjectId );
				break;
			case "author":
				publications = persistenceStrategy.getPublicationDAO().getDataMiningObjects();
				// publications =
				// persistenceStrategy.getPublicationDAO().getPublicationByAuthorId(
				// relatedObjectId );
				break;
			default:
				publications = persistenceStrategy.getPublicationDAO().getDataMiningObjects();
				// publications =
				// persistenceStrategy.getPublicationDAO().getPublicationByEventId(
				// relatedObjectId );
				break;
			}
		}
		else
		{
			publications = persistenceStrategy.getPublicationDAO().getDataMiningObjects();
		}

		if ( publications.size() > 0 )
		{
			System.out.println( "Clustering Publications" );
			Map<String, Attribute> attributeMap = new HashMap<String, Attribute>();
			Set<String> uniqueTerms = new HashSet<String>();

			ArrayList<Attribute> attributes = new ArrayList<Attribute>();
			for ( DataMiningPublication p : publications )
			{
				for ( PublicationTopic pt : p.getPublicationTopics() )
				{
					uniqueTerms.addAll( pt.getTermValues().keySet() );
				}
			}

			for ( String term : uniqueTerms )
			{
				Attribute a = new Attribute( term );
				attributes.add( a );
				attributeMap.put( term, a );
			}

			System.out.println( "Built attributes" );

			Attribute citedBy = new Attribute( "citedBy" );
			attributes.add( citedBy );

			Instances data = new Instances( "publications", attributes, publications.size() );

			for ( DataMiningPublication p : publications )
			{
				Instance i = new SparseInstance( attributes.size() );

				for ( String term : attributeMap.keySet() )
				{
					Attribute a = attributeMap.get( term );
					double value = 0;
					for ( PublicationTopic pt : p.getPublicationTopics() )
					{
						if ( pt.getTermValues().keySet().contains( term ) )
						{
							value = pt.getTermValues().get( term );
						}
					}
					System.out.println( p.getId() + ", " + term + ", " + value );
					i.setValue( a, value );
				}
				data.add( i );
			}

			if ( algorithm.equals( "kmeans" ) )
			{
				SimpleKMeans result = WekaSimpleKMeans.run( 4, data );
				int i = 0;
				for ( int clusterNum : result.getAssignments() )
				{
					resultMap.put( publications.get( i ), clusterNum );
					i++;
				}
			}
		}

		return resultMap;
	}

}
