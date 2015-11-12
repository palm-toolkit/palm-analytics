package de.rwth.i9.palm.analytics.algorithm.ngram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import cc.mallet.topics.TopicalNGrams;
import cc.mallet.types.InstanceList;
import cc.mallet.types.InstanceList;
import cc.mallet.util.Randoms;
import cc.mallet.util.Randoms;
import de.rwth.i9.palm.analytics.config.AppConfig;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = AppConfig.class, loader = AnnotationConfigContextLoader.class )
public class Ngram
{
	@Test
	public void test() throws Exception
	{	
		
		try{
			
			// Get the data from a directory and convert it into mallet format
			// Use importData Class to make input traverse through the following
			// pipes
			// 1. Input2CharSequence
			// 2. CharSequence2TokenSequence
			// 3. TokenSequenceLowercase
			// 4. TokenSequenceRemoveStopwords
			// 5. TokenSequence2FeatureSequenceWithBigrams

			// importDataNgram importer = new importDataNgram();
			// InstanceList instances = importer.readDirectory(new
			// File("C:/Users/Piro/Desktop/Documents"));
			// instances.save( new
			// File("C:/Users/Piro/Desktop/Outputs/myoutputs-ngrams.mallet") );

			String path = "C:/Users/Piro/Desktop/";

			// specify the file from which the data will be gathered
			// DONE BY USING THE METHOD BELLOW
			File texting = new File( "C:/Users/Piro/Desktop/Outputs/myNewNgramDB.mallet" );
			InstanceList training = InstanceList.load( texting );

			System.out.println( "Data loaded." );

			// call the TopicalNGrams methods with the followong parameters as
			// specified by Blei
			// numTopics=10 , alphas = 1.0, beta = 0.001, gamma = 0.1, delta =
			// 0.001, delta1 = 0.2, delta2=1000.0

			int numTopics = 100;

			TopicalNGrams tng = new TopicalNGrams( numTopics, 1.0, 0.001, 0.1, 0.001, 0.2, 1000.0 );

			// estimate the model parameters and prepare the results
			tng.estimate( training, 200, 1, 0, null, new Randoms() );

			// get the list of unigrams & ngrams
			// later we get the content of console and add it to a file for
			// further processing
			tng.printTopWords( 10, true );
			// File file = new
			// File("C:/Users/Piro/Desktop/Outputs/Uni-Ngrams.txt");
			// FileOutputStream fos = new FileOutputStream(file);
			// PrintStream ps = new PrintStream(fos);
			// System.setOut(ps);

			// assign a file for the output of topic proportions
			PrintWriter out = new PrintWriter( new File( "C:/Users/Piro/Desktop/Outputs/DocTopic-Ngrams.txt" ) );

			// run the method to get topic proportions for each doc.
			tng.printDocumentTopics( out, 0.0, -1 );
			out.close();

			System.out.println( "_____________________________________________________________________________________" );
			// Start the procedure of merging the contents of file for mapping
			// the documents with their "bag-of-words" topics

			@SuppressWarnings( "resource" )
			BufferedReader docs = new BufferedReader( new FileReader( "C:/Users/Piro/Desktop/Outputs/DocTopic-Ngrams.txt" ) );
			@SuppressWarnings( "resource" )
			BufferedReader tops = new BufferedReader( new FileReader( "C:/Users/Piro/Desktop/Outputs/Uni-Ngrams.txt" ) );
			String document, topic;

			// get line by line the bag of words for each of the topics starting
			// from the bottom
			List<String> listtopic = new ArrayList<String>();
			while ( ( topic = tops.readLine() ) != null )
			{
				if ( topic.contains( ":" ) != true )
				{
					if ( ( topic.contains( "Topic" ) && topic.contains( "bigrams" ) ) || ( topic.contains( "_" ) && topic.contains( "." ) ) )
					{
						listtopic.add( topic );
					}
				}
			}

			for ( int i = 1; i < listtopic.size(); i++ )
			{
				String numbis = listtopic.get( i );
				if ( numbis.contains( "Topic" ) )
				{
					listtopic.set( i, ( numbis.split( "\\s+" )[1] ) );
				}
			}

			for ( String i : listtopic )
			{
				System.out.println( i );
			}

			// get Line by line the topic distribution for each of the documents
			List<String> listdoc = new ArrayList<String>();
			while ( ( document = docs.readLine() ) != null )
			{
				listdoc.add( document );
			}

			// connect the two files so that one has the mapping document ->
			// phrases
			for ( int i = 1; i < listdoc.size() - 1; i++ )
			{
				String docu[] = listdoc.get( i ).split( ".txt" );
				if ( docu[1] != null )
				{
					Integer index = listtopic.indexOf( docu[1].split( "\\s+" )[1] );
					Integer index2 = listtopic.indexOf( ( Integer.parseInt( docu[1].split( "\\s+" )[1] ) + 1 ) + "" );
					System.out.print( docu[0] + " -> " );
					for ( int j = index + 1; j < index2; j++ )
					{
						System.out.print( listtopic.get( j ) + " " );
					}
					System.out.println();
				}
				else
				{
					System.out.print( docu[0] + " ->  NULL" );
				}

			}

		}
		catch ( Exception e )
		{
			e.printStackTrace();
			}
	}

	// this method is used to get data and transforms it to a .mallet file
	// get the .mallet file and returns InstanceList ready for further
	// processing
	public InstanceList getInstanceData( String path, String purpose, String specify )
	{
		importDataNgram importer = new importDataNgram();
		InstanceList instances = importer.readDirectory( new File( path + "/" + purpose + "/" + purpose ) ); // +
																												// "/"+
																												// specify
																												// ));
		instances.save( new File( path + purpose + "/" + purpose + "-" + specify + ".mallet" ) );
		InstanceList training = InstanceList.load( new File( path + purpose + "/" + purpose + "-" + specify + ".mallet" ) );
		return training;
	}

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
}