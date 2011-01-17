package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseReturToVendorDetail;



public class ReturToVendorDetail extends BaseReturToVendorDetail {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ReturToVendorDetail () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ReturToVendorDetail (com.mpe.financial.model.ReturToVendorDetailPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ReturToVendorDetail (
		com.mpe.financial.model.ReturToVendorDetailPK id,
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
		return Formater.getFormatedOutput(getId().getReturToVendor().getNumberOfDigit(), getPrice());
	}
	
	public String getFormatedDiscountAmount() {
		return Formater.getFormatedOutput(getId().getReturToVendor().getNumberOfDigit(), getDiscountAmount());
	}
	
	public double getPriceQuantity() {
		return Formater.getFormatedOutputResult(getId().getReturToVendor().getNumberOfDigit(), getPrice() * getQuantity() * getExchangeRate() * getUnitConversion());
	}
	
	public String getFormatedPriceQuantity() {
		return Formater.getFormatedOutput(getId().getReturToVendor().getNumberOfDigit(), getPriceQuantity());
	}
	
	public String getFormatedPriceQuantityAfterDiscountTax() {
		return Formater.getFormatedOutput(getId().getReturToVendor().getNumberOfDigit(), getPriceQuantityAfterDiscountTax());
	}
	
	public double getPriceQuantityAfterDiscount() {
		return Formater.getFormatedOutputResult(getId().getReturToVendor().getNumberOfDigit(), (getPriceQuantity() * (1 - (getDiscountProcent()/100))) - getDiscountAmount());
	}
	
	public double getPriceQuantityAfterDiscountTax() {
		return Formater.getFormatedOutputResult(getId().getReturToVendor().getNumberOfDigit(), getPriceQuantityAfterDiscount() * (1 + (getTaxAmount()/100)));
	}


}