package de.rwth.i9.palm.analytics.algorithm.dynamicLDA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import cc.mallet.topics.MarginalProbEstimator;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.types.IDSorter;
import cc.mallet.types.InstanceList;
import cc.mallet.types.LabelSequence;
import cc.mallet.util.MalletLogger;
import de.rwth.i9.palm.analytics.algorithm.lda.importData;
import de.rwth.i9.palm.analytics.config.AppConfig;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = AppConfig.class, loader = AnnotationConfigContextLoader.class )
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
		
			ParallelTopicModel model = createModel( path, "Years", 10, 10 );

		// define number of Topics 
		int numTopics = 70;

		// call ParallelTopicModel class to run simple parallel version of LDA with
		// alpha=0.1 (sumalpha)50 beta=0,01 numTopics=5
		ParallelTopicModel lda = new ParallelTopicModel (numTopics, 50.0 , 0.01);
		lda.printLogLikelihood = true;
		
		// Start the printing of results. Other methods can be called 
		MalletLogger.getLogger(ParallelTopicModel.class.getName()).info("printing state");
		
		// get the top words for each topic 
			// USE THE OTHER METHODS TO GET THIS AS STRING (Or later change to
			// get kinda list)
			// lda.printTopWords( new
			// File("C:/Users/Piro/Desktop/Outputs/TopWords-NEW.txt"), 11, false
			// );
			model.displayTopWords( 10, true );
		
		// get the topic distribution for each of the files
			// Instead use one of the two below methods
		lda.printDocumentTopics( new File("C:/Users/Piro/Desktop/Outputs/DocTopic-NEW.txt") );
		
		// get the weight of each word if needed
			lda.printTopicWordWeights( new File( "C:/Users/Piro/Desktop/Outputs/Wordweight.txt" ) );
		
		MalletLogger.getLogger(ParallelTopicModel.class.getName()).info("finished printing");
		
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
	public int getNumTopics()
	{

		ParallelTopicModel lda = new ParallelTopicModel( 50, 50.0, 0.1 );
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
}
