package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseCurrencyExchange;



public class CurrencyExchange extends BaseCurrencyExchange {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CurrencyExchange () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CurrencyExchange (com.mpe.financial.model.CurrencyExchangePK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CurrencyExchange (
		com.mpe.financial.model.CurrencyExchangePK id,
		com.mpe.financial.model.Organization organization,
		double exchangeRate) {

		super (
			id,
			organization,
			exchangeRate);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getExchangeRate());
	}


}