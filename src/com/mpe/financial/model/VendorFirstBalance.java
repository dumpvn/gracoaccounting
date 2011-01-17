package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseVendorFirstBalance;



public class VendorFirstBalance extends BaseVendorFirstBalance {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public VendorFirstBalance () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public VendorFirstBalance (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public VendorFirstBalance (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Vendors vendor,
		java.util.Date firstBalanceDate,
		double amount,
		double exchangeRate) {

		super (
			id,
			currency,
			organization,
			vendor,
			firstBalanceDate,
			amount,
			exchangeRate);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmount());
	}


}