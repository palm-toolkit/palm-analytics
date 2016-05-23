package de.rwth.i9.palm.analytics.algorithm.ngram;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.junit.Test;

import cc.mallet.types.InstanceList;
import cc.mallet.util.Randoms;


public class Ngram implements NGrams
{
	public String path = "C:/Users/Piro/Desktop/";
	// c442983a-0099-4d6d-89b1-6cfc57fa6138
	public TopicalNGrams tng;// = useTrainedData( path, "Author-Test", "TEST1",
								// 10 );
	// public TopicalNGrams temporaltng;

	// public Ngram( String purpose, String specify, int numTopics )
	// {
	// if (purpose == "Years"){
	// temporaltng = createTemporalModel( path, numTopics );
	// }
	// else
	// {
	// tng = createNormalModel( path, purpose, specify, numTopics );
	// }
	// }
	//
	// public Ngram()
	// {
	//
	// }

	@Test
	public void test() throws Exception
	{	
		try{
			// call the TopicalNGrams methods with the following parameters by Blei
			// numTopics=100 , alpha = 1.0, beta = 0.001, gamma = 0.1, delta = 0.001, delta1 = 0.2, delta2=1000.0


			// get the list of unigrams & ngrams

			// tng.printTopWords( 10, true );
			 
			// long start = System.nanoTime();
			// System.out.println( "________________________GET THE TOPICS AS
			// UNIGRAMS__________________________" );
			// String[] u = getStringTopicsUnigrams( tng, 10, false );
			// for ( String h : u )
			// {
			// System.out.println( h );
			// }
			// long end = System.nanoTime();
			// System.out.println( "Time for getting unigrams: [" + ( end /
			// Math.pow( 10, 9 ) - start / Math.pow( 10, 9 ) ) + " sec]" );
			//
			// start = System.nanoTime();
			// System.out.println( "________________________GET THE TOPICS AS
			// N-GRAMS__________________________" );
			// String[] n = getStringTopicsNgrams( tng, 10, false );
			// for ( String h : n )
			// {
			// System.out.println( h );
			// }
			// end = System.nanoTime();
			// System.out.println( "Time for getting Ngrams: [" + ( end /
			// Math.pow( 10, 9 ) - start / Math.pow( 10, 9 ) ) + " sec]" );
			//
			// start = System.nanoTime();
			// System.out.println( "________________________GET THE TOP X TOPICS
			// FOLLOWED BY THEIR PROPORTIONS__________________________" );
			// String[] d = getStringDocumentTopicIndex( tng, 0.0, 10, true );
			// for ( String h : d )
			// {
			// System.out.println( h );
			// }
			// end = System.nanoTime();
			// System.out.println( "Time for getting x - Topics: [" + ( end /
			// Math.pow( 10, 9 ) - start / Math.pow( 10, 9 ) ) + " sec]" );
			//
			// start = System.nanoTime();
			// System.out.println( "________________________GET THE TOPIC
			// ASSIGNMENT AS N-GRAMS CONTENT __________________________" );
			// for ( int i = 0; i < tng.topics.length; i++ )
			// {
			// for ( Entry<String, List<String>> entry : getTopicNgramsDocument(
			// i, 10, 0.0, tng.numTopics, 10, true ).entrySet() )
			// {
			// System.out.println( ( entry.getKey() ) + " ->-> " +
			// entry.getValue() );
			// }
			// }
			// end = System.nanoTime();
			// System.out.println( "Time for getting Topic Assigned presented as
			// N-grams: [" + ( end / Math.pow( 10, 9 ) - start / Math.pow( 10, 9
			// ) ) + " sec]" );
			 
			 // used for all the doucuments instead of having a single one
			// for ( Entry<String, List<String>> e : getTopicNGramsAllDocuments(
			// tng, -1, 0.0, 100, 10, false ).entrySet() )
			// {
			// System.out.println( ( e.getKey() ) + " ->-> " + e.getValue() );
			// }
			
			// Test of the method regarding finding the id
			// System.out.println( maptoRealDatabaseID(
			// "07397ed7-3deb-442f-a297-bdb5b476d3e6" ) );
			// for ( Entry<String, List<String>> entry : getTopicNgramsDocument(
			// maptoRealDatabaseID( "07397ed7-3deb-442f-a297-bdb5b476d3e6" ),
			// -1, 0.0, tng.numTopics, 10, true ).entrySet() )
			// {
			// System.out.println( ( entry.getKey() ) + " ->-> " +
			// entry.getValue() );
			// }

// 			TO BE TESTED
//			System.out.println( "________________________GET THE TOPIC ASSIGNMENT and PROPORTIONS FOR EACH ID __________________________" );
//			for ( Entry<String, LinkedHashMap<String, Double>> e : getDocumentTopicDetailMap( tng, -1, 0.0, 100, 10, false ).entrySet() )
//			{
//				System.out.println( "Author Id -> " + e.getKey() );
//				for ( Entry<String, Double> p : e.getValue().entrySet() )
//				{
//					System.out.println( p.getKey() + " -> " + p.getValue() );
//				}
//				System.out.println();
//			}

			// start = System.nanoTime();
			// System.out.println( "________________________GET THE TOPIC
			// ASSIGNMENT AS UNIGRAMS CONTENT __________________________" );
			// for ( int i = 0; i < tng.topics.length; i++ )
			// {
			// for ( Entry<String, List<String>> entry :
			// getTopicUnigramsDocument( i, 10, 0.0, tng.numTopics, 10, true
			// ).entrySet() )
			// {
			// System.out.println( ( entry.getKey() ) + " ->-> " +
			// entry.getValue() );
			// }
			// }
			// end = System.nanoTime();
			// System.out.println( "Time for getting Topic Assigned presented as
			// Unigrams: [" + ( end / Math.pow( 10, 9 ) - start / Math.pow( 10,
			// 9 ) ) + " sec]" );
			//
			// // similar thing as above but used to get all the documents
			// // informations as above
			//
			// start = System.nanoTime();
			// System.out.println( "________________________GET THE
			// TOPICASSIGNMENT AND PROPORTIONS NOT
			// ORDERED__________________________" );
			// for ( Entry<String, List<Double>> entry :
			// getDoumentTopicProportion().entrySet() )
			// {
			// System.out.println( ( entry.getKey() ) );
			// System.out.println( " **** " );
			// for ( Double z : entry.getValue() )
			// {
			// System.out.println( z );
			// }
			// }
			// end = System.nanoTime();
			//
			// System.out.println(
			// "_____________________________________________________________________________________"
			// );
			// System.out.println( "TEST THE SIMILARITY RETRIEVAL METHOD" );
			// for ( Entry<String, List<String>> entry : calculateSimilarity( 3,
			// 3 ).entrySet() )
			// {
			// System.out.println( "Similar to :" + entry.getKey() );
			// int i = 1;
			// for ( String value : entry.getValue() )
			// {
			// System.out.println( i + ". " + value );
			// i++;
			// }
			// }
			//
			// System.out.println(
			// "_____________________________________________________________________________________"
			// );
			// System.out.println( "TEST THE Entity Level Topic Proportion" );
			// for ( String topic : getTopicProportionEntityLevel( tng, false,
			// 10, false ) )
			// {
			// System.out.println( topic.split( "_-_" )[0] + " -> " +
			// topic.split( "_-_" )[1] );
			// }
			// System.out.println(
			// "_____________________________________________________________________________________"
			// );
			// System.out.println( "TEST THE Entity Level Topic Proportion" );
			// for ( String topic : getTopicProportionEntityLevel( tng, true,
			// 10, false ) )
			// {
			// System.out.println( topic.split( "_-_" )[0] + " -> " +
			// topic.split( "_-_" )[1] );
			// }

			// System.out.println(
			// "_____________________________________________________________________________________"
			// );
			// System.out.println( "TEST THE OVERALL map" );
			// for ( Entry<String, List<String>> entry :
			// getTopicUnigramsAllDocuments( tng, 10, 0.0, tng.numTopics, 5,
			// true ).entrySet() )
			// {
			// System.out.println( entry.getKey() + "-->" + entry.getValue() );
			// }
			//
			// System.out.println(
			// "_____________________________________________________________________________________"
			// );
			// System.out.println( "TEST THE OVERALL PROPORTION" );
			// for ( Entry<String, List<Double>> entry :
			// tng.documentAllTopicsasMap().entrySet() )
			// {
			// System.out.println( entry.getKey() + "-->" + entry.getValue() );
			// }
			//
			// System.out.println(
			// "_____________________________________________________________________________________"
			// );
			// System.out.println( "TEST THE OVERALL PROPORTION INVERSE" );
			// for ( Entry<String, List<Double>> entry :
			// tng.documentTransposedAllTopicsasMap().entrySet() )
			// {
			// System.out.println( entry.getKey() + "-->" + entry.getValue() );
			// }
			//
			// System.out.println(
			// "_____________________________________________________________________________________"
			// );
			// System.out.println( "TEST THE OVERALL ORDERED" );
			// for ( Entry<String, List<String>> entry :
			// getEvolutionofTopicOverTime( 0, 5, true ).entrySet() )
			// {
			// System.out.println( entry.getKey() + "-->" + entry.getValue() );
			// }

			// // run the method to get topic proportions for each doc.
			// // printDocTopicprobs(tng, path, "Authors", "Trainer");
			//
			// System.out.println(
			// "_____________________________________________________________________________________"
			// );

			System.out.println( "________________________RUN THE TOPIC COMPOSITION NEW APPROACH__________________________" );
			for ( Entry<String, List<String>> entry : runTopicComposition( "c442983a-0099-4d6d-89b1-6cfc57fa6138", path, "Author-Test", 20, 20, 10, false, true, true ).entrySet() )
			{
				System.out.println( ( entry.getKey() ) );
				System.out.println( " **** " );
				for ( String z : entry.getValue() )
				{
					System.out.println( z );
				}
			}

		}
		catch ( Exception e )
		{
			e.printStackTrace();
			}
	}
	
