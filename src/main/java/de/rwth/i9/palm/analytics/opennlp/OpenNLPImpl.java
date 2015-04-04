package de.rwth.i9.palm.analytics.opennlp;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import opennlp.tools.coref.Linker;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.parser.Parser;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.rwth.i9.palm.analytics.service.ConfigurationService;

@Service
public class OpenNLPImpl implements OpenNLP
{
	static private final Logger logger = LoggerFactory.getLogger( OpenNlpToolkit.class );
	/**
	 * application configuration
	 */
	@Autowired
	private ConfigurationService configurationService;

	// the named entities
	private static final String[] NAME_TYPES = { "person", "organization", "location" };

	// OpenNLP components, lazily initialized.
	private SentenceDetector sentenceDetector = null;
	private Tokenizer tokenizer = null;
	private POSTagger posTagger = null;
	final private Map<String, TokenNameFinder> _nameFinderMap = new HashMap<String, TokenNameFinder>();
	private Parser _parser = null;
	private Linker _linker = null;

	@Override
	public String[] detectSentences( String content )
	{
		if ( sentenceDetector == null )
		{
			// lazy initialize
			InputStream modelIn = null;
			try
			{
				// sentence detector
				logger.info( "Loading sentence detection model" );
				modelIn = getClass().getResourceAsStream( configurationService.getOpenNLPSentence() );
				final SentenceModel sentenceModel = new SentenceModel( modelIn );
				modelIn.close();
				sentenceDetector = new SentenceDetectorME( sentenceModel );
				logger.info( "done." );
			}
			catch ( final IOException ioe )
			{
				logger.error( "Error loading sentence detector", ioe );
			}
			finally
			{
				if ( modelIn != null )
				{
					try
					{
						modelIn.close();
					}
					catch ( final IOException e )
					{
					}
				}
			}
		}

		// detect sentences
		return sentenceDetector.sentDetect( content );
	}

	@Override
	public String[] tokenize( String sentence )
	{
		if ( tokenizer == null )
		{
			// lazy initialize
			InputStream modelIn = null;
			try
			{
				// tokenizer
				logger.info( "Loading tokenizer model" );
				modelIn = getClass().getResourceAsStream( configurationService.getOpenNLPTokenizer() );
				final TokenizerModel tokenModel = new TokenizerModel( modelIn );
				modelIn.close();
				tokenizer = new TokenizerME( tokenModel );
				logger.info( "done." );
			}
			catch ( final IOException ioe )
			{
				logger.error( "Error loading tokenizer", ioe );
			}
			finally
			{
				if ( modelIn != null )
				{
					try
					{
						modelIn.close();
					}
					catch ( final IOException e )
					{
					}
				}
			}
		}
		return tokenizer.tokenize( sentence );
	}

	@Override
	public String[] tagPartOfSpeech( String[] tokens )
	{
		if ( posTagger == null )
		{
			// lazy initialize
			InputStream modelIn = null;
			try
			{
				// tagger
				logger.info( "Loading part-of-speech model" );
				modelIn = getClass().getResourceAsStream( configurationService.getOpenNLPPos() );
				final POSModel posModel = new POSModel( modelIn );
				modelIn.close();
				posTagger = new POSTaggerME( posModel );
				logger.info( "done." );
			}
			catch ( final IOException ioe )
			{
				logger.error( "Error loading part-of-speech tagger", ioe );
			}
			finally
			{
				if ( modelIn != null )
				{
					try
					{
						modelIn.close();
					}
					catch ( final IOException e )
					{
					}
				}
			}
		}
		return posTagger.tag( tokens );
	}

}
