package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseItemPosReportDetailReceivableCreditCard;



public class ItemPosReportDetailReceivableCreditCard extends BaseItemPosReportDetailReceivableCreditCard {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ItemPosReportDetailReceivableCreditCard () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ItemPosReportDetailReceivableCreditCard (long posOrderId) {
		super(posOrderId);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedPosOrderDate() {
	    return Formater.getFormatedDate(getPosOrderDate());
	}
	
	public String getFormatedPaidDate() {
	    return Formater.getFormatedDate(getPaidDate());
	}
	
	public String getFormatedCreditCardAdm() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getCreditCardAdm());
	}
	
	public String getFormatedTotalCostAfterDiscountAndTax() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getTotalCostAfterDiscountAndTax());
	}


}