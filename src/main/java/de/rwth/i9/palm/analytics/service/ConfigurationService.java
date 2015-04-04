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
	@Value( "${openNLP.sentence}" )
	private String openNLPSentence;

	@Value( "${openNLP.tokenizer}" )
	private String openNLPTokenizer;

	@Value( "${openNLP.pos}" )
	private String openNLPPos;

	@Value( "${openNLP.namefinder.format}" )
	private String openNLPNamefinderFormat;

	@Value( "${openNLP.parser}" )
	private String openNLPParser;

	@Value( "${openNLP.coref.dir}" )
	private String openNLPCorefDir;

	public String getOpenNLPSentence()
	{
		return openNLPSentence;
	}

	public void setOpenNLPSentence( String openNLPSentence )
	{
		this.openNLPSentence = openNLPSentence;
	}

	public String getOpenNLPTokenizer()
	{
		return openNLPTokenizer;
	}

	public void setOpenNLPTokenizer( String openNLPTokenizer )
	{
		this.openNLPTokenizer = openNLPTokenizer;
	}

	public String getOpenNLPPos()
	{
		return openNLPPos;
	}

	public void setOpenNLPPos( String openNLPPos )
	{
		this.openNLPPos = openNLPPos;
	}

	public String getOpenNLPNamefinderFormat()
	{
		return openNLPNamefinderFormat;
	}

	public void setOpenNLPNamefinderFormat( String openNLPNamefinderFormat )
	{
		this.openNLPNamefinderFormat = openNLPNamefinderFormat;
	}

	public String getOpenNLPParser()
	{
		return openNLPParser;
	}

	public void setOpenNLPParser( String openNLPParser )
	{
		this.openNLPParser = openNLPParser;
	}

	public String getOpenNLPCorefDir()
	{
		return openNLPCorefDir;
	}

	public void setOpenNLPCorefDir( String openNLPCorefDir )
	{
		this.openNLPCorefDir = openNLPCorefDir;
	}
}
