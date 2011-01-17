package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseCustomerFirstBalance;



public class CustomerFirstBalance extends BaseCustomerFirstBalance {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CustomerFirstBalance () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CustomerFirstBalance (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CustomerFirstBalance (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Customers customer,
		java.util.Date firstBalanceDate,
		double amount,
		double exchangeRate) {

		super (
			id,
			currency,
			organization,
			customer,
			firstBalanceDate,
			amount,
			exchangeRate);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmount());
	}


}