	// USE CMD or the other class ImportDataNgram http://stackoverflow.com/questions/15464111/run-cmd-commands-through-java
	// purpose - {Authors, Publications, Conferences, Years} specify - {Trainer, Infer}
	
	public InstanceList getInstanceDataDirectoryLevel( String path, String purpose, String entityId ) throws IOException
	{
		 // Get the data from a directory and convert it into mallet format
		 // Use importData Class to make input traverse through the following pipes
		 // 1. Input2CharSequence
		 // 2. CharSequence2TokenSequence
		 // 3. TokenSequenceLowercase
		 // 4. TokenSequenceRemoveStopwords
		 // 5. TokenSequence2FeatureSequenceBigrams

		if ( entityId.isEmpty() )
		{
			ProcessBuilder builder = new ProcessBuilder( "cmd.exe", "/c", "cd \"C:\\mallet\"&& bin\\mallet import-dir --input C:\\Users\\Piro\\Desktop\\" + purpose + "\\" + purpose + " --keep-sequence-bigrams --remove-stopwords --extra-stopwords C:\\mallet\\stoplists\\extra-stoplist.txt" + "--output C:\\Users\\Piro\\Desktop\\" + purpose + "\\" + purpose + "-N-Authors" + ".mallet" );
			builder.redirectErrorStream( true );
			Process p = builder.start();
			BufferedReader r = new BufferedReader( new InputStreamReader( p.getInputStream() ) );
			String line;

			while ( true )
			{
				line = r.readLine();
				if ( line == null )
				{
					break;
				}
			}
			InstanceList training = InstanceList.load( new File( path + purpose + "/" + purpose + "-N-Authors.mallet" ) );
			return training;
		}
		else
		{
			ProcessBuilder builder = new ProcessBuilder( "cmd.exe", "/c", "cd \"C:\\mallet\"&& bin\\mallet import-dir --input C:\\Users\\Piro\\Desktop\\" + purpose + "\\" + entityId + " --keep-sequence-bigrams --remove-stopwords --stoplist-file C:\\mallet\\stoplists\\extra-stoplist.txt" + " --output C:\\Users\\Piro\\Desktop\\" + purpose + "\\MALLET\\" + entityId + ".mallet" );
			builder.redirectErrorStream( true );
			Process p = builder.start();
			BufferedReader r = new BufferedReader( new InputStreamReader( p.getInputStream() ) );
			String line;

			while ( true )
			{
				line = r.readLine();
				if ( line == null )
				{
					break;
				}
			}
			InstanceList training = InstanceList.load( new File( path + purpose + "/MALLET/" + entityId + ".mallet" ) );
			return training;
		}
	}

