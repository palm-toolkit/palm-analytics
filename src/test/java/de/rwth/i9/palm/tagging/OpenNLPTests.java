package de.rwth.i9.palm.tagging;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import de.rwth.i9.palm.analytics.api.PalmAnalytics;
import de.rwth.i9.palm.analytics.config.AppConfig;

/**
 * Test all functionalities of openNLP through interface
 * 
 * @author sigit
 *
 */

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = AppConfig.class, loader = AnnotationConfigContextLoader.class )
public class OpenNLPTests
{
	@Autowired
	PalmAnalytics iAnalytics;

	@Before
	public void init()
	{
		assertThat( iAnalytics, is( not( ( nullValue() ) ) ) );
	}

	/**
	 * Test whether the OpenNLP sentences detector split sentence correctly
	 */
	@Test
	public void testSentenceDetector()
	{
		// Example taken from:
	      // http://sourceforge.net/apps/mediawiki/opennlp/index.php?title=Sentence_Detector
	      final String content =
	            "Pierre Vinken, 61 years old, will join the board as a nonexecutive"
	            + " director Nov. 29. Mr. Vinken is chairman of Elsevier N.V., the"
	            + " Dutch publishing group. Rudolph Agnew, 55 years old and former"
	            + " chairman of Consolidated Gold Fields PLC, was named a director"
	            + " of this British industrial conglomerate."
	            // added this for more boundary cases
	            + " Those contraction-less sentences don't have boundary/odd"
	            + " cases...this one does.";
	      
	      final String[] expected = new String[] {
	            "Pierre Vinken, 61 years old, will join the board as a nonexecutive"
	               + " director Nov. 29.",
	            "Mr. Vinken is chairman of Elsevier N.V., the Dutch publishing"
	               + " group.",
	            "Rudolph Agnew, 55 years old and former chairman of Consolidated"
	               + " Gold Fields PLC, was named a director of this British"
	               + " industrial conglomerate.",
	            "Those contraction-less sentences don't have boundary/odd"
	               + " cases...this one does."
	      };
	      
	      final String[] sentences = iAnalytics.getOpenNLPTool().detectSentences(content);
	      // compare each sentence against expectations
	      assertEquals("Incorrect number of sentences detected.",
	            expected.length, sentences.length);
	      for (int i=0; i < expected.length; i++) {
	         assertEquals("Unexpected sentence content",
	               expected[i], sentences[i]);
	      }
	}

}
