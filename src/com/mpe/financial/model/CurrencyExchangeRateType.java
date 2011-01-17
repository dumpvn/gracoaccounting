package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseCurrencyExchangeRateType;



public class CurrencyExchangeRateType extends BaseCurrencyExchangeRateType {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CurrencyExchangeRateType () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CurrencyExchangeRateType (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CurrencyExchangeRateType (
		long id,
		java.lang.String name,
		java.lang.String type) {

		super (
			id,
			name,
			type);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}


}