	// create instanceList for each of the authors in order to run the Topical
	// ngrams for each of them
	// generate the unique set of topics for each of the authors
	public InstanceList getInstanceDataFileLevel( String path, String purpose, String entityId ) throws IOException
	{
		// Get the data from a directory and convert it into mallet format
		// Use importData Class to make input traverse through the following
		// pipes
		// 1. Input2CharSequence
		// 2. CharSequence2TokenSequence
		// 3. TokenSequenceLowercase
		// 4. TokenSequenceRemoveStopwords
		// 5. TokenSequence2FeatureSequenceBigrams
		ProcessBuilder builder = new ProcessBuilder( "cmd.exe", "/c", "cd \"C:\\mallet\"&& bin\\mallet import-file --input C:\\Users\\Piro\\Desktop\\" + purpose + "\\" + purpose + "\\" + entityId + ".txt" + " --keep-sequence-bigrams --remove-stopwords " + "--output C:\\Users\\Piro\\Desktop\\" + purpose + "\\" + purpose + "-N-grams" + ".mallet" );
		builder.redirectErrorStream( true );
		Process p = builder.start();
		BufferedReader r = new BufferedReader( new InputStreamReader( p.getInputStream() ) );
		String line;

		while ( true )
		{
			line = r.readLine();
			if ( line == null )
			{
				break;
			}
		}
		InstanceList training = InstanceList.load( new File( path + purpose + "/" + purpose + "-N-Grams" + entityId + ".mallet" ) );
		return training;
	}

	// method to create a specific Yearly method
	// public TopicalNGrams createTemporalModel( String path, int numTopics )
	// {
	// temporaltng = createModel( path, "Years", "Trainer", numTopics );
	// return temporaltng;
	// }

	// set the number of topics to the model and returns a model constructed by
	// the given number
	public TopicalNGrams setNumberTopics( int numTopics )
	{
		TopicalNGrams m = new TopicalNGrams(numTopics, 50.0, 0.01, 0.01, 0.03, 0.2, 1000);
			return m;
	}
	
	public int getNumTopics()
	{
		return tng.numTopics;
	}

	public int getNumInstances()
	{
		return tng.ilist.size();
	}

	// create a model of reference using training corpus
	public TopicalNGrams createModel( String path, String purpose, String entityId, int numTopics )
	{
		int numFiles;

		// get the number of files available for each level of hierarchy, it
		// influences the number of topics
		if ( entityId.isEmpty() )
		{
			numFiles = new File( path + "/" + purpose + "/" + purpose ).listFiles().length;
		}
		else
		{
			numFiles = new File( path + "/" + purpose + "/" + entityId ).listFiles().length;
		}

		// by heuristics decide on maximal number of Topics the model will
		// contain dependent on number of files
		if ( numFiles > 100 )
		{
			numTopics = (int) ( numFiles * 0.25 );
		}
		else if ( numFiles < 10 )
		{
			numTopics = numFiles;
		}
		else
		{
			numTopics = 10;
		}

		TopicalNGrams ngram = new TopicalNGrams( numTopics, 50.0, 0.01, 0.01, 0.03, 0.2, 1000 );
		InstanceList trained;
		try
		{
			trained = getInstanceDataDirectoryLevel( path, purpose, entityId );
			ngram.estimate( trained, 100, 1, 0, null, new Randoms() );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}

		return ngram;
		}

	// method to create a model based on an already existing instancelist
	public TopicalNGrams useTrainedData( String path, String purpose, String entityId, int numTopics )
	{
		TopicalNGrams ngram = new TopicalNGrams( numTopics, 50.0, 0.01, 0.01, 0.03, 0.2, 1000 );
		InstanceList trained = InstanceList.load( new File( path + purpose + "/MALLET/" + entityId + ".mallet" ) );
		if ( !trained.isEmpty() )
			ngram.estimate( trained, 200, 1, 0, "C:/Users/Piro/Desktop/Model", new Randoms() );
		else
			return null;

		return ngram;
	}

