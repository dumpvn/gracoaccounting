package com.mpe.financial.model;

import java.util.Iterator;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BasePurchaseRequest;



public class PurchaseRequest extends BasePurchaseRequest {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public PurchaseRequest () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public PurchaseRequest (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public PurchaseRequest (
		long id,
		com.mpe.financial.model.Organization organization,
		java.util.Date requestDate,
		java.lang.String number,
		java.lang.String status) {

		super (
			id,
			organization,
			requestDate,
			number,
			status);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public double getPurchaseRequestDetailAmount() {
		double x = 0;
		Iterator iterator = getPurchaseRequestDetails().iterator();
		while (iterator.hasNext()) {
			PurchaseRequestDetail purchaseOrderDetail = (PurchaseRequestDetail)iterator.next();
			if (purchaseOrderDetail.getDiscountProcent()>0) {
				x = x + (purchaseOrderDetail.getPrice() * purchaseOrderDetail.getQuantity() * purchaseOrderDetail.getExchangeRate())-(purchaseOrderDetail.getPrice() * purchaseOrderDetail.getQuantity() * purchaseOrderDetail.getExchangeRate() * purchaseOrderDetail.getDiscountProcent());
			} else if (purchaseOrderDetail.getDiscountAmount()>0) {
				x = x + (purchaseOrderDetail.getPrice() * purchaseOrderDetail.getQuantity() * purchaseOrderDetail.getExchangeRate()) - purchaseOrderDetail.getDiscountAmount();
			} else {
				x = x + (purchaseOrderDetail.getPrice() * purchaseOrderDetail.getQuantity() * purchaseOrderDetail.getExchangeRate());
			}
		}
		return x;
	}
	
	public double getAmountTax() {
		return (getPurchaseRequestDetailAmount()-getAmountDiscount()) * getTaxAmount()/100;
	}
	
	public String getFormatedAmountTax() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountTax());
	}
	
	public double getAmountDiscount() {
		return getPurchaseRequestDetailAmount() * getDiscountProcent()/100;
	}
	
	public double getAmountAfterDiscount() {
	    return getPurchaseRequestDetailAmount() - getAmountDiscount();
	}
	
	public String getFormatedAmountAfterDiscount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountAfterDiscount());
	}
	
	public String getFormatedAmountDiscount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountDiscount());
	}
	
	public String getFormatedPurchaseRequestDetailAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getPurchaseRequestDetailAmount());
	}
	
	public double getAmountAfterTaxAndDiscount() {
		return getPurchaseRequestDetailAmount() - getAmountDiscount() + getAmountTax();
	}
	
	public String getFormatedAmountAfterTaxAndDiscount() {
		return Formater.getFormatedOutput(getNumberOfDigit(), getAmountAfterTaxAndDiscount());
	}
	
	public String getFormatedCreateOn() {
	    return Formater.getFormatedDateTime(getCreateOn());
	}
	
	public String getFormatedChangeOn() {
	    return Formater.getFormatedDateTime(getChangeOn());
	}
	
	public String getFormatedRequestDate() {
	    return Formater.getFormatedDate(getRequestDate());
	}


}