package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseReceivingDetail;



public class ReceivingDetail extends BaseReceivingDetail {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ReceivingDetail () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ReceivingDetail (com.mpe.financial.model.ReceivingDetailPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ReceivingDetail (
		com.mpe.financial.model.ReceivingDetailPK id,
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
		return Formater.getFormatedOutput(getId().getReceiving().getNumberOfDigit(), getPrice());
	}
	
	public String getFormatedDiscountAmount() {
		return Formater.getFormatedOutput(getId().getReceiving().getNumberOfDigit(), getDiscountAmount());
	}
	
	public double getPriceQuantity() {
		return Formater.getFormatedOutputResult(getId().getReceiving().getNumberOfDigit(), getPrice() * getQuantity() * getExchangeRate() * getUnitConversion());
	}
	
	public double getPriceQuantityAfterDiscount() {
		return Formater.getFormatedOutputResult(getId().getReceiving().getNumberOfDigit(), (getPriceQuantity() * (1 - (getDiscountProcent()/100))) - getDiscountAmount());
	}
	
	public double getPriceQuantityAfterDiscountTax() {
		return Formater.getFormatedOutputResult(getId().getReceiving().getNumberOfDigit(), getPriceQuantityAfterDiscount() * (1 + (getTaxAmount()/100)));
	}
	
	public String getFormatedPriceQuantity() {
		return Formater.getFormatedOutput(getId().getReceiving().getNumberOfDigit(), getPriceQuantity());
	}
	
	public String getFormatedPriceQuantityAfterDiscountTax() {
		return Formater.getFormatedOutput(getId().getReceiving().getNumberOfDigit(), getPriceQuantityAfterDiscountTax());
	}
	
	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}
	


}