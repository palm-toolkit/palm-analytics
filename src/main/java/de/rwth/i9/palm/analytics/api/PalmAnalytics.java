package de.rwth.i9.palm.analytics.api;

import de.rwth.i9.palm.analytics.algorithm.corephrase.CorePhrase;
import de.rwth.i9.palm.analytics.algorithm.cvalue.CValue;
import de.rwth.i9.palm.analytics.algorithm.lda.Lda;
import de.rwth.i9.palm.analytics.opennlp.OpenNLP;

public interface PalmAnalytics
{
	public CorePhrase getCorePhraseAlgorithm();

	public CValue getCValueAlgorithm();

	public Lda getLdaAlgorithm();

	public OpenNLP getOpenNLPTool();

}