	// purpose - {Authors, Publications, Conferences, Years} ; specify - {Trainer, Infer} File version
	public void printDocTopicprobs(TopicalNGrams m,String path, String purpose, String specify){
			try
			{	
				PrintWriter out = new PrintWriter( new File( path + purpose + "/DocTopic-NGram-" +purpose +"-"+specify +".txt" ) );
				m.printDocumentTopics( out, 0.0, -1 );
				out.close();
			} catch ( IOException e )
			{
				e.printStackTrace(); }
				}
		
	// returns an array of Strings with each element a topic followed by its bag of ngrams 
	public String[] getStringDocumentTopicIndex (TopicalNGrams m, double threshold, int max, boolean weight){
		String[] document = m.documentTopics( threshold, max, weight ).split( "\n");
	return document;
	}
	
	// returns a List of Strings with each element a topic followed by its bag of ngrams 
	public List<String> getListDocumentTopicIndex (TopicalNGrams m, double threshold, int max, boolean weight){
		String[] document = m.documentTopics( threshold, max, weight ).split( "\n");
		List<String> topics = new ArrayList<String>();
		for (String d : document)
			topics.add( d );
	return topics;
	}
	
	// returns the list of all the topic proportions for all the documents (they are not ordered so it can serve as an input to document similarity
	public HashMap<String, List<Double>> getDoumentTopicProportion()
	{
		HashMap<String, List<Double>> h = new HashMap<String, List<Double>>();
		h = tng.documentAllTopicsasMap();
		return h;
	}
	
	// returns the list of all the topic proportions for all the documents (they
	// are not ordered so it can serve as an input to document similarity
	public HashMap<String, List<Double>> getDoumentTopicProportion( TopicalNGrams model )
	{
		HashMap<String, List<Double>> h = new HashMap<String, List<Double>>();
		h = model.documentAllTopicsasMap();
		return h;
	}

	// Method used for topic composition on Entity Level
	// if unigram is true then we return topics as unigrams otherwise we go for
	// ngrams
	public List<String> getTopicProportionEntityLevel( TopicalNGrams model, boolean unigram, int nwords, boolean weight )
	{
		List<String> topiconEntity = new ArrayList<String>();
		HashMap<String, List<Double>> unorderedDistributions = getDoumentTopicProportion( model );
		String[] topics;
		// decide on having the unigrams or ngrams
		if ( unigram )
		{
			topics = getStringTopicsUnigrams( model, nwords, weight );
		}
		else
		{
			topics = getStringTopicsNgrams( model, nwords, weight );
		}

		double[][] overallTopics = new double[unorderedDistributions.size()][unorderedDistributions.get( unorderedDistributions.keySet().toArray()[0] ).size()];
		
		int i = 0;
		// get the topics of publications
		for ( Entry<String, List<Double>> distributions : unorderedDistributions.entrySet() )
		{
			overallTopics[i] = getdouble( distributions.getValue() );
			i++;
		}

		// calculate the average for each topic distribution overall
		// publications of the author (entity)
		int j = 0;
		double[] overallavg = new double[overallTopics[0].length];
		while ( j < overallTopics[0].length )
		{
			for ( int k = 0; k < overallTopics.length; k++ )
			{
				overallavg[j] += overallTopics[k][j];
			}
			overallavg[j] /= overallTopics.length;
			topiconEntity.add( topics[j] + "_-_" + overallavg[j] );
			j++;
		}
		return topiconEntity;
	}

	// returns an array of Strings with each element a topic followed by its bag of unigrams
	public String[] getStringTopicsUnigrams (TopicalNGrams m, int nwords, boolean weight){
		
		String[] topics = m.printUnigrams( nwords, weight ).split( "\n" );
		
		return topics;
		}

	// returns list of strings of topics where each element is topic followed by its bag of unigrams
	public List<String> getListTopicsUnigrams (TopicalNGrams m, int nwords, boolean weight){
			
			List<String> listtopics = new ArrayList<String>();
			String[] topics = m.printUnigrams( nwords,weight ).split( "\n" );
			for (String topic : topics){
				listtopics.add( topic );
			}
		return listtopics;
		}
	
	// returns an array of Strings with each element a topic followed by its bag of Ngrams 
	public String[] getStringTopicsNgrams (TopicalNGrams m, int nwords, boolean weight){
			String[] topics = m.printNgrams( nwords, weight ).split( "\n" );
		return topics;
		}

	// returns list of strings of topics where each element is topic followed by its bag of unigrams
	public List<String> getListTopicsNgrams (TopicalNGrams m, int nwords, boolean weight){
			List<String> listtopics = new ArrayList<String>();
			String[] topics = m.printNgrams( nwords, weight ).split( "\n" );
			for (String topic : topics){
				listtopics.add( topic );
			}
		return listtopics;
		}		
		

	// gets some random files from path/purpose and pastes them on path/purpose/specify specify = Trainer
	public void getRandomTrainerFiles(String path, String purpose){
		int count = 20;
		String[] trainer = new File (path + purpose + "/" + purpose).list();
		while (count != 0){
			int random = new Random().nextInt(trainer.length-1);
			try
			{	Files.copy( new File (path + purpose + "/" + purpose, trainer[random]).toPath(), new File(path + purpose + "/Trainer", trainer[random] ).toPath(), StandardCopyOption.REPLACE_EXISTING);// Here can be also used ATOMIC_MOVE
			} 	catch ( IOException e )
			{	e.printStackTrace();	}
			count--;	}
		}
		
