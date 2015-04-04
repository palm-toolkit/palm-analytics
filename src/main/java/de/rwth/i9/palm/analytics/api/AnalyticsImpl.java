package de.rwth.i9.palm.analytics.api;

import org.springframework.beans.factory.annotation.Autowired;

import de.rwth.i9.palm.analytics.algorithm.corephrase.CorePhraseImpl;
import de.rwth.i9.palm.analytics.algorithm.corephrase.ICorePhrase;
import de.rwth.i9.palm.analytics.algorithm.cvalue.CValueImpl;
import de.rwth.i9.palm.analytics.algorithm.cvalue.ICValue;
import de.rwth.i9.palm.analytics.algorithm.lda.ILda;
import de.rwth.i9.palm.analytics.algorithm.lda.LdaImpl;
import de.rwth.i9.palm.analytics.opennlp.IOpenNLP;
import de.rwth.i9.palm.analytics.opennlp.OpenNLPImpl;

/**
 * This interface is a Factory-interface for any analytics
 */
public class AnalyticsImpl implements IAnalytics
{
	@Autowired( required = false )
	private ICorePhrase iCorePhrase;
	
	@Autowired( required = false )
	private ICValue iCValue;

	@Autowired( required = false )
	private ILda iLda;

	@Autowired( required = false )
	private IOpenNLP iOpenNLP;

	@Override
	public ICorePhrase getCorePhraseAlgorithm()
	{
		if ( this.iCorePhrase == null )
			this.iCorePhrase = new CorePhraseImpl();

		return this.iCorePhrase;
	}

	public ICValue getCValueAlgorithm()
	{
		if ( this.iCValue == null )
			this.iCValue = new CValueImpl();

		return this.iCValue;
	}

	@Override
	public ILda getLdaAlgorithm()
	{
		if ( this.iLda == null )
			this.iLda = new LdaImpl();

		return this.iLda;
	}

	@Override
	public IOpenNLP getOpenNLPTool()
	{
		if ( this.iOpenNLP == null )
			this.iOpenNLP = new OpenNLPImpl();

		return this.iOpenNLP;
	}

}
