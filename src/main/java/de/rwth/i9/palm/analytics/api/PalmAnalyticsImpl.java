package de.rwth.i9.palm.analytics.api;

import org.springframework.beans.factory.annotation.Autowired;

import de.rwth.i9.palm.analytics.algorithm.corephrase.CorePhrase;
import de.rwth.i9.palm.analytics.algorithm.corephrase.CorePhraseImpl;
import de.rwth.i9.palm.analytics.algorithm.cvalue.CValue;
import de.rwth.i9.palm.analytics.algorithm.cvalue.CValueImpl;
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
	private CorePhrase corePhrase;
	
	@Autowired( required = false )
	private CValue cValue;

	@Autowired( required = false )
	private Lda lda;

	@Autowired( required = false )
	private OpenNLP openNLP;

	@Override
	public CorePhrase getCorePhraseAlgorithm()
	{
		if ( this.corePhrase == null )
			this.corePhrase = new CorePhraseImpl();

		return this.corePhrase;
	}

	public CValue getCValueAlgorithm()
	{
		if ( this.cValue == null )
			this.cValue = new CValueImpl();

		return this.cValue;
	}

	@Override
	public Lda getLdaAlgorithm()
	{
		if ( this.lda == null )
			this.lda = new LdaImpl();

		return this.lda;
	}

	@Override
	public OpenNLP getOpenNLPTool()
	{
		if ( this.openNLP == null )
			this.openNLP = new OpenNLPImpl();

		return this.openNLP;
	}

}
