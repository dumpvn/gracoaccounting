package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BasePurchaseOrderDetail;



public class PurchaseOrderDetail extends BasePurchaseOrderDetail {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public PurchaseOrderDetail () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public PurchaseOrderDetail (com.mpe.financial.model.PurchaseOrderDetailPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public PurchaseOrderDetail (
		com.mpe.financial.model.PurchaseOrderDetailPK id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.ItemUnit itemUnit,
		double quantity,
		double price,
		double exchangeRate,
		double unitConversion) {

		super (
			id,
			currency,
			itemUnit,
			quantity,
			price,
			exchangeRate,
			unitConversion);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedPrice() {
		return Formater.getFormatedOutput(getId().getPurchaseOrder().getNumberOfDigit(), getPrice());
	}
	
	public String getFormatedDiscountAmount() {
		return Formater.getFormatedOutput(getId().getPurchaseOrder().getNumberOfDigit(), getDiscountAmount());
	}
	
	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}
	
	public double getPriceQuantity() {
		return Formater.getFormatedOutputResult(getId().getPurchaseOrder().getNumberOfDigit(), getPrice() * getQuantity() * getExchangeRate() * getUnitConversion());
	}
	
	public String getFormatedPriceQuantity() {
		return Formater.getFormatedOutput(getId().getPurchaseOrder().getNumberOfDigit(), getPriceQuantity());
	}
	
	public String getFormatedPriceQuantityAfterDiscountTax() {
		return Formater.getFormatedOutput(getId().getPurchaseOrder().getNumberOfDigit(), getPriceQuantityAfterDiscountTax());
	}
	
	public double getPriceQuantityAfterDiscount() {
		return Formater.getFormatedOutputResult(getId().getPurchaseOrder().getNumberOfDigit(), (getPrice() * getQuantity() * getExchangeRate() * (1 - (getDiscountProcent()/100))) - getDiscountAmount());
	}
	
	public double getPriceQuantityAfterDiscountTax() {
		return Formater.getFormatedOutputResult(getId().getPurchaseOrder().getNumberOfDigit(), getPriceQuantityAfterDiscount() * (1 + (getTaxAmount()/100)));
	}

	public double getOutstandingQuantity() {
	    return getQuantity() - getReceiveQuantity() + getReturQuantity();
	}

}