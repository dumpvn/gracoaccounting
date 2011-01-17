package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseItemPosReportRekapReceivableCreditCard;



public class ItemPosReportRekapReceivableCreditCard extends BaseItemPosReportRekapReceivableCreditCard {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemPosReportRekapReceivableCreditCard () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ItemPosReportRekapReceivableCreditCard (long locationId) {
		super(locationId);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedBeginningGross() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getBeginningGross());
	}
	
	public String getFormatedBeginningNetto() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getBeginningNetto());
	}
	
	public String getFormatedBeginningAdm() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getBeginningAdm());
	}
	
	public String getFormatedTransactionGross() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getTransactionGross());
	}
	
	public String getFormatedTransactionNetto() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getTransactionNetto());
	}
	
	public String getFormatedTransactionAdm() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getTransactionAdm());
	}
	
	public String getFormatedPaidGross() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getPaidGross());
	}
	
	public String getFormatedPaidNetto() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getPaidNetto());
	}
	
	public String getFormatedPaidAdm() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getPaidAdm());
	}


}