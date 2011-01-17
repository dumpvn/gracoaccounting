package com.mpe.financial.model;

import com.mpe.financial.model.base.BasePaidCreditCard;



public class PaidCreditCard extends BasePaidCreditCard {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public PaidCreditCard () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public PaidCreditCard (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public PaidCreditCard (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Bank bank,
		com.mpe.financial.model.Location location,
		com.mpe.financial.model.PosOrder posOrder,
		java.lang.String creditCardNumber,
		double adm,
		double amount) {

		super (
			id,
			currency,
			organization,
			bank,
			location,
			posOrder,
			creditCardNumber,
			adm,
			amount);
	}

/*[CONSTRUCTOR MARKER END]*/


}