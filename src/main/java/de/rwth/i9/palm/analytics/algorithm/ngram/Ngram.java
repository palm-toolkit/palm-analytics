package de.rwth.i9.palm.analytics.algorithm.ngram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeSet;

import org.junit.Test;

import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.types.IDSorter;
import cc.mallet.types.InstanceList;
import cc.mallet.types.LabelSequence;
import cc.mallet.util.Randoms;

public class Ngram implements NGrams
{
	@Test
	public void test() throws Exception
	{	
		
		try{

			String path = "C:/Users/Piro/Desktop/";
			
			// call the TopicalNGrams methods with the following parameters by Blei
			// numTopics=100 , alpha = 1.0, beta = 0.001, gamma = 0.1, delta = 0.001, delta1 = 0.2, delta2=1000.0

			TopicalNGrams tng = createModel(path, "Authors", "Trainer", 100); 

			// get the list of unigrams & ngrams

			// tng.printTopWords( 10, true );
			 
			long start = System.nanoTime();
			System.out.println( "________________________GET THE TOPICS AS UNIGRAMS__________________________" );
			String[] u = getStringTopicsUnigrams( tng, 5, false );
			for ( String h : u )
			{
				System.out.println( h );
			}
			 long end = System.nanoTime();
			System.out.println( "Time for getting unigrams: [" + ( end / Math.pow( 10, 9 ) - start / Math.pow( 10, 9 ) ) + " sec]" );

			start = System.nanoTime();
			System.out.println( "________________________GET THE TOPICS AS N-GRAMS__________________________" );
			String[] n = getStringTopicsNgrams( tng, 10, false );
			for ( String h : n )
			{
				System.out.println( h );
			}
			end = System.nanoTime();
			System.out.println( "Time for getting Ngrams: [" + ( end / Math.pow( 10, 9 ) - start / Math.pow( 10, 9 ) ) + " sec]" );
			 
			 start = System.nanoTime();
			 System.out.println( "________________________GET THE TOP X TOPICS FOLLOWED BY THEIR PROPORTIONS__________________________" );
			 String[] d = getStringDocumentTopicIndex(tng, 0.0, -1, true);
			 for (String h : d){
				 System.out.println(h);
			 }
			 end = System.nanoTime();
			 System.out.println("Time for getting x - Topics: [" + (end/Math.pow( 10, 9 )-start/Math.pow( 10, 9 )) + " sec]");
			 
			 start = System.nanoTime();
			 System.out.println( "________________________GET THE TOPIC ASSIGNMENT AS N-GRAMS CONTENT __________________________" );
			for ( int i = 0; i < tng.topics.length; i++ )
			{
				for ( Entry<String, List<String>> entry : getTopicNgramsDocument( tng, i, -1, 0.0, 100, 10, true ).entrySet() )
				{
					 System.out.println((entry.getKey()) + " ->-> " + entry.getValue());
					 }
			 }
			 end = System.nanoTime();
			 System.out.println("Time for getting Topic Assigned presented as N-grams: [" + (end/Math.pow( 10, 9 )-start/Math.pow( 10, 9 )) + " sec]");
			 
			 // used for all the doucuments instead of having a single one
			for ( Entry<String, List<String>> e : getTopicNGramsAllDocuments( tng, -1, 0.0, 100, 10, false ).entrySet() )
			{
				System.out.println( ( e.getKey() ) + " ->-> " + e.getValue() );
			}
			
// 			TO BE TESTED
//			System.out.println( "________________________GET THE TOPIC ASSIGNMENT and PROPORTIONS FOR EACH ID __________________________" );			 
//			for (Entry<String, LinkedHashMap<String, Double>> e : getDocumentTopicDetailMap( tng, -1, 0.0, 100, 10, false).entrySet()){
//				System.out.println("Author Id -> " + e.getKey());
//				for (Entry<String, Double> p : e.getValue().entrySet()){
//					System.out.println( p.getKey() + " -> " + p.getValue() );
//				}
//				System.out.println();
//			}
			start = System.nanoTime();
			System.out.println( "________________________GET THE TOPIC ASSIGNMENT AS UNIGRAMS CONTENT __________________________" );
			for ( int i = 0; i < tng.topics.length; i++ )
			{
				for ( Entry<String, List<String>> entry : getTopicUnigramsDocument( tng, i, -1, 0.0, 100, 10, false ).entrySet() )
				{
					System.out.println( ( entry.getKey() ) + " ->-> " + entry.getValue() );
				}
			}
			end = System.nanoTime();
			System.out.println( "Time for getting Topic Assigned presented as Unigrams: [" + ( end / Math.pow( 10, 9 ) - start / Math.pow( 10, 9 ) ) + " sec]" );
			
			// similar thing as above but used to get all the documents
			// informations as above
			
			start = System.nanoTime();
			System.out.println( "________________________GET THE TOPIC ASSIGNMENT AND PROPORTIONS NOT ORDERED __________________________" );
			for ( Entry<String, List<Double>> entry : getDoumentTopicProportion( tng ).entrySet() )
			{
				System.out.println( ( entry.getKey() ) );
				System.out.println( " **** " );
				for ( Double z : entry.getValue() )
				{
					System.out.println( z );
				}
			}
			end = System.nanoTime();

//			 start = System.nanoTime();
//			 System.out.println( "________________________TEST FOR SIMILARITY MEASUREMENTS AND RECOMMENDED PUBLICATIONS __________________________" );
//			 similarityMeasures similarity = new similarityMeasures();
//			 for (Entry<String, List<Double>> entry : getDoumentTopicProportion(tng).entrySet()){
//				 double[] cos = new double[tng.ilist.size()];
//				 double[] euc = new double[tng.ilist.size()];
//				 double[] pea = new double[tng.ilist.size()];
//				 int i = 0;
//				 //System.out.println(entry.getKey()  + " Compared to : " ); 
//				 System.out.println("Recommended Documents for : " + entry.getKey() );
//				 for (Entry<String, List<Double>> entry1 : getDoumentTopicProportion(tng).entrySet()){
//					 //System.out.print(entry1.getKey() + " ");
//					 cos[i] = similarity.cosineSimilarity( entry.getValue(), entry1.getValue() );
//					 euc[i] = similarity.sqrtEuclidianSimilarity( entry.getValue(), entry1.getValue() );
//					 pea[i] = similarity.pearsonCorrelation( entry.getValue(), entry1.getValue() );
//					 boolean c = false, e = false ,p = false;
//					 if (cos[i] > 0.5 && cos[i] < 1.0){
////						 System.out.print(" C " + entry1.getKey() + " ");
////						 System.out.println(" " + cos[i] + " ");
//						 c = true;
//					 }
//					 if (euc[i] < 0.6 && euc[i] >= 0){
////						 System.out.print(" E " + entry1.getKey() + " ");
////						 System.out.println(" " + euc[i] + " ");
//						 e = true;
//					 }
//					 if (pea[i] > 0.5 && pea[i] < 1.0){
////						 System.out.print(" P " + entry1.getKey() + " ");
////						 System.out.println(" " + pea[i] + " ");
//						 p = true;
//					 }
//					 if ( (c && e) || (e && p) || (c && p) ){
//						 System.out.println(entry1.getKey());
//					 }
//					 i++;
//				 	}
//				 System.out.println("* END RECOMMENDATION FOR THIS PUBLICATION *");
//				 }
//			 end = System.nanoTime();
			 
			for ( Entry<String, List<String>> entry : recommendSimilar( tng ).entrySet() )
			{
				System.out.println( "RECOMMENDATIONS FOR " + entry.getKey() );
				int i = 1;
				for ( String s : entry.getValue() )
				{
					System.out.print( i + " - " );
					System.out.println( "  " + s );
					i++;
				}
			}
			System.out.println( "Time for getting Recommendations based on topic distribution: [" + ( end / Math.pow( 10, 9 ) - start / Math.pow( 10, 9 ) ) + " sec]" );

			System.out.println( "_____________________________________________________________________________________" );
			System.out.println( "TEST THE SIMILARITY RETRIEVAL METHOD" );
			for ( Entry<String, HashMap<List<String>, List<Double>>> entry : similarityResult( tng, 1 ).entrySet() )
			{
				System.out.println( entry.getKey() + " -------------------------------------" );
				for ( Entry<List<String>, List<Double>> d1 : entry.getValue().entrySet() )
				{
					System.out.println( d1.getKey() + " -> " + d1.getValue() );
					System.out.println();
				}
			}
			// run the method to get topic proportions for each doc.
			// printDocTopicprobs(tng, path, "Authors", "Trainer");
		
			System.out.println( "_____________________________________________________________________________________" );

		}
		catch ( Exception e )
		{
			e.printStackTrace();
			}
	}
	
