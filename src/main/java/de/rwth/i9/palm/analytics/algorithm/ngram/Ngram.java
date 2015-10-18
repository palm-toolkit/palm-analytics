package de.rwth.i9.palm.analytics.algorithm.ngram;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import cc.mallet.topics.TopicalNGrams;
import cc.mallet.types.InstanceList;
import cc.mallet.util.Randoms;
import de.rwth.i9.palm.analytics.config.AppConfig;
import cc.mallet.util.CommandOption;
import cc.mallet.util.Randoms;
import cc.mallet.types.InstanceList;
import cc.mallet.types.FeatureSequence;
import cc.mallet.topics.*;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = AppConfig.class, loader = AnnotationConfigContextLoader.class )
public class Ngram
{
	@Test
	public void test() throws Exception
	{	
		// specify the file from which the data will be gathered
		InstanceList ilist = InstanceList.load (new File("C:/Users/Piro/Desktop/Outputs/myngramdb.mallet"));
		
		System.out.println ("Data loaded.");
		
		// call the TopicalNGrams methods with the followong parameters as specified by Blei
		// numTopics=10 , alphas = 1.0, beta = 0.001, gamma = 0.1, delta = 0.001, delta1 = 0.2, delta2=1000.0
		TopicalNGrams tng = new TopicalNGrams (10,1.0, 0.001,0.1,0.001,0.2, 1000.0);
		
		// estimate the model parameters and prepare the results
		tng.estimate (ilist, 200, 1, 0, null, new Randoms());
		
		// get the list of unigrams & ngrams
		tng.printTopWords( 10, true );
		
		// assign a file for the output of topic proportions
		PrintWriter out = new PrintWriter (new File("C:/Users/Piro/Desktop/Outputs/DocTopic-Ngrams.txt"));
		
		// run the method to get topic proportions for each doc.
		tng.printDocumentTopics (out, 0.0, -1);
		out.close();
		}

	}