	// Returns a map <DocumentID, Top Ngrams Topic Assigned to it> which shows the topic assigned to a specific document with given ID
	// When calling max = -1, threshold = 0.05, 
	public HashMap<String, List<String>> getTopicNgramsDocument( int docID, int max, double threshold, int numTopics, int numWords, boolean weight )
	{
		
		LinkedHashMap<String, List<String>> h = new LinkedHashMap<String, List<String>>();
		List<String> topdoc = new ArrayList<String>();// getListDocumentTopic(m,threshold,max,weight);
		List<String> topics = getListTopicsNgrams( tng, numWords, false );
		int docLen;
		double topicDist[] = new double[tng.numTopics];
		docLen = tng.topics[docID].length;
		for ( int ti = 0; ti < numTopics; ti++ )
			topicDist[ti] = ( ( (float) tng.docTopicCounts[docID][ti] ) / docLen );
		if ( max < 0 )
			max = numTopics;
		for ( int tp = 0; tp < max; tp++ )
		{
			double maxvalue = 0;
			int maxindex = -1;
			for ( int ti = 0; ti < numTopics; ti++ )
				if ( topicDist[ti] > maxvalue )
				{
					maxvalue = topicDist[ti];
					maxindex = ti;
				}
			if ( maxindex == -1 || topicDist[maxindex] < threshold )
				break;
			if ( weight )
			{
				// (maxindex+" "+topicDist[maxindex]+" ");

				topdoc.add( topics.get( maxindex ) + " _-_ " + topicDist[maxindex] );
			} else {
				topdoc.add( topics.get( maxindex ) );
			}
			        topicDist[maxindex] = 0;

		}
		h.put( tng.ilist.get( docID ).getSource().toString().replace( "\\", ";" ).split( ";" )[6].replace( ".txt", "" ), topdoc );
			return h;
		}

	// get the top x toics for a given entitity docID NGrams
	public HashMap<String, List<String>> getTopTopicNgramsDocument( int docID, int max, double threshold, int numTopics, int numWords, boolean weight )
	{

		HashMap<String, List<String>> temp = getTopicNgramsDocument( docID, -1, 0.0, tng.numTopics, numWords, true );
		LinkedHashMap<String, List<String>> result = new LinkedHashMap<String, List<String>>();
		List<String> topics = new ArrayList<String>( numTopics );
		if ( numTopics > tng.numTopics )
		{
			numTopics = tng.numTopics;
		}
		int count = 0;
		for ( Entry<String, List<String>> entry : temp.entrySet() )
		{
			for ( String topic : entry.getValue() )
			{
				if ( count < numTopics )
				{
					topics.add( topic );
					count++;
				}
			}
			result.put( entry.getKey(), topics );
		}

		return result;
	}

	public HashMap<String, List<String>> getTopicNGramsAllDocuments( TopicalNGrams m, int max, double threshold, int numTopics, int numWords, boolean weight )
	{
		HashMap<String, List<String>> alldocumentTopics = new HashMap<>();
		for ( int i = 0; i < m.topics.length; i++ )
		{
			for ( Entry<String, List<String>> topicsOneDoc : getTopicNgramsDocument( i, max, threshold, numTopics, numWords, weight ).entrySet() )
			{
				alldocumentTopics.put( topicsOneDoc.getKey(), topicsOneDoc.getValue() );
			}
		}
		return alldocumentTopics;
	}

	// Returns a map <DocumentID, Top Unigrams Topic Assigned to it> which shows the topic assigned to a specific document with given ID
	// When calling max = -1, threshold = 0.05, 
	public HashMap<String, List<String>> getTopicUnigramsDocument( int docID, int max, double threshold, int numTopics, int numWords, boolean weight )
	{
		HashMap<String, List<String>> h = new HashMap<String, List<String>>();
		List<String> topdoc = new ArrayList<String>();//getListDocumentTopic(m,threshold,max,weight);
		List<String> topics = getListTopicsUnigrams( tng, numWords, false );
		
		int docLen;
		double topicDist[] = new double[tng.numTopics];
		docLen = tng.topics[docID].length;
		for ( int ti = 0; ti < numTopics; ti++ )
			topicDist[ti] = ( ( (float) tng.docTopicCounts[docID][ti] ) / docLen );
		if ( max < 0 )
			max = numTopics;
		for ( int tp = 0; tp < max; tp++ )
		{
			double maxvalue = 0;
			int maxindex = -1;
			for ( int ti = 0; ti < numTopics; ti++ )
				if ( topicDist[ti] > maxvalue )
				{
					maxvalue = topicDist[ti];
					maxindex = ti;
				}
			if ( maxindex == -1 || topicDist[maxindex] < threshold )
				break;
			// (maxindex+" "+topicDist[maxindex]+" ");
			if ( weight )
			{
				topdoc.add( topics.get( maxindex ) + " _-_ " + topicDist[maxindex] );
			}
			else
			{
				topdoc.add( topics.get( maxindex ) );
			}
			topicDist[maxindex] = 0;
		}
		      
		h.put( tng.ilist.get( docID ).getSource().toString().replace( "\\", ";" ).split( ";" )[6].replace( ".txt", "" ), topdoc );
		return h;
	}

