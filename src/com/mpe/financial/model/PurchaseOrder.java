package com.mpe.financial.model;

import java.util.Iterator;
import java.util.LinkedList;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BasePurchaseOrder;



public class PurchaseOrder extends BasePurchaseOrder {
	private static final long serialVersionUID = 1L;
	
/*[CONSTRUCTOR MARKER BEGIN]*/
	public PurchaseOrder () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public PurchaseOrder (long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public PurchaseOrder (
		long id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.Organization organization,
		com.mpe.financial.model.Vendors vendor,
		java.util.Date purchaseDate,
		java.lang.String number,
		java.lang.String status,
		java.lang.String receivingStatus,
		java.lang.String paymentToVendorStatus) {

		super (
			id,
			currency,
			organization,
			vendor,
			purchaseDate,
			number,
			status,
			receivingStatus,
			paymentToVendorStatus);
	}
	
	

/*[CONSTRUCTOR MARKER END]*/
	
	public double getPurchaseOrderDetailAmount() {
		double x = 0;
		Iterator iterator = getPurchaseOrderDetails()!=null?getPurchaseOrderDetails().iterator():new LinkedList().iterator();
		while (iterator.hasNext()) {
			PurchaseOrderDetail purchaseOrderDetail = (PurchaseOrderDetail)iterator.next();
			x = x + Formater.getFormatedOutputResult(getNumberOfDigit(), purchaseOrderDetail.getPriceQuantityAfterDiscountTax());
		}
		return x;
	}
	
	public double getAmountTax() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), (getPurchaseOrderDetailAmount()-getAmountDiscount()) * getTaxAmount()/100);
	}
	
	public String getFormatedAmountTax() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountTax());
	}
	
	public double getAmountDiscount() {
		return Formater.getFormatedOutputResult(getNumberOfDigit(), getPurchaseOrderDetailAmount() * getDiscountProcent()/100);
	}
	
	public double getAmountAfterDiscount() {
	    return Formater.getFormatedOutputResult(getNumberOfDigit(), getPurchaseOrderDetailAmount() - getAmountDiscount());
	}
	
	public String getFormatedAmountAfterDiscount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountAfterDiscount());
	}
	
	public String getFormatedAmountDiscount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getAmountDiscount());
	}
	
	public String getFormatedPurchaseOrderDetailAmount() {
	    return Formater.getFormatedOutput(getNumberOfDigit(), getPurchaseOrderDetailAmount());
	}
	
	public double getAmountAfterTaxAndDiscount() {
		return getPurchaseOrderDetailAmount() - getAmountDiscount() + getAmountTax();
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
	
	public String getFormatedPurchaseDate() {
	    return Formater.getFormatedDate(getPurchaseDate());
	}
	
	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}

}