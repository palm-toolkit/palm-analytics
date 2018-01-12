package de.rwth.i9.palm.analytics.algorithm.apriori;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import weka.associations.Apriori;
import weka.core.Instances;

@Component
@Transactional
public class WekaApriori
{
	Apriori apriori = new Apriori();

	public Apriori run( Instances data ) throws Exception
	{
		apriori.setClassIndex( data.size() - 1 );
		apriori.setNumRules( 1000 );
		apriori.buildAssociations( data );
		return apriori;
	}

}
