package com.mpe.financial.model;

import java.util.Iterator;
import java.util.LinkedList;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseReceiving;



public class Receiving extends BaseReceiving {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public Receiving () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public Receiving (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public Receiving (
		long id,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.PurchaseOrder purchaseOrder,
		com.mpe.financial.model.Vendors vendor,
		java.util.Date receivingDate,
		java.lang.String number,
		java.lang.String status,
		java.lang.String vendorBillStatus) {

		super (
			id,
			organization,
			purchaseOrder,
			vendor,
			receivingDate,
			number,
			status,
			vendorBillStatus);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedReceivingDate() {
	    return Formater.getFormatedDate(getReceivingDate());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}
	
	public double getReceivingDetailAmount() {
		double x = 0;
		Iterator iterator = getReceivingDetails()!=null?getReceivingDetails().iterator():new LinkedList().iterator();
		while (iterator.hasNext()) {
			ReceivingDetail receivingDetail = (ReceivingDetail)iterator.next();
			x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), receivingDetail.getPriceQuantityAfterDiscountTax());
		}
		return x;
	}
	
	public double getAmountTax() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getPurchaseOrder()!=null?((getReceivingDetailAmount()-getAmountDiscount()) * (getPurchaseOrder().getTaxAmount()/100)):0);
	}
	
	public String getFormatedAmountTax() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountTax());
	}
	
	public double getAmountDiscount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getPurchaseOrder()!=null?(getReceivingDetailAmount() * getPurchaseOrder().getDiscountProcent()/100):0);
	}
	
	public double getAmountAfterDiscount() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getReceivingDetailAmount() - getAmountDiscount());
	}
	
	public String getFormatedAmountAfterDiscount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountAfterDiscount());
	}
	
	public String getFormatedAmountDiscount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountDiscount());
	}
	
	public String getFormatedReceivingDetailAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getReceivingDetailAmount());
	}
	
	public double getAmountAfterTaxAndDiscount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getReceivingDetailAmount() - getAmountDiscount() + getAmountTax());
	}
	
	public String getFormatedAmountAfterTaxAndDiscount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getAmountAfterTaxAndDiscount());
	}


}