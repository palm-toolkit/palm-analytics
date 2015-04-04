package de.rwth.i9.palm.analytics.opennlp;

public interface IOpenNLP
{
	/**
	 * Break the given content into sentences.
	 * <p>
	 * The sentence detector is lazily initialized on first use.
	 * </p>
	 * 
	 * @param content
	 *            the content to break into sentences
	 * @return the detected sentences
	 */
	public String[] detectSentences( final String content );
}
