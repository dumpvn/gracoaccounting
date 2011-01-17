package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseGeneralLedger;



public class GeneralLedger extends BaseGeneralLedger {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public GeneralLedger () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public GeneralLedger (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public GeneralLedger (
		long id,
		java.util.Date ledgerDate,
		double amount) {

		super (
			id,
			ledgerDate,
			amount);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmount());
	}


}