package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseCreditCard;



public class CreditCard extends BaseCreditCard {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CreditCard () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CreditCard (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CreditCard (
		long id,
		com.mpe.financial.model.Bank bank,
		com.mpe.financial.model.ChartOfAccount chartOfAccount,
		com.mpe.financial.model.Organization organization,
		java.lang.String name,
		double discount) {

		super (
			id,
			bank,
			chartOfAccount,
			organization,
			name,
			discount);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedCreateOn() {
    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}


}