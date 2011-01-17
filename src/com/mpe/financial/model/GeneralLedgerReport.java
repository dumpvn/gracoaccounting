package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseGeneralLedgerReport;



public class GeneralLedgerReport extends BaseGeneralLedgerReport {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public GeneralLedgerReport () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public GeneralLedgerReport (long chartOfAccountId) {
		super(chartOfAccountId);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public double getFirstAmount() {
		return getFirstSetupAmount() + getPreviousAmount();
	}
	
	public String getFormatedFirstAmount() {
    return Formater.getFormatedOutput(getNumberOfDigit(), getFirstAmount());
	}
	
	public String getFormatedAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmount());
	}
	
	public double getEndAmount() {
		return getAmount() + getFirstAmount();
	}
	
	public String getFormatedEndAmount() {
    return Formater.getFormatedOutput(getNumberOfDigit(), getEndAmount());
	}


}