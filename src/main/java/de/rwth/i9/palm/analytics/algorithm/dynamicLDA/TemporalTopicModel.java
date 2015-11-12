package de.rwth.i9.palm.analytics.algorithm.dynamicLDA;
// package com.google.profiles.arnimbleier;

import java.util.Stack;

import org.apache.commons.math3.special.Beta;

public class TemporalTopicModel
{


	/**
	 * Topics over Time
	 * 
	 * For more information on the algorithm see: X. Wang and A. McCallum.
	 * Topics over time: a non-Markov continuous-time model of topical trends.
	 * KDD 2006.
	 * 
	 * Notes on the implementations as at
	 * http://comments.gmane.org/gmane.comp.ai.mallet.devel/1669
	 * 
	 * 
	 * @author arnim.bleier@gmail.com
	 * @date 2014
	 * 
	 */

	public int[][] countTerm_Topic, countDoc_Topic;
	public int K, V;
	public int[] numOfWordsByTopic;
	public int[][] documents;
	public double alpha = 1.5, beta = 0.5;
	public int[][] z;
	public double[][] betaDistrByTopic;
	private double[] timeStamps;

	// get the number of topics by using mallet ( gettopic number)
	// get the length of documents by using mallet -> DocumentLengths
	// size of vocabulary by mallet -> getAlphabet.size()

	public void addInstances( int[][] documentsInput, double[] timeStamps, int sizeOfVocabulary, int numOfTopics )
	{
		documents = documentsInput;
		K = numOfTopics;
		V = sizeOfVocabulary;
		z = new int[documents.length][];
		this.timeStamps = timeStamps;
		countTerm_Topic = new int[sizeOfVocabulary][K];
		countDoc_Topic = new int[documents.length][K];
		numOfWordsByTopic = new int[K];
		for ( int m = 0; m < documents.length; m++ )
		{
			z[m] = new int[documents[m].length];
			for ( int n = 0; n < documents[m].length; n++ )
			{
				// random topic assignment for all tokens
				z[m][n] = (int) ( Math.random() * K );
				countTerm_Topic[documents[m][n]][z[m][n]]++;
				countDoc_Topic[m][z[m][n]]++;
				numOfWordsByTopic[z[m][n]]++;
			}
		}
		betaDistrByTopic = new double[K][];
		for ( int k = 0; k < K; k++ )
			// draw a timestamp t_di from beta distr.
			betaDistrByTopic[k] = estimateBetaParams( getTimeStamps( k ) );
	}

	public void run( int maxIter )
	{
		for ( int iter = 0; iter < maxIter; iter++ )
		{
			System.out.format( "iter: %04d\n", iter );
			double[] p;
			double pSum = 0.0, u;
			int topic, i, k;
			double[] tProb = new double[K];
			for ( int d = 0; d < documents.length; d++ )
			{
				for ( k = 0; k < K; k++ )
					tProb[k] = ( ( Math.pow( 1 - timeStamps[d], betaDistrByTopic[k][0] - 1 ) * Math.pow( timeStamps[d], betaDistrByTopic[k][1] - 1 ) ) / beta( betaDistrByTopic[k][0], betaDistrByTopic[k][1] ) );
				for ( i = 0; i < documents[d].length; i++ )
				{
					p = new double[K];
					pSum = 0.0;
					topic = z[d][i];
					countTerm_Topic[documents[d][i]][topic]--;
					countDoc_Topic[d][topic]--;
					numOfWordsByTopic[topic]--;

					for ( k = 0; k < K; k++ )
					{
						pSum += ( countDoc_Topic[d][k] + alpha ) * ( ( countTerm_Topic[documents[d][i]][k] + beta ) / ( numOfWordsByTopic[k] + V * beta ) ) * tProb[k];
						p[k] = pSum;
					}

					u = Math.random() * pSum;
					for ( topic = 0; topic < K - 1; topic++ )
						if ( u < p[topic] )
							break;

					countTerm_Topic[documents[d][i]][topic]++;
					countDoc_Topic[d][topic]++;
					numOfWordsByTopic[topic]++;
					z[d][i] = topic;
				}
			}
			for ( k = 0; k < K; k++ )
				betaDistrByTopic[k] = estimateBetaParams( getTimeStamps( k ) );
		}
	}

	public double[] getTimeStamps( int k )
	{
		Stack<Double> kStack = new Stack<Double>();
		for ( int m = 0; m < documents.length; m++ )
			for ( int n = 0; n < documents[m].length; n++ )
				if ( z[m][n] == k )
					kStack.push( timeStamps[m] );
		int stack_size = kStack.size();
		double[] _x = new double[stack_size];
		for ( int i = 0; i < stack_size; i++ )
			_x[i] = kStack.pop();
		return _x;
	}

	/*
	 * Method of Moments
	 *
	 * Modified as discussed in
	 * http://comments.gmane.org/gmane.comp.ai.mallet.devel/1669
	 */
	static double[] estimateBetaParams( double[] _x )
	{
		double[] shapes = new double[2];
		double sum = .0, _var = .0;
		int _n = _x.length;
		for ( double i : _x )
			sum = sum + i;
		double mean = sum / _n;
		for ( double i : _x )
			_var = Math.pow( mean - i, 2 );
		double variance = ( 1 / _n ) * ( _var / _n ) + 0.001;
		double commonTerm = Math.abs( ( ( mean * ( 1.0 - mean ) ) / variance ) - 1.0 );
		shapes[0] = mean * commonTerm;
		shapes[1] = ( 1 - mean ) * commonTerm;
		return shapes;
	}

	static double beta( double a, double b )
	{
		return Math.exp( Beta.logBeta( a, b ) );
	}

}
