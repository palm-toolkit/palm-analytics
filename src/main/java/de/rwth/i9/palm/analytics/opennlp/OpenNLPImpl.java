package de.rwth.i9.palm.analytics.opennlp;

import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.rwth.i9.palm.analytics.service.ConfigurationService;

@Service
public class OpenNLPImpl implements IOpenNLP
{
	static private final Logger logger = LoggerFactory.getLogger( OpenNlpToolkit.class );

	@Autowired
	private ConfigurationService configurationService;

	/**
	 * OpenNLP components, lazily initialized.
	 * <ul>
	 * <li>{@link #detectSentences(String)}</li>
	 * </ul>
	 */
	private SentenceDetector _sentenceDetector = null;

	@Override
	public String[] detectSentences( String content )
	{
		if ( _sentenceDetector == null )
		{
			// lazy initialize
			InputStream modelIn = null;
			try
			{
				// sentence detector
				final String location = configurationService.getopennlpSentence();
				logger.info( "Loading sentence detection model: " + location );
				modelIn = getClass().getResourceAsStream( location );
				final SentenceModel sentenceModel = new SentenceModel( modelIn );
				modelIn.close();
				_sentenceDetector = new SentenceDetectorME( sentenceModel );
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
		return _sentenceDetector.sentDetect( content );
	}

}
