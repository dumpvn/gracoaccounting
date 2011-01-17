package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseRekapMutationPayableReport;



public class RekapMutationPayableReport extends BaseRekapMutationPayableReport {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public RekapMutationPayableReport () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public RekapMutationPayableReport (long vendorId) {
		super(vendorId);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedFirstBalance() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getFirstBalance());
	}
	
	public double getDebit() {
	    return getDebitPayment() + getDebitRetur();
	}
	
	public String getFormatedDebit() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getDebit());
	}
	
	public String getFormatedCredit() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getCredit());
	}
	
	public String getFormatedEndBalance() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getFirstBalance()-getDebit()+getCredit());
	}


}