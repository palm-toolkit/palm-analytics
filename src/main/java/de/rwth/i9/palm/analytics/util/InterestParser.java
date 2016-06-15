package de.rwth.i9.palm.analytics.util;

import java.util.HashMap;
import java.util.Map;

public class InterestParser
{
	public static Map<String, Double> parseInterestString( String interestString )
	{
		Map<String, Double> interestWeights = new HashMap<String, Double>();

		String[] parts = interestString.split( "," );
		for ( String s : parts )
		{
			String elem = s.trim();
			if ( elem.indexOf( "=" ) != -1 )
			{
				String[] keyAndValue = elem.split( "=" );
				String key = keyAndValue[0];
				Double value = Double.parseDouble( keyAndValue[1] );
				interestWeights.put( key, value );
			}
		}
		return interestWeights;
	}

}
