package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseCurrencyExchangePK;

public class CurrencyExchangePK extends BaseCurrencyExchangePK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CurrencyExchangePK () {}
	
	public CurrencyExchangePK (
		com.mpe.financial.model.Currency toCurrency,
		com.mpe.financial.model.Currency fromCurrency,
		com.mpe.financial.model.CurrencyExchangeRateType currencyExchangeRateType,
		java.util.Date validFrom,
		java.util.Date validTo) {

		super (
			toCurrency,
			fromCurrency,
			currencyExchangeRateType,
			validFrom,
			validTo);
	}
/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedValidFrom() {
	    return Formater.getFormatedDate(getValidFrom());
	}

	public String getFormatedValidTo() {
	    return Formater.getFormatedDate(getValidTo());
	}


}