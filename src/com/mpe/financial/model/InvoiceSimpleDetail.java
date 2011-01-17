package com.mpe.financial.model;

import com.mpe.common.Formater;
import com.mpe.financial.model.base.BaseInvoiceSimpleDetail;



public class InvoiceSimpleDetail extends BaseInvoiceSimpleDetail {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public InvoiceSimpleDetail () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public InvoiceSimpleDetail (com.mpe.financial.model.InvoiceSimpleDetailPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public InvoiceSimpleDetail (
		com.mpe.financial.model.InvoiceSimpleDetailPK id,
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
		return Formater.getFormatedOutput(getId().getInvoiceSimple().getNumberOfDigit(), getPrice());
	}
	
	public double getPriceQuantity() {
		return Formater.getFormatedOutputResult(getId().getInvoiceSimple().getNumberOfDigit(), getPrice() * getQuantity() * getExchangeRate() * getUnitConversion());
	}
	
	public double getCostPriceQuantity() {
		return Formater.getFormatedOutputResult(getId().getInvoiceSimple().getNumberOfDigit(), getCostPrice() * getQuantity() * getCostPriceExchangeRate() * getUnitConversion());
	}
	
	public String getFormatedPriceQuantity() {
		return Formater.getFormatedOutput(getId().getInvoiceSimple().getNumberOfDigit(), getPriceQuantity());
	}
	
	public String getFormatedPriceQuantityAfterDiscountTax() {
		return Formater.getFormatedOutput(getId().getInvoiceSimple().getNumberOfDigit(), getPriceQuantityAfterDiscountTax());
	}

	public String getFormatedExchangeRate() {
		return Formater.getFormatedOutput(getExchangeRate());
	}
	
	public String getFormatedDiscountAmount() {
		return Formater.getFormatedOutput(getId().getInvoiceSimple().getNumberOfDigit(), getDiscountAmount());
	}
	
	public double getPriceQuantityAfterDiscount() {
		return Formater.getFormatedOutputResult(getId().getInvoiceSimple().getNumberOfDigit(), (getPriceQuantity() * (1 - (getDiscountProcent()/100))) - getDiscountAmount());
	}
	
	public double getPriceQuantityAfterDiscountTax() {
		return Formater.getFormatedOutputResult(getId().getInvoiceSimple().getNumberOfDigit(), getPriceQuantityAfterDiscount() * (1 + (getTaxAmount()/100)));
	}

}