package de.rwth.i9.palm.analytics.api;

import de.rwth.i9.palm.analytics.algorithm.corephrase.ICorePhrase;
import de.rwth.i9.palm.analytics.algorithm.cvalue.ICValue;
import de.rwth.i9.palm.analytics.algorithm.lda.ILda;
import de.rwth.i9.palm.analytics.opennlp.IOpenNLP;

public interface IAnalytics
{
	public ICorePhrase getCorePhraseAlgorithm();

	public ICValue getCValueAlgorithm();

	public ILda getLdaAlgorithm();

	public IOpenNLP getOpenNLPTool();

}
