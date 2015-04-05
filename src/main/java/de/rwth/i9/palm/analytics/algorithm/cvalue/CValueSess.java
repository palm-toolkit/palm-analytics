package de.rwth.i9.palm.analytics.algorithm.cvalue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CValueSess
{
	private HashMap<String, Candidate> candidates;

	public CValueSess()
	{
		candidates = new HashMap<String, Candidate>();
	}

	public void observe( String candStr )
	{
		Candidate candidate;
		if ( ( candidate = candidates.get( candStr ) ) == null )
		{
			candidate = new Candidate( candStr );
			candidates.put( candStr, candidate );
		}
		candidate.observe();
	}

	public Collection<Candidate> getCandidates()
	{
		return candidates.values();
	}

	public void calculate()
	{
		Collection<Candidate> cands = this.getCandidates();
		List<Candidate> candList = new ArrayList<Candidate>( cands );
		Collections.sort( candList, new CValueLenFreqComparator() );
		Collections.reverse( candList );
		Candidate hit;

		for ( Candidate cand : candList )
		{
			Collection<String> substrings = cand.getSubstrings();
			for ( String substr : substrings )
			{
				if ( ( hit = candidates.get( substr ) ) != null )
				{
					hit.observeNested();
					hit.incrementFreqNested( cand.getFrequency() );
				}
			}
		}
	}
}
