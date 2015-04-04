package de.rwth.i9.palm.analytics.api;

import org.springframework.beans.factory.annotation.Autowired;

import de.rwth.i9.palm.analytics.algorithm.corephrase.CorePhraseImpl;
import de.rwth.i9.palm.analytics.algorithm.corephrase.CorePhrase;
import de.rwth.i9.palm.analytics.algorithm.cvalue.CValueImpl;
import de.rwth.i9.palm.analytics.algorithm.cvalue.CValue;
import de.rwth.i9.palm.analytics.algorithm.lda.Lda;
import de.rwth.i9.palm.analytics.algorithm.lda.LdaImpl;
import de.rwth.i9.palm.analytics.opennlp.OpenNLP;
import de.rwth.i9.palm.analytics.opennlp.OpenNLPImpl;

/**
 * This interface is a Factory-interface for any analytics
 */
public class PalmAnalyticsImpl implements PalmAnalytics
{
	@Autowired( required = false )
	private CorePhrase iCorePhrase;
	
	@Autowired( required = false )
	private CValue iCValue;

	@Autowired( required = false )
	private Lda iLda;

	@Autowired( required = false )
	private OpenNLP iOpenNLP;

	@Override
	public CorePhrase getCorePhraseAlgorithm()
	{
		if ( this.iCorePhrase == null )
			this.iCorePhrase = new CorePhraseImpl();

		return this.iCorePhrase;
	}

	public CValue getCValueAlgorithm()
	{
		if ( this.iCValue == null )
			this.iCValue = new CValueImpl();

		return this.iCValue;
	}

	@Override
	public Lda getLdaAlgorithm()
	{
		if ( this.iLda == null )
			this.iLda = new LdaImpl();

		return this.iLda;
	}

	@Override
	public OpenNLP getOpenNLPTool()
	{
		if ( this.iOpenNLP == null )
			this.iOpenNLP = new OpenNLPImpl();

		return this.iOpenNLP;
	}

}