	// USE CMD or the other class ImportDataNgram http://stackoverflow.com/questions/15464111/run-cmd-commands-through-java
	// purpose - {Authors, Publications, Conferences, Years} specify - {Trainer, Infer}
	
	public InstanceList getInstanceData( String path, String purpose, String specify ) throws IOException
	{
		 // Get the data from a directory and convert it into mallet format
		 // Use importData Class to make input traverse through the following pipes
		 // 1. Input2CharSequence
		 // 2. CharSequence2TokenSequence
		 // 3. TokenSequenceLowercase
		 // 4. TokenSequenceRemoveStopwords
		 // 5. TokenSequence2FeatureSequenceBigrams
		ProcessBuilder builder = new ProcessBuilder(
	            "cmd.exe", "/c", "cd \"C:\\mallet\"&& bin\\mallet import-dir --input C:\\Users\\Piro\\Desktop\\"+ purpose + "\\" + purpose +
	            " --keep-sequence-bigrams --remove-stopwords "
	            + "--output C:\\Users\\Piro\\Desktop\\" + purpose + "\\" + purpose + "-N-" + specify + ".mallet");
		builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) { break; }
        }
		InstanceList training = InstanceList.load( new File( path + purpose + "/" + purpose + "-N-" + specify + ".mallet" ) );
		return training;
	}

	// set the number of topics to the model and returns a model constructed by the given number 
	public TopicalNGrams setNumberTopics( int numTopics )
	{
		TopicalNGrams m = new TopicalNGrams(numTopics, 50.0, 0.01, 0.01, 0.03, 0.2, 1000);
			return m;
	}
	
	public int getNumTopics( TopicalNGrams m ){
		return m.numTopics;
	}

	// create a model of reference using training corpus
	public TopicalNGrams createModel(String path, String purpose, String specify, int numTopics) throws IOException{
		
		TopicalNGrams ngram = new TopicalNGrams(numTopics, 50.0, 0.01, 0.01, 0.03, 0.2, 1000);
		InstanceList trained = getInstanceData(path, purpose, specify);
		ngram.estimate(trained, 200, 1, 0, null, new Randoms());
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
	public HashMap<String, List<Double>> getDoumentTopicProportion(TopicalNGrams m){
		HashMap<String, List<Double>> h = new HashMap<String, List<Double>>();
		h = m.documentAllTopicsasMap();
		return h;
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
		
	// create some random files
	public  File createTempDirectory() throws IOException{
		    final File temp;

		    temp = File.createTempFile("temp", Long.toString(System.nanoTime()));

		    if(!(temp.delete()))
		    {
		        throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
		    }

		    if(!(temp.mkdir()))
		    {
		        throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
		    }

		    return (temp);
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

	// this methods maps the best topic with the suitable document
	// used only for initial version - files as output (File Version)
	public void DocTopicMapper(String path, String purpose, String specify) throws IOException{
		@SuppressWarnings( "resource" )
		BufferedReader docs = new BufferedReader(new FileReader(path + purpose + "/DocTopic-" + purpose + "-" + specify + ".txt"));
		@SuppressWarnings( "resource" )
		BufferedReader tops = new BufferedReader(new FileReader(path + purpose + "/TopWords-" + purpose + "-" + specify + ".txt"));
		String document, topic;
		
		// get Line by line the bag of words for each of the topics
		List<String> listtopic = new ArrayList<String>();
		while(( topic = tops.readLine())!=null){
			listtopic.add( topic );
		}
		
		// get Line by line the topic distribution for each of the documents
		List<String> listdoc = new ArrayList<String>();
		while((document=docs.readLine())!=null){
			listdoc.add( document );
		}
		
		// map documents to topic's bag-of-words
		for (int i=1; i<listdoc.size();i++){
			int numTopics = 50;
			String[] docsplit = listdoc.get( i ).split( "\\s+" );
			for(int j =0;j<numTopics;j++){
				if (listtopic.get( j ).startsWith( docsplit[2]) == true){
					System.out.println(docsplit[1] +" -> " + listtopic.get( j ).substring( 10 ));
						break;			
						
					} 
				}
			}
		}
		
	// Returns a map <DocumentID, Top Ngrams Topic Assigned to it> which shows the topic assigned to a specific document with given ID
	// When calling max = -1, threshold = 0.05, 
	public HashMap<String, List<String>> getTopicNgramsDocument( TopicalNGrams m, int docID, int max, double threshold, int numTopics, int numWords, boolean weight ){
		
			HashMap<String, List<String>> h = new HashMap<String, List<String>>();
			List<String> topdoc = new ArrayList<String>();//getListDocumentTopic(m,threshold,max,weight);
		List<String> topics = getListTopicsNgrams( m, numWords, false );
			 int docLen;
			    double topicDist[] = new double[m.numTopics];
			      docLen = m.topics[docID].length;
			      for (int ti = 0; ti < numTopics; ti++)
			        topicDist[ti] = (((float)m.docTopicCounts[docID][ti])/docLen);
			      if (max < 0) max = numTopics;
			      for (int tp = 0; tp < max; tp++) {
			        double maxvalue = 0;
			        int maxindex = -1;
			        for (int ti = 0; ti < numTopics; ti++)
			          if (topicDist[ti] > maxvalue) {
			            maxvalue = topicDist[ti];
			            maxindex = ti;
			          }
			        if (maxindex == -1 || topicDist[maxindex] < threshold)
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
			h.put(m.ilist.get(docID).getSource().toString().replace( "\\",";").split( ";" )[6].replace( ".txt", "" ), topdoc);
			return h;
		}

	public HashMap<String, List<String>> getTopicNGramsAllDocuments( TopicalNGrams m, int max, double threshold, int numTopics, int numWords, boolean weight )
	{
		HashMap<String, List<String>> alldocumentTopics = new HashMap<>();
		for ( int i = 0; i < m.topics.length; i++ )
		{
			for ( Entry<String, List<String>> topicsOneDoc : getTopicNgramsDocument( m, i, max, threshold, numTopics, numWords, weight ).entrySet() )
			{
				alldocumentTopics.put( topicsOneDoc.getKey(), topicsOneDoc.getValue() );
			}
		}
		return alldocumentTopics;
	}

	// Returns a map <DocumentID, Top Unigrams Topic Assigned to it> which shows the topic assigned to a specific document with given ID
	// When calling max = -1, threshold = 0.05, 
	public HashMap<String,  List<String>> getTopicUnigramsDocument( TopicalNGrams m, int docID, int max, double threshold, int numTopics, int numWords, boolean weight ){
		
		HashMap<String, List<String>> h = new HashMap<String, List<String>>();
		List<String> topdoc = new ArrayList<String>();//getListDocumentTopic(m,threshold,max,weight);
		List<String> topics = getListTopicsUnigrams(m, numWords, false);
		
		
		 int docLen;
		    double topicDist[] = new double[m.numTopics];
		      docLen = m.topics[docID].length;
		      for (int ti = 0; ti < numTopics; ti++)
		        topicDist[ti] = (((float)m.docTopicCounts[docID][ti])/docLen);
		      if (max < 0) max = numTopics;
		      for (int tp = 0; tp < max; tp++) {
		        double maxvalue = 0;
		        int maxindex = -1;
		        for (int ti = 0; ti < numTopics; ti++)
		          if (topicDist[ti] > maxvalue) {
		            maxvalue = topicDist[ti];
		            maxindex = ti;
		          }
		        if (maxindex == -1 || topicDist[maxindex] < threshold)
		          break;
		        //(maxindex+" "+topicDist[maxindex]+" ");
		        if(weight){
		        	 topdoc.add( topics.get( maxindex ) + " _-_ " + topicDist[maxindex]);
		        } 
		        else {
				topdoc.add( topics.get( maxindex ) );
		        }
		        topicDist[maxindex] = 0;
		        
		       
		      }
		      
		h.put(m.ilist.get(docID).getSource().toString().replace( "\\",";").split( ";" )[6].replace( ".txt", "" ), topdoc);
		return h;
	}
	
	//
	public HashMap<String, List<String>> getTopicUnigramsAllDocuments( TopicalNGrams m, int max, double threshold, int numTopics, int numWords, boolean weight )
	{
		HashMap<String, List<String>> alldocumentTopics = new HashMap<>();
		for ( int i = 0; i < m.topics.length; i++ )
		{
			for ( Entry<String, List<String>> topicsOneDoc : getTopicUnigramsDocument( m, i, max, threshold, numTopics, numWords, weight ).entrySet() )
			{
				alldocumentTopics.put( topicsOneDoc.getKey(), topicsOneDoc.getValue() );
			}
		}
		return alldocumentTopics;

	}
	//produce a Map wish holds <DocumentId, List<TopicWeight, String of Words>> 
	public HashMap<String, List<String>> getAllDocumentTopics( ParallelTopicModel m, int docID, int max, double threshold, int numTopics ){
			
			HashMap<String, List<String>> h = new HashMap<String, List<String>>();
			List<String> topics = new ArrayList<String>();
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
			
			// m.data.get(docID).instance.getName(); // can be also
			// model.data.get(doc).instance.getID(); or whatever :))))
			for ( int i = 0; i < max; i++ )
			{
				if ( sortedTopics[i].getWeight() < threshold ){	break; }
			topicID = sortedTopics[i].getID();
			topicWeight = sortedTopics[i].getWeight();
			topics.add( topicID + "-" + topicWeight );
			}
			h.put( m.data.get(docID).instance.getName()+ "", topics );
			Arrays.fill( topicCounts, 0 );
			return h;
		}

	// used to get the sorted words per topic
	public ArrayList<TreeSet<IDSorter>> getSortedWords( ParallelTopicModel m, int numTopics ){

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
	
	// creates a hashmap <String, List<Double>> holding for each document, its distance with other documents (can be used later on for publications and authors)
	// the second parameter is used to specify which similarity measurement will be used among Euclidian(0), Cosine(1), Pearson(2) 
	public HashMap<String, HashMap<List<String>, List<Double>>> similarityResult (TopicalNGrams tng, int choise){
		
		HashMap<String, HashMap<List<String>, List<Double>>> distance = new HashMap<String, HashMap<List<String>, List<Double>>>();
		 
		similarityMeasures similarity = new similarityMeasures();
		 for (Entry<String, List<Double>> entry : getDoumentTopicProportion(tng).entrySet()){
			 List<String> name = new ArrayList<String>();
			 int i=0;
			 HashMap<List<String>, List<Double>> temp = new HashMap<List<String>, List<Double>>();
			 double[] simcalc = new double[tng.ilist.size()];
			 List<Double> sim = new ArrayList<Double>();
			 for (Entry<String, List<Double>> entry1 : getDoumentTopicProportion(tng).entrySet()){
				 
				 switch (choise){
				 case 0: {
					 simcalc[i] = similarity.sqrtEuclidianSimilarity( entry.getValue(), entry1.getValue() );
				 	}
				 case 1: {
					 simcalc[i] = similarity.cosineSimilarity( entry.getValue(), entry1.getValue() );
				 	}
				 case 2:{
					 simcalc[i] = similarity.pearsonCorrelation( entry.getValue(), entry1.getValue() );
					 }
				 }
				 sim.add( simcalc[i] );
				 i++;
				 name.add( entry1.getKey() );
			 }
			 temp.put( name, sim );
			 distance.put( entry.getKey(), temp);
		 }
		return distance;
	}
	
	// based on similarity results, we recommend most similar publications/authors 
	// criteria for this is that a pub. is considered "similar" by at least two similarity measures 
	public HashMap<String, List<String>> recommendSimilar(TopicalNGrams tng){
		HashMap<String, List<String>> result = new HashMap<String, List<String>>();
		similarityMeasures similarity = new similarityMeasures();
		for (Entry<String, List<Double>> entry : getDoumentTopicProportion(tng).entrySet()){
			List<String> recommend = new ArrayList<String>();
			 double[] cos = new double[tng.ilist.size()];
			 double[] euc = new double[tng.ilist.size()];
			 double[] pea = new double[tng.ilist.size()];
			 int i = 0;
			 for (Entry<String, List<Double>> entry1 : getDoumentTopicProportion(tng).entrySet()){
				 
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
				 if ( (c && e) || (e && p) || (c && p) ){
					 recommend.add( entry1.getKey() );
				 }
				 i++;
			 	}
			 result.put( entry.getKey(), recommend );
			 
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

}