	// get the top x toics for a given entitity docID Unigrams
	// **************************************************************************************************************
	public HashMap<String, List<String>> getTopTopicUnigramsDocument( int docID, int max, double threshold, int numTopics, int numWords, boolean weight )
	{

		HashMap<String, List<String>> temp = getTopicUnigramsDocument( docID, -1, 0.0, tng.numTopics, numWords, true );
		LinkedHashMap<String, List<String>> result = new LinkedHashMap<String, List<String>>();
		List<String> topics = new ArrayList<String>( numTopics );
		if ( numTopics > tng.numTopics )
		{
			numTopics = tng.numTopics;
		}
		int count = 0;
		for ( Entry<String, List<String>> entry : temp.entrySet() )
		{
			for ( String topic : entry.getValue() )
			{
				if ( count < numTopics )
				{
					topics.add( topic );
					count++;
				}
			}
			result.put( entry.getKey(), topics );
		}

		return result;
	}

	//
	public HashMap<String, List<String>> getTopicUnigramsAllDocuments( TopicalNGrams m, int max, double threshold, int numTopics, int numWords, boolean weight )
	{
		HashMap<String, List<String>> alldocumentTopics = new HashMap<>();
		for ( int i = 0; i < m.topics.length; i++ )
		{
			for ( Entry<String, List<String>> topicsOneDoc : getTopicUnigramsDocument( i, max, threshold, numTopics, numWords, weight ).entrySet() )
			{
				alldocumentTopics.put( topicsOneDoc.getKey(), topicsOneDoc.getValue() );
			}
		}
		return alldocumentTopics;
	}

	// method to visualize the topic evolution
	// note this method is only in cases when tng.createModel("Years") = true
	public HashMap<String, List<String>> getEvolutionofTopicOverTime( int docID, int numWords, boolean weight )
	{
		LinkedHashMap<String, List<String>> topicevolution = new LinkedHashMap<String, List<String>>();
		HashMap<String, List<Double>> doctopicsAll = tng.documentTransposedAllTopicsasMap();
		List<String> topics = getListTopicsUnigrams( tng, numWords, false );

		for ( Entry<String, List<Double>> topicMapping : doctopicsAll.entrySet() )
		{
			List<String> topdoc = new ArrayList<String>();
			int i = 0;
			for ( Double topic : topicMapping.getValue() )
			{
				topdoc.add( ( tng.ilist.get( i ) ).getSource().toString().replace( "\\", ";" ).split( ";" )[6].replace( ".txt", "" ) + "_-_" + topic );
				i++;
			}
			topicevolution.put( topics.get( Integer.parseInt( topicMapping.getKey().toString() ) ), topdoc );
		}

		return topicevolution;
	}
	
	// creates a hashmap <String, List<Double>> holding for each document, its distance with other documents (can be used later on for publications and authors)
	// the second parameter is used to specify which similarity measurement will
	// be used among Euclidian(0), Cosine(1), Pearson(2), KL(3)
	public HashMap<String, List<String>> calculateSimilarity( int similarityMeasure, int maxresult )
	{
		HashMap<String, List<String>> distance = new HashMap<String, List<String>>();
		HashMap<String, List<Double>> topicProportions = new HashMap<String, List<Double>>();
		topicProportions = getDoumentTopicProportion();

		similarityMeasures similarity = new similarityMeasures();
		double[][] similarityMatrix = new double[tng.ilist.size()][tng.ilist.size()];

		// create the matrix which will hold the distances of each document from
		// all the other documents
		int k = 0;
		for ( Entry<String, List<Double>> entry : topicProportions.entrySet() )
		{
			int i = 0;
			double[] similarityperElement = new double[tng.ilist.size()];
			for ( Entry<String, List<Double>> entry1 : topicProportions.entrySet() )
			{
				switch ( similarityMeasure ) {
					case 0: {
					similarityperElement[i] = similarity.sqrtEuclidianSimilarity( entry.getValue(), entry1.getValue() );
					break;
					}
					case 1: {
					similarityperElement[i] = similarity.cosineSimilarity( entry.getValue(), entry1.getValue() );
					break;
					}
					case 2: {
					similarityperElement[i] = similarity.pearsonCorrelation( entry.getValue(), entry1.getValue() );
					break;
					}
					case 3 : {
					similarityperElement[i] = similarity.divergenceJennsenShannon( getdouble( entry.getValue() ), getdouble( entry1.getValue() ) );
					break;
					}
				}

				i++;
			 }
			similarityMatrix[k] = similarityperElement;
			k++;
		}

		if ( maxresult > tng.ilist.size() )
			maxresult = tng.ilist.size();

		// for each document find the top similar elements, take their id, and
		// put them to

		for ( int i = 0; i < similarityMatrix.length; i++ )
		{
			// the list of similar elements
			List<String> similarIds = new ArrayList<String>();
			// number of maximums you will have to find
			int N = 0;

			int index = -1;
			while ( N < maxresult )
			{
				double max = similarityMatrix[i][0];
				// find the maximum in array
				for ( int j = 0; j < similarityMatrix[i].length; j++ )
				{
					if ( similarityMatrix[i][j] >= max )
					{
						max = similarityMatrix[i][j];
						index = j;
					}
				}
				similarIds.add( tng.ilist.get( index ).getSource().toString().replace( "\\", ";" ).split( ";" )[6].replace( ".txt", "" ) + "->" + max );
				similarityMatrix[i][index] = -1;
				N++;
			}
			distance.put( tng.ilist.get( i ).getSource().toString().replace( "\\", ";" ).split( ";" )[6].replace( ".txt", "" ), similarIds );
		}
		return distance;
	}
	
