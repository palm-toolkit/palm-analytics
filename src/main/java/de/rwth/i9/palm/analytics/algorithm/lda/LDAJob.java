package de.rwth.i9.palm.analytics.algorithm.lda;

import cc.mallet.util.*;
import de.rwth.i9.palm.analytics.config.AppConfig;
import cc.mallet.types.*;
import cc.mallet.topics.*;

import java.io.*;
import java.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = AppConfig.class, loader = AnnotationConfigContextLoader.class )
public class LDAJob
{	
@Test
public void test() throws Exception {
	
	//try {
		
		// Get the data from a directory and convert it into mallet format 
		// Use importData Class to make input traverse through the following pipes
		// 		1.	Input2CharSequence
		//		2.	CharSequence2TokenSequence
		//		3.	TokenSequenceLowercase
		//		4.	TokenSequenceRemoveStopwords
		//		5.	TokenSequence2FeatureSequence
		
	// logger
		final Logger logger = LoggerFactory.getLogger( LDAJob.class );
		
		String path = "C:/Users/Piro/Desktop/";
		
		// import data from db (can be authors, publications, yearly results, conferences)
		// DONE TILL 42
		importData importer = new importData();
		InstanceList instances = importer.readDirectory(new File(path+"MyOutputs"));
		instances.save( new File("C:/Users/Piro/Desktop/Outputs/myoutputs.mallet") );
		
		File texting = new File("C:/Users/Piro/Desktop/Outputs/myoutputs.mallet");
		InstanceList training = InstanceList.load (texting);
		
		// define number of Topics 
		// DONE the set and get methods
		// private int numTopics = 50;
		
		// define aplpha; beta
		// DONE by Mallet to set the values
		
		
		
		// call ParallelTopicModel class to run simple parallel version of LDA with
		// alpha=0.1 (sumalpha)50 beta=0,01 numTopics=5
		
		// use the method createModel to create the LDA model with the assigned parameters
		// DONE till model estimation - Line 74
		ParallelTopicModel lda = new ParallelTopicModel (50, 50.0 , 0.01);
		lda.printLogLikelihood = true;
		
		// Assign the number of threads to Maximally number of cores of your pc
		lda.setNumThreads(1);
					
		// Assign an optimizing factor of 50 and let the number of words be 10 per each topic
		lda.setTopicDisplay(50, 11);
		lda.addInstances(training);
		
		// Calculate the lda running time for single and multi-thread calls
		lda.estimate();
		
		// Start the printing of results. Other methods can be called 
		// MalletLogger.getLogger(ParallelTopicModel.class.getName()).info("printing state");
		
		// get the top words for each topic 
		lda.printTopWords( new File("C:/Users/Piro/Desktop/Outputs/TopWords-NEW.txt"), 11, false );
		
		// get the topic distribution for each of the files
		lda.printDocumentTopics( new File("C:/Users/Piro/Desktop/Outputs/DocTopic-NEW.txt") );
		
		// get the weight of each word if needed
		//lda.printTopicWordWeights( new File("C:/Users/Piro/Desktop/Outputs/Wordweight.txt") );
		
		MalletLogger.getLogger(ParallelTopicModel.class.getName()).info("finished printing");
		
		//Start the procedure of merging the contents of file for mapping
		//the documents with their "bag-of-words" topics 
		@SuppressWarnings( "resource" )
		BufferedReader docs = new BufferedReader(new FileReader("C:/Users/Piro/Desktop/Outputs/DocTopic-NEW.txt"));
		@SuppressWarnings( "resource" )
		BufferedReader tops = new BufferedReader(new FileReader("C:/Users/Piro/Desktop/Outputs/TopWords-NEW.txt"));
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
//		} catch (Exception e) {
//			e.printStackTrace();
//		}	
   }

// filename specifies the list of files that will come from db 
// purpose specifies the instances of analysis (author, publication, conference, yearly)
// returns the list of instances that will serve as input to LDA for analysis
public InstanceList getData(File filename, String purpose)
{		
		String path = "C:/Users/Piro/Desktop/Outputs/";
		importData importer = new importData( );
		InstanceList instances = importer.readDirectory(filename);
		instances.save( new File( path + purpose + ".mallet") );
		File data = new File(path + purpose + ".mallet");
		InstanceList training = InstanceList.load (data);
		return training;
	}

// get the number of topics 
//public int getNumbertopics(){
//	return numtopics;
//	
//}

// set the number of topics
//public int setNumberTopics(final int numTopics){
//	if (numTopics <= 0){
//		Logger.info("Number of Topics should be more than 0.");
//	} else {
//		return this.numTopics = numTopics;}
//}

public void createModel(int numTopics, int numWords){
	
	ParallelTopicModel lda = new ParallelTopicModel(numTopics, 50.0, 1.0);
	lda.printLogLikelihood = true;
	lda.setTopicDisplay(numTopics, numWords);
	
	InstanceList trained = getData(new File("C:/Users/Piro/Desktop/Outputs/MyOutputs"), "Authors");
	lda.addInstances(trained);
	try
	{
		lda.estimate();
	}
	catch ( IOException e )
	{
		e.printStackTrace();
	}
}
}
	
