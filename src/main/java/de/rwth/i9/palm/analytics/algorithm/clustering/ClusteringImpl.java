package de.rwth.i9.palm.analytics.algorithm.clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.rwth.i9.palm.model.DataMiningAuthor;
import de.rwth.i9.palm.model.DataMiningPublication;
import de.rwth.i9.palm.model.PublicationTopic;
import de.rwth.i9.palm.persistence.PersistenceStrategy;
import de.rwth.i9.palm.util.InterestParser;
import weka.clusterers.EM;
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
			String[] objectIds = relatedObjectId.split(",");
			switch ( relatedObjectType ) {
			case "event":
				publications = persistenceStrategy.getPublicationDAO().getDataMiningObjectsByEvent( objectIds );
				break;
			case "author":
				publications = persistenceStrategy.getPublicationDAO().getDataMiningObjectsByAuthor( objectIds );
				break;
			case "circle":
				publications = persistenceStrategy.getPublicationDAO().getDataMiningObjectsByCircle( objectIds );
				break;
			default:
				publications = persistenceStrategy.getPublicationDAO().getDataMiningObjects();
				break;
			}
		}
		else
		{
			publications = persistenceStrategy.getPublicationDAO().getDataMiningObjects();
		}

		if ( publications.size() > 0 )
		{
			System.out.println( "Clustering " + publications.size() + " Publications" );
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

	public Map<DataMiningAuthor, Integer> clusterAuthors( PersistenceStrategy persistenceStrategy, String algorithm, String relatedObjectId, String relatedObjectType, String algorithmParameters ) throws Exception
	{
		Map<DataMiningAuthor, Integer> resultMap = new HashMap<DataMiningAuthor, Integer>();

		List<DataMiningAuthor> authors;
		if ( relatedObjectId != null && relatedObjectType != null )
		{
			String[] objectIds = relatedObjectId.split( "," );
			switch ( relatedObjectType ) {
			case "event":
				authors = persistenceStrategy.getAuthorDAO().getDataMiningObjectsByEvent( objectIds );
				break;
			case "publication":
				authors = persistenceStrategy.getAuthorDAO().getDataMiningObjectsByPublication( objectIds );
				break;
			case "circle":
				authors = persistenceStrategy.getAuthorDAO().getDataMiningObjectsByCircle( objectIds );
				break;
			default:
				authors = persistenceStrategy.getAuthorDAO().getDataMiningObjects();
				break;
			}
		}
		else
		{
			authors = persistenceStrategy.getAuthorDAO().getDataMiningObjects();
		}

		if ( authors.size() > 0 )
		{
			System.out.println( "Clustering " + authors.size() + " Authors" );
			Map<String, Attribute> attributeMap = new HashMap<String, Attribute>();
			Map<String, Map<String, Double>> interestsMap = new HashMap<String, Map<String, Double>>();
			Set<String> uniqueTerms = new HashSet<String>();

			ArrayList<Attribute> attributes = new ArrayList<Attribute>();
			for ( DataMiningAuthor author : authors )
			{
				interestsMap.put( author.getId(), InterestParser.parseInterestString( author.getAuthor_interest_flat().getInterests() ) );
				uniqueTerms.addAll( interestsMap.get( author.getId() ).keySet() );
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

			Instances data = new Instances( "publications", attributes, authors.size() );

			for ( DataMiningAuthor author : authors )
			{
				Instance i = new SparseInstance( attributes.size() );
				i.setValue( citedBy, author.getCitedBy() );

				for ( String term : attributeMap.keySet() )
				{
					Attribute a = attributeMap.get( term );
					double value = 0;

					if ( interestsMap.get( author.getId() ).containsKey( term ) )
					{
						value = interestsMap.get( author.getId() ).get( term );
					}

					i.setValue( a, value );
				}
				data.add( i );
			}

			System.out.println( "Filled attributes" );

			if ( algorithm.equals( "kmeans" ) )
			{
				int k = Integer.parseInt( algorithmParameters );
				SimpleKMeans result = WekaSimpleKMeans.run( k, data );
				int i = 0;
				for ( int clusterNum : result.getAssignments() )
				{
					resultMap.put( authors.get( i ), clusterNum );
					i++;
				}
			}
			else if ( algorithm.equals( "em" ) )
			{
				EM result = WekaEM.run( data );
				System.out.println( result.getNumClusters() );
			}
		}

		return resultMap;
	}

}
