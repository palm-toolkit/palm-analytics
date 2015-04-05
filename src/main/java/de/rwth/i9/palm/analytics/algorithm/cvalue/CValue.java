package de.rwth.i9.palm.analytics.algorithm.cvalue;

/**
 * An implementation of the CValue term recognition algorithm. See Frantzi et.
 * al 2000, <i> Automatic recognition of multi-word terms:. the C-value/NC-value
 * method</i>
 * 
 */

public interface CValue
{
	/**
	 * Set the frequency threshold of candidate terms. Any terms that has
	 * frequency below this threshold will be ignored
	 * 
	 * @param frequencyThreshold
	 *            an integer value of threshold
	 */
	public void setFrequencyThreshold( int frequencyThreshold );

	/**
	 * Add a term into list of term phrases<br/>
	 * Terms are extracted using <i>part of speech Penn The Bank</i>
	 * and usually in these following form:<br/>
	 * 	<i>1. Noun + Noun,</i></br>
	 *	<i>2.(Adj|Noun) + Noun,</i></br>
	 *	<i>3.((Adj|Noun) +|((Adj|Noun)* (NounPrep)?)(Adj|Noun)*)Noun</i></br>
	 * 
	 * @param term
	 *            the term phrase
	 * @return the CValueAlgorithm class itself
	 */
	public CValueAlgorithm addTerms( final String term );
	
	
}
