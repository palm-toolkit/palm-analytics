package de.rwth.i9.palm.analytics.algorithm.lda;

import cc.mallet.topics.tui.*;
import cc.mallet.util.*;
import de.rwth.i9.palm.analytics.config.AppConfig;
import cc.mallet.types.*;
import cc.mallet.pipe.*;
import cc.mallet.pipe.iterator.*;
import cc.mallet.topics.*;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = AppConfig.class, loader = AnnotationConfigContextLoader.class )
public class LDAJob
{	
@Test
public void test() throws Exception {
	
	try {
		
		// Get the data from a directory and convert it into mallet format 
		// Use importData Class to make input traverse through the following pipes
		// 		1.	Input2CharSequence
		//		2.	CharSequence2TokenSequence
		//		3.	TokenSequenceLowercase
		//		4.	TokenSequenceRemoveStopwords
		//		5.	TokenSequence2FeatureSequence
		
		importData importer = new importData();
		InstanceList instances = importer.readDirectory(new File("C:/Users/Piro/Desktop/MyOutputs"));
		instances.save( new File("C:/Users/Piro/Desktop/Outputs/myoutputs.mallet") );
		
		File texting = new File("C:/Users/Piro/Desktop/Outputs/myoutputs.mallet");
		InstanceList training = InstanceList.load (texting);
		
		// define number of Topics 
		int numTopics = 147;
		
		// call ParallelTopicModel class to run simple parallel version of LDA with
		// alpha=0.1 (sumalpha)50 beta=0,01 numTopics=5
		ParallelTopicModel lda = new ParallelTopicModel (numTopics, 50.0 , 0.01);
		lda.printLogLikelihood = true;
		
		// Assign the number of threads to Maximally number of cores of your pc
		lda.setNumThreads(1);
					
		// Assign an optimizing factor of 50 and let the number of words be 10 per each topic
		lda.setTopicDisplay(50, 11);
		lda.addInstances(training);
		
		// Calculate the lda running time for single and multi-thread calls
		lda.estimate();
		
		// Start the printing of results. Other methods can be called 
		MalletLogger.getLogger(ParallelTopicModel.class.getName()).info("printing state");
		
		// get the top words for each topic 
		lda.printTopWords( new File("C:/Users/Piro/Desktop/Outputs/TopWords.txt"), 11, false );
		
		// get the topic distribution for each of the files
		lda.printDocumentTopics( new File("C:/Users/Piro/Desktop/Outputs/DocTopic.txt") );
		
		// get the weight of each word if needed
		//lda.printTopicWordWeights( new File("C:/Users/Piro/Desktop/Outputs/Wordweight.txt") );
		
		MalletLogger.getLogger(ParallelTopicModel.class.getName()).info("finished printing");
		
		//Start the procedure of merging the contents of file for mapping
		//the documents with their "bag-of-words" topics 
		@SuppressWarnings( "resource" )
		BufferedReader docs = new BufferedReader(new FileReader("C:/Users/Piro/Desktop/Outputs/DocTopic.txt"));
		@SuppressWarnings( "resource" )
		BufferedReader tops = new BufferedReader(new FileReader("C:/Users/Piro/Desktop/Outputs/TopWords.txt"));
		String document, topic;
		
		// get Line by line the bag of words for each of the topics
		List<String> listtopic = new ArrayList<String>();
		while((topic=tops.readLine())!=null){
			listtopic.add( topic );
		}
		String[] topics = listtopic.toArray( new String[0] );
		
		// get Line by line the topic distribution for each of the documents
		List<String> listdoc = new ArrayList<String>();
		while((document=docs.readLine())!=null){
			listdoc.add( document );
		}
		
		// map documents to topic's bag-of-words
		String[] documents = listdoc.toArray(new String[0]);
		for (int i=1; i<documents.length;i++){
			String[] docsplit = documents[i].split( "\\s+" );
			for(int j =0;j<numTopics;j++){
				if (topics[j].startsWith( docsplit[2]) == true){
					System.out.println(docsplit[1] +" -> " + topics[j].substring( 9 ));
						break;
					} 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
   }
	
}
