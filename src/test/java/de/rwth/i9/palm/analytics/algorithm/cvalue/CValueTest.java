package de.rwth.i9.palm.analytics.algorithm.cvalue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class CValueTest
{

	@Test
	public void test()
	{

		List<String> phrases = new LinkedList<String>();

		for ( int i = 0; i < 5; i++ )
			phrases.add( "ADENOID CYSTIC BASAL CELL CARCINOMA" );

		for ( int i = 0; i < 11; i++ )
			phrases.add( "CYSTIC BASAL CELL CARCINOMA" );

		for ( int i = 0; i < 7; i++ )
			phrases.add( "ULCERATED BASAL CELL CARCINOMA" );

		for ( int i = 0; i < 5; i++ )
			phrases.add( "RECURRENT BASAL CELL CARCINOMA" );

		for ( int i = 0; i < 3; i++ )
			phrases.add( "CIRCUMSCRIBED BASAL CELL CARCINOMA" );

		for ( int i = 0; i < 984; i++ )
			phrases.add( "BASAL CELL CARCINOMA" );
		
		phrases.add( "CELL CARCINOMA" );

		CValueSess cvals;
		ArrayList<Candidate> candList;

		cvals = new CValueSess();

		for ( String phrase : phrases )
		{

			cvals.observe( phrase );
		}

		cvals.calculate();

		candList = new ArrayList<Candidate>( cvals.getCandidates() );
		Collections.sort( candList, new CValueComparatorOri() );

		System.out.println( "Original" );

		for ( Candidate cand : candList )
		{
			String resultLine = cand.getLength() + " " + cand.getFrequency() + "  " + cand.getNesterCount() + " " + cand.getFreqNested() + " " + cand.getString() + " " + cand.getCValue();
			System.out.println( resultLine );
		}

		System.out.println();
	}

	@Test
	@Ignore
	public void testCandidateTermSubstring()
	{
		TermCandidate tc = new TermCandidate( "RECURRENT BASAL CELL CARCINOMA" );
		List<String> tcSubTerm = tc.getTermCandidateSubString();

		for ( String term : tcSubTerm )
			System.out.println( term );
	}

	@Test
	public void testCValueCalculation()
	{
		CValueAlgorithm cva = new CValueAlgorithm();

		List<String> terms = new LinkedList<String>();

		for ( int i = 0; i < 5; i++ )
			terms.add( "ADENOID CYSTIC BASAL CELL CARCINOMA" );

		for ( int i = 0; i < 11; i++ )
			terms.add( "CYSTIC BASAL CELL CARCINOMA" );

		for ( int i = 0; i < 7; i++ )
			terms.add( "ULCERATED BASAL CELL CARCINOMA" );

		for ( int i = 0; i < 5; i++ )
			terms.add( "RECURRENT BASAL CELL CARCINOMA" );

		for ( int i = 0; i < 3; i++ )
			terms.add( "CIRCUMSCRIBED BASAL CELL CARCINOMA" );

		for ( int i = 0; i < 984; i++ )
			terms.add( "BASAL CELL CARCINOMA" );

		terms.add( "CELL CARCINOMA" );

		cva.setTerms( terms );
		cva.setFrequencyThreshold( 2 );
		cva.calculateCValue();

		for ( TermCandidate cand : cva.getTermCandidates() )
		{
			String resultLine = cand.getCandidateLength() + " " + cand.getCandidateFrequencies() + "  " + cand.getUniqueNesterCandidates() + " " + cand.getCandidateNestedFrequencies() + " " + cand.getCandidateTerm() + " " + cand.getCValue();
			System.out.println( resultLine );
		}
		System.out.println();
	}

}
