package de.rwth.i9.palm.analytics.algorithm.cvalue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of the CValue term recognition algorithm. See Frantzi et.
 * al 2000, <i> Automatic recognition of multi-word terms:. the C-value/NC-value
 * method</i>
 * 
 */
public class CValueAlgorithm implements CValue
{
	// logger
	static private final Logger logger = LoggerFactory.getLogger( CValueAlgorithm.class );

	// minimum frequencies of candidate term
	private int frequencyThreshold = 1;

	// list of terms
	private List<String> terms;

	// the map of term candidate
	private HashMap<String, TermCandidate> candidatesMap;

	// constructor
	public CValueAlgorithm()
	{
		candidatesMap = new HashMap<String, TermCandidate>();
	}

	// getter setter
	public int getFrequencyThreshold()
	{
		return frequencyThreshold;
	}

	public void setFrequencyThreshold( final int frequencyThreshold )
	{
		if ( frequencyThreshold < 0 )
			logger.info( "Frequency threshold must be larger than 0" );
		else
			this.frequencyThreshold = frequencyThreshold;
	}

	public List<String> getTerms()
	{
		return terms;
	}

	public void setTerms( List<String> terms )
	{
		this.terms = terms;
	}

	public CValueAlgorithm addTerms( final String term )
	{
		if ( this.terms == null )
			this.terms = new ArrayList<String>();
		this.terms.add( term );
		return this;
	}

	// build the termCandidate
	private void buildTermCandidate( String term )
	{
		TermCandidate candidate;
		if ( ( candidate = candidatesMap.get( term ) ) == null )
		{
			candidate = new TermCandidate( term );
			candidatesMap.put( term, candidate );
		}
		candidate.increaseCandidateFrequencies();
	}

	// list of term candidate
	public Collection<TermCandidate> getTermCandidates()
	{
		List<TermCandidate> candidates = new ArrayList<TermCandidate>( candidatesMap.values() );
		Collections.sort( candidates, new CValueComparator() );
		return candidates;
	}

	public void calculateCValue()
	{
		// first build the term candidates
		for ( String term : this.terms )
			buildTermCandidate( term );

		// check frequency threshold
		if ( this.frequencyThreshold > 1 )
			for ( Iterator<Map.Entry<String, TermCandidate>> candidatesMapEntry = candidatesMap.entrySet().iterator(); candidatesMapEntry.hasNext(); )
			{
				Map.Entry<String, TermCandidate> entry = candidatesMapEntry.next();
				if ( entry.getValue().getCandidateFrequencies() < frequencyThreshold )
				{
					candidatesMapEntry.remove();
				}
			}

		List<TermCandidate> candidates = new ArrayList<TermCandidate>( candidatesMap.values() );
		// sort based on term length and frequencies
		Collections.sort( candidates, new CValueTermLengthComparator() );

		// prepare the nested candidate
		TermCandidate nestedCandidate = null;

		// loop the ordered candidats
		for ( TermCandidate candidate : candidates )
		{
			// check nested candidate by checking the current candidate
			// substring with candidates hashmap
			for ( String candidateSubString : candidate.getTermCandidateSubString() )
			{
				// check whether any other candidate nested into current
				// candidate
				if ( ( nestedCandidate = candidatesMap.get( candidateSubString ) ) != null )
				{
					// if nested, do two things
					// increase the number of unique nester by one on nester
					// candidate
					nestedCandidate.increaseNumberOfUniqueNesterCandidates();
					// increase the nested candidate frequency by longer/current
					// candidate
					nestedCandidate.increaseCandidateNestedFrequencies( candidate.getCandidateFrequencies() );

				}
			}
		}


	}
}
