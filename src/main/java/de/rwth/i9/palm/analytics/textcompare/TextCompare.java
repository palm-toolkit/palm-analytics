package de.rwth.i9.palm.analytics.textcompare;

public interface TextCompare
{
	float getDistanceByLuceneLevenshteinDistance( String text1, String text2);
}