	// returns the List of top similar entities (author, publication etc)
	public List<String> similarEntities( String id, int maxresult )
	{
		List<String> result = new ArrayList<String>();
		HashMap<String, List<String>> similarMap = new HashMap<String, List<String>>();
		similarMap = calculateSimilarity( 1, maxresult );
		if ( similarMap.containsKey( id ) )
		{
			result = similarMap.get( id );
		}
		else
		{
			result.add( "Element not found in our list" );
		}
		return result;
	}

	// topics wihch contribute to high similarity
	public LinkedHashMap<String, List<String>> similarTopics( String id, int maxresult )
	{
		LinkedHashMap<String, List<String>> result = new LinkedHashMap<String, List<String>>();

		return result;
	}

	// creates a hashmap <String, List<Double>> holding for each document, its
	// distance with other documents (can be used later on for publications and
	// authors)
	// the second parameter is used to specify which similarity measurement will
	// be used among Euclidian(0), Cosine(1), Pearson(2), KL(3)
	public HashMap<String, LinkedHashMap<String, Double>> calculateSimilarityMap( int similarityMeasure, int maxresult )
	{
		HashMap<String, LinkedHashMap<String, Double>> distance = new HashMap<String, LinkedHashMap<String, Double>>();

		HashMap<String, List<Double>> topicProportions = new HashMap<String, List<Double>>();
		topicProportions = getDoumentTopicProportion();

		similarityMeasures similarity = new similarityMeasures();
		double[][] similarityMatrix = new double[tng.ilist.size()][tng.ilist.size()];

		// create the matrix which will hold the distances of each document from
		// all the other documents
		int k = 0;
		for ( Entry<String, List<Double>> entry : topicProportions.entrySet() )
		{
			int i = 0;
			double[] similarityperElement = new double[tng.ilist.size()];
			for ( Entry<String, List<Double>> entry1 : topicProportions.entrySet() )
			{
				switch ( similarityMeasure ) {
				case 0: {
					similarityperElement[i] = similarity.sqrtEuclidianSimilarity( entry.getValue(), entry1.getValue() );
					break;
				}
				case 1: {
					similarityperElement[i] = similarity.cosineSimilarity( entry.getValue(), entry1.getValue() );
					break;
				}
				case 2: {
					similarityperElement[i] = similarity.pearsonCorrelation( entry.getValue(), entry1.getValue() );
					break;
				}
				case 3: {
					similarityperElement[i] = similarity.divergenceJennsenShannon( getdouble( entry.getValue() ), getdouble( entry1.getValue() ) );
					break;
				}
				}
				i++;
			}
			similarityMatrix[k] = similarityperElement;
			k++;
		}

		// for each document find the top similar elements, take their id, and
		// put them to

		for ( int i = 0; i < similarityMatrix.length; i++ )
		{
			// the list of similar elements
			LinkedHashMap<String, Double> similarIds = new LinkedHashMap<String, Double>();
			// number of maximums you will have to find
			int N = 0;

			int index = -1;
			while ( N < maxresult )
			{
				double max = similarityMatrix[i][0];
				// find the maximum in array
				for ( int j = 0; j < similarityMatrix[i].length; j++ )
				{
					if ( similarityMatrix[i][j] >= max )
					{
						max = similarityMatrix[i][j];
						index = j;
					}
				}
				similarIds.put( tng.ilist.get( index ).getSource().toString().replace( "\\", ";" ).split( ";" )[6].replace( ".txt", "" ), max );
				similarityMatrix[i][index] = -1;
				N++;
			}
			distance.put( tng.ilist.get( i ).getSource().toString().replace( "\\", ";" ).split( ";" )[6].replace( ".txt", "" ), similarIds );
		}
		return distance;
	}

	// returns the List of top similar entities (author, publication etc)
	public List<String> similarEntitiesMap( String id, int maxresult, int similarityMeasure )
	{
		List<String> result = new ArrayList<String>();
		HashMap<String, LinkedHashMap<String, Double>> similarMap = new HashMap<String, LinkedHashMap<String, Double>>();
		similarMap = calculateSimilarityMap( similarityMeasure, maxresult );
		if ( similarMap.containsKey( id ) )
		{
			for ( Entry<String, Double> value : similarMap.get( id ).entrySet() )
				result.add( value.getKey() );
		}
		else
		{
			result.add( "Element not found in our list" );
		}
		return result;
	}

	// based on similarity results, we recommend most similar publications/authors 
	// criteria for this is that a pub. is considered "similar" by at least two similarity measures 
	public HashMap<String, List<String>> recommendSimilar( int maxresult )
	{
		HashMap<String, List<String>> result = new HashMap<String, List<String>>();
		similarityMeasures similarity = new similarityMeasures();
		for ( Entry<String, List<Double>> entry : getDoumentTopicProportion().entrySet() )
		{
			List<String> recommend = new ArrayList<String>();
			 double[] cos = new double[tng.ilist.size()];
			 double[] euc = new double[tng.ilist.size()];
			 double[] pea = new double[tng.ilist.size()];
			 int i = 0;
			int count = maxresult;
			for ( Entry<String, List<Double>> entry1 : getDoumentTopicProportion().entrySet() )
			{
				 cos[i] = similarity.cosineSimilarity( entry.getValue(), entry1.getValue() );
				 euc[i] = similarity.sqrtEuclidianSimilarity( entry.getValue(), entry1.getValue() );
				 pea[i] = similarity.pearsonCorrelation( entry.getValue(), entry1.getValue() );
				 boolean c = false, e = false ,p = false;
				 if (cos[i] > 0.5 && cos[i] < 1.0){
					 c = true;
				 }
				 if (euc[i] < 0.6 && euc[i] >= 0){
					 e = true;
				 }
				 if (pea[i] > 0.5 && pea[i] < 1.0){
					 p = true;
				 }

				if ( ( count > 0 ) && ( ( c && e ) || ( e && p ) || ( c && p ) ) )
				{
					recommend.add( entry1.getKey() );
					count--;
				 }
				 i++;
			 	}
			 result.put( entry.getKey(), recommend );
			 
			 }
		return result;
	}

