package de.rwth.i9.palm.analytics.algorithm.dynamicLDA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeSet;
import org.junit.Test;
import cc.mallet.topics.MarginalProbEstimator;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.types.IDSorter;
import cc.mallet.types.InstanceList;
import cc.mallet.types.LabelSequence;
import de.rwth.i9.palm.analytics.algorithm.lda.importData;

//@RunWith( SpringJUnit4ClassRunner.class )
//@ContextConfiguration( classes = AppConfig.class, loader = AnnotationConfigContextLoader.class )
public class dynamicLDA
{
	@Test
	public void test() throws Exception
	{	
		
		try{
			
		// Get the data from a directory and convert it into mallet format 
		// Use importData Class to make input traverse through the following pipes
		// 		1.	Input2CharSequence
		//		2.	CharSequence2TokenSequence
		//		3.	TokenSequenceLowercase
		//		4.	TokenSequenceRemoveStopwords
		//		5.	TokenSequence2FeatureSequence
		
			String path = "C:/Users/Piro/Desktop/";
			
			// Temporal Topic Modeling 
			ParallelTopicModel years = createModel(path, "Years",5 , 5);

			TemporalTopicModel tot = new TemporalTopicModel();
			int[][] trial = new int[years.data.size()][];
			trial = wordIndicesperDocument(years);
			for(int i = 0; i<trial.length;i++){
				for (int j =0; j<trial[i].length; j++){
					System.out.print(trial[i][j] + " ");
				}
				System.out.println();
			}
			
			
			double[] timestamp = generateTimestamps(years.data.size(), 11);
			tot.addInstances( trial, timestamp, years.alphabet.size(), 5 );
			tot.run(30);
			
			
			double[][] beta = new double[10][];
			beta = tot.betaDistrByTopic;
			
			System.out.println(" BETA ------------");
			for(int i = 0; i<beta.length;i++){
				for (int j =0; j<beta[i].length; j++){
					System.out.print(beta[i][j] + " ");
			}
			System.out.println();
		}
			
			
			double [][] theta = new double[years.data.size()][tot.K];
			theta = tot.getTheta();
			System.out.println(" THETA ------------");
			for(int i = 0; i<theta.length;i++){
				for (int j =0; j<theta[i].length; j++){
					System.out.print(theta[i][j] + " ");
				}
				System.out.println();
			}
			
			double [][] phi = new double[tot.K][years.alphabet.size()];
			phi = tot.getPhi();
			System.out.println(" PHI ------------");
			for(int i = 0; i<phi.length;i++){
				for (int j =0; j<phi[i].length; j++){
					System.out.print(phi[i][j] + " ");
				}
				System.out.println();
			}
			
			
			String indexes[][] = getWordsperTopic(years, tot);
			System.out.println("ALL WORDS ------------");
			for(int i = 0; i< indexes.length;i++){
				for (int j =0; j< indexes[i].length; j++){
					System.out.print(indexes[i][j] + " ");
				}
				System.out.println();
			}
			
			String x[][] = getTopWordsperTopic(years, tot, 10);
			System.out.println(" TOP WORDS ------------");
			for(int i = 0; i< x.length;i++){
				for (int j =0; j< 10; j++){
					System.out.print(x[i][j] + " ");
				}
				System.out.println();
			}
			System.out.println("-----");
			for (Entry<String, List<String>> entry : getTopWordperTopic(years, tot, 10).entrySet()){
				System.out.print(entry.getKey());
				for (String list: entry.getValue())
					System.out.print(" " + list + " ");
				System.out.println();
			}
			
			
			for (Entry<Integer, List<Double>> entry : getTopicDistribution(tot).entrySet()){
				System.out.print(entry.getKey());
				for (Double list: entry.getValue())
					System.out.print(" " + list + " ");
				System.out.println();
			}
			
			
			for (Entry<String, List<Double>> entry : TopicEvolution(tot).entrySet()){
				System.out.print(entry.getKey());
				for (Double list: entry.getValue())
					System.out.print(" " + list + " ");
				System.out.println();
			}
			
			
			
//			double[] time = new double[timestamp.length];
//			for (int k = 0; k<10; k++){
//				time = tot.getTimeStamps( k );
//				System.out.println("Timestamps for topic - " + (k + 1) );
//				for (double s : time){
//					System.out.print(s + " ");
//				}
//				System.out.println("");
//			}
			//System.out.print((toksperdoc.get(1).split( "\n" )[4]).split( " " )[1] + " " + (toksperdoc.get(1).split( "\n" )[5]).split( " " )[1]);
			//System.out.println();
//			int[][] matrix = new int[years.data.size()][years.getAlphabet().size()];
//			for (int i = 0; i < years.data.size(); i++){
//				for (int j = 0; j< ( (FeatureSequence) years.data.get( i).instance.getData() ).size(); j++){
//					
//				}
//			}
			
			
//			for (int i = 0 ; i< years.data.size(); i++){
//				System.out.println(((FeatureSequence) years.data.get( i ).instance.getData() ).size());
//				System.out.println(years.data.get( i ).instance.getData().toString());
//			}
//			
//			for (String l : getListTopics(years, 6)){
//				System.out.println(l);
//			}
//			
//			
//			for (int i=0; i< years.data.size(); i++){
//				System.out.println( ((years.data.get( i ).instance.getName() + "").split( "/" )[7]).replaceAll(".txt",""));
//				for (Entry<Integer, Double> entry : getTopicProportion(years, 0.0, i, 10, years.getNumTopics()).entrySet()){
//					System.out.println(/*entry.getKey() + "-> " + */(new DecimalFormat("#.######").format(entry.getValue())));
//					}
//			}
			
//			double[] a = null;
//			for (int i =0; i< years.data.size(); i++){
//				 a = years.getTopicProbabilities( i );
//				for (double j : a ){
//					System.out.print(" " + j + " ");
//				}'
//				System.out.println("");
//			}
			
//			double[][] c = getTopicProbabilityAll(years);
//			for (int m = 0; m < years.data.size(); m++){
//				for (int n =0; n < years.numTopics; n++){
//					System.out.print(" " + c[m][n] + " ");
//				}
//				System.out.println();
//			}
//			
//			for (int topic = 0; topic < years.numTopics; topic++) {
//				for (int type = 0; type < years.numTypes; type++) {
//
//					int[] topicCounts = years.typeTopicCounts[type];
//					
//					double weight = years.beta;
//
//					int index = 0;
//					while (index < topicCounts.length &&
//						   topicCounts[index] > 0) {
//
//						int currentTopic = topicCounts[index] & years.topicMask;
//						
//						
//						if (currentTopic == topic) {
//							weight += topicCounts[index] >> years.topicBits;
//							break;
//						}
//
//						index++;
//					}
//
//					System.out.println(topic + "\t" + years.alphabet.lookupObject(type) + "\t" + weight);
//				}
//			}	
//			for (int i=0; i< years.data.size(); i++){
//				System.out.println( ((years.data.get( i ).instance.getName() + "").split( "/" )[7]).replaceAll(".txt",""));
//			for (Entry<Integer, Double> entry : (sortHashMapByValues(getTopicProportion(years, 0.0, i, 10, years.getNumTopics())).entrySet())){
//				System.out.println(entry.getKey() + "-> " + entry.getValue());
//				}
//			}
//			
//			
//			for (int i=0; i< years.data.size(); i++){
//				System.out.println( ((years.data.get( i ).instance.getName() + "").split( "/" )[7]).replaceAll(".txt",""));
//			for (Entry<Integer, Double> entry : (getTopicProportion(years, 0.0, i, 10, years.getNumTopics())).entrySet()){
//				System.out.println(entry.getKey() + "-> " + entry.getValue());
//				}
//			}
//			
//			for (int i =0; i< years.data.size(); i++){
//				for (int j=0; j< years.numTopics; j++){
//					System.out.print(" " + getTopicProbabilityAll(years)[i][j] + " ");
//				}
//				System.out.println();
//			}
			
			// USE THE METHOD Below to get the data (call directly the create
			// model method ;)
			// importData importer = new importData();
			// InstanceList instances = importer.readDirectory(new
			// File("C:/Users/Piro/Desktop/Documents"));
			// instances.save( new
			// File("C:/Users/Piro/Desktop/Outputs/myoutputs-ngrams.mallet") );
			//
			// // specify the file from which the data will be gathered
			// File texting = new
			// File("C:/Users/Piro/Desktop/Outputs/myNewNgramDB.mallet");
			// InstanceList training = InstanceList.load (texting);
			//
			// System.out.println ("Data loaded.");
//		
//			ParallelTopicModel model = createModel( path, "Years", 10, 10 );
//
//		// define number of Topics 
//		int numTopics = 70;
//
//		// call ParallelTopicModel class to run simple parallel version of LDA with
//		// alpha=0.1 (sumalpha)50 beta=0,01 numTopics=5
//		ParallelTopicModel lda = new ParallelTopicModel (numTopics, 50.0 , 0.01);
//		lda.printLogLikelihood = true;
//		
//		// Start the printing of results. Other methods can be called 
//		MalletLogger.getLogger(ParallelTopicModel.class.getName()).info("printing state");
//		
//		// get the top words for each topic 
//			// USE THE OTHER METHODS TO GET THIS AS STRING (Or later change to
//			// get kinda list)
//			// lda.printTopWords( new
//			// File("C:/Users/Piro/Desktop/Outputs/TopWords-NEW.txt"), 11, false
//			// );
//			model.displayTopWords( 10, true );
//		
//		// get the topic distribution for each of the files
//			// Instead use one of the two below methods
//		lda.printDocumentTopics( new File("C:/Users/Piro/Desktop/Outputs/DocTopic-NEW.txt") );
//		
//		// get the weight of each word if needed
//			lda.printTopicWordWeights( new File( "C:/Users/Piro/Desktop/Outputs/Wordweight.txt" ) );
//		
//		MalletLogger.getLogger(ParallelTopicModel.class.getName()).info("finished printing");
		
			// From Now on this will not be any more possible
		//Start the procedure of merging the contents of file for mapping
		//the documents with their "bag-of-words" topics 
			// @SuppressWarnings( "resource" )
			// BufferedReader docs = new BufferedReader(new
			// FileReader("C:/Users/Piro/Desktop/Outputs/DocTopic-NEW.txt"));
			// @SuppressWarnings( "resource" )
			// BufferedReader tops = new BufferedReader(new
			// FileReader("C:/Users/Piro/Desktop/Outputs/TopWords-NEW.txt"));
			// String document, topic;
			//
			// // get Line by line the bag of words for each of the topics
			// List<String> listtopic = new ArrayList<String>();
			// while(( topic = tops.readLine())!=null){
			// listtopic.add( topic );
			// }
			//
			// // get Line by line the topic distribution for each of the
			// documents
			// List<String> listdoc = new ArrayList<String>();
			// while((document=docs.readLine())!=null){
			// listdoc.add( document );
			// }
			//
			// // map documents to topic's bag-of-words
			// for (int i=1; i<listdoc.size();i++){
			// String[] docsplit = listdoc.get( i ).split( "\\s+" );
			// for(int j =0;j<numTopics;j++){
			// if (listtopic.get( j ).startsWith( docsplit[2]) == true){
			// System.out.println(docsplit[1] +" -> " + listtopic.get( j
			// ).substring( 10 ));
			// break;
			// }
			// }
			// }
		}  catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * METHODS USED FOR DISCRETE TIME DYNAMIC TOPIC MODEL
	 */
	
	// purpose - {Authors, Publications, Conferences, Years}
	// specify - {Trainer, Infer}
	public InstanceList getInstanceData( String path, String purpose )
	{
		importData importer = new importData();
		InstanceList instances = importer.readDirectory( new File( path + "/" + purpose + "/" ) );
		instances.save( new File( path + purpose + "/" + purpose + ".mallet" ) );
		InstanceList training = InstanceList.load( new File( path + purpose + "/" + purpose + ".mallet" ) );
		return training;
	}

	// get the number of topics
	// public int getNumbertopics(){
	// return numtopics;
	// }

	// set the number of topics
	public int setNumberTopics( int numTopics )
	{
		if ( numTopics <= 0 )
		{
			System.out.print( "Wrong input" );
			return -1;
		}
		else
		{
			return numTopics;
		}
	}

	// create a model of reference in a training corpora
	public ParallelTopicModel createModel( String path, String purpose, int numTopics, int numWords )
	{

		ParallelTopicModel lda = new ParallelTopicModel( numTopics, 50.0, 1.0 );
		lda.setNumThreads( 1 );
		lda.optimizeInterval = 20;
		lda.printLogLikelihood = true;
		lda.setTopicDisplay( numTopics, numWords + 1 );
		InstanceList trained = getInstanceData( path, purpose );
		lda.addInstances( trained );
		try
		{
			lda.estimate();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		return lda;
	}

	//// Return the default LDA number of topics
	public int getNumTopics(ParallelTopicModel lda)
	{
		return lda.getNumTopics();
	}

	// purpose - {Authors, Publications, Conferences, Years}
	// specify - {Trainer, Infer}
	public void printTopWords( ParallelTopicModel m, int nwords, String path, String purpose, String specify )
	{
		try
		{
			m.printTopWords( new File( path + purpose + "/TopWords-" + purpose + "-" + specify + ".txt" ), nwords + 1, false );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}

	// purpose - {Authors, Publications, Conferences, Years}
	// specify - {Trainer, Infer}
	public void printDocTopicprobs( ParallelTopicModel m, String path, String purpose, String specify )
	{
		try
		{
			m.printDocumentTopics( new File( path + purpose + "/DocTopic-" + purpose + "-" + specify + ".txt" ) );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}

	// Some minor problems need to be fixed here
	public void evaluateModel( ParallelTopicModel m, String path, String purpose, String specify )
	{

		InstanceList training = InstanceList.load( new File( path + purpose + "/" + purpose + "-" + specify + ".mallet" ) );
		MarginalProbEstimator evaluator = m.getProbEstimator();
		double logLikelyhood = evaluator.evaluateLeftToRight( training, 10, false, null );
		System.out.println( logLikelyhood );
	}

	public static File createTempDirectory() throws IOException
	{
		final File temp;

		temp = File.createTempFile( "temp", Long.toString( System.nanoTime() ) );

		if ( !( temp.delete() ) )
		{
			throw new IOException( "Could not delete temp file: " + temp.getAbsolutePath() );
		}

		if ( !( temp.mkdir() ) )
		{
			throw new IOException( "Could not create temp directory: " + temp.getAbsolutePath() );
		}

		return ( temp );
	}

	// gets some random files from path/purpose and pastes them on
	// path/purpose/specify
	// specify = Trainer
	public void getRandomTrainerFiles( String path, String purpose )
	{
		int count = 20;
		String[] trainer = new File( path + purpose + "/" + purpose ).list();
		while ( count != 0 )
		{
			int random = new Random().nextInt( trainer.length - 1 );
			try
			{
				Files.copy( new File( path + purpose + "/" + purpose, trainer[random] ).toPath(), new File( path + purpose + "/Trainer", trainer[random] ).toPath(), StandardCopyOption.REPLACE_EXISTING );
			}
			catch ( IOException e )
			{
				e.printStackTrace();
			}
			count--;
		}
	}

	// this methods maps the best topic with the suitable document
	public void DocTopicMapper( String path, String purpose, String specify ) throws IOException
	{
		@SuppressWarnings( "resource" )
		BufferedReader docs = new BufferedReader( new FileReader( path + purpose + "/DocTopic-" + purpose + "-" + specify + ".txt" ) );
		@SuppressWarnings( "resource" )
		BufferedReader tops = new BufferedReader( new FileReader( path + purpose + "/TopWords-" + purpose + "-" + specify + ".txt" ) );
		String document, topic;

		// get Line by line the bag of words for each of the topics
		List<String> listtopic = new ArrayList<String>();
		while ( ( topic = tops.readLine() ) != null )
		{
			listtopic.add( topic );
		}

		// get Line by line the topic distribution for each of the documents
		List<String> listdoc = new ArrayList<String>();
		while ( ( document = docs.readLine() ) != null )
		{
			listdoc.add( document );
		}

		// map documents to topic's bag-of-words
		for ( int i = 1; i < listdoc.size(); i++ )
		{
			int numTopics = 50;
			String[] docsplit = listdoc.get( i ).split( "\\s+" );
			for ( int j = 0; j < numTopics; j++ )
			{
				if ( listtopic.get( j ).startsWith( docsplit[2] ) == true )
				{
					System.out.println( docsplit[1] + " -> " + listtopic.get( j ).substring( 10 ) );
					break;
				}
			}
		}
	}

	// This is used to assign a topic distribution to new Documents
	// Used for AuthorId, DocId uzw
	public double[] TopicInferencer( ParallelTopicModel trainedModel, String path, String purpose, String specify, String docID ) throws IOException
	{
		double[] topicProbs;
		TopicInferencer infere = trainedModel.getInferencer();
		InstanceList instance = getInstanceData( path, purpose );
		topicProbs = infere.getSampledDistribution( instance.get( 0 ), 100, 10, 10 );
		return topicProbs;
	}

	// Returns another version of topics (not as a File) but as vector of
	// strings
	// FOR THE FUTURE -> MAKE CHANGES TO GET ONLY MAP<INT, STRING[]>
	public String[] getStringTopics( ParallelTopicModel m, int nwords )
	{

		String[] topics = m.displayTopWords( nwords, false ).split( "\n" );
		return topics;
	}
	
	// similar to the above method, but with List as an output
	public List<String> getListTopics (ParallelTopicModel m, int nwords){
		List<String> listtopics = new ArrayList<String>();
		String[] topics = m.displayTopWords( nwords, false ).split( "\n" );
		for (String topic : topics){
			listtopics.add( topic );
		}
	return listtopics;
	}
	
	// The above method but in this case with the possibility to have a List of
	// strings as output
	public HashMap<Integer, Map<Double, String>> getlistTopics( ParallelTopicModel m, int nwords, int numTopics, int numWords )
	{
		HashMap<Integer, Map<Double, String>> topics = new HashMap<Integer, Map<Double, String>>();
		ArrayList<TreeSet<IDSorter>> topicSortedWords = getSortedWords( m, numTopics );
		for (int topic = 0; topic < numTopics; topic++) {
			TreeSet<IDSorter> sortedWords = topicSortedWords.get(topic);
			int word = 1;
			Iterator<IDSorter> iterator = sortedWords.iterator();
				while (iterator.hasNext() && word < numWords) {
					IDSorter info = iterator.next();
				( (Map) topics.get( topic ) ).put(m.formatter.format(m.alpha[topic]) ,(String) m.alphabet.lookupObject( info.getID() ) );
					word++;
				}
		}
		return topics;
	}

	// get the topic proportion per instance (in this case one instance corresponds to a specific timestamp [year])
	public HashMap<Integer, Double> getTopicProportion( ParallelTopicModel m, double threshold, int docID, int max, int numTopics )
	{
		HashMap<Integer, Double> topics = new HashMap<Integer, Double>();
		int[] topicCounts = new int[numTopics];
		int docLen = 0;
		int topicID = 0;
		double topicWeight = 0;
		IDSorter[] sortedTopics = new IDSorter[numTopics];
		for ( int topic = 0; topic < numTopics; topic++ )
		{
			// Initialize the sorters with dummy values
			sortedTopics[topic] = new IDSorter( topic, topic );
		}

		if ( max < 0 || max > numTopics )
		{
			max = numTopics;
		}

		LabelSequence topicSequence = (LabelSequence) m.data.get( docID ).topicSequence;
		int[] currentDocTopics = topicSequence.getFeatures();

		docLen = currentDocTopics.length;

		// Count up the tokens
		for ( int token = 0; token < docLen; token++ )
		{
			topicCounts[currentDocTopics[token]]++;
		}

		// And normalize
		for ( int topic = 0; topic < numTopics; topic++ )
		{
			sortedTopics[topic].set( topic, ( m.alpha[topic] + topicCounts[topic] ) / ( docLen + m.alphaSum ) );
		}

		Arrays.sort( sortedTopics );
		for ( int i = 0; i < max; i++ )
		{
			if ( sortedTopics[i].getWeight() < threshold ){	break; }
			
				topicID = sortedTopics[i].getID();
				topicWeight = sortedTopics[i].getWeight();
				topics.put( topicID, topicWeight);
		}
		Arrays.fill( topicCounts, 0 );
		return topics;
	}
	
	// USe the tree-struct to get the sorted words for each topic
	public ArrayList<TreeSet<IDSorter>> getSortedWords( ParallelTopicModel m, int numTopics )
	{

		ArrayList<TreeSet<IDSorter>> topicSortedWords = new ArrayList<TreeSet<IDSorter>>( numTopics );

		// Initialize the tree sets
		for ( int topic = 0; topic < numTopics; topic++ )
		{
			topicSortedWords.add( new TreeSet<IDSorter>() );
		}

		// Collect counts
		for ( int type = 0; type < m.numTypes; type++ )
		{

			int[] topicCounts = m.typeTopicCounts[type];

			int index = 0;
			while ( index < topicCounts.length && topicCounts[index] > 0 )
			{

				int topic = topicCounts[index] & m.topicMask;
				int count = topicCounts[index] >> m.topicBits;

				topicSortedWords.get( topic ).add( new IDSorter( type, count ) );

				index++;
			}
		}

		return topicSortedWords;
	}

	// Returns a map which shows the topic assigned to a specific document with
	// given ID
	// When calling max = -1, threshold = 0.05,
	public HashMap<String, String> getTopicDocument( ParallelTopicModel m, int docID, int max, double threshold, int numTopics )
	{

		HashMap<String, String> h = new HashMap<String, String>();
		int[] topicCounts = new int[numTopics];
		String[] topicNames;
		int docLen = 0;
		int topicID = 0;
		double topicWeight = 0;
		IDSorter[] sortedTopics = new IDSorter[numTopics];
		for ( int topic = 0; topic < numTopics; topic++ )
		{
			// Initialize the sorters with dummy values
			sortedTopics[topic] = new IDSorter( topic, topic );
		}

		if ( max < 0 || max > numTopics )
		{
			max = numTopics;
		}

		LabelSequence topicSequence = (LabelSequence) m.data.get( docID ).topicSequence;
		int[] currentDocTopics = topicSequence.getFeatures();

		docLen = currentDocTopics.length;

		// Count up the tokens
		for ( int token = 0; token < docLen; token++ )
		{
			topicCounts[currentDocTopics[token]]++;
		}

		// And normalize
		for ( int topic = 0; topic < numTopics; topic++ )
		{
			sortedTopics[topic].set( topic, ( m.alpha[topic] + topicCounts[topic] ) / ( docLen + m.alphaSum ) );
		}

		Arrays.sort( sortedTopics );

		// m.data.get(docID).instance.getName(); // can be also
		// model.data.get(doc).instance.getID(); or whatever :))))
		if ( sortedTopics[0].getWeight() < threshold )
		{
			topicID = sortedTopics[0].getID();
			topicWeight = sortedTopics[0].getWeight();
		}
		topicNames = getStringTopics( m, 5 );
		h.put( (String) m.data.get( docID ).instance.getName(), topicWeight + "-" + topicNames[topicID] );
		Arrays.fill( topicCounts, 0 );
		return h;
	}

	public HashMap<String, List<String>> getAllDocumentTopics( ParallelTopicModel m, int max, double threshold, int numTopics )
	{
		HashMap<String, List<String>> h = new HashMap<String, List<String>>();
		int[] topicCounts = new int[numTopics];
		int docLen = 0;
		int topicID = 0;
		double topicWeight = 0;
		IDSorter[] sortedTopics = new IDSorter[numTopics];
		for ( int topic = 0; topic < numTopics; topic++ )
		{
			// Initialize the sorters with dummy values
			sortedTopics[topic] = new IDSorter( topic, topic );
		}

		if ( max < 0 || max > numTopics )
		{
			max = numTopics;
		}

		for ( int docID = 0; docID < m.data.size(); docID++ )
		{
			LabelSequence topicSequence = (LabelSequence) m.data.get( docID ).topicSequence;
			int[] currentDocTopics = topicSequence.getFeatures();

			docLen = currentDocTopics.length;

			// Count up the tokens
			for ( int token = 0; token < docLen; token++ )
			{
				topicCounts[currentDocTopics[token]]++;
			}

			// And normalize
			for ( int topic = 0; topic < numTopics; topic++ )
			{
				sortedTopics[topic].set( topic, ( m.alpha[topic] + topicCounts[topic] ) / ( docLen + m.alphaSum ) );
			}

			Arrays.sort( sortedTopics );
			List<String> distribution = new ArrayList<String>();
			// m.data.get(docID).instance.getName(); // can be also
			// model.data.get(doc).instance.getID(); or whatever :))))
			for ( int i = 0; i < max; i++ )
			{
				if ( sortedTopics[i].getWeight() < threshold )
				{
					break;
				}
				if ( sortedTopics[0].getWeight() < threshold )
				{
					topicID = sortedTopics[0].getID();
					topicWeight = sortedTopics[0].getWeight();
					distribution.add( topicID + "-" + topicWeight );
				}
			}
			h.put( (String) m.data.get( docID ).instance.getName(), distribution );
			Arrays.fill( topicCounts, 0 );
		}
		return h;
	}
	
	public LinkedHashMap<Integer,Double> sortHashMapByValues(HashMap<Integer,Double> passedMap) {
		   List<Integer> mapKeys = new ArrayList<Integer>(passedMap.keySet());
		   List<Double> mapValues = new ArrayList<Double>(passedMap.values());
		   Collections.sort(mapValues);
		   Collections.sort(mapKeys);

		   LinkedHashMap<Integer,Double> sortedMap = new LinkedHashMap<Integer,Double>();

		   Iterator<Double> valueIt = mapValues.iterator();
		   while (valueIt.hasNext()) {
		       Object val = valueIt.next();
		       Iterator<Integer> keyIt = mapKeys.iterator();

		       while (keyIt.hasNext()) {
		           Object key = keyIt.next();
		           String comp1 = passedMap.get(key).toString();
		           String comp2 = val.toString();

		           if (comp1.equals(comp2)){
		               passedMap.remove(key);
		               mapKeys.remove(key);
		               sortedMap.put((Integer)key, (Double)val);
		               break;
		           }
		       }
		   }
		   return sortedMap;
		}
	
	
	public double[][] getTopicProbabilityAll(ParallelTopicModel m){
		double[][] a = new double[m.data.size()][m.numTopics];
		double[] temp = new double[m.data.size()];
		for (int i =0; i< m.data.size(); i++){
			temp = m.getTopicProbabilities( i );
			for ( int j=0; j < m.numTopics; j++){
			 a[i][j] = temp[j];
			}
		}
		return a;
	}
	
 // create the term frequency matrix
	public int[][] generateTermFreqMatrix(ParallelTopicModel m){
		List<String> alphabet = new ArrayList<String>();
		List<String> toksperdoc = new ArrayList<String>();
		List<String[]> toksperdocArray = new ArrayList<String[]>();
		List<String[]> tokens = new ArrayList<String[]>();
		int[][] termfreq = new int[m.data.size()][];
		
		// get the alphabet as a List<String>
		for (Object o : m.getAlphabet().toArray())
			alphabet.add( o.toString() );
		for (int i = 0; i < m.data.size(); i++)
				toksperdoc.add( (String) m.data.get(i).instance.getData().toString() );
		for (String s : toksperdoc)
			toksperdocArray.add( s.split( "\n" ) );
		
		// tokens as terms per document in an array for each document 
		for (int i=0; i < toksperdocArray.size(); i++){
			String[] temporal = new String[toksperdocArray.get(i).length];
			for (int j=0; j< toksperdocArray.get(i).length; j++){
				temporal[j] = (toksperdocArray.get( i )[j]).split(" ")[1];
			}
			tokens.add( temporal );
		}

		// generate the matrix with term frequency for each document
		for (int i=0; i < m.data.size(); i++){
			termfreq[i] = new int[tokens.get( i ).length];
			int j = 0;
			for (j= 0; j< termfreq[i].length; j++){
				if (alphabet.indexOf( tokens.get( i )[j] ) != -1)
					termfreq[i][alphabet.indexOf( tokens.get( i )[j] )]++;
				else
					termfreq[i][alphabet.indexOf( tokens.get( i )[j] )] = 0;
			}
		}
		return termfreq;
	}
	
	/*
	 * METHODS USED ON TOPIC OVER TIME MODEL 
	 */
	// create timestamps vector 
	// range has to be: 7-weeks, 30-months, 4-quarters, x-years
	public double[] generateTimestamps(int ndocs, int range){
		double[] timest = new double[range];
		double[] result = new double[ndocs];
		for (int i=0; i < result.length; i++)
			result[i] = 0.0;
		
		timest[0] = 0.01;
		timest[range-1] = 0.99;
		for(int i=1; i< range - 1 ; i++){
			timest[i] = timest[i-1] + (double) 1/range;
		}
		for(int i=0; i < ndocs; i++){
			result[i] = timest[(int) Math.random()*10];
		}
		return result;
	}
	
	// create the input for documents 
	// a int[][] containing the indices of words found in the document
	public int[][] wordIndicesperDocument(ParallelTopicModel m){
		int[][] documents = new int[m.data.size()][];
		List<String> alphabet = new ArrayList<String>();
		List<String> toksperdoc = new ArrayList<String>();
		List<String[]> toksperdocArray = new ArrayList<String[]>();
		List<String[]> tokens = new ArrayList<String[]>();
		
		// get the alphabet as a List<String>
		for (Object o : m.getAlphabet().toArray())
			alphabet.add( o.toString() );
		for (int i = 0; i < m.data.size(); i++)
			toksperdoc.add( (String) m.data.get(i).instance.getData().toString() );
		for (String s : toksperdoc)
			toksperdocArray.add( s.split( "\n" ) );		
		
		// tokens as terms per document in an array for each document 
		for (int i=0; i < toksperdocArray.size(); i++){
			String[] temporal = new String[toksperdocArray.get(i).length];
			for (int j=0; j< toksperdocArray.get(i).length; j++){
				temporal[j] = (toksperdocArray.get( i )[j]).split(" ")[1];
			}
			tokens.add( temporal );
		}

		// generate the matrix with term frequency for each document
		for (int i=0; i < m.data.size(); i++){
			documents[i] = new int[tokens.get( i ).length];
			for (int j= 0; j< tokens.get( i ).length; j++){
				if (alphabet.indexOf( tokens.get( i )[j] ) != -1)
					documents[i][j] = alphabet.indexOf( tokens.get( i )[j] );
			}
		}
		return documents;
	}
	
	
	// Method used to get the list of significant words per topic
	public String[][] getWordsperTopic (ParallelTopicModel m, TemporalTopicModel t){
		String[][] words = new String[t.K][t.V];
		List<String> alphabet = new ArrayList<String>();
		Integer[][] indices = t.sortedIndicesperTopic(t.getPhi());
		
		for (Object o : m.getAlphabet().toArray())
			alphabet.add( o.toString() );

		for (int i=0; i< indices.length; i++){
			for (int j=0; j<indices[i].length; j++){
				words[i][j] = alphabet.get(indices[i][j]);
			}
		}
		return words;
	}
	
	// Method used to return the top words per topic
	public String[][] getTopWordsperTopic (ParallelTopicModel m, TemporalTopicModel t,int nwords){
		String[][] topwords = new String[t.K][nwords];
		String[][] words = getWordsperTopic(m,t);
		for (int i=0; i < words.length; i++){
			int count=0;
			for (int j= words[i].length - nwords; j< words[i].length; j++){
				topwords[i][count++] = words[i][j];
			}
		}
		return topwords;
	}
	
	// Method used to return the top words per topic
	public LinkedHashMap<String, List<String>> getTopWordperTopic(ParallelTopicModel m, TemporalTopicModel t, int nwords){
		LinkedHashMap<String, List<String>> words = new LinkedHashMap<>();
		String[][] wordresult = getTopWordsperTopic(m,t,nwords);
		for (int i =0; i< wordresult.length; i++){
			List<String> wordlist = new ArrayList<>();
			for (int j=0; j < wordresult[i].length; j++){
				wordlist.add( wordresult[i][j]);
			}
			words.put( "Topic " +i, wordlist );
		}
		return words;
	}
	
	// Method used to return the topic distribution for each year
	public LinkedHashMap<Integer, List<Double>> getTopicDistribution(TemporalTopicModel t){
		LinkedHashMap<Integer, List<Double>> words = new LinkedHashMap<>();
		double[][] topicresult = t.getTheta();
		for (int i =0; i< topicresult.length; i++){
			List<Double> wordlist = new ArrayList<>();
			for (int j=0; j < topicresult[i].length; j++){
				wordlist.add( topicresult[i][j]);
			}
			words.put(i + 2005, wordlist );
		}
		return words;
	}
	
	// Method used to return the topic evolution over years
	public LinkedHashMap<String, List<Double>> TopicEvolution(TemporalTopicModel t){
		LinkedHashMap<String, List<Double>> evolution = new LinkedHashMap<>();
		double[][] topicresult = transposeMatrix(t.getTheta());
		for (int i =0; i< topicresult.length; i++){
			List<Double> wordlist = new ArrayList<>();
			for (int j=0; j < topicresult[i].length; j++){
				wordlist.add( topicresult[i][j]);
			}
			evolution.put("Topic" + i, wordlist );
		}
		return evolution;
	}
	
	// method used to facilitate the above method of TopicEvolution
	private double[][] transposeMatrix( double[][] theta )
	{
		double[][] result = new double[theta[0].length][theta.length];
		int j =0;
		while( j < theta[0].length){
			for (int i=0; i< theta.length; i++){
				result[j][i] = theta[i][j];
			}
			j++;
		}
		return result;
	}
}
