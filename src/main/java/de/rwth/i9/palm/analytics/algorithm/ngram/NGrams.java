package de.rwth.i9.palm.analytics.algorithm.ngram;

/**
 * author piro
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeSet;

import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.types.IDSorter;
import cc.mallet.types.InstanceList;

public interface NGrams
{
	/**
	 * 
	 * @param path
	 * @param purpose
	 * @param specify
	 * @return
	 * @throws IOException
	 */
	public InstanceList getInstanceData( String path, String purpose, String specify ) throws IOException;
	
	
	/**
	 * 
	 * @param numTopics
	 * @return
	 */
	public TopicalNGrams setNumberTopics( int numTopics );
	
	/**
	 * 
	 * @return number of topics existing in the model
	 */
	public int getNumTopics();

	/**
	 * 
	 * @return the number of instances of the model 1 instance created for each
	 *         document
	 */
	public int getNumIntances();

	/**
	 * 
	 * @param path
	 * @param purpose
	 * @param specify
	 * @param numTopics
	 * @return
	 * @throws IOException
	 */
	public TopicalNGrams createModel(String path, String purpose, String specify, int numTopics) throws IOException;

	/**
	 * 
	 * @param m
	 * @param path
	 * @param purpose
	 * @param specify
	 */
	public void printDocTopicprobs(TopicalNGrams m,String path, String purpose, String specify);
		
	/**
	 *  
	 * @param m
	 * @param threshold
	 * @param max
	 * @param weight
	 * @return
	 */
	public String[] getStringDocumentTopicIndex (TopicalNGrams m, double threshold, int max, boolean weight);
	
	/**
	 *  
	 * @param m
	 * @param threshold
	 * @param max
	 * @param weight
	 * @return
	 */
	public List<String> getListDocumentTopicIndex (TopicalNGrams m, double threshold, int max, boolean weight);
	
	/**
	 * 
	 * @param m
	 * @return
	 */
	public HashMap<String, List<Double>> getDoumentTopicProportion();
	
	/**
	 * 
	 * @param m
	 * @param nwords
	 * @param weight
	 * @return
	 */
	public String[] getStringTopicsUnigrams (TopicalNGrams m, int nwords, boolean weight);

	/**
	 * 
	 * @param m
	 * @param nwords
	 * @param weight
	 * @return
	 */
	public List<String> getListTopicsUnigrams (TopicalNGrams m, int nwords, boolean weight);
	
	/**
	 *  
	 * @param m
	 * @param nwords
	 * @param weight
	 * @return
	 */
	public String[] getStringTopicsNgrams (TopicalNGrams m, int nwords, boolean weight);

	/**
	 * 
	 * @param m
	 * @param nwords
	 * @param weight
	 * @return
	 */
	public List<String> getListTopicsNgrams (TopicalNGrams m, int nwords, boolean weight);	
		
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public  File createTempDirectory() throws IOException;

	/**
	 * 
	 * @param path
	 * @param purpose
	 */
	public void getRandomTrainerFiles(String path, String purpose);

	/**
	 * 
	 * @param path
	 * @param purpose
	 * @param specify
	 * @throws IOException
	 */
	public void DocTopicMapper(String path, String purpose, String specify) throws IOException;
		
	/**
	 *  
	 * @param m
	 * @param docID
	 * @param max
	 * @param threshold
	 * @param numTopics
	 * @param numWords
	 * @param weight
	 * @return
	 */
	public HashMap<String, List<String>> getTopicNgramsDocument( int docID, int max, double threshold, int numTopics, int numWords, boolean weight );

	/**
	 *  
	 * @param m
	 * @param docID
	 * @param max
	 * @param threshold
	 * @param numTopics
	 * @param numWords
	 * @param weight
	 * @return
	 */
	public HashMap<String, List<String>> getTopicUnigramsDocument( int docID, int max, double threshold, int numTopics, int numWords, boolean weight );
	
	/**
	 * 
	 * @param m
	 * @param docID
	 * @param max
	 * @param threshold
	 * @param numTopics
	 * @return
	 */
	public HashMap<String, List<String>> getAllDocumentTopics( ParallelTopicModel m, int docID, int max, double threshold, int numTopics );
	
	/**
	 * 
	 * @param m
	 * @param numTopics
	 * @return
	 */
	public ArrayList<TreeSet<IDSorter>> getSortedWords( ParallelTopicModel m, int numTopics );
	
	/**
	 * 
	 * @param tng
	 * @param choise
	 * @return
	 */
	public HashMap<String, List<String>> calculateSimilarity( int choise, int maxresult );
	
	/**
	 *  
	 * @param tng
	 * @return
	 */
	public HashMap<String, List<String>> recommendSimilar( int maxresult );

	/**
	 * 
	 * @param id
	 * @param maxresult
	 * @return
	 */
	public List<String> recommendedEntity( String id, int maxresult );

	/**
	 * 
	 * @param m
	 * @param max
	 * @param threshold
	 * @param numTopics
	 * @param numWords
	 * @param weight
	 * @return
	 */
	public LinkedHashMap<String, LinkedHashMap<String, Double>> getDocumentTopicDetailMap( TopicalNGrams m, int max, double threshold, int numTopics, int numWords, boolean weight );

	/**
	 * 
	 * @param m
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int maptoRealDatabaseID( String id ) throws Exception;

}
