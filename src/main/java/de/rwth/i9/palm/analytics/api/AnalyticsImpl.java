package de.rwth.i9.palm.analytics.api;

import org.springframework.beans.factory.annotation.Autowired;

import de.rwth.i9.palm.analytics.algorithm.cvalue.CValueImpl;
import de.rwth.i9.palm.analytics.algorithm.cvalue.ICValue;

public class AnalyticsImpl implements IAnalytics
{
	
	@Autowired( required = false )
	private ICValue iCValue;

	public ICValue getCValueAlgorithm()
	{
		if ( this.iCValue == null )
			this.iCValue = new CValueImpl();

		return this.iCValue;
	}

}
