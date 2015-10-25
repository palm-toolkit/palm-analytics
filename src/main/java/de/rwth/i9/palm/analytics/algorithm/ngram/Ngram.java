package de.rwth.i9.palm.analytics.algorithm.ngram;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import cc.mallet.topics.TopicalNGrams;
import cc.mallet.types.InstanceList;
import cc.mallet.util.Randoms;
import de.rwth.i9.palm.analytics.algorithm.lda.importData;
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
		
		try{
			
		// Get the data from a directory and convert it into mallet format 
		// Use importData Class to make input traverse through the following pipes
		// 		1.	Input2CharSequence
		//		2.	CharSequence2TokenSequence
		//		3.	TokenSequenceLowercase
		//		4.	TokenSequenceRemoveStopwords
		//		5.	TokenSequence2FeatureSequenceWithBigrams
		
//		importDataNgram importer = new importDataNgram();
//		InstanceList instances = importer.readDirectory(new File("C:/Users/Piro/Desktop/Documents"));
//		instances.save( new File("C:/Users/Piro/Desktop/Outputs/myoutputs-ngrams.mallet") );
		
		// specify the file from which the data will be gathered
		File texting = new File("C:/Users/Piro/Desktop/Outputs/myNewNgramDB.mallet");
		InstanceList training = InstanceList.load (texting);
		
		System.out.println ("Data loaded.");
		
		// call the TopicalNGrams methods with the followong parameters as specified by Blei
		// numTopics=10 , alphas = 1.0, beta = 0.001, gamma = 0.1, delta = 0.001, delta1 = 0.2, delta2=1000.0
		TopicalNGrams tng = new TopicalNGrams (100,1.0, 0.001,0.1,0.001,0.2, 1000.0);
		
		// estimate the model parameters and prepare the results
		tng.estimate (training, 200, 1, 0, null, new Randoms());
		
		// get the list of unigrams & ngrams
		// later we get the content of console and add it to a file for further processing
		tng.printTopWords( 10, true );
		File file = new File("C:/Users/Piro/Desktop/Outputs/Uni-Ngrams.txt"); //Your file
		FileOutputStream fos = new FileOutputStream(file);
		PrintStream ps = new PrintStream(fos);
		System.setOut(ps);
		

		
		// assign a file for the output of topic proportions
		PrintWriter out = new PrintWriter (new File("C:/Users/Piro/Desktop/Outputs/DocTopic-Ngrams.txt"));
		
		// run the method to get topic proportions for each doc.
		tng.printDocumentTopics (out, 0.0, -1);
		out.close();
		
		}	catch (Exception e) {
		e.printStackTrace();
			}
		
	        
//		try {
//		      //create a buffered reader that connects to the console, we use it so we can read lines
//		      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//
//		      //read a line from the console
//		      String lineFromInput = in.readLine();
//
//		      //create an print writer for writing to a file
//		      PrintWriter out = new PrintWriter(new FileWriter("output.txt"));
//
//		      //output to the file a line
//		      out.println(lineFromInput);
//
//		      //close the file (VERY IMPORTANT!)
//		      out.close();
//		   }
//		      catch(IOException e1) {
//		        System.out.println("Error during reading/writing");
//		   }
//		
		
//		PrintWriter outconsole = new PrintWriter (new File ("C:/Users/Piro/Desktop/Outputs/Uni-Ngrams.txt"), "UTF-8");
//		LoggedPrintStream cons = LoggedPrintStream.create( System.out );
//		System.setOut( cons );
//		System.setOut( cons.underlying );
//		 System.out.println("----- Log for System.out: -----\n" + cons.buf);
		
//		//create pairs of Piped input and output streasm for std out and std err
//		final PipedInputStream outPipedInputStream = new PipedInputStream();
//		final PrintStream outPrintStream = new PrintStream(new PipedOutputStream(
//		    outPipedInputStream));
//		final BufferedReader outReader = new BufferedReader(
//		    new InputStreamReader(outPipedInputStream));
//	
//		final PrintStream originalOutStream = System.out;
//
//		final Thread writingThread = new Thread(new Runnable() {
//		    @Override
//		    public void run() {
//		        try {
//		            System.setOut(outPrintStream);
//		            // You could also set the System.in here using a
//		            // PipedInputStream
//		            
//		            // Even better would be to refactor DoSomething to accept
//		            // PrintStream objects as parameters to replace all uses of
//		            // System.out and System.err. DoSomething could also have 
//		            // an overload with DoSomething() calling: 
//		          
//		        } finally {
//		            // may also want to add a catch for exceptions but it is
//		            // essential to restore the original System output and error
//		            // streams since it can be very confusing to not be able to
//		            // find System.out output on your console
//		            System.setOut(originalOutStream);
//		
//		            //You must close the streams which will auto flush them
//		            outPrintStream.close();
//	
//		        }
//		    } // end run()
//		}); // end writing thread
//		//Start the code that will write into streams
//		writingThread.start();
//		String line;
//		final List<String> completeOutputStreamContent = new ArrayList<String>();
//		while ((line = outReader.readLine()) != null) {
//		    completeOutputStreamContent.add(line);
//		} // end reading output stream
	}
}