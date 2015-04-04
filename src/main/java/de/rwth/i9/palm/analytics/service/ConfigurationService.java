package de.rwth.i9.palm.analytics.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author sigit
 *
 */
@Service
public class ConfigurationService
{
	private final Logger log = LoggerFactory.getLogger( ConfigurationService.class );

	// properties
	@Value( "${opennlp.sentence}" )
	private String opennlpSentence;

	@Value( "${opennlp.tokenizer}" )
	private String opennlpTokenizer;

	@Value( "${opennlp.pos}" )
	private String opennlpPos;

	@Value( "${opennlp.namefinder.format}" )
	private String opennlpNamefinderFormat;

	@Value( "${opennlp.parser}" )
	private String opennlpParser;

	@Value( "${opennlp.coref.dir}" )
	private String opennlpCorefDir;

	public String getopennlpSentence()
	{
		return opennlpSentence;
	}

	public void setopennlpSentence( String opennlpSentence )
	{
		this.opennlpSentence = opennlpSentence;
	}

	public String getopennlpTokenizer()
	{
		return opennlpTokenizer;
	}

	public void setopennlpTokenizer( String opennlpTokenizer )
	{
		this.opennlpTokenizer = opennlpTokenizer;
	}

	public String getopennlpPos()
	{
		return opennlpPos;
	}

	public void setopennlpPos( String opennlpPos )
	{
		this.opennlpPos = opennlpPos;
	}

	public String getopennlpNamefinderFormat()
	{
		return opennlpNamefinderFormat;
	}

	public void setopennlpNamefinderFormat( String opennlpNamefinderFormat )
	{
		this.opennlpNamefinderFormat = opennlpNamefinderFormat;
	}

	public String getopennlpParser()
	{
		return opennlpParser;
	}

	public void setopennlpParser( String opennlpParser )
	{
		this.opennlpParser = opennlpParser;
	}

	public String getopennlpCorefDir()
	{
		return opennlpCorefDir;
	}

	public void setopennlpCorefDir( String opennlpCorefDir )
	{
		this.opennlpCorefDir = opennlpCorefDir;
	}
}