	// returns the List of recommended entities (author publication etc)
	public List<String> recommendedEntity( String id, int maxresult )
	{
		List<String> result = new ArrayList<String>();
		HashMap<String, List<String>> similarMap = new HashMap<String, List<String>>();
		similarMap = recommendSimilar( maxresult );

		if ( similarMap.containsKey( id ) )
		{
			result = similarMap.get( id );
		}
		else
		{
			result.add( "Element not found in our list" );
		}
		return result;
	}

	// documentAllTopicsasMap <String, List<Double>> &&
	// documentAllTopicsasMap<String, List<String>>
	// this method has to match the String (ID) with List of topics and list of
	// topic proportions
	public LinkedHashMap<String, LinkedHashMap<String, Double>> getDocumentTopicDetailMap( TopicalNGrams m, int max, double threshold, int numTopics, int numWords, boolean weight )
	{
		LinkedHashMap<String, LinkedHashMap<String, Double>> topicDetails = new LinkedHashMap<>();
		HashMap<String, List<String>> topicWords = new HashMap<>();
		HashMap<String, List<Double>> topicDistribution = new HashMap<>();
		LinkedHashMap<String, Double> topicWordDistributionMatch = new LinkedHashMap<>();
		// loop over all the documents to populate the two input maps

		topicDistribution = m.documentAllTopicsasMap();
		topicWords = getTopicNGramsAllDocuments( m, max, threshold, numTopics, numWords, weight );
		for (Entry<String, List<String>> topic : topicWords.entrySet()){
			for ( Entry<String, List<Double>> distribution : topicDistribution.entrySet() )
			{
				for ( int i = 0; i < topic.getValue().size(); i++ )
				{
					if ( topic.getKey() == distribution.getKey() )
					{
						Collections.sort( distribution.getValue() );
						Collections.reverse( distribution.getValue() );
						topicWordDistributionMatch.put( topic.getValue().get( i ), distribution.getValue().get( i ) );
					}
				}
				topicDetails.put( topic.getKey(), topicWordDistributionMatch );
				}
			}

		return topicDetails;
	}

	// this method is used to return an integer which corresponds to the
	// Author/Conference/Years/Publication ID from db
	public int maptoRealDatabaseID( String id ) throws Exception
	{
		int docID = -1;
		for ( int i = 0; i < tng.ilist.size(); i++ )
		{
			String trial = tng.ilist.get( i ).getSource().toString().replace( "\\", ";" ).split( ";" )[6].replace( ".txt", "" );
			if ( id.equals( trial ) )
			{
				docID = i;
				break;
			}
			else
			{
				continue;
			}
		}
		return docID;
	}

	public double[] TopicInferencer()
	{
		double[] hi = new double[10];
		return hi;
	}

	/*
	 * Convert Double[] to double[]
	 */
	public double[] getdouble( List<Double> array )
	{
		double[] d = new double[array.size()];
		int i = 0;
		for ( Double arr : array )
			d[i++] = arr;
		return d;
	}

	/*
	 * Method called by the controller to create a specific model, use model
	 * generate topics, their proportions, final hash-map
	 */
	public HashMap<String, List<String>> runTopicComposition( String id, String path, String purpose, int numTopics, int maxnumberTopics, int numWords, boolean weight, boolean createmodel, boolean unigram )
	{
		LinkedHashMap<String, List<String>> topicComposition = new LinkedHashMap<String, List<String>>();
		List<String> topicList = new ArrayList<String>();
		TopicalNGrams model;

		// check if you need to create a model based on trained data or new ones
		if ( createmodel )
		{
			model = createModel( path, purpose, id, numTopics );
		}
		else
		{
			model = useTrainedData( path, purpose, id, numTopics );
		}

		// pick to run the Ngrams or Unigrams
		topicList = getTopicProportionEntityLevel( model, unigram, numWords, weight );
		double[] distributions = new double[topicList.size()];
		int i = 0;

		List<String> addtopics = new ArrayList<String>();
		// calculate the maxnumber of top topics
		for ( String proportion : topicList )
		{
			distributions[i] = Double.parseDouble( proportion.split( "_-_" )[1] );

			i++;
		}

		// finf the top N maxtopics
		if ( maxnumberTopics > model.numTopics )
		{
			maxnumberTopics = model.numTopics;
		}

		int N = 0;
		int index = -1;
		while ( N < maxnumberTopics )
		{
			double max = distributions[0];
			// find the maximum in array
			for ( int j = 0; j < distributions.length; j++ )
			{
				if ( distributions[j] >= max )
				{
					max = distributions[j];
					index = j;
				}
			}
			addtopics.add( topicList.get( index ).split( "_-_" )[0] + "_-_" + max );
			distributions[index] = -1;
			N++;
		}

		topicComposition.put( id, addtopics );
		return topicComposition;
	}
}