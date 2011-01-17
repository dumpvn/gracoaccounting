package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseRekapMutationReceivableReport;



public class RekapMutationReceivableReport extends BaseRekapMutationReceivableReport {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public RekapMutationReceivableReport () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public RekapMutationReceivableReport (long customerId) {
		super(customerId);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedFirstBalance() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getFirstBalance());
	}
	
	public double getCredit() {
	    return getCreditPayment() + getCreditRetur();
	}
	
	public String getFormatedDebit() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getDebit()+getDebitPolos()+getDebitSimple());
	}
	
	public String getFormatedCredit() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getCredit());
	}
	
	public String getFormatedEndBalance() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getFirstBalance()+getDebit()+getDebitPolos()+getDebitSimple()-getCredit());
	}


}