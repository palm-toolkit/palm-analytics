package de.rwth.i9.palm.analytics.textcompare;

import org.apache.lucene.search.spell.LevensteinDistance;

public class TextCompareImpl implements TextCompare
{

	@Override
	public float getDistanceByLuceneLevenshteinDistance( String text1, String text2 )
	{
		LevensteinDistance ld = new LevensteinDistance();
		return ld.getDistance( text1, text2 );
	}

}
