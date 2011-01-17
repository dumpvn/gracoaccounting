package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseCustomerReturDetail;



public class CustomerReturDetail extends BaseCustomerReturDetail {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CustomerReturDetail () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CustomerReturDetail (com.mpe.financial.model.CustomerReturDetailPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CustomerReturDetail (
		com.mpe.financial.model.CustomerReturDetailPK id,
		com.mpe.financial.model.Currency currency,
		com.mpe.financial.model.ItemUnit itemUnit,
		com.mpe.financial.model.Currency costPriceCurrency,
		double quantity,
		double price,
		double exchangeRate,
		double unitConversion,
		double costPrice,
		double costPriceExchangeRate) {

		super (
			id,
			currency,
			itemUnit,
			costPriceCurrency,
			quantity,
			price,
			exchangeRate,
			unitConversion,
			costPrice,
			costPriceExchangeRate);
	}

/*[CONSTRUCTOR MARKER END]*/
	
	public String getFormatedPrice() {
		return Formater.getFormatedOutput(getId().getCustomerRetur().getNumberOfDigit(), getPrice());
	}
	
	public double getPriceQuantity() {
		return Formater.getFormatedOutputResult(getId().getCustomerRetur().getNumberOfDigit(), getPrice() * getQuantity() * getExchangeRate() * getUnitConversion());
	}
	
	public double getCostPriceQuantity() {
		return Formater.getFormatedOutputResult(getId().getCustomerRetur().getNumberOfDigit(), getCostPrice() * getQuantity() * getCostPriceExchangeRate() * getUnitConversion());
	}
	
	public String getFormatedPriceQuantity() {
		return Formater.getFormatedOutput(getId().getCustomerRetur().getNumberOfDigit(), getPriceQuantityAfterDiscountTax());
	}

	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}
	
	public String getFormatedDiscountAmount() {
		return Formater.getFormatedOutput(getId().getCustomerRetur().getNumberOfDigit(), getDiscountAmount());
	}
	
	public double getPriceQuantityAfterDiscount() {
		return Formater.getFormatedOutputResult(getId().getCustomerRetur().getNumberOfDigit(), (getPriceQuantity() * (1 - (getDiscountProcent()/100))) - getDiscountAmount());
	}
	
	public double getPriceQuantityAfterDiscountTax() {
		return Formater.getFormatedOutputResult(getId().getCustomerRetur().getNumberOfDigit(), getPriceQuantityAfterDiscount() * (1 + (getTaxAmount()/100)));
	